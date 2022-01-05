package react.pw.carly.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import react.pw.carly.models.Car;
import react.pw.carly.models.CarOrder;
import react.pw.carly.vo.FullOrder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface CarOrderRepository extends JpaRepository<CarOrder, Long> {

    List<CarOrder> findAllByStartDateLessThanEqualAndEndDateGreaterThanEqualAndCarIs(LocalDateTime endDate, LocalDateTime startDate, Car car);

    @Query(value = "SELECT new react.pw.carly.vo.FullOrder(o.orderId,o.booklyId,  o.firstName,o.lastName,o.status,o.startDate,o.endDate, " +
            "c.carId,c.carName,c.carModel,c.price,c.location,c.description,c.images) " +
            "FROM CarOrder as o,  Car as c  WHERE " +
            "o.car.carId = c.carId and" +
            "(:keyword is null or o.firstName like %:keyword% ) or " +
            "(:keyword is null or o.lastName like %:keyword%) or "+
            "(:keyword is null or c.carName like %:keyword%) "
    )
    List<FullOrder> findAllByInputString( String keyword, Pageable pageable);


    @Query(value = "SELECT new react.pw.carly.vo.FullOrder(o.orderId,o.booklyId,  o.firstName,o.lastName,o.status,o.startDate,o.endDate, " +
            "c.carId,c.carName,c.carModel,c.price,c.location,c.description,c.images) " +
            "FROM CarOrder as o,  Car as c  WHERE " +
            "o.car.carId = c.carId and " +
            "o.orderId = :orderId"
    )
    Optional<FullOrder> findByInputString(Long orderId);
}
