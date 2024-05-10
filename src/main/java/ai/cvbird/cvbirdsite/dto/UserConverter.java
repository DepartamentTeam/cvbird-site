package ai.cvbird.cvbirdsite.dto;

import ai.cvbird.cvbirdsite.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    @Autowired
    PasswordEncoder passwordEncoder;

    public User fromUserDTO(UserDto userDto) {
        return User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();
    }

    public UserDto fromUser(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .build();
    }
}
