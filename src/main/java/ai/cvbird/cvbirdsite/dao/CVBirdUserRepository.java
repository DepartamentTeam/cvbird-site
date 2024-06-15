package ai.cvbird.cvbirdsite.dao;

import ai.cvbird.cvbirdsite.model.CVBirdUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CVBirdUserRepository extends JpaRepository<CVBirdUser, Long> {

    CVBirdUser findByEmail(String email);

    Optional<CVBirdUser> findByCvBirdUserId (Long cVBirdUserId);

    CVBirdUser findByTelegramId (String id);
}
