package ai.cvbird.cvbirdsite.service;

import ai.cvbird.cvbirdsite.dto.Vacancy;
import ai.cvbird.cvbirdsite.exception.NotEnoughFundsException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VacancyService {

    List<Vacancy> getVacancies(Long cvBirdUserID) throws NotEnoughFundsException;
}
