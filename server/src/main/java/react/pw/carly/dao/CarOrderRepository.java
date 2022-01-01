package react.pw.carly.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import react.pw.carly.models.Car;
import react.pw.carly.models.CarOrder;
import react.pw.carly.models.FullOrder;

import java.time.LocalDate;
import java.util.List;


public interface CarOrderRepository extends JpaRepository<CarOrder, Long> {

    List<CarOrder> findAllByStartDateLessThanEqualAndEndDateGreaterThanEqualAndCarIs(LocalDate endDate, LocalDate startDate, Car car);

    @Query(value = "SELECT new react.pw.carly.models.FullOrder(o.orderId,o.booklyId,  o.firstName,o.lastName,o.status,o.startDate,o.endDate, " +
            "c.carId,c.carName,c.carModel,c.price,c.location,c.description,c.pic1,c.pic2,c.pic3) " +
            "FROM CarOrder as o,  Car as c  WHERE " +
            "o.car.carId = c.carId and" +
            "(:keyword is null or o.firstName like %:keyword% ) or " +
            "(:keyword is null or o.lastName like %:keyword%) or "+
            "(:keyword is null or c.carName like %:keyword%) "
    )
    List<FullOrder> findAllByInputString( String keyword, Pageable pageable);
}
