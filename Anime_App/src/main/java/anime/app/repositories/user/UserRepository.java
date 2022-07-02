package anime.app.repositories.user;

import anime.app.entities.database.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository for the {@link anime.app.entities.database.user.User} class (<i>users</i> table).
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
}
