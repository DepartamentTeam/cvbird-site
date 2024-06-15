package ai.cvbird.cvbirdsite.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import feign.form.FormProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetVacanciesRequestBody {
    @FormProperty("user_id")
    @JsonProperty("user_id")
    Long userId;

    @FormProperty("num_job")
    @JsonProperty("num_job")
    Integer jobNumber;
}
