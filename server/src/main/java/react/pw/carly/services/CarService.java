package react.pw.carly.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import react.pw.carly.dao.CarRepository;
import react.pw.carly.models.Car;

@Service
public class CarService implements ICarService {
    private final Logger logger = LoggerFactory.getLogger(CarService.class);

    private CarRepository repository;

    CarService() { /*Needed only for initializing spy in unit tests*/}

    @Autowired
    CarService(CarRepository repository) {
        this.repository = repository;
    }

    @Override
    public Car updateCompany(Long id, Car updatedCompany) {
        Car result = Car.EMPTY;
        if (repository.existsById(id)) {
            updatedCompany.setCarId(id);
            result = repository.save(updatedCompany);
            logger.info("Company with id {} updated.", id);
        }
        return result;
    }

    @Override
    public boolean deleteCompany(Long companyId) {
        boolean result = false;
        if (repository.existsById(companyId)) {
            repository.deleteById(companyId);
            logger.info("Company with id {} deleted.", companyId);
            result = true;
        }
        return result;
    }
}
