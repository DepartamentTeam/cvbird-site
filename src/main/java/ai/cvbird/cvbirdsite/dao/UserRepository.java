package ai.cvbird.cvbirdsite.dao;

import ai.cvbird.cvbirdsite.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
