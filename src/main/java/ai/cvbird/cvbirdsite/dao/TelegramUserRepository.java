package ai.cvbird.cvbirdsite.dao;

import ai.cvbird.cvbirdsite.model.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {

    TelegramUser findByEmail(String email);

    TelegramUser findByTelegramId(String id);
}
