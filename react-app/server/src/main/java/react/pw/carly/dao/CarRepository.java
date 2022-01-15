package react.pw.carly.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import react.pw.carly.models.Car;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface CarRepository extends JpaRepository<Car, Long> {

    @Query(value = "SELECT c From Car as c WHERE " +

            "((:keyword is null or c.carName like %:keyword% ) or " +
            "(:keyword is null or c.location like %:keyword%) or "+
            "(:keyword is null or c.description like %:keyword%) or " +
            "(:keyword is null or c.carModel like %:keyword%) ) and c.isActive = :isActive "
    )
    List<Car> findAllByKeyWords(String keyword, Boolean isActive, Pageable pageable);

    @Query(value = "SELECT c From Car as c WHERE " +

            "(:carName is null or c.carName like %:carName% ) and " +
            "(:location is null or c.location like %:location%) and "+
            "(:description is null or c.description like %:description%) and " +
            "(:model is null or c.carModel like %:model%) and" +
            " c.isActive = :isActive and  " +
            "( (:startDate is null and :endDate is null) or c.carId not in" +
            " (select o.car.carId from CarOrder o " +
            "   where (:startDate is null or (o.startDate <= :startDate and o.endDate >= :startDate))" +
            "   and (:endDate is null or (o.startDate <= :endDate and o.endDate >= :endDate)))" +
            ") "
    )
    List<Car>   findAllByInputString(String carName, String location, String description, String model, LocalDateTime startDate , LocalDateTime endDate,Boolean isActive, Pageable pageable);

}
