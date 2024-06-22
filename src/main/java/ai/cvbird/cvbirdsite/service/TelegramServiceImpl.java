package ai.cvbird.cvbirdsite.service;

import ai.cvbird.cvbirdsite.dao.*;
import ai.cvbird.cvbirdsite.dto.*;
import ai.cvbird.cvbirdsite.model.*;
import ai.cvbird.cvbirdsite.registration.OnTelegramRegistrationCompleteEvent;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Service
public class TelegramServiceImpl implements TelegramService{

    BigDecimal FIRST_BALANCE_AMOUNT = new BigDecimal("0.2");

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TelegramUserRepository telegramUserRepository;

    @Autowired
    TelegramUserConverter telegramUserConverter;

    @Autowired
    CVBirdUserRepository cvBirdUserRepository;

    @Autowired
    CVBirdUserConverter cvBirdUserConverter;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    BalanceRepository balanceRepository;

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
    @Transactional
    public CVBirdUser saveUnknownUser(TelegramStatisticDTO telegramStatisticDTO) {
        if (telegramStatisticDTO.getTelegramId() != null) {
            CVBirdUser cvBirdUser = cvBirdUserConverter.fromTelegramStatisticDTO(telegramStatisticDTO);
            cvBirdUser.setRegistrationDate(ZonedDateTime.now());
            cvBirdUser.setEnabled(true);
            CVBirdUser newCVbirdUser =  cvBirdUserRepository.save(cvBirdUser);
            Balance balance = new Balance();
            balance.setCvbirdUser(newCVbirdUser);
            balance.setBalance(FIRST_BALANCE_AMOUNT);
            balanceRepository.save(balance);
            return cvBirdUser;
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
    public CVBirdUser getCVBirdUser(String telegramId) {
       return cvBirdUserRepository.findByTelegramId(telegramId);
    }
}
