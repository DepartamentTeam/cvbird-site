package ai.cvbird.cvbirdsite.service;

import ai.cvbird.cvbirdsite.dao.CVBirdUserRepository;
import ai.cvbird.cvbirdsite.dao.CVDataRepository;
import ai.cvbird.cvbirdsite.dao.TelegramUserRepository;
import ai.cvbird.cvbirdsite.dao.UserRepository;
import ai.cvbird.cvbirdsite.dto.*;
import ai.cvbird.cvbirdsite.model.CVBirdUser;
import ai.cvbird.cvbirdsite.model.CVData;
import ai.cvbird.cvbirdsite.model.TelegramUser;
import ai.cvbird.cvbirdsite.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    TelegramUserRepository telegramUserRepository;

    @Autowired
    CVBirdUserRepository cvBirdUserRepository;

    @Override
    public byte[] getCVFile(String telegramId) {
        CVBirdUser cvBirdUser = cvBirdUserRepository.findByTelegramId(telegramId);
        if (cvBirdUser != null) {
            return getCVFile(cvBirdUser);
        }
        return null;
    }

    @Override
    public byte[] getCVFile(CVBirdUserDTO cvBirdUserDTO) {
        cvBirdUserConverter.fromDTO(cvBirdUserDTO);
        return getCVFile(cvBirdUserDTO.getTelegramId());
    }

    @Override
    public byte[] getCVFile(Long id) {
       Optional<CVBirdUser> optionalCVBirdUser = cvBirdUserRepository.findById(id);
        return optionalCVBirdUser.map(this::getCVFile).orElse(null);
    }

    @Override
    public byte[] getCVFile(CVBirdUser cvBirdUser) {
        CVData cvData = cvDataRepository.findByCvbirdUser(cvBirdUser);
        if (cvData != null) {
            return cvData.getCvFile();
        }
        return null;
    }

    @Override
    public CVData setCVFile(String telegramId, byte[] cvFile) {
        CVBirdUser cvBirdUser = cvBirdUserRepository.findByTelegramId(telegramId);
        return  setCVFile(cvBirdUser, cvFile);
    }

    @Override
    public CVData setCVFile(CVBirdUser cvBirdUser, byte[] cvFile) {
        CVData cvData = new CVData();
        cvData.setCvbirdUser(cvBirdUser);
        cvData.setCvFile(cvFile);
        return cvDataRepository.save(cvData);
    }
}
