package nz.gershunov.testtask.pccw.repository;

import nz.gershunov.testtask.pccw.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    @Query("SELECT u FROM User u WHERE u.isDeleted = 0")
    public List<User> findAll();
}
