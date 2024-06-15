package ai.cvbird.cvbirdsite.service;

import ai.cvbird.cvbirdsite.dto.AIServiceUploadCVBase64;
import ai.cvbird.cvbirdsite.dto.AIServiceVacancyRequest;
import ai.cvbird.cvbirdsite.dto.GetVacanciesRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;
import java.util.Collections;

@Service
public class AIRestServiceImpl implements AIRestService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${ai.service.url}")
    String aiServiceURL;

    @Override
    public String checkUserExist(Long cvBirdUserId) {
        return null;
    }

    @Override
    public AIServiceVacancyRequest getVacancies(GetVacanciesRequestBody getVacanciesRequestBody) {
        return null;
    }

    @Override
    public String upload_cv(AIServiceUploadCVBase64 data) {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setAccept(Collections.singletonList(
                MediaType.APPLICATION_JSON));

        HttpEntity<AIServiceUploadCVBase64> entity
                = new HttpEntity<>(data, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(aiServiceURL + "/upload_cv_base64",
                HttpMethod.POST, entity,
                String.class);
        return responseEntity.getBody();

    }
}
