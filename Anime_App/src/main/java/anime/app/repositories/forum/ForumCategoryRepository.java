package anime.app.repositories.forum;

import anime.app.entities.database.forum.ForumCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the {@link ForumCategory} class (<i>forum_categories</i> table).
 */
@Repository
public interface ForumCategoryRepository extends JpaRepository<ForumCategory, Integer> {
}
