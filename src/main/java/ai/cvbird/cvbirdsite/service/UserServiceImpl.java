package ai.cvbird.cvbirdsite.service;

import ai.cvbird.cvbirdsite.dao.UserRepository;
import ai.cvbird.cvbirdsite.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public User registerNewUserAccount(User user) {
        return userRepository.save(user);
    }
}
