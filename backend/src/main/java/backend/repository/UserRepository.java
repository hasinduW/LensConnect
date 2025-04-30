package backend.repository;

import backend.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    // e.g. lookup by username
    UserModel findByUsername(String username);
}
