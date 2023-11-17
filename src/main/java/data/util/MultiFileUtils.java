package data.util;

import data.dto.FileDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class MultiFileUtils {

    @Value("${upload-path-test}")
    private String uploadPath;

    @Value("${local-path-test}")
    private String urlPath;

    /**
     * 다중 파일 업로드
     * @param multipartFiles - 파일 객체 List
     * @return DB에 저장할 파일 정보 List
     */
    public List<FileDto.Request> uploadFiles(final List<MultipartFile> multipartFiles, String saveRoute) {
        List<FileDto.Request> files = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile.isEmpty()) {
                continue;
            }
            files.add(uploadFile(multipartFile, saveRoute));
        }
        return files;
    }

    /**
     * 단일 파일 업로드
     * @param multipartFile - 파일 객체
     * @return DB에 저장할 파일 정보
     */
    public FileDto.Request uploadFile(final MultipartFile multipartFile, String saveRoute) {

        if (multipartFile.isEmpty()) {
            return null;
        }

        String saveName = generateSaveFilename(multipartFile.getOriginalFilename());
        String uploadPath = getUploadPath(saveRoute) + File.separator + saveName;
        File uploadFile = new File(uploadPath);

        try {
            multipartFile.transferTo(uploadFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return FileDto.Request.builder()
                .original_name(multipartFile.getOriginalFilename())
                .save_name(saveRoute+"/"+saveName)
                .size((int) multipartFile.getSize())
                .build();
    }

    /**
     * 파일 로컬 경로 변환
     * @param imageList - image_name list
     * @return - image_file_path list
     */
    public List<String> generateFilePath(List<FileDto.Response> imageList) {
        List<String> filePathList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyMMdd");

        for (FileDto.Response image : imageList) {
            String filePath = urlPath + "/" + image.getSaveName();
            filePathList.add(filePath);
        }

        return filePathList;
    }

    /**
     * 저장 파일명 생성
     * @param filename 원본 파일명
     * @return 디스크에 저장할 파일명
     */
    private String generateSaveFilename(final String filename) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = StringUtils.getFilenameExtension(filename);
        return uuid + "." + extension;
    }

    /**
     * 업로드 경로 반환
     * @return 업로드 경로
     */
    private String getUploadPath() {
        return makeDirectories(uploadPath);
    }

    /**
     * 업로드 경로 반환
     * @param addPath - 추가 경로
     * @return 업로드 경로
     */
    private String getUploadPath(final String addPath) {
        return makeDirectories(uploadPath + File.separator + addPath);
    }

    /**
     * 업로드 폴더(디렉터리) 생성
     * @param path - 업로드 경로
     * @return 업로드 경로
     */
    private String makeDirectories(final String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getPath();
    }

    /**
     * 파일 삭제 (from Disk)
     * @param files - 삭제할 파일 정보 List
     */
    public void deleteFiles(final List<FileDto.Response> files) {
        if (CollectionUtils.isEmpty(files)) {
            return;
        }
        for (FileDto.Response file : files) {
            String uploadedDate = file.getCreatedDate().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyMMdd"));
            deleteFile(uploadedDate, file.getSaveName());
        }
    }

    /**
     * 파일 삭제 (from Disk)
     * @param addPath - 추가 경로
     * @param filename - 파일명
     */
    private void deleteFile(final String addPath, final String filename) {
        String filePath = Paths.get(uploadPath, addPath, filename).toString();
        deleteFile(filePath);
    }

    /**
     * 파일 삭제 (from Disk)
     * @param filePath - 파일 경로
     */
    private void deleteFile(final String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

}