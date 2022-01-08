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
import react.pw.carly.exceptions.ExceptionDetails;
import react.pw.carly.models.Car;
import react.pw.carly.models.CarImage;
import react.pw.carly.models.CarOrder;
import react.pw.carly.services.CarImageService;
import react.pw.carly.services.CarService;
import react.pw.carly.web.UploadFileResponse;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.util.stream.Collectors.joining;

@RestController
@RequestMapping(path = "V1/cars")
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
        Date date = new Date(System.currentTimeMillis());
        if (car.getCarId() == null){
            car.setUpdateTime(date);
            car.setCreateTime(date);
            Car savedCar = repository.save(car);
            return ResponseEntity.ok().body(savedCar);
        }else{
            Optional<Car> carDB= repository.findById(car.getCarId());
            if (!carDB.isEmpty()){
                Car dbocar= carDB.get();
                dbocar.setCarModel(car.getCarModel());
                dbocar.setCarName(car.getCarName());
                dbocar.setActive(car.isActive());
                dbocar.setDescription(car.getDescription());
                dbocar.setImages(car.getImages());
                dbocar.setLocation(car.getLocation());
                dbocar.setPrice(car.getPrice());
                dbocar.setStartDateTime(car.getStartDateTime());
                dbocar.setEndDateTime(car.getEndDateTime());
                dbocar.setUpdateTime(date);
                Car savedCar = repository.save(dbocar);
                return ResponseEntity.ok().body(savedCar);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(car);

        }


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


    @PostMapping(path = "/{carId}/orders")
    public ResponseEntity<?> createOrder(@RequestHeader HttpHeaders headers, @PathVariable Long carId, @RequestBody CarOrder carOrder) {
        logHeaders(headers);
        Optional<Car> car = repository.findById(carId);
        if (!car.isEmpty()){
            if (car.get().getStartDateTime().isAfter(carOrder.getStartDate()) || car.get().getEndDateTime().isBefore(carOrder.getEndDate())  ){
                return new ResponseEntity<>(new ExceptionDetails(HttpStatus.CONFLICT,
                        String.format("car [%d] is not active during this timeslot", car.get().getCarId())), HttpStatus.CONFLICT);
            }
            String[] status = {"0","1"};
            List<CarOrder> orders = orderRepository.findAllByStartDateLessThanEqualAndEndDateGreaterThanEqualAndCarIsAndStatusIn(
                    carOrder.getEndDate(), carOrder.getStartDate(),car.get(),status);
            logger.info("-----"+orders.size());
            if (orders.size() > 0){
                return new ResponseEntity<>(new ExceptionDetails(HttpStatus.CONFLICT, "timeslot occupied"), HttpStatus.CONFLICT);
            }
            Date date = new Date(System.currentTimeMillis());
            carOrder.setCreateTime(date);
            carOrder.setUpdateTime(date);
            carOrder.setCar(car.get());
            CarOrder carOrderSaved = orderRepository.save(carOrder);
            return ResponseEntity.ok(carOrderSaved);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CarOrder.EMPTY);
    }

    @PatchMapping(path = "/{carId}/orders")
    public ResponseEntity<CarOrder> updateOrder(@RequestHeader HttpHeaders headers, @PathVariable Long carId, @RequestBody CarOrder carOrder) {
        logHeaders(headers);
        Date date = new Date(System.currentTimeMillis());
        Optional<CarOrder> dbOrder = orderRepository.findById(carOrder.getOrderId());
        if (!dbOrder.isEmpty()){
            CarOrder order = dbOrder.get();
            order.setUpdateTime(date);
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


    @GetMapping(path = "")
    public ResponseEntity<Collection<Car>> getAllCars(@RequestHeader HttpHeaders headers,
                                                               @RequestParam(required=false,defaultValue = "0" ) Integer pageNum,
                                                               @RequestParam(required=false,defaultValue = "10" ) Integer maxNum,
                                                                @RequestParam(required=false) String model,
                                                                           @RequestParam(required=false) String startDate,
                                                                            @RequestParam(required=false) String endDate,
                                                                            @RequestParam(required=false) String description,
                                                                           @RequestParam(required=false) String location,
                                                                           @RequestParam(required=false) String carName,
                                                                            @RequestParam(required=false,defaultValue = "true") Boolean isActive,
                                                                           @RequestParam(required=false) String keyword) {
        logHeaders(headers);
        Pageable pageable = PageRequest.of(pageNum*maxNum, maxNum);
        if (!StringUtils.isEmpty(keyword)){
                List<Car> result = repository.
                    findAllByKeyWords(
                            keyword,isActive,pageable);
            return ResponseEntity.ok(result);
        }else{
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            LocalDateTime startDateD = StringUtils.isEmpty(startDate)? null: LocalDateTime.parse(startDate, formatter);
            LocalDateTime endDateD = StringUtils.isEmpty(endDate)? null: LocalDateTime.parse(endDate, formatter);
            List<Car> result = repository.findAllByInputString(carName, location,description,model,startDateD,endDateD,isActive,pageable);
            return ResponseEntity.ok(result);
        }
    }


}
