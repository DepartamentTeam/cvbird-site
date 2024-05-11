package ai.cvbird.cvbirdsite.dao;

import ai.cvbird.cvbirdsite.model.CVData;
import ai.cvbird.cvbirdsite.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CVDataRepository extends JpaRepository<CVData, Long> {

    CVData findByCvbirdUser(User user);

}
