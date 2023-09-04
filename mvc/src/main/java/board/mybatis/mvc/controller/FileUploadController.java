package board.mybatis.mvc.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import board.mybatis.mvc.dto.file.UploadResultDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;

/**
 * {@code FileUploadController}는 파일 업로드 및 삭제를 처리하는 컨트롤러 클래스입니다.
 */
@Log4j2
@RestController
@RequiredArgsConstructor
public class FileUploadController {

    // File Upload Path = Enginx
    @Value("${org.zerock.upload.path}")
    private String uploadPath;

    /**
     * 파일 업로드 처리 메소드
     *
     * @param files 업로드할 파일 배열
     * @return 업로드 결과 목록
     */
    @PostMapping("/upload")
    public List<UploadResultDTO> postFileUpload(@Valid MultipartFile[] files) {
        log.info("RestController | Upload File");
        if (files == null || files.length == 0) {
            return null;
        }
        List<UploadResultDTO> resultList = new ArrayList<>();
        for (MultipartFile file : files) {
            UploadResultDTO result = null;
            String fileNmae = file.getOriginalFilename();
            Long size = file.getSize();
            String uuidStr = UUID.randomUUID().toString();
            String saveFileName = uuidStr + "_" + fileNmae;
            File saveFile = new File(uploadPath, saveFileName);
            try {
                FileCopyUtils.copy(file.getBytes(), saveFile);
                result = UploadResultDTO.builder().uuid(uuidStr).fileName(fileNmae).build();
                // Values Check Img
                String mimeType = Files.probeContentType(saveFile.toPath());
                if (mimeType != null || mimeType.startsWith("image")) {
                    File thumFile = new File(uploadPath, "s_" + saveFileName);
                    Thumbnailator.createThumbnail(saveFile, thumFile, 100, 100);
                    result.setImg(true);
                }
                resultList.add(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    /**
     * 파일 삭제 처리 메소드
     *
     * @param fileName 삭제할 파일 이름
     * @return 삭제 결과 메시지
     */
    @DeleteMapping("removeFile/{fileName}")
    public Map<String, String> deleteFile(@PathVariable("fileName") String fileName) {
        log.info("RestController | Delete File");
        File originFile = new File(uploadPath, fileName);

        // JVM외부랑 연결되는 소스는 exception 처리
        try {
            String mimeType = Files.probeContentType(originFile.toPath());

            if (mimeType.startsWith("image")) {
                File thumbFile = new File(uploadPath, "s_" + fileName);
                thumbFile.delete();
            }
            originFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Map.of("result", "success");
    }
}
