package ai.cvbird.cvbirdsite.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vacancy {

    String title;
    String company;
    String url;
    String salary;
    String location;

    @JsonProperty("employment_type")
    String employmentType;
    String created;
    String contacts;
    String score;

    @JsonProperty("job_id")
    String jobId;

    @JsonProperty("tg_chat_id")
    String tgChatId;

}
