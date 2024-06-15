package ai.cvbird.cvbirdsite.dto;

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
    String employmentType;
    String created;
    String contacts;
    String score;

}
