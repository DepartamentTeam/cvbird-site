package ai.cvbird.cvbirdsite.service;

import ai.cvbird.cvbirdsite.dto.CVBirdUserDTO;
import ai.cvbird.cvbirdsite.dto.TelegramUserDTO;
import ai.cvbird.cvbirdsite.dto.UserDto;
import ai.cvbird.cvbirdsite.model.CVBirdUser;
import ai.cvbird.cvbirdsite.model.CVData;
import ai.cvbird.cvbirdsite.model.User;

public interface CVDataService {

    byte[] getCVFile (String telegramId);
    byte[] getCVFile (CVBirdUserDTO telegramUserDTO);
    byte[] getCVFile (Long id);
    byte[] getCVFile (CVBirdUser cvBirdUser);

    CVData setCVFile (String telegramId, byte[] cvFile);

    CVData setCVFile(CVBirdUser cvBirdUser, byte[] cvFile);

}
