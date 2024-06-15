package ai.cvbird.cvbirdsite.dao;

import ai.cvbird.cvbirdsite.model.Balance;
import ai.cvbird.cvbirdsite.model.CVBirdUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance, Long> {

        Balance findByCvbirdUser(CVBirdUser cvBirdUser);
}