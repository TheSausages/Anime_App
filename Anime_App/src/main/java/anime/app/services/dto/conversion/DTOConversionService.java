package anime.app.services.dto.conversion;

import anime.app.entities.AuthenticationToken;
import anime.app.entities.database.forum.ForumCategory;
import anime.app.entities.database.forum.Tag;
import anime.app.openapi.model.AuthenticationTokenDTO;
import anime.app.openapi.model.ForumCategoryDTO;
import anime.app.openapi.model.TagDTO;
import anime.app.openapi.model.TagImportance;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Default implementation for the {@link DTOConversionServiceInterface} interface.
 */
@Service
public class DTOConversionService implements DTOConversionServiceInterface {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForumCategoryDTO convertToDTO(ForumCategory category) {
		Objects.requireNonNull(category, "Category cannot be null");

		return ForumCategoryDTO.builder()
				.id((long) category.getId())
				.name(category.getName())
				.description(category.getDescription())
				.build();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AuthenticationTokenDTO convertToDTO(AuthenticationToken authenticationToken) {
		Objects.requireNonNull(authenticationToken, "Authorization token cannot be null");

		return AuthenticationTokenDTO.builder()
				.accessToken(authenticationToken.getAccess_token())
				.expiresIn(authenticationToken.getExpires_in())
				.refreshToken(authenticationToken.getRefresh_token())
				.tokenType(authenticationToken.getToken_type())
				.build();
	}

	@Override
	public TagDTO convertToDTO(Tag tag) {
		Objects.requireNonNull(tag, "Tag cannot be null");

		return TagDTO.builder()
				.id((long) tag.getId())
				.name(tag.getName())
				.importance(TagImportance.fromValue(tag.getImportance().name()))
				.color(tag.getColor())
				.build();
	}
}
