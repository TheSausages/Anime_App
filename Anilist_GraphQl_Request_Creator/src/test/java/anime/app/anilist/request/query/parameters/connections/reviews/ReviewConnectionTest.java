package anime.app.anilist.request.query.parameters.connections.reviews;

import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.connections.PageInfo;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static anime.app.anilist.request.query.parameters.connections.reviews.ReviewConnection.REVIEW_CONNECTION_TITLE;
import static anime.app.anilist.request.utils.QueryTitleAndParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class ReviewConnectionTest {

	@Test
	void getReviewConnectionWithoutFieldName__ReturnCorrectString() {
		//given
		PageInfo info = PageInfo.getPageInfoBuilder().total().build();
		Set<ParameterString> expectedConnection = TestUtils.getParameterStringSetField(
				info.getPageInfoString()
		);

		//when
		ReviewConnection actualConnection = ReviewConnection.getReviewConnectionBuilder()
				.pageInfo(info)
				.build();

		//then
		assertThat(actualConnection.getReviewConnectionWithoutFieldName(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedConnection)
		));
	}

	@Test
	void getReviewConnectionBuilder__ReturnValidBuilder() {
		//given

		//when
		ReviewConnection.ReviewConnectionBuilder builder = ReviewConnection.getReviewConnectionBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(ReviewConnection.ReviewConnectionBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Review Connection Builder")
	class ReviewConnectionBuilderTest {
		@Test
		void reviewConnectionBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> ReviewConnection.getReviewConnectionBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void reviewConnectionBuilder_Edge_ReturnCorrectString() {
			//given
			ReviewEdge edge = ReviewEdge.fromReview(Review.getReviewBuilder().id().build());
			Set<ParameterString> expectedConnection = TestUtils.getParameterStringSetField(
					"edges " + edge.getReviewEdgeWithoutFieldName()
			);

			//when
			ReviewConnection actualConnection = ReviewConnection.getReviewConnectionBuilder()
					.edge(edge)
					.build();

			//then
			assertThat(actualConnection.getReviewConnectionString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(REVIEW_CONNECTION_TITLE, expectedConnection)
			));
		}

		@Test
		void reviewConnectionBuilder_Nodes_ReturnCorrectString() {
			//given
			Review review = Review.getReviewBuilder().id().build();
			Set<ParameterString> expectedConnection = TestUtils.getParameterStringSetField(
					"nodes " + review.getReviewWithoutFieldName()
			);

			//when
			ReviewConnection actualConnection = ReviewConnection.getReviewConnectionBuilder()
					.nodes(review)
					.build();

			//then
			assertThat(actualConnection.getReviewConnectionString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(REVIEW_CONNECTION_TITLE, expectedConnection)
			));
		}

		@Test
		void reviewConnectionBuilder_PageInfo_ReturnCorrectString() {
			//given
			PageInfo info = PageInfo.getPageInfoBuilder().total().build();
			Set<ParameterString> expectedConnection = TestUtils.getParameterStringSetField(
					info.getPageInfoString()
			);

			//when
			ReviewConnection actualConnection = ReviewConnection.getReviewConnectionBuilder()
					.pageInfo(info)
					.build();

			//then
			assertThat(actualConnection.getReviewConnectionString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(REVIEW_CONNECTION_TITLE, expectedConnection)
			));
		}

		@Test
		void reviewConnectionBuilder_AllParameters_ReturnCorrectString() {
			//given
			Review review = Review.getReviewBuilder().id().build();
			ReviewEdge edge = ReviewEdge.fromReview(review);
			PageInfo info = PageInfo.getPageInfoBuilder().total().build();
			Set<ParameterString> expectedConnection = TestUtils.getParameterStringSetField(
					"edges " + edge.getReviewEdgeWithoutFieldName(),
					"nodes " + review.getReviewWithoutFieldName(),
					info.getPageInfoString()
			);

			//when
			ReviewConnection actualConnection = ReviewConnection.getReviewConnectionBuilder()
					.edge(edge)
					.nodes(review)
					.pageInfo(info)
					.build();

			//then
			assertThat(actualConnection.getReviewConnectionString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(REVIEW_CONNECTION_TITLE, expectedConnection)
			));
		}
	}
}