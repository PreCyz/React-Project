package react.pw.carly.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import react.pw.carly.dao.CarImageRepository;
import react.pw.carly.security.exceptions.InvalidFileException;
import react.pw.carly.models.CarImage;

import java.io.IOException;

@Service
public class CarImageService implements IImageService {

    private final Logger logger = LoggerFactory.getLogger(CarImageService.class);

    private final CarImageRepository repository;

    @Autowired
    public CarImageService(CarImageRepository repository) {
        this.repository = repository;
    }

    @Override
    public CarImage storeImage(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new InvalidFileException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            CarImage image = new CarImage(fileName, file.getContentType(), file.getBytes());
            //repository.findByCompanyId(companyId).ifPresent(companyLogo -> newCompanyLogo.setId(companyLogo.getId()));
            return repository.save(image);
        } catch (IOException ex) {
            throw new InvalidFileException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public CarImage getImage(String ImageId) {
        return repository.getById(ImageId);
    }
}
