package ai.cvbird.cvbirdsite.service;

import ai.cvbird.cvbirdsite.dto.TelegramUserDTO;
import ai.cvbird.cvbirdsite.model.TelegramUser;

public interface TelegramService {
    TelegramUser registerTelegramUser(TelegramUserDTO telegramUserDTO);

    TelegramUser getTelegramUserById(String telegramId);

    TelegramUser saveTelegramUser(TelegramUserDTO telegramUserDTO);

    TelegramUser provideTelegramUser(TelegramUserDTO telegramUserDTO);
}
