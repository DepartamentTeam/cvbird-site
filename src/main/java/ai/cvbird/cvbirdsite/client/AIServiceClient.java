package ai.cvbird.cvbirdsite.client;

import ai.cvbird.cvbirdsite.dto.AIServiceUploadCVBase64;
import ai.cvbird.cvbirdsite.dto.AIServiceVacancyRequest;
import ai.cvbird.cvbirdsite.dto.GetVacanciesRequestBody;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ai-service-client", url = "${feign.ai-service.url}")
public interface AIServiceClient {

    @RequestLine("GET /check_user_exist")
    String checkUserExist(@Param("user_id") Long cvBirdUserId);

    @RequestLine("POST /get_jobs_for_user/")
    AIServiceVacancyRequest getVacancies(@RequestBody GetVacanciesRequestBody getVacanciesRequestBody);

    @RequestLine("POST /upload_cv_base64/")
    String uploadCv(@RequestBody AIServiceUploadCVBase64 data);

    @RequestLine("POST /delete_user_data/")
    String deleteCv(@Param("user_id") Long cvBirdUserId);

}