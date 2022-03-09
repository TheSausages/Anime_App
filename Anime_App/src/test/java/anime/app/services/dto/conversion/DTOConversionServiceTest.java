package anime.app.services.dto.conversion;

import anime.app.entities.AuthenticationToken;
import anime.app.entities.database.anime.Anime;
import anime.app.entities.database.forum.ForumCategory;
import anime.app.entities.database.forum.Tag;
import anime.app.openapi.model.*;
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
		ForumCategory category = ForumCategory.builder()
				.id(1)
				.name("title")
				.description("description")
				.build();

		ForumCategoryDTO expectedCategoryDTO = ForumCategoryDTO.builder()
				.id((long) category.getId())
				.name(category.getName())
				.description(category.getDescription())
				.build();

		//when
		ForumCategoryDTO actualCategoryDTO = dtoConversionService.convertToDTO(category);

		//then
		assertThat(actualCategoryDTO, allOf(
				notNullValue(),
				instanceOf(ForumCategoryDTO.class),
				equalTo(expectedCategoryDTO)
		));
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
		AuthenticationToken token = AuthenticationToken.builder()
				.access_token("accessToken")
				.expires_in(1)
				.token_type("Bearer")
				.refresh_token("refreshToken")
				.build();

		AuthenticationTokenDTO expectedTokenDTO = AuthenticationTokenDTO.builder()
				.accessToken(token.getAccess_token())
				.expiresIn(token.getExpires_in())
				.tokenType(token.getToken_type())
				.refreshToken(token.getRefresh_token())
				.build();

		//when
		AuthenticationTokenDTO actualTokenDTO = dtoConversionService.convertToDTO(token);

		//then
		assertThat(actualTokenDTO, allOf(
				notNullValue(),
				instanceOf(AuthenticationTokenDTO.class),
				equalTo(expectedTokenDTO)
		));
	}

	@Test
	void convertToDTO_NullTag_ThrowException() {
		//given
		Tag tag = null;

		//when
		Exception exception = assertThrows(NullPointerException.class, () ->
				dtoConversionService.convertToDTO(tag)
		);

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(NullPointerException.class)
		));
	}

	@Test
	void convertToDTO_CorrectTag_ReturnDTO() {
		//given
		Tag tag = Tag.builder()
				.id(1)
				.name("name")
				.color("color")
				.importance(Tag.TagImportance.HIGH)
				.build();
		TagDTO expectedTagDTO = TagDTO.builder()
				.id((long) tag.getId())
				.name(tag.getName())
				.color(tag.getColor())
				.importance(TagImportance.fromValue(tag.getImportance().name()))
				.build();

		//when
		TagDTO actualTagDTO = dtoConversionService.convertToDTO(tag);

		//then
		assertThat(actualTagDTO, allOf(
				notNullValue(),
				instanceOf(TagDTO.class),
				equalTo(expectedTagDTO)
		));
	}

	@Test
	void convertToDTO_NullAnime_ThrowException() {
		//given
		Anime anime = null;

		//when
		Exception exception = assertThrows(NullPointerException.class, () ->
				dtoConversionService.convertToDTO(anime)
		);

		//then
		assertThat(exception, allOf(
				notNullValue(),
				instanceOf(NullPointerException.class)
		));
	}

	@Test
	void convertToDTO_CorrectAnime_ReturnDTO() {
		//given
		Anime anime = Anime.builder()
				.id(1)
				.averageScore(25.0)
				.averageEpisodeLength(25)
				.nrOfFavourites(5)
				.nrOfReviews(5)
				.nrOfScores(5)
				.build();

		LocalAnimeInformationDTO expectedDTO = LocalAnimeInformationDTO.builder()
				.animeId((long) anime.getId())
				.nrOfFavourites(anime.getNrOfFavourites())
				.nrOfReviews(anime.getNrOfReviews())
				.averageScore(anime.getAverageScore())
				.build();

		//when
		LocalAnimeInformationDTO actualDTO = dtoConversionService.convertToDTO(anime);

		//then
		assertThat(actualDTO, allOf(
				notNullValue(),
				instanceOf(LocalAnimeInformationDTO.class),
				equalTo(expectedDTO)
		));
	}
}