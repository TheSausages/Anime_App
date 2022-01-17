package anime.app.anilist.request.query.parameters.connections.reviews;

import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static anime.app.anilist.request.query.parameters.connections.reviews.ReviewEdge.REVIEW_EDGE_TITLE;
import static anime.app.anilist.request.utils.QueryTitleAndParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class ReviewEdgeTest {

	@Test
	void getReviewEdgeWithoutFieldName_NullReview_ThrowException() {
		//given
		Review review = null;

		//when
		Exception thrownException = Assertions.assertThrows(
				NullPointerException.class,
				() -> new ReviewEdge(review)
		);

		//then
		assertThat(thrownException, instanceOf(NullPointerException.class));
		assertThat(thrownException.getMessage(), notNullValue());
	}

	@Test
	void getReviewEdgeWithoutFieldName_NonNullReview_ReturnCorrectString() {
		//given
		Review review = Review.getReviewBuilder().id().build();
		Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
				"node " + review.getReviewWithoutFieldName()
		);

		//when
		ReviewEdge actualEdge = new ReviewEdge(review);

		//then
		assertThat(actualEdge.getReviewEdgeWithoutFieldName(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedEdge)
		));
	}

	@Test
	void fromReview_NullReview_ThrowException() {
		//given
		Review review = null;

		//when
		Exception thrownException = Assertions.assertThrows(
				NullPointerException.class,
				() -> ReviewEdge.fromReview(review)
		);

		//then
		assertThat(thrownException, instanceOf(NullPointerException.class));
		assertThat(thrownException.getMessage(), notNullValue());
	}

	@Test
	void fromReview_NonNullReview_ReturnCorrectString() {
		//given
		Review review = Review.getReviewBuilder().id().build();
		Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
				"node " + review.getReviewWithoutFieldName()
		);

		//when
		ReviewEdge actualEdge = ReviewEdge.fromReview(review);

		//then
		assertThat(actualEdge.getReviewEdgeString(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(REVIEW_EDGE_TITLE, expectedEdge)
		));
	}
}