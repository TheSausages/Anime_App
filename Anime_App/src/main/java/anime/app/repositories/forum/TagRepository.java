package anime.app.repositories.forum;

import anime.app.entities.database.forum.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the {@link Tag} class (<i>tags</i> table).
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
}
