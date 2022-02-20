package anime.app.services.dto.conversion;

import anime.app.entities.AuthenticationToken;
import anime.app.entities.database.forum.ForumCategory;
import anime.app.openapi.model.AuthenticationTokenDTO;
import anime.app.openapi.model.ForumCategoryDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class DTOConversionServiceTest {

	@InjectMocks
	DTOConversionService dtoConversionService;

	@Test
	void convertToDTO_NullCategory_ThrowException() {
		//given
		ForumCategory category = null;

		//when
		Exception exception = assertThrows(NullPointerException.class, () ->
				dtoConversionService.convertToDTO(category)
		);

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(NullPointerException.class)
		));
	}

	@Test
	void convertToDTO_CorrectCategory_ReturnDTO() {
		//given
		int id = 1;
		String description = "description";
		String name = "title";
		ForumCategory category = ForumCategory.builder()
				.id(id)
				.name(name)
				.description(description)
				.build();

		//when
		ForumCategoryDTO categoryDTO = dtoConversionService.convertToDTO(category);

		//then
		assertThat(categoryDTO, allOf(
				notNullValue(),
				instanceOf(ForumCategoryDTO.class)
		));
		assertThat(categoryDTO.getId(), equalTo((long) id));
		assertThat(categoryDTO.getDescription(), equalTo(description));
		assertThat(categoryDTO.getName(), equalTo(name));
	}

	@Test
	void convertToDTO_NullAuthenticationToken_ThrowException() {
		//given
		AuthenticationToken token = null;

		//when
		Exception exception = assertThrows(NullPointerException.class, () ->
				dtoConversionService.convertToDTO(token)
		);

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(NullPointerException.class)
		));
	}

	@Test
	void convertToDTO_CorrectAuthenticationToken_ReturnDTO() {
		//given
		String accessToken = "accessToken";
		int expiresIn = 1;
		String tokenType = "Bearer";
		String refreshToken = "refreshToken";
		AuthenticationToken token = AuthenticationToken.builder()
				.access_token(accessToken)
				.expires_in(expiresIn)
				.token_type(tokenType)
				.refresh_token(refreshToken)
				.build();

		//when
		AuthenticationTokenDTO tokenDTO = dtoConversionService.convertToDTO(token);

		//then
		assertThat(tokenDTO, allOf(
				notNullValue(),
				instanceOf(AuthenticationTokenDTO.class)
		));
		assertThat(tokenDTO.getAccessToken(), equalTo(accessToken));
		assertThat(tokenDTO.getExpiresIn(), equalTo(expiresIn));
		assertThat(tokenDTO.getTokenType(), equalTo(tokenType));
		assertThat(tokenDTO.getRefreshToken(), equalTo(refreshToken));
	}
}