package ai.cvbird.cvbirdsite.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AIServiceVacancyRequest {
    Integer numJobs;
    Long userId;
    List<Vacancy> jobs;
}
