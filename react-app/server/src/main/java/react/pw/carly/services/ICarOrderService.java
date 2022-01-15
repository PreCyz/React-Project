package react.pw.carly.services;

import react.pw.carly.models.Car;
import react.pw.carly.models.CarOrder;

public interface ICarOrderService {
    CarOrder updateCarOrder(Long id, CarOrder orderCar);
    boolean deleteCarOrder(Long carId);
}
