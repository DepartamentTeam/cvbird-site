package ai.cvbird.cvbirdsite.dto;


import ai.cvbird.cvbirdsite.model.TelegramUser;
import org.springframework.stereotype.Component;

@Component
public class TelegramUserConverter {
    public TelegramUser fromDTO(TelegramUserDTO telegramUserDTO) {
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setId(telegramUserDTO.getId());
        telegramUser.setEmail(telegramUserDTO.getEmail());
        telegramUser.setRegistrationDate(telegramUserDTO.getRegistrationDate());
        telegramUser.setTelegramId(telegramUserDTO.getTelegramId());
        telegramUser.setTelegramFirstName(telegramUserDTO.getTelegramFirstName());
        telegramUser.setTelegramUserName(telegramUserDTO.getTelegramUserName());
        telegramUser.setTelegramLastName(telegramUserDTO.getTelegramLastName());
        telegramUser.setTelegramIsBot(telegramUserDTO.getTelegramIsBot());
        telegramUser.setTelegramLanguageCode(telegramUserDTO.getTelegramLanguageCode());
        telegramUser.setCvbirdUser(telegramUser.getCvbirdUser());
        return telegramUser;
    }

    public TelegramUserDTO toDTO(TelegramUser telegramUser) {
        TelegramUserDTO telegramUserDTO = new TelegramUserDTO();
        telegramUserDTO.setId(telegramUser.getId());
        telegramUserDTO.setEmail(telegramUser.getEmail());
        telegramUserDTO.setRegistrationDate(telegramUser.getRegistrationDate());
        telegramUserDTO.setTelegramId(telegramUser.getTelegramId());
        telegramUserDTO.setTelegramFirstName(telegramUser.getTelegramFirstName());
        telegramUserDTO.setTelegramUserName(telegramUser.getTelegramUserName());
        telegramUserDTO.setTelegramLastName(telegramUser.getTelegramLastName());
        telegramUserDTO.setTelegramIsBot(telegramUser.getTelegramIsBot());
        telegramUser.setTelegramLanguageCode(telegramUser.getTelegramLanguageCode());
        telegramUserDTO.setCvbirdUserId(telegramUser.getCvbirdUser());
        return telegramUserDTO;
    }
}
