package ai.cvbird.cvbirdsite.dto;


import ai.cvbird.cvbirdsite.model.TelegramStatistic;
import ai.cvbird.cvbirdsite.model.TelegramUser;
import org.springframework.stereotype.Component;

@Component
public class TelegramStatisticConverter {
    public TelegramStatistic fromDTO(TelegramStatisticDTO telegramStatisticDTO) {
        TelegramStatistic telegramStatistic = new TelegramStatistic();
        telegramStatistic.setId(telegramStatisticDTO.getId());
        telegramStatistic.setRegistrationDate(telegramStatisticDTO.getRegistrationDate());
        telegramStatistic.setTelegramId(telegramStatisticDTO.getTelegramId());
        telegramStatistic.setTelegramFirstName(telegramStatisticDTO.getTelegramFirstName());
        telegramStatistic.setTelegramUserName(telegramStatisticDTO.getTelegramUserName());
        telegramStatistic.setTelegramLastName(telegramStatisticDTO.getTelegramLastName());
        telegramStatistic.setTelegramIsBot(telegramStatisticDTO.getTelegramIsBot());
        telegramStatistic.setTelegramLanguageCode(telegramStatisticDTO.getTelegramLanguageCode());
        return telegramStatistic;
    }

    public TelegramStatisticDTO toDTO(TelegramStatistic telegramStatistic) {
        TelegramStatisticDTO telegramStatisticDTO = new TelegramStatisticDTO();
        telegramStatisticDTO.setId(telegramStatistic.getId());
        telegramStatisticDTO.setRegistrationDate(telegramStatistic.getRegistrationDate());
        telegramStatisticDTO.setTelegramId(telegramStatistic.getTelegramId());
        telegramStatisticDTO.setTelegramFirstName(telegramStatistic.getTelegramFirstName());
        telegramStatisticDTO.setTelegramUserName(telegramStatistic.getTelegramUserName());
        telegramStatisticDTO.setTelegramLastName(telegramStatistic.getTelegramLastName());
        telegramStatisticDTO.setTelegramIsBot(telegramStatistic.getTelegramIsBot());
        telegramStatisticDTO.setTelegramLanguageCode(telegramStatistic.getTelegramLanguageCode());
        return telegramStatisticDTO;
    }
}
