package ai.cvbird.cvbirdsite.dto;

import ai.cvbird.cvbirdsite.model.CVBirdUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CVBirdUserConverter {

    @Autowired
    PasswordEncoder passwordEncoder;

    public CVBirdUserResponse toCVBirdUserResponse(CVBirdUser cvBirdUser) {
        CVBirdUserResponse cvBirdUserResponse = new CVBirdUserResponse();
        cvBirdUserResponse.setCvBirdUserId(cvBirdUser.getCvBirdUserId());
        cvBirdUserResponse.setRegistrationDate(cvBirdUser.getRegistrationDate());
        cvBirdUserResponse.setTelegramId(cvBirdUser.getTelegramId());
        cvBirdUserResponse.setTelegramFirstName(cvBirdUser.getTelegramFirstName());
        cvBirdUserResponse.setTelegramUserName(cvBirdUser.getTelegramUserName());
        cvBirdUserResponse.setTelegramLastName(cvBirdUser.getTelegramLastName());
        cvBirdUserResponse.setTelegramIsBot(cvBirdUser.getTelegramIsBot());
        cvBirdUserResponse.setTelegramLanguageCode(cvBirdUser.getTelegramLanguageCode());
        cvBirdUserResponse.setEmail(cvBirdUserResponse.getEmail());
        return cvBirdUserResponse;
    }
    public CVBirdUser fromTelegramStatisticDTO(TelegramStatisticDTO telegramStatisticDTO) {
        CVBirdUser cvBirdUser = new CVBirdUser();
        cvBirdUser.setTelegramId(telegramStatisticDTO.getTelegramId());
        cvBirdUser.setTelegramFirstName(telegramStatisticDTO.getTelegramFirstName());
        cvBirdUser.setTelegramUserName(telegramStatisticDTO.getTelegramUserName());
        cvBirdUser.setTelegramLastName(telegramStatisticDTO.getTelegramLastName());
        cvBirdUser.setTelegramIsBot(telegramStatisticDTO.getTelegramIsBot());
        cvBirdUser.setTelegramLanguageCode(telegramStatisticDTO.getTelegramLanguageCode());
        return cvBirdUser;
    }

    public CVBirdUser fromDTO(CVBirdUserDTO cvBirdUserDTO) {
        CVBirdUser cvBirdUser = new CVBirdUser();
        cvBirdUser.setCvBirdUserId(cvBirdUserDTO.getCvBirdUserId());
        cvBirdUser.setRegistrationDate(cvBirdUserDTO.getRegistrationDate());
        cvBirdUser.setTelegramId(cvBirdUserDTO.getTelegramId());
        cvBirdUser.setTelegramFirstName(cvBirdUserDTO.getTelegramFirstName());
        cvBirdUser.setTelegramUserName(cvBirdUserDTO.getTelegramUserName());
        cvBirdUser.setTelegramLastName(cvBirdUserDTO.getTelegramLastName());
        cvBirdUser.setTelegramIsBot(cvBirdUserDTO.getTelegramIsBot());
        cvBirdUser.setTelegramLanguageCode(cvBirdUserDTO.getTelegramLanguageCode());
        cvBirdUser.setEmail(cvBirdUserDTO.getEmail());
        if (cvBirdUserDTO.getPassword() != null) {
            cvBirdUser.setPassword((passwordEncoder.encode(cvBirdUserDTO.getPassword())));
        }

        return cvBirdUser;
    }

    public CVBirdUserDTO toDTO(CVBirdUser cvBirdUser) {
        CVBirdUserDTO cVBirdUserDTO = new CVBirdUserDTO();
        cVBirdUserDTO.setCvBirdUserId(cvBirdUser.getCvBirdUserId());
        cVBirdUserDTO.setRegistrationDate(cvBirdUser.getRegistrationDate());
        cVBirdUserDTO.setTelegramId(cvBirdUser.getTelegramId());
        cVBirdUserDTO.setTelegramFirstName(cvBirdUser.getTelegramFirstName());
        cVBirdUserDTO.setTelegramUserName(cvBirdUser.getTelegramUserName());
        cVBirdUserDTO.setTelegramLastName(cvBirdUser.getTelegramLastName());
        cVBirdUserDTO.setTelegramIsBot(cvBirdUser.getTelegramIsBot());
        cVBirdUserDTO.setTelegramLanguageCode(cvBirdUser.getTelegramLanguageCode());
        cVBirdUserDTO.setEmail(cVBirdUserDTO.getEmail());
        cVBirdUserDTO.setPassword(cVBirdUserDTO.getPassword());
        return cVBirdUserDTO;
    }
}
