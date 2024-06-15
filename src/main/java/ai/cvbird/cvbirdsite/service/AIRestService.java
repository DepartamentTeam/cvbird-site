package ai.cvbird.cvbirdsite.service;

import ai.cvbird.cvbirdsite.dto.AIServiceUploadCVBase64;
import ai.cvbird.cvbirdsite.dto.AIServiceVacancyRequest;
import ai.cvbird.cvbirdsite.dto.GetVacanciesRequestBody;

public interface AIRestService {
    String checkUserExist(Long cvBirdUserId);

    AIServiceVacancyRequest getVacancies(GetVacanciesRequestBody getVacanciesRequestBody);

    String upload_cv(AIServiceUploadCVBase64 data);
}
