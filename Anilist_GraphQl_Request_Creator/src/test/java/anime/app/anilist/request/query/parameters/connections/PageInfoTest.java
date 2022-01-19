package anime.app.anilist.request.query.parameters.connections;

import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static anime.app.anilist.request.query.parameters.connections.PageInfo.PAGE_INFO_TITLE;
import static anime.app.anilist.request.utils.QueryTitleWithParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class PageInfoTest {

	@Test
	void getPageInfoBuilder__ReturnValidBuilder() {
		//given

		//when
		PageInfo.PageInfoBuilder builder = PageInfo.getPageInfoBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(PageInfo.PageInfoBuilder.class)
		));
	}

	@Test
	void getPageInfoStringWithoutFieldName__ReturnCorrectString() {
		//given
		Set<ParameterString> elements = TestUtils.getParameterStringSetField("perPage");

		//when
		String actualString = PageInfo.getPageInfoBuilder()
				.perPage()
				.build()
				.getPageInfoStringWithoutFieldName();

		//then
		assertThat(actualString, allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(elements)
		));
	}

	@Nested
	@DisplayName("Test Page Info Builder")
	class PageInfoBuilderTest {
		@Test
		void pageInfoBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> PageInfo.getPageInfoBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void pageInfoBuilder_Total_ReturnCorrectString() {
			//given
			Set<ParameterString> elements = TestUtils.getParameterStringSetField("total");

			//when
			PageInfo actualPageInfo = PageInfo.getPageInfoBuilder().total().build();

			//then
			assertThat(actualPageInfo.getPageInfoString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(PAGE_INFO_TITLE, elements)
			));
		}

		@Test
		void pageInfoBuilder_PerPage_ReturnCorrectString() {
			//given
			Set<ParameterString> elements = TestUtils.getParameterStringSetField("perPage");

			//when
			PageInfo actualPageInfo = PageInfo.getPageInfoBuilder().perPage().build();

			//then
			assertThat(actualPageInfo.getPageInfoString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(PAGE_INFO_TITLE, elements)
			));
		}

		@Test
		void pageInfoBuilder_CurrentPage_ReturnCorrectString() {
			//given
			Set<ParameterString> elements = TestUtils.getParameterStringSetField("currentPage");

			//when
			PageInfo actualPageInfo = PageInfo.getPageInfoBuilder().currentPage().build();

			//then
			assertThat(actualPageInfo.getPageInfoString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(PAGE_INFO_TITLE, elements)
			));
		}

		@Test
		void pageInfoBuilder_LastPage_ReturnCorrectString() {
			//given
			Set<ParameterString> elements = TestUtils.getParameterStringSetField("lastPage");

			//when
			PageInfo actualPageInfo = PageInfo.getPageInfoBuilder().lastPage().build();

			//then
			assertThat(actualPageInfo.getPageInfoString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(PAGE_INFO_TITLE, elements)
			));
		}

		@Test
		void pageInfoBuilder_HasNextPage_ReturnCorrectString() {
			//given
			Set<ParameterString> elements = TestUtils.getParameterStringSetField("hasNextPage");

			//when
			PageInfo actualPageInfo = PageInfo.getPageInfoBuilder().hasNextPage().build();

			//then
			assertThat(actualPageInfo.getPageInfoString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(PAGE_INFO_TITLE, elements)
			));
		}

		@Test
		void pageInfoBuilder_AllParameters_ReturnCorrectString() {
			//given
			Set<ParameterString> elements = TestUtils.getParameterStringSetField(
					"total",
					"perPage",
					"currentPage",
					"lastPage",
					"hasNextPage"
			);

			//when
			PageInfo actualPageInfo = PageInfo.getPageInfoBuilder()
					.total()
					.perPage()
					.currentPage()
					.lastPage()
					.hasNextPage()
					.build();

			//then
			assertThat(actualPageInfo.getPageInfoString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(PAGE_INFO_TITLE, elements)
			));
		}
	}
}