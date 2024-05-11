package ai.cvbird.cvbirdsite.service;

import ai.cvbird.cvbirdsite.dto.TelegramUserDTO;
import ai.cvbird.cvbirdsite.dto.UserDto;
import ai.cvbird.cvbirdsite.model.CVData;
import ai.cvbird.cvbirdsite.model.User;

public interface CVDataService {

    byte[] getCVFile (String telegramId);
    byte[] getCVFile (TelegramUserDTO telegramUserDTO);
    byte[] getCVFile (Long id);
    byte[] getCVFile (User user);

    CVData setCVFile (TelegramUserDTO telegramUserDTO, byte[] cvFile);

    CVData setCVFile (String telegramId, byte[] cvFile);

    CVData setCVFile (User user, byte[] cvFile);

}
