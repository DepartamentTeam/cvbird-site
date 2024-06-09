package ai.cvbird.cvbirdsite.service;

import ai.cvbird.cvbirdsite.dto.CVBirdUserDTO;
import ai.cvbird.cvbirdsite.dto.TelegramStatisticDTO;
import ai.cvbird.cvbirdsite.dto.TelegramUserDTO;
import ai.cvbird.cvbirdsite.model.CVBirdUser;
import ai.cvbird.cvbirdsite.model.TelegramStatistic;
import ai.cvbird.cvbirdsite.model.TelegramUser;

public interface TelegramService {
    TelegramUser registerTelegramUser(TelegramUserDTO telegramUserDTO);

    TelegramUser getTelegramUserById(String telegramId);

    TelegramUser saveTelegramUser(TelegramUserDTO telegramUserDTO);

    CVBirdUser saveUnknownUser(CVBirdUserDTO cvBirdUserDTO);

    CVBirdUser saveUnknownUser(TelegramStatisticDTO telegramStatisticDTO);

    CVBirdUser getCVBirdUser(String telegramId);
}
