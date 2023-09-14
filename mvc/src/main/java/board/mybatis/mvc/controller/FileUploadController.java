package board.mybatis.mvc.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "File API", description = "파일 업로드와 관련된 모든 API")
public class FileUploadController {

    // File Upload Path = Enginx
    @Value("${org.kwon.upload.path}")
    private String uploadPath;

    /**
     * 파일 업로드 처리 메소드
     *
     * @param files 업로드할 파일 배열
     * @return 업로드 결과 목록
     * @throws UnsupportedEncodingException
     */
    @PostMapping("/upload")
    @Operation(summary = "파일 업로드", description = "파일을 업로드합니다.")
    public List<UploadResultDTO> postFileUpload(
            @Parameter(description = "업로드할 파일 배열", required = true) @Valid MultipartFile[] files)
            throws UnsupportedEncodingException {
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
            String encodedFileName = URLEncoder.encode(fileNmae, "UTF-8");
            String saveFileName = uuidStr + "_" + encodedFileName;

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
     * @throws UnsupportedEncodingException
     */
    @DeleteMapping("removeFile/{fileName}")
    @Operation(summary = "파일 삭제", description = "특정 파일을 삭제합니다.")
    public Map<String, String> deleteFile(
            @Parameter(description = "삭제할 파일 이름", required = true) @PathVariable("fileName") String fileName)
            throws UnsupportedEncodingException {
        log.info("RestController | Delete File");
        String decodedFileName = URLDecoder.decode(fileName, "UTF-8");
        File originFile = new File(uploadPath, decodedFileName);
        // JVM외부랑 연결되는 소스는 exception 처리
        try {
            String mimeType = Files.probeContentType(originFile.toPath());

            if (mimeType.startsWith("image")) {
                File thumbFile = new File(uploadPath, "s_" + decodedFileName);
                thumbFile.delete();
            }
            originFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Map.of("result", "success");
    }
}
