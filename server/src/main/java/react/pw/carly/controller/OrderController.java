package react.pw.carly.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import react.pw.carly.dao.CarOrderRepository;
import react.pw.carly.dao.CarRepository;
import react.pw.carly.vo.FullOrder;
import react.pw.carly.services.CarOrderService;
import react.pw.carly.services.CarService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

@RestController
@RequestMapping(path = "V1/orders")
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
        Pageable pageable = PageRequest.of(pageNum, maxNum);

        List<FullOrder> result = orderRepository.findAllByInputString(keyword,pageable);
        return ResponseEntity.ok(result);

    }

    @GetMapping(path = "/{orderId}")
    public ResponseEntity<FullOrder> getOrder(@RequestHeader HttpHeaders headers,@PathVariable Long orderId) {
        logHeaders(headers);

        Optional<FullOrder> result = orderRepository.findByInputString(orderId);
        if (!result.isEmpty()){
            return ResponseEntity.ok(result.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FullOrder.EMPTY);
    }


}
