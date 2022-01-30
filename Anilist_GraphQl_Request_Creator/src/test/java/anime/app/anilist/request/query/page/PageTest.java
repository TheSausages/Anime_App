package anime.app.anilist.request.query.page;

import anime.app.anilist.request.query.media.Field;
import anime.app.anilist.request.query.media.Media;
import anime.app.anilist.request.query.parameters.common.BasicQueryParameters;
import anime.app.anilist.request.query.parameters.connections.PageInfo;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static anime.app.anilist.request.query.page.Page.PAGE_TITLE;
import static anime.app.anilist.request.utils.QueryTitleWithArgumentsAndParametersMatcher.containsTitleWithArgumentsAndParameters;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class PageTest {

	@Test
	void fromMediaAndPageInfo_NullMedia_ThrowException() {
		//given
		int page = 1;
		int perPage = 30;
		Media media = null;
		PageInfo info = PageInfo.getPageInfoBuilder().total().build();

		//when
		Exception thrownException = Assertions.assertThrows(
				NullPointerException.class,
				() -> Page.fromMediaAndPageInfo(page, perPage, media, info)
		);

		//then
		assertThat(thrownException, instanceOf(NullPointerException.class));
		assertThat(thrownException.getMessage(), notNullValue());
	}

	@Test
	void fromMediaAndPageInfo_NullPageInfo_ThrowException() {
		//given
		int id = 1;
		int page = 1;
		int perPage = 30;
		Media media = Media
				.getMediaArgumentBuilder(Field.getFieldBuilder().parameter(BasicQueryParameters.ID).build())
				.id(id)
				.build();
		PageInfo info = null;

		//when
		Exception thrownException = Assertions.assertThrows(
				NullPointerException.class,
				() -> Page.fromMediaAndPageInfo(page, perPage, media, info)
		);

		//then
		assertThat(thrownException, instanceOf(NullPointerException.class));
		assertThat(thrownException.getMessage(), notNullValue());
	}

	@Test
	void fromMediaAndPageInfo_CorrectData_NoException() {
		//given
		int id = 1;
		int page = 1;
		int perPage = 30;
		Media media = Media
				.getMediaArgumentBuilder(Field.getFieldBuilder().parameter(BasicQueryParameters.ID).build())
				.id(id)
				.build();
		PageInfo info = PageInfo.getPageInfoBuilder().total().build();

		Map<String, Object> expectedParameters = Map.of("page", page, "perPage", perPage, "id", id);
		Set<String> expectedQueryParameters = Set.of(
				"$page: Int", "$perPage: Int", "$id: Int"
		);
		List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet(
				"page: $page, perPage: $perPage, id: $id"
		);

		//when
		Page actualPage = Page.fromMediaAndPageInfo(page, perPage, media, info);

		//then
		assertThat(actualPage.getElementString(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleWithArgumentsAndParameters(PAGE_TITLE, expectedArguments, TestUtils.buildFieldParameterStringSet(
						Media.MEDIA_TITLE.toLowerCase(Locale.ROOT) + media.getRequestedMediaFieldsWithArguments(),
						info.getPageInfoString()
				)
		)));

		assertThat(actualPage.getQueryParameters(), allOf(
				notNullValue(),
				instanceOf(Set.class),
				containsInAnyOrder(expectedQueryParameters.toArray())
		));

		assertThat(actualPage.getVariables(), allOf(
				notNullValue(),
				instanceOf(Map.class),
				equalTo(expectedParameters)
		));
	}

	@Test
	void fromMedia_NullMedia_ThrowException() {
		//given
		int page = 1;
		int perPage = 30;
		Media media = null;

		//when
		Exception thrownException = Assertions.assertThrows(
				NullPointerException.class,
				() -> Page.fromMedia(page, perPage, media)
		);

		//then
		assertThat(thrownException, instanceOf(NullPointerException.class));
		assertThat(thrownException.getMessage(), notNullValue());
	}

	@Test
	void fromMedia_CorrectData_NoException() {
		//given
		int id = 1;
		int page = 1;
		int perPage = 30;
		Media media = Media
				.getMediaArgumentBuilder(Field.getFieldBuilder().parameter(BasicQueryParameters.ID).build())
				.id(id)
				.build();

		Map<String, Object> expectedParameters = Map.of("page", page, "perPage", perPage, "id", id);
		Set<String> expectedQueryParameters = Set.of(
				"$page: Int", "$perPage: Int", "$id: Int"
		);
		List<String> expectedArguments = TestUtils.buildArgumentParameterStringSet(
				"page: $page, perPage: $perPage, id: $id"
		);

		//when
		Page actualPage = Page.fromMedia(page, perPage, media);

		//then
		assertThat(actualPage.getElementString(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleWithArgumentsAndParameters(PAGE_TITLE, expectedArguments, TestUtils.buildFieldParameterStringSet(
								Media.MEDIA_TITLE.toLowerCase(Locale.ROOT) + media.getRequestedMediaFieldsWithArguments()
						)
				)));

		assertThat(actualPage.getQueryParameters(), allOf(
				notNullValue(),
				instanceOf(Set.class),
				containsInAnyOrder(expectedQueryParameters.toArray())
		));

		assertThat(actualPage.getVariables(), allOf(
				notNullValue(),
				instanceOf(Map.class),
				equalTo(expectedParameters)
		));
	}
}