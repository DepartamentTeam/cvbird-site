package ai.cvbird.cvbirdsite.service;

import ai.cvbird.cvbirdsite.model.User;
import ai.cvbird.cvbirdsite.model.VerificationToken;

public interface UserService {
    User registerNewUserAccount(User user);

    void createVerificationTokenForUser(User user, String token);

    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);
}
