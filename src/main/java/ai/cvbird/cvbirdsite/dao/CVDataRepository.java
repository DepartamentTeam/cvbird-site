package ai.cvbird.cvbirdsite.dao;

import ai.cvbird.cvbirdsite.model.CVBirdUser;
import ai.cvbird.cvbirdsite.model.CVData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CVDataRepository extends JpaRepository<CVData, Long> {

    CVData findByCvbirdUser(CVBirdUser cvBirdUser);

    Integer deleteByCvbirdUser(CVBirdUser cvBirdUser);

    void deleteById(Long id);

}
