package ai.cvbird.cvbirdsite.dao;

import ai.cvbird.cvbirdsite.model.TelegramStatistic;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TelegramStatisticRepository extends JpaRepository<TelegramStatistic, Long> {
    TelegramStatistic findByTelegramId(String telegramId);
}
