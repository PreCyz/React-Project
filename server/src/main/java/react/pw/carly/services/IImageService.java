package react.pw.carly.services;

import org.springframework.web.multipart.MultipartFile;
import react.pw.carly.models.CarImage;


public interface IImageService {
    CarImage storeImage(MultipartFile file);

    CarImage getImage(String ImageId);
}
