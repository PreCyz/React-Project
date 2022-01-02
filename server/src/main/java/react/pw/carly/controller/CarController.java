package react.pw.carly.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import react.pw.carly.dao.CarOrderRepository;
import react.pw.carly.dao.CarRepository;
import react.pw.carly.models.Car;
import react.pw.carly.models.CarImage;
import react.pw.carly.models.CarOrder;
import react.pw.carly.services.CarImageService;
import react.pw.carly.services.CarService;
import react.pw.carly.web.UploadFileResponse;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.util.stream.Collectors.joining;

@RestController
@RequestMapping(path = "V1/car")
public class CarController {

    private final Logger logger = LoggerFactory.getLogger(CarController.class);

    private final CarRepository repository;
    private final CarService carService;
    private final CarImageService carImageService;
    private final CarOrderRepository orderRepository;

    @Autowired
    public CarController(CarRepository repository, CarService carService,CarImageService carImageService,CarOrderRepository orderRepository) {
        this.repository = repository;
        this.carService = carService;
        this.carImageService = carImageService;
        this.orderRepository = orderRepository;
    }


    @PutMapping(path = "")
    public ResponseEntity<Car> createCar(@RequestHeader HttpHeaders headers, @RequestBody Car car) {
        logHeaders(headers);
        Car savedCar = repository.save(car);
        return ResponseEntity.ok().body(savedCar);

    }

    private void logHeaders(@RequestHeader HttpHeaders headers) {
        logger.info("Controller request headers {}",
                headers.entrySet()
                        .stream()
                        .map(entry -> String.format("%s->[%s]", entry.getKey(), String.join(",", entry.getValue())))
                        .collect(joining(","))
        );
    }


