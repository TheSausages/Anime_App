package anime.app.anilist.request.query.parameters.connections.recommendation;

import anime.app.anilist.request.query.parameters.connections.PageInfo;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static anime.app.anilist.request.query.parameters.connections.recommendation.RecommendationConnection.RECOMMENDATION_CONNECTION_TITLE;
import static anime.app.anilist.request.utils.QueryTitleWithParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class RecommendationConnectionTest {

	@Test
	void getRecommendationConnectionWithoutFieldName__ReturnCorrectString() {
		//given
		PageInfo info = PageInfo.getPageInfoBuilder().total().build();
		List<String> expectedConnection = TestUtils.buildFieldParameterStringSet(
				info.getPageInfoString()
		);

		//when
		RecommendationConnection actualConnection = RecommendationConnection.getRecommendationConnectionBuilder()
				.pageInfo(info)
				.build();

		//then
		assertThat(actualConnection.getRecommendationConnectionWithoutFieldName(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedConnection)
		));
	}

	@Test
	void getRecommendationConnectionBuilder__ReturnValidBuilder() {
		//given

		//when
		RecommendationConnection.RecommendationConnectionBuilder builder = RecommendationConnection.getRecommendationConnectionBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(RecommendationConnection.RecommendationConnectionBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Recommendation Connection Builder")
	class RecommendationConnectionBuilderTest {
		@Test
		void recommendationConnectionBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> RecommendationConnection.getRecommendationConnectionBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void recommendationBuilder_Edge_ReturnCorrectString() {
			//given
			Recommendation recommendation = Recommendation.getRecommendationBuilder().id().build();
			RecommendationEdge edge = RecommendationEdge.fromRecommendation(recommendation);
			List<String> expectedConnection = TestUtils.buildFieldParameterStringSet(
					"edges " + edge.getRecommendationEdgeWithoutFieldName()
			);

			//when
			RecommendationConnection actualConnection = RecommendationConnection.getRecommendationConnectionBuilder()
					.edges(edge)
					.build();

			//then
			assertThat(actualConnection.getRecommendationConnectionString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(RECOMMENDATION_CONNECTION_TITLE, expectedConnection)
			));
		}

		@Test
		void recommendationBuilder_Nodes_ReturnCorrectString() {
			//given
			Recommendation recommendation = Recommendation.getRecommendationBuilder().id().build();
			List<String> expectedConnection = TestUtils.buildFieldParameterStringSet(
					"nodes " + recommendation.getRecommendationStringWithoutFieldName()
			);

			//when
			RecommendationConnection actualConnection = RecommendationConnection.getRecommendationConnectionBuilder()
					.nodes(recommendation)
					.build();

			//then
			assertThat(actualConnection.getRecommendationConnectionString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(RECOMMENDATION_CONNECTION_TITLE, expectedConnection)
			));
		}

		@Test
		void recommendationBuilder_PageInfo_ReturnCorrectString() {
			//given
			PageInfo info = PageInfo.getPageInfoBuilder().total().build();
			List<String> expectedConnection = TestUtils.buildFieldParameterStringSet(
					info.getPageInfoString()
			);

			//when
			RecommendationConnection actualConnection = RecommendationConnection.getRecommendationConnectionBuilder()
					.pageInfo(info)
					.build();

			//then
			assertThat(actualConnection.getRecommendationConnectionString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(RECOMMENDATION_CONNECTION_TITLE, expectedConnection)
			));
		}

		@Test
		void recommendationBuilder_AllParameters_ReturnCorrectString() {
			//given
			Recommendation recommendation = Recommendation.getRecommendationBuilder().id().build();
			RecommendationEdge edge = RecommendationEdge.fromRecommendation(recommendation);
			PageInfo info = PageInfo.getPageInfoBuilder().total().build();
			List<String> expectedConnection = TestUtils.buildFieldParameterStringSet(
					"edges " + edge.getRecommendationEdgeWithoutFieldName(),
					"nodes " + recommendation.getRecommendationStringWithoutFieldName(),
					info.getPageInfoString()
			);

			//when
			RecommendationConnection actualConnection = RecommendationConnection.getRecommendationConnectionBuilder()
					.edges(edge)
					.nodes(recommendation)
					.pageInfo(info)
					.build();

			//then
			assertThat(actualConnection.getRecommendationConnectionString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(RECOMMENDATION_CONNECTION_TITLE, expectedConnection)
			));
		}
	}
}