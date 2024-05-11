package ai.cvbird.cvbirdsite.service;

import ai.cvbird.cvbirdsite.dao.TelegramUserRepository;
import ai.cvbird.cvbirdsite.dao.UserRepository;
import ai.cvbird.cvbirdsite.dto.TelegramUserConverter;
import ai.cvbird.cvbirdsite.dto.TelegramUserDTO;
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
    TelegramUserRepository telegramUserRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TelegramUserConverter telegramUserConverter;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public TelegramUser registerTelegramUser(TelegramUserDTO telegramUserDTO) {
        if (userRepository.findByEmail(telegramUserDTO.getEmail()) == null) {

            User user = new User();
            user.setEmail(telegramUserDTO.getEmail());
            user.setEnabled(true);
            String password = getNewPassword();
            user.setPassword(passwordEncoder.encode(password));
            User cvbirdUser = userRepository.save(user);

            TelegramUser telegramUser = telegramUserConverter.fromDTO(telegramUserDTO);
            telegramUser.setCvbirdUser(cvbirdUser);
            telegramUser.setRegistrationDate(ZonedDateTime.now());
            TelegramUser newTelegramUser = telegramUserRepository.save(telegramUser);

            applicationEventPublisher.publishEvent(new OnTelegramRegistrationCompleteEvent(cvbirdUser.getEmail(), password));
            return newTelegramUser;
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
}
