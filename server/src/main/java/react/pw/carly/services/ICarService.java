package react.pw.carly.services;

import react.pw.carly.models.Car;

public interface ICarService {
    Car updateCompany(Long id, Car updatedCar);
    boolean deleteCompany(Long carId);
}
