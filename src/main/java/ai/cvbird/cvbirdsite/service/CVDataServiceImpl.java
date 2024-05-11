package ai.cvbird.cvbirdsite.service;

import ai.cvbird.cvbirdsite.dao.CVDataRepository;
import ai.cvbird.cvbirdsite.dao.TelegramUserRepository;
import ai.cvbird.cvbirdsite.dao.UserRepository;
import ai.cvbird.cvbirdsite.dto.TelegramUserConverter;
import ai.cvbird.cvbirdsite.dto.TelegramUserDTO;
import ai.cvbird.cvbirdsite.dto.UserConverter;
import ai.cvbird.cvbirdsite.dto.UserDto;
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
    UserConverter userConverter;

    @Autowired
    TelegramUserRepository telegramUserRepository;

    @Autowired
    TelegramUserConverter telegramUserConverter;

    @Override
    public byte[] getCVFile(String telegramId) {
        TelegramUser telegramUser = telegramUserRepository.findByTelegramId(telegramId);
        return getCVFile(telegramUser.getCvbirdUser());
    }

    @Override
    public byte[] getCVFile(TelegramUserDTO telegramUserDTO) {
        return getCVFile(telegramUserDTO.getTelegramId());
    }

    @Override
    public byte[] getCVFile(Long id) {
       Optional<User> oUser = userRepository.findById(id);
        return oUser.map(this::getCVFile).orElse(null);
    }

    @Override
    public byte[] getCVFile(User user) {
        CVData cvData = cvDataRepository.findByCvbirdUser(user);
        if (cvData != null) {
            return cvData.getCvFile();
        }
        return null;
    }

    @Override
    public CVData setCVFile(String telegramId, byte[] cvFile) {
        TelegramUser telegramUser = telegramUserRepository.findByTelegramId(telegramId);
        return  setCVFile(telegramUser.getCvbirdUser(), cvFile);
    }

    @Override
    public CVData setCVFile(TelegramUserDTO telegramUserDTO, byte[] cvFile) {
        TelegramUser telegramUser = telegramUserRepository.findByTelegramId(telegramUserDTO.getTelegramId());
        return  setCVFile( telegramUser.getCvbirdUser(), cvFile);
    }

    @Override
    public CVData setCVFile(User user, byte[] cvFile) {
        CVData cvData = new CVData();
        cvData.setCvbirdUser(user);
        cvData.setCvFile(cvFile);
        return cvDataRepository.save(cvData);
    }
}
