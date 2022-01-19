package anime.app.anilist.request.query.parameters.connections.studio;

import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.connections.PageInfo;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static anime.app.anilist.request.query.parameters.connections.studio.StudioConnection.STUDIO_CONNECTION_TITLE;
import static anime.app.anilist.request.utils.QueryTitleWithParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class StudioConnectionTest {

	@Test
	void getStudioConnectionWithoutFieldName__ReturnCorrectString() {
		//given
		PageInfo info = PageInfo.getPageInfoBuilder().total().build();
		Set<ParameterString> expectedConnection = TestUtils.getParameterStringSetField(
				info.getPageInfoString()
		);

		//when
		StudioConnection actualConnection = StudioConnection.getStudioConnectionBuilder()
				.pageInfo(info)
				.build();

		//then
		assertThat(actualConnection.getStudioConnectionWithoutFieldName(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedConnection)
		));
	}

	@Test
	void getStudioConnectionBuilder__ReturnValidBuilder() {
		//given

		//when
		StudioConnection.StudioConnectionBuilder builder = StudioConnection.getStudioConnectionBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(StudioConnection.StudioConnectionBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Studio Connection Builder")
	class StudioConnectionBuilderTest {
		@Test
		void studioConnectionBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> StudioConnection.getStudioConnectionBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void studioConnectionBuilder_Edge_ReturnCorrectString() {
			//given
			StudioEdge edge = StudioEdge.getStudioEdgedBuilder().id().build();
			Set<ParameterString> expectedConnection = TestUtils.getParameterStringSetField(
					"edges " + edge.getStudioEdgeWithoutFieldName()
			);

			//when
			StudioConnection actualConnection = StudioConnection.getStudioConnectionBuilder()
					.edges(edge)
					.build();

			//then
			assertThat(actualConnection.getStudioConnectionString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(STUDIO_CONNECTION_TITLE, expectedConnection)
			));
		}

		@Test
		void studioConnectionBuilder_Nodes_ReturnCorrectString() {
			//given
			Studio studio = Studio.getStudioBuilder().id().build();
			Set<ParameterString> expectedConnection = TestUtils.getParameterStringSetField(
					"nodes " + studio.getStudioWithoutFieldName()
			);

			//when
			StudioConnection actualConnection = StudioConnection.getStudioConnectionBuilder()
					.nodes(studio)
					.build();

			//then
			assertThat(actualConnection.getStudioConnectionString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(STUDIO_CONNECTION_TITLE, expectedConnection)
			));
		}

		@Test
		void studioConnectionBuilder_PageInfo_ReturnCorrectString() {
			//given
			PageInfo info = PageInfo.getPageInfoBuilder().total().build();
			Set<ParameterString> expectedConnection = TestUtils.getParameterStringSetField(
					info.getPageInfoString()
			);

			//when
			StudioConnection actualConnection = StudioConnection.getStudioConnectionBuilder()
					.pageInfo(info)
					.build();

			//then
			assertThat(actualConnection.getStudioConnectionWithoutFieldName(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(expectedConnection)
			));
		}

		@Test
		void studioConnectionBuilder_AllParameters_ReturnCorrectString() {
			//given
			StudioEdge edge = StudioEdge.getStudioEdgedBuilder().id().build();
			Studio studio = Studio.getStudioBuilder().id().build();
			PageInfo info = PageInfo.getPageInfoBuilder().total().build();
			Set<ParameterString> expectedConnection = TestUtils.getParameterStringSetField(
					"edges " + edge.getStudioEdgeWithoutFieldName(),
					"nodes " + studio.getStudioWithoutFieldName(),
					info.getPageInfoString()
			);

			//when
			StudioConnection actualConnection = StudioConnection.getStudioConnectionBuilder()
					.pageInfo(info)
					.build();

			//then
			assertThat(actualConnection.getStudioConnectionWithoutFieldName(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(expectedConnection)
			));
		}
	}
}