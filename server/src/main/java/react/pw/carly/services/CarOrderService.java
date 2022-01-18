package react.pw.carly.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import react.pw.carly.dao.CarOrderRepository;
import react.pw.carly.dao.CarRepository;
import react.pw.carly.models.Car;
import react.pw.carly.models.CarOrder;

@Service
public class CarOrderService implements ICarOrderService {
    private final Logger logger = LoggerFactory.getLogger(CarOrderService.class);

    private CarOrderRepository repository;

    CarOrderService() { /*Needed only for initializing spy in unit tests*/}

    @Autowired
    CarOrderService(CarOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public CarOrder updateCarOrder(Long id, CarOrder order) {
        CarOrder result = CarOrder.EMPTY;
        if (repository.existsById(id)) {
            order.setOrderId(id);
            result = repository.save(order);
            logger.info("Company with id {} updated.", id);
        }
        return result;
    }

    @Override
    public boolean deleteCarOrder(Long companyId) {
        boolean result = false;
        if (repository.existsById(companyId)) {
            repository.deleteById(companyId);
            logger.info("Company with id {} deleted.", companyId);
            result = true;
        }
        return result;
    }
}
