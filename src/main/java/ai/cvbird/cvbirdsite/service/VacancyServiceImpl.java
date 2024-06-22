package ai.cvbird.cvbirdsite.service;

import ai.cvbird.cvbirdsite.client.AIServiceClient;
import ai.cvbird.cvbirdsite.dao.BalanceRepository;
import ai.cvbird.cvbirdsite.dao.CVBirdUserRepository;
import ai.cvbird.cvbirdsite.dto.AIServiceVacancyRequest;
import ai.cvbird.cvbirdsite.dto.GetVacanciesRequestBody;
import ai.cvbird.cvbirdsite.dto.Vacancy;
import ai.cvbird.cvbirdsite.exception.NotEnoughFundsException;
import ai.cvbird.cvbirdsite.model.CVBirdUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class VacancyServiceImpl implements VacancyService {

    Integer VACANCIES_NUMBER = 15;

    BigDecimal cost = new BigDecimal("0.1");

    @Autowired
    AIServiceClient aiServiceClient;

    @Autowired
    CVBirdUserRepository cvBirdUserRepository;

    @Autowired
    BalanceService balanceService;

    @Override
    public List<Vacancy> getVacancies(Long cvBirdUserID) throws NotEnoughFundsException{
        Optional<CVBirdUser> oCvBirdUser = cvBirdUserRepository.findByCvBirdUserId(cvBirdUserID);
        if(oCvBirdUser.isPresent()) {
            try {
                balanceService.balanceCharging(oCvBirdUser.get(),cost);
                GetVacanciesRequestBody aiServiceVacancyRequest = new GetVacanciesRequestBody(cvBirdUserID, VACANCIES_NUMBER);
                AIServiceVacancyRequest request = aiServiceClient.getVacancies(aiServiceVacancyRequest);
                List<Vacancy> list = request.getJobs();
                return list;
            } catch (NotEnoughFundsException e) {
                throw new NotEnoughFundsException(e.getMessage());
            }
        }
        return null;
    }
}
