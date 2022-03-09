package anime.app.services.forum.tag;

import anime.app.entities.database.forum.Tag;
import anime.app.openapi.model.TagDTO;

import java.util.Set;

/**
 * Interface for a Tag Service. Each implementation must use this interface.
 */
public interface TagServiceInterface {
    /**
     * Get all forum tags from the database.
     * @return List of tags already converted to {@link TagDTO}
     */
    Set<TagDTO> getAllTags();

    /**
     * Find a forum Tag by its id.
     * @param id Id of the searched Tag
     * @return The found tag
     */
    Tag getTagById(int id);

    /**
     * Find a forum Tag by its id, and then transform it into it's DTO.
     * @param id Id of the searched Tag
     * @return The found tag in DTO form
     */
    TagDTO getTagDTOById(int id);
}
