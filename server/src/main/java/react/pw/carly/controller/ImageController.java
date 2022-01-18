package react.pw.carly.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import react.pw.carly.dao.CarRepository;
import react.pw.carly.models.Car;
import react.pw.carly.models.CarImage;
import react.pw.carly.services.CarImageService;
import react.pw.carly.services.CarService;
import react.pw.carly.web.UploadFileResponse;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

@RestController
@RequestMapping(path = "V1/images")
public class ImageController {

    private final Logger logger = LoggerFactory.getLogger(ImageController.class);

    private final CarRepository repository;
    private final CarService carService;
    private final CarImageService carImageService;

    @Autowired
    public ImageController(CarRepository repository, CarService carService, CarImageService carImageService) {
        this.repository = repository;
        this.carService = carService;
        this.carImageService = carImageService;
    }

    private void logHeaders(@RequestHeader HttpHeaders headers) {
        logger.info("Controller request headers {}",
                headers.entrySet()
                        .stream()
                        .map(entry -> String.format("%s->[%s]", entry.getKey(), String.join(",", entry.getValue())))
                        .collect(joining(","))
        );
    }

    @PostMapping("")
    public ResponseEntity<Collection<UploadFileResponse>> uploadLogo(@RequestHeader HttpHeaders headers,
                                                                    @RequestParam("files") MultipartFile[] files) {
        logHeaders(headers);
        List<UploadFileResponse> result = new ArrayList<UploadFileResponse>();
        for (MultipartFile file : files){
            CarImage image = carImageService.storeImage(file);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/V1/images/")
                    .path(image.getId())
                    .toUriString();
            result.add(new UploadFileResponse(image.getId(),
                    image.getFileName(), fileDownloadUri, file.getContentType(), file.getSize()
            ));
        }
        return ResponseEntity.ok(result);
    }
//
//    @GetMapping(value = "/{companyId}/logo", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    public @ResponseBody byte[] getLog(@RequestHeader HttpHeaders headers, @PathVariable Long companyId) {
//        logHeaders(headers);
//        if (securityService.isAuthorized(headers)) {
//            CompanyLogo companyLogo = companyLogoService.getCompanyLogo(companyId);
//            return companyLogo.getData();
//        }
//
//        throw new UnauthorizedException("Unauthorized access to resources.");
//    }
//
    @GetMapping(value = "/{imageId}")
    public ResponseEntity<Resource> getLogo2(@RequestHeader HttpHeaders headers, @PathVariable String imageId) {
        logHeaders(headers);

        CarImage image = carImageService.getImage(imageId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(new ByteArrayResource(image.getData()));
    }
//
//    @DeleteMapping(value = "/{companyId}/logo")
//    public ResponseEntity<String> removeLogo(@RequestHeader HttpHeaders headers, @PathVariable String companyId) {
//        logHeaders(headers);
//        if (securityService.isAuthorized(headers)) {
//            companyLogoService.deleteCompanyLogo(Long.parseLong(companyId));
//            return ResponseEntity.ok().body(String.format("Logo for the company with id %s removed.", companyId));
//        }
//        throw new UnauthorizedException("Unauthorized access to resources.");
//    }

}
