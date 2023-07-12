package data.util;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class FileUploadUtil {

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads";

    public static String uploadFile(MultipartFile file) throws IOException {
        String originalFilename = StringUtils.cleanPath(requireNonNull(file.getOriginalFilename()));
        String fileName = generateUniqueFileName(originalFilename);
        Path uploadPath = Path.of(UPLOAD_DIR);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        return fileName;
    }

    public static void deleteFile(String fileName) {
        File file = new File(UPLOAD_DIR+"/"+fileName);

        if(file.exists()) {
            if(file.delete())
                System.out.println(fileName+" delete");

        }
    }

    private static String generateUniqueFileName(String originalFilename) {
        String extension = StringUtils.getFilenameExtension(originalFilename);
        return UUID.randomUUID() + "." + extension;
    }
}