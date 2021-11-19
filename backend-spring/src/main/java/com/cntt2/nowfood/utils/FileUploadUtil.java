package com.cntt2.nowfood.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/17/2021 10:49 AM
 */
@Component
public class FileUploadUtil {

    private static final String IMAGE_PATTERN =
            "(image/(jpe?g|png|gif|bmp|))";

    public static boolean isImage(MultipartFile file) {
        return file.getContentType().toLowerCase().matches(IMAGE_PATTERN);
    }

    public static String getImageName(MultipartFile images) {
        if (!FileUploadUtil.isImage(images)) {
            return null;
        }
        // clean image
        String fileName = StringUtils.cleanPath(images.getOriginalFilename());
        fileName = CommonUtils.toImageUrl(fileName);
        return fileName;
    }

    public static void saveImage(String uploadDir, String fileName,
                                 MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }
}
