package board.mybatis.mvc.dto.file;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Enginx 에 FileUpload 쓰이는 DTO
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UploadResultDTO {

    @NotNull(message = "UUID Must Not Be Null")
    private String uuid;

    @NotNull(message = "fileName Must Not Be Null")
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
