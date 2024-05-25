package ai.cvbird.cvbirdsite.service;

import ai.cvbird.cvbirdsite.dao.TelegramStatisticRepository;
import ai.cvbird.cvbirdsite.dao.TelegramUserRepository;
import ai.cvbird.cvbirdsite.dao.UserRepository;
import ai.cvbird.cvbirdsite.dto.TelegramStatisticConverter;
import ai.cvbird.cvbirdsite.dto.TelegramStatisticDTO;
import ai.cvbird.cvbirdsite.dto.TelegramUserConverter;
import ai.cvbird.cvbirdsite.dto.TelegramUserDTO;
import ai.cvbird.cvbirdsite.model.TelegramStatistic;
import ai.cvbird.cvbirdsite.model.TelegramUser;
import ai.cvbird.cvbirdsite.model.User;
import ai.cvbird.cvbirdsite.registration.OnTelegramRegistrationCompleteEvent;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class TelegramServiceImpl implements TelegramService{

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TelegramUserRepository telegramUserRepository;

    @Autowired
    TelegramUserConverter telegramUserConverter;

    @Autowired
    TelegramStatisticRepository telegramStatisticRepository;

    @Autowired
    TelegramStatisticConverter telegramStatisticConverter;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public TelegramUser registerTelegramUser(TelegramUserDTO telegramUserDTO) {
        if (telegramUserDTO.getEmail() != null) {
            if (telegramUserRepository.findByEmail(telegramUserDTO.getEmail()) == null) {
                User user = userRepository.findByEmail(telegramUserDTO.getEmail());
                if (user == null) {
                    User newUser = new User();
                    newUser.setEmail(telegramUserDTO.getEmail());
                    newUser.setEnabled(true);
                    String password = getNewPassword();
                    newUser.setPassword(passwordEncoder.encode(password));
                    User cvbirdUser = userRepository.save(newUser);

                    TelegramUser telegramUser = telegramUserConverter.fromDTO(telegramUserDTO);
                    telegramUser.setCvbirdUser(cvbirdUser);
                    telegramUser.setRegistrationDate(ZonedDateTime.now());
                    TelegramUser newTelegramUser = telegramUserRepository.save(telegramUser);

                    applicationEventPublisher.publishEvent(new OnTelegramRegistrationCompleteEvent(cvbirdUser.getEmail(), password));
                    return newTelegramUser;
                } else {                                                // Link telegram user to cvbirdUser
                    TelegramUser telegramUser = telegramUserConverter.fromDTO(telegramUserDTO);
                    user.setEnabled(true);
                    telegramUser.setCvbirdUser(user);
                    telegramUser.setRegistrationDate(ZonedDateTime.now());
                    TelegramUser newTelegramUser = telegramUserRepository.save(telegramUser);
                    return newTelegramUser;
                }
            }

        }
        return null;
    }

    @Override
    public TelegramStatistic saveTelegramStatistic(TelegramStatisticDTO telegramStatisticDTO) {
        if (telegramStatisticDTO.getTelegramId() != null) {
            TelegramStatistic telegramStatistic = telegramStatisticConverter.fromDTO(telegramStatisticDTO);
            telegramStatistic.setRegistrationDate(ZonedDateTime.now());
            return telegramStatisticRepository.save(telegramStatistic);
        }
        return null;
    }

    @Override
    public TelegramUser getTelegramUserById(String telegramId) {
        return telegramUserRepository.findByTelegramId(telegramId);
    }

    @Override
    public TelegramUser saveTelegramUser(TelegramUserDTO telegramUserDTO) {
        //TODO
        return null;
    }

    private String getNewPassword() {
        char [][] pairs = {{'a','z'},{'0','9'},{'A','Z'}};
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator.Builder().withinRange(pairs).build();
        return randomStringGenerator.generate(8,12);
    }

    @Override
    public TelegramStatistic getUserStatistic(String id) {
       return telegramStatisticRepository.findByTelegramId(id);
    }
}
