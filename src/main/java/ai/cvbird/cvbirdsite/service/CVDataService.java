package ai.cvbird.cvbirdsite.service;

import ai.cvbird.cvbirdsite.dto.CVBirdUserDTO;
import ai.cvbird.cvbirdsite.model.CVBirdUser;
import ai.cvbird.cvbirdsite.model.CVData;

public interface CVDataService {

    String getCVFile (String telegramId);
    String getCVFile (CVBirdUserDTO cvBirdUserDTO);
    String getCVFile (Long id);
    String getCVFile (CVBirdUser cvBirdUser);

    CVData setCVFile (String telegramId, String cvFile);

    CVData setCVFile(CVBirdUser cvBirdUser, String cvFile);

    CVData getCVData(String telegramId);

    Boolean deleteCVData(CVBirdUser cvBirdUser);

}
