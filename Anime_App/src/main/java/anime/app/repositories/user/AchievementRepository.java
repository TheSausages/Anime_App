package anime.app.repositories.user;

import anime.app.entities.database.user.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the {@link anime.app.entities.database.user.Achievement} class (<i>achievements</i> table).
 */
@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Integer> {
}