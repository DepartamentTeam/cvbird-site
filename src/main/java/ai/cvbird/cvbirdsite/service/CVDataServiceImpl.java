package ai.cvbird.cvbirdsite.service;

import ai.cvbird.cvbirdsite.client.AIServiceClient;
import ai.cvbird.cvbirdsite.dao.CVBirdUserRepository;
import ai.cvbird.cvbirdsite.dao.CVDataRepository;
import ai.cvbird.cvbirdsite.dao.UserRepository;
import ai.cvbird.cvbirdsite.dto.*;
import ai.cvbird.cvbirdsite.model.CVBirdUser;
import ai.cvbird.cvbirdsite.model.CVData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CVDataServiceImpl implements CVDataService{

    @Autowired
    CVDataRepository cvDataRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CVBirdUserConverter cvBirdUserConverter;

    @Autowired
    CVBirdUserRepository cvBirdUserRepository;

    @Autowired
    AIServiceClient aiServiceClient;

    @Autowired
    AIRestServiceImpl aiRestService;

    @Override
    public String getCVFile(String telegramId) {
        CVBirdUser cvBirdUser = cvBirdUserRepository.findByTelegramId(telegramId);
        if (cvBirdUser != null) {
            return getCVFile(cvBirdUser);
        }
        return null;
    }

    @Override
    public String getCVFile(CVBirdUserDTO cvBirdUserDTO) {
        cvBirdUserConverter.fromDTO(cvBirdUserDTO);
        return getCVFile(cvBirdUserDTO.getTelegramId());
    }

    @Override
    public String getCVFile(Long id) {
       Optional<CVBirdUser> optionalCVBirdUser = cvBirdUserRepository.findById(id);
        return optionalCVBirdUser.map(this::getCVFile).orElse(null);
    }

    @Override
    public String getCVFile(CVBirdUser cvBirdUser) {
        CVData cvData = cvDataRepository.findByCvbirdUser(cvBirdUser);
        if (cvData != null) {
            return cvData.getCvFile();
        }
        return null;
    }

    @Override
    public CVData setCVFile(String telegramId, String cvFile) {
        CVBirdUser cvBirdUser = cvBirdUserRepository.findByTelegramId(telegramId);
        return  setCVFile(cvBirdUser, cvFile);
    }

    @Override
    public CVData getCVData(String telegramId) {
        CVBirdUser cvBirdUser = cvBirdUserRepository.findByTelegramId(telegramId);
        if (cvBirdUser != null) {
            return cvDataRepository.findByCvbirdUser(cvBirdUser);
        }
        return null;
    }

    @Override
    public CVData setCVFile(CVBirdUser cvBirdUser, String cvFile) {
        CVData cvData = new CVData();
        cvData.setCvbirdUser(cvBirdUser);
        cvData.setCvFile(cvFile);
        try {
            AIServiceUploadCVBase64 aiServiceUploadCVBase64 = new AIServiceUploadCVBase64(cvBirdUser.getCvBirdUserId(), cvFile);
            aiServiceClient.uploadCv(aiServiceUploadCVBase64);
        } catch (Exception e) {
            System.out.println("cvBirdUser:" + cvBirdUser + "didn't upload cv : " + e);
        }

        return cvDataRepository.save(cvData);
    }

    @Override
    @Transactional
    public Boolean deleteCVData(CVBirdUser cvBirdUser) {
        //deleteAivectorCV(cvBirdUser);
        CVData cvData = cvDataRepository.findByCvbirdUser(cvBirdUser);
        Integer integer = cvDataRepository.deleteByCvbirdUser(cvBirdUser);
        return integer == 1;
    }

    private String deleteAivectorCV(CVBirdUser cvBirdUser){
        return aiServiceClient.deleteCv(cvBirdUser.getCvBirdUserId());
    }
}
