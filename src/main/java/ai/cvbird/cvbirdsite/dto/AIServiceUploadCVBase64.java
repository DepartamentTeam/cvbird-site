package ai.cvbird.cvbirdsite.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import feign.form.FormProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AIServiceUploadCVBase64 {

    @FormProperty("user_id")
    @JsonProperty("user_id")
    Long userId;

    @FormProperty("file")
    @JsonProperty("file")
    String fileBase64;
}