    @GetMapping(path = "/{carId}")
    public ResponseEntity<Car> getCompany(@RequestHeader HttpHeaders headers,
                                              @PathVariable Long carId) {
        logHeaders(headers);
        Optional<Car> car = repository.findById(carId);
        if (!car.isEmpty()){
            return ResponseEntity.ok(car.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Car.EMPTY);
    }


    @PostMapping(path = "/{carId}/order")
    public ResponseEntity<CarOrder> createOrder(@RequestHeader HttpHeaders headers, @PathVariable Long carId, @RequestBody CarOrder carOrder) {
        logHeaders(headers);
        Optional<Car> car = repository.findById(carId);
        if (!car.isEmpty()){
            List<CarOrder> orders = orderRepository.findAllByStartDateLessThanEqualAndEndDateGreaterThanEqualAndCarIs(
                    carOrder.getEndDate(), carOrder.getStartDate(),car.get());
            logger.info("-----"+orders.size());
            if (orders.size() > 0){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(CarOrder.EMPTY);
            }
            carOrder.setCar(car.get());
            CarOrder carOrderSaved = orderRepository.save(carOrder);
            return ResponseEntity.ok(carOrderSaved);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CarOrder.EMPTY);
    }

    @PatchMapping(path = "/{carId}/order")
    public ResponseEntity<CarOrder> updateOrder(@RequestHeader HttpHeaders headers, @PathVariable Long carId, @RequestBody CarOrder carOrder) {
        logHeaders(headers);

        Optional<CarOrder> dbOrder = orderRepository.findById(carOrder.getOrderId());
        if (!dbOrder.isEmpty()){
            CarOrder order = dbOrder.get();
            if (!StringUtils.isEmpty(carOrder.getStatus()) ){
                order.setStatus(carOrder.getStatus());
            }
            if (!StringUtils.isEmpty(carOrder.getBooklyId()) ){
                order.setBooklyId(carOrder.getBooklyId());
            }
            CarOrder carOrderSaved = orderRepository.save(order);
            return ResponseEntity.ok(carOrderSaved);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CarOrder.EMPTY);




    }
//
private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @GetMapping(path = "")
    public ResponseEntity<Collection<Car>> getAllCompanies(@RequestHeader HttpHeaders headers,
                                                               @RequestParam(required=false,defaultValue = "0" ) Integer pageNum,
                                                               @RequestParam(required=false,defaultValue = "10" ) Integer maxNum,
                                                                @RequestParam(required=false) String model,
                                                                           @RequestParam(required=false) String startDate,
                                                                            @RequestParam(required=false) String endDate,
                                                                            @RequestParam(required=false) String description,
                                                                           @RequestParam(required=false) String location,
                                                                           @RequestParam(required=false) String carName,
                                                                           @RequestParam(required=false) String keyword) {
        logHeaders(headers);
        Pageable pageable = PageRequest.of(pageNum*maxNum, maxNum);
        if (!StringUtils.isEmpty(keyword)){
            List<Car> result = repository.
                    findAllByCarNameContainsOrLocationContainsOrDescriptionContainsOrCarModelContains(
                            keyword,keyword,keyword,keyword,pageable);
            return ResponseEntity.ok(result);
        }else{
            LocalDate startDateD = StringUtils.isEmpty(startDate)? null: LocalDate.parse(startDate, formatter);
            LocalDate endDateD = StringUtils.isEmpty(endDate)? null: LocalDate.parse(endDate, formatter);
            List<Car> result = repository.findAllByInputString(carName, location,description,model,startDateD,endDateD,pageable);
            return ResponseEntity.ok(result);
        }
    }
//
//    @PutMapping(path = "/{companyId}")
//    public ResponseEntity<Company> updateCompany(@RequestHeader HttpHeaders headers,
//                                                 @PathVariable Long companyId,
//                                                 @RequestBody Company updatedCompany) {
//        logHeaders(headers);
//        Company result;
//        if (securityService.isAuthorized(headers)) {
//            result = companyService.updateCompany(companyId, updatedCompany);
//            if (Company.EMPTY.equals(result)) {
//                return ResponseEntity.badRequest().body(updatedCompany);
//            }
//            return ResponseEntity.ok(result);
//        }
//        throw new UnauthorizedException("Request is unauthorized");
//    }
//
//    @DeleteMapping(path = "/{companyId}")
//    public ResponseEntity<String> updateCompany(@RequestHeader HttpHeaders headers, @PathVariable Long companyId) {
//        logHeaders(headers);
//        if (securityService.isAuthorized(headers)) {
//            boolean deleted = companyService.deleteCompany(companyId);
//            if (!deleted) {
//                return ResponseEntity.badRequest().body(String.format("Company with id %s does not exists.", companyId));
//            }
//            return ResponseEntity.ok(String.format("Company with id %s deleted.", companyId));
//        }
//        throw new UnauthorizedException("Unauthorized access to resources.");
//    }
//

//
//    @GetMapping(value = "/{companyId}/logo", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    public @ResponseBody byte[] getLog(@RequestHeader HttpHeaders headers, @PathVariable Long companyId) {
//        logHeaders(headers);
//        if (securityService.isAuthorized(headers)) {
//            CompanyLogo companyLogo = companyLogoService.getCompanyLogo(companyId);
//            return companyLogo.getData();
//        }
//
//        throw new UnauthorizedException("Unauthorized access to resources.");
//    }
//
//    @GetMapping(value = "/{companyId}/logo2")
//    public ResponseEntity<Resource> getLogo2(@RequestHeader HttpHeaders headers, @PathVariable Long companyId) {
//        logHeaders(headers);
//        if (securityService.isAuthorized(headers)) {
//            CompanyLogo companyLogo = companyLogoService.getCompanyLogo(companyId);
//
//            return ResponseEntity.ok()
//                    .contentType(MediaType.parseMediaType(companyLogo.getFileType()))
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + companyLogo.getFileName() + "\"")
//                    .body(new ByteArrayResource(companyLogo.getData()));
//        }
//        throw new UnauthorizedException("Unauthorized access to resources.");
//    }
//
//    @DeleteMapping(value = "/{companyId}/logo")
//    public ResponseEntity<String> removeLogo(@RequestHeader HttpHeaders headers, @PathVariable String companyId) {
//        logHeaders(headers);
//        if (securityService.isAuthorized(headers)) {
//            companyLogoService.deleteCompanyLogo(Long.parseLong(companyId));
//            return ResponseEntity.ok().body(String.format("Logo for the company with id %s removed.", companyId));
//        }
//        throw new UnauthorizedException("Unauthorized access to resources.");
//    }

}
