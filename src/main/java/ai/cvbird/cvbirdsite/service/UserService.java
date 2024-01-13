package ai.cvbird.cvbirdsite.service;

import ai.cvbird.cvbirdsite.model.User;

public interface UserService {
    User registerNewUserAccount(User user);

    void createVerificationTokenForUser(User user, String token);
}
