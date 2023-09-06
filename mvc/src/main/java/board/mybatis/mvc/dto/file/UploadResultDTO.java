package board.mybatis.mvc.dto.file;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * {@code UploadResultDTO} 클래스는 Enginx에서 파일 업로드에 사용되는 DTO 클래스입니다.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UploadResultDTO {

    @NotBlank(message = "UUID Must Not Be Blank")
    private String uuid;

    @NotBlank(message = "fileName Must Not Be Blank")
    private String fileName;
    private boolean img;

    public String getLink() {
        if (img) {
            return "s_" + uuid + "_" + fileName;
        } else {
            return "default.jpg";
        }
    }
}
