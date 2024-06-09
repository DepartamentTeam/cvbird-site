package ai.cvbird.cvbirdsite.client;

import ai.cvbird.cvbirdsite.dto.AIServiceUploadCVBase64;
import ai.cvbird.cvbirdsite.dto.AIServiceVacancyRequest;
import ai.cvbird.cvbirdsite.dto.GetVacanciesRequestBody;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ai-service-client", url = "${feign.ai-service.url}")
public interface AIServiceClient {

    @RequestLine("GET /check_user_exist")
    String checkUserExist(@Param("user_id") Long cvBirdUserId);

    @RequestLine("POST /get_jobs_for_user/")
    AIServiceVacancyRequest getVacancies(@RequestBody GetVacanciesRequestBody getVacanciesRequestBody);

    @RequestLine("POST /upload_cv_base64/")
    String upload_cv(@RequestBody AIServiceUploadCVBase64 data);

   //@RequestLine("POST /telegram/statistic/save")
   //String saveStatistic(StatisticDTO statisticDTO);

   //@RequestLine("POST /telegram/get_user_statistic")
   //StatisticDTO getUserStatistic(@SpringQueryMap RequestGetUserStatistic requestGetUserStatistic);

   //@RequestLine("GET /user/user_info")
   //String userInfo();

   //@RequestMapping(method = RequestMethod.GET, value = "/stores")
   //Page<Store> getStores(Pageable pageable);
}