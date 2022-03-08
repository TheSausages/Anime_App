package anime.app.services.dto.conversion;

import anime.app.entities.AuthenticationToken;
import anime.app.entities.database.forum.ForumCategory;
import anime.app.entities.database.forum.Tag;
import anime.app.openapi.model.AuthenticationTokenDTO;
import anime.app.openapi.model.ForumCategoryDTO;
import anime.app.openapi.model.TagDTO;

/**
 * Interface for a DTO Conversion Service. Each implementation must use this interface.
 * <p>
 * Conversion methods for:
 * <ul>
 *     <li>Complete DTO objects (ex. {@link anime.app.openapi.model.CompleteUserDTO}) should be named <i>convertToDTO</i></li>
 *     <li>Simple DTO objects (ex. {@link anime.app.openapi.model.SimpleUserDTO}) should be named <i>convertToSimpleDTO</i></li>
 * </ul>
 */
public interface DTOConversionServiceInterface {
	/** Convert a {@link ForumCategory} into it's DTO */
	ForumCategoryDTO convertToDTO(ForumCategory category);

	/** Convert a {@link AuthenticationToken} into it's DTO */
	AuthenticationTokenDTO convertToDTO(AuthenticationToken authenticationToken);

	/** Convert a {@link Tag} into it's DTO */
	TagDTO convertToDTO(Tag tag);
}
