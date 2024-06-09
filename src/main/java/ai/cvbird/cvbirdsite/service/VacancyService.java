package ai.cvbird.cvbirdsite.service;

import ai.cvbird.cvbirdsite.dto.Vacancy;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VacancyService {

    Page<Vacancy> getVacancies(Long cvBirdUserID, Integer currentPage, Integer pageSize);
}
