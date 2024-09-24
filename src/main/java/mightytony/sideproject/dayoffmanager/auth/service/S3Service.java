package mightytony.sideproject.dayoffmanager.auth.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {
    String uploadFile(MultipartFile image) throws IOException;

    void deleteImageFromS3(String imageUrl);
}
