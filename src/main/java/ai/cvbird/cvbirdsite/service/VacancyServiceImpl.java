package ai.cvbird.cvbirdsite.service;

import ai.cvbird.cvbirdsite.client.AIServiceClient;
import ai.cvbird.cvbirdsite.dto.AIServiceVacancyRequest;
import ai.cvbird.cvbirdsite.dto.GetVacanciesRequestBody;
import ai.cvbird.cvbirdsite.dto.Vacancy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacancyServiceImpl implements VacancyService {

    Integer VACANCIES_NUMBER = 100;

    @Autowired
    AIServiceClient aiServiceClient;

    @Override
    public Page<Vacancy> getVacancies(Long cvBirdUserID, Integer currentPage, Integer pageSize) {
        GetVacanciesRequestBody aiServiceVacancyRequest = new GetVacanciesRequestBody(cvBirdUserID, VACANCIES_NUMBER);
        AIServiceVacancyRequest request = aiServiceClient.getVacancies(aiServiceVacancyRequest);
        List<Vacancy> list = request.getJobs();
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        return new PageImpl<>(list,pageable, list.size());
    }
}
