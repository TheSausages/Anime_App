package anime.app.anilist.request.query.parameters.connections.studio;

import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static anime.app.anilist.request.query.parameters.connections.studio.StudioEdge.STUDIO_EDGE_TITLE;
import static anime.app.anilist.request.utils.QueryTitleWithParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class StudioEdgeTest {

	@Test
	void getStudioEdgeWithoutFieldName__ReturnCorrectString() {
		//given
		Set<ParameterString> expectedEdge = TestUtils.buildFieldParameterStringSet(
				"id"
		);

		//when
		StudioEdge actualEdge = StudioEdge.getStudioEdgedBuilder()
				.id()
				.build();

		//then
		assertThat(actualEdge.getStudioEdgeWithoutFieldName(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedEdge)
		));
	}

	@Test
	void getStudioEdgedBuilder__ReturnValidBuilder() {
		//given

		//when
		StudioEdge.StudioEdgeBuilder builder = StudioEdge.getStudioEdgedBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(StudioEdge.StudioEdgeBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Studio Edge Builder")
	class StudioEdgeBuilderTest {
		@Test
		void studioEdgeBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> StudioEdge.getStudioEdgedBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void studioEdgeBuilder_Node_ReturnCorrectString() {
			//given
			Studio studio = Studio.getStudioBuilder().id().build();
			Set<ParameterString> expectedEdge = TestUtils.buildFieldParameterStringSet(
					"node " + studio.getStudioWithoutFieldName()
			);

			//when
			StudioEdge actualEdge = StudioEdge.getStudioEdgedBuilder()
					.node(studio)
					.build();
			//then
			assertThat(actualEdge.getStudioEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(STUDIO_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void studioEdgeBuilder_Id_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedEdge = TestUtils.buildFieldParameterStringSet(
					"id"
			);

			//when
			StudioEdge actualEdge = StudioEdge.getStudioEdgedBuilder()
					.id()
					.build();
			//then
			assertThat(actualEdge.getStudioEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(STUDIO_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void studioEdgeBuilder_IsMain_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedEdge = TestUtils.buildFieldParameterStringSet(
					"isMain"
			);

			//when
			StudioEdge actualEdge = StudioEdge.getStudioEdgedBuilder()
					.isMain()
					.build();
			//then
			assertThat(actualEdge.getStudioEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(STUDIO_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void studioEdgeBuilder_FavouriteOrder_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedEdge = TestUtils.buildFieldParameterStringSet(
					"favouriteOrder"
			);

			//when
			StudioEdge actualEdge = StudioEdge.getStudioEdgedBuilder()
					.favouriteOrder()
					.build();
			//then
			assertThat(actualEdge.getStudioEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(STUDIO_EDGE_TITLE, expectedEdge)
			));
		}

		@Test
		void studioEdgeBuilder_AllParameters_ReturnCorrectString() {
			//given
			Studio studio = Studio.getStudioBuilder().id().build();
			Set<ParameterString> expectedEdge = TestUtils.buildFieldParameterStringSet(
					"id",
					"isMain",
					"favouriteOrder",
					"node " + studio.getStudioWithoutFieldName()
			);

			//when
			StudioEdge actualEdge = StudioEdge.getStudioEdgedBuilder()
					.id()
					.isMain()
					.favouriteOrder()
					.node(studio)
					.build();
			//then
			assertThat(actualEdge.getStudioEdgeString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(STUDIO_EDGE_TITLE, expectedEdge)
			));
		}
	}
}