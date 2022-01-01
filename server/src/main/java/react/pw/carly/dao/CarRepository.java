package react.pw.carly.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import react.pw.carly.models.Car;

import java.util.List;


public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByCarNameContainsOrLocationContainsOrDescriptionContainsOrCarModelContains(
            String carName,String location, String description, String model, Pageable pageable);
}
