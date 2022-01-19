package anime.app.anilist.request.query.parameters.connections.recommendation;

import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static anime.app.anilist.request.utils.QueryTitleWithParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class RecommendationEdgeTest {

	@Test
	void getRecommendationEdgeWithoutFieldName_NullReview_ThrowException() {
		//given
		Recommendation recommendation = null;

		//when
		Exception thrownException = Assertions.assertThrows(
				NullPointerException.class,
				() -> new RecommendationEdge(recommendation)
		);

		//then
		assertThat(thrownException, instanceOf(NullPointerException.class));
		assertThat(thrownException.getMessage(), notNullValue());
	}

	@Test
	void getRecommendationEdgeWithoutFieldName_NonNullReview_ReturnCorrectString() {
		//given
		Recommendation recommendation = Recommendation.getRecommendationBuilder().id().build();
		Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
				"node " + recommendation.getRecommendationStringWithoutFieldName()
		);

		//when
		RecommendationEdge actualEdge = new RecommendationEdge(recommendation);

		//then
		assertThat(actualEdge.getRecommendationEdgeWithoutFieldName(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedEdge)
		));
	}

	@Test
	void fromRecommendation_NullReview_ThrowException() {
		//given
		Recommendation recommendation = null;

		//when
		Exception thrownException = Assertions.assertThrows(
				NullPointerException.class,
				() -> RecommendationEdge.fromRecommendation(recommendation)
		);

		//then
		assertThat(thrownException, instanceOf(NullPointerException.class));
		assertThat(thrownException.getMessage(), notNullValue());
	}

	@Test
	void fromRecommendation_NonNullReview_ReturnCorrectString() {
		//given
		Recommendation recommendation = Recommendation.getRecommendationBuilder().id().build();
		Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
				"node " + recommendation.getRecommendationStringWithoutFieldName()
		);

		//when
		RecommendationEdge actualEdge = RecommendationEdge.fromRecommendation(recommendation);

		//then
		assertThat(actualEdge.getRecommendationEdgeWithoutFieldName(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedEdge)
		));
	}
}