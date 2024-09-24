package mightytony.sideproject.dayoffmanager.auth.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mightytony.sideproject.dayoffmanager.auth.service.S3Service;
import mightytony.sideproject.dayoffmanager.common.util.CommonUtil;
import mightytony.sideproject.dayoffmanager.exception.CustomException;
import mightytony.sideproject.dayoffmanager.exception.ResponseCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3ServiceImpl implements S3Service {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.url}")
    private String bucketUrl;

    @Value("${cloud.aws.cloudfront.url")
    private String cloudfrontUrl;

    private static final List<String> ALLOWED_FILE_EXTENSIONS = Arrays.asList("jpg","jpeg","png","gif");

    public String uploadFile(MultipartFile image) throws IOException {
        if(image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
            throw new CustomException(ResponseCode.EMPTY_FILE_EXCEPTION);
        }

        String fileExtension = getFileExtension(image.getOriginalFilename());
        if (!ALLOWED_FILE_EXTENSIONS.contains(fileExtension.toLowerCase())){
            throw new CustomException(ResponseCode.NOT_SUPPORTED_EXTENSION);
        }

        // 원본 파일 이미지 이름 한글이면 URL 인코딩 등 복잡함.
        String imageName = CommonUtil.makeUUID() + "." + fileExtension; //+ "_" + image.getOriginalFilename();
        if(imageName.isEmpty() || imageName.isBlank()) {
            throw new CustomException(ResponseCode.NOT_FOUND_IMAGE);
        }

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(image.getSize());
        metadata.setContentType(image.getContentType());

        amazonS3.putObject(new PutObjectRequest(bucketUrl, imageName, image.getInputStream(), metadata));

        //return amazonS3.getUrl(bucketUrl, imageName).toString();
        String imageUrlOnCloudfront = cloudfrontUrl + "/profile/" + imageName;


        return imageUrlOnCloudfront;
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public void deleteImageFromS3(String imageUrl) {
        String key = getKeyFromImageAddress(imageUrl);
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(bucketUrl, key));
        } catch (Exception e) {
            throw new CustomException(ResponseCode.IO_EXCEPTION_ON_IMAGE_DELETE);
        }
    }

    private String getKeyFromImageAddress(String imageAddress){
        try{
            URL url = new URL(imageAddress);
            String decodingKey = URLDecoder.decode(url.getPath(), "UTF-8");
            return decodingKey.substring(1); // 맨 앞의 '/' 제거
        }catch (MalformedURLException | UnsupportedEncodingException e){
            throw new CustomException(ResponseCode.IO_EXCEPTION_ON_IMAGE_DELETE);
        }
    }

}
