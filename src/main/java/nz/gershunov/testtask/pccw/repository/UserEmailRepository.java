package nz.gershunov.testtask.pccw.repository;

import nz.gershunov.testtask.pccw.model.UserEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserEmailRepository extends JpaRepository<UserEmail, Long>
{
    @Query("select ue from UserEmail ue where ue.userId = ?1 ORDER BY ue.id")
    List<UserEmail> findByUserId(Long userId);
}
