package react.pw.carly.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import react.pw.carly.dao.CarOrderRepository;
import react.pw.carly.dao.CarRepository;
import react.pw.carly.models.Car;
import react.pw.carly.models.CarOrder;
import react.pw.carly.models.FullOrder;
import react.pw.carly.services.CarImageService;
import react.pw.carly.services.CarOrderService;
import react.pw.carly.services.CarService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

@RestController
@RequestMapping(path = "V1/order")
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final CarRepository repository;
    private final CarService carService;
    private final CarOrderService orderService;
    private final CarOrderRepository orderRepository;

    @Autowired
    public OrderController(CarRepository repository, CarService carService, CarOrderService orderService,CarOrderRepository orderRepository) {
        this.repository = repository;
        this.carService = carService;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

//    @Autowired
//    public void setCompanyLogoService(LogoService companyLogoService) {
//        this.companyLogoService = companyLogoService;
//    }



    private void logHeaders(@RequestHeader HttpHeaders headers) {
        logger.info("Controller request headers {}",
                headers.entrySet()
                        .stream()
                        .map(entry -> String.format("%s->[%s]", entry.getKey(), String.join(",", entry.getValue())))
                        .collect(joining(","))
        );
    }


    @GetMapping(path = "")
    public ResponseEntity<Collection<FullOrder>> getAllOrders(@RequestHeader HttpHeaders headers,
                                                           @RequestParam(required=false,defaultValue = "0" ) Integer pageNum,
                                                           @RequestParam(required=false,defaultValue = "10" ) Integer maxNum,
                                                           @RequestParam(required=false) String keyword) {
        logHeaders(headers);
        Pageable pageable = PageRequest.of(pageNum*maxNum, maxNum);

        List<FullOrder> result = orderRepository.findAllByInputString(keyword,pageable);
        return ResponseEntity.ok(result);

    }
//
//    @GetMapping(path = "")
//    public ResponseEntity<Collection<Company>> getAllCompanies(@RequestHeader HttpHeaders headers) {
//        logHeaders(headers);
//        if (securityService.isAuthorized(headers)) {
//            return ResponseEntity.ok(repository.findAll());
//        }
//        throw new UnauthorizedException("Request is unauthorized");
//    }
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
