package anime.app.anilist.request.query.parameters.connections.reviews;

import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.common.CommonParameterFieldNames;
import lombok.Getter;

import java.util.Objects;

@Getter
public class ReviewEdge {
	public static final String REVIEW_EDGE_TITLE = "reviewEdge";

	private final String reviewEdgeString;

	public ReviewEdge(Review review) {
		Objects.requireNonNull(review, "Review cannot be null");

		this.reviewEdgeString = QueryParameterUtils.buildStringField(
				REVIEW_EDGE_TITLE,
				QueryParameterUtils.combineIntoStringField(CommonParameterFieldNames.NODE, review.getReviewWithoutFieldName())
		);
	}

	public static ReviewEdge fromReview(Review review) {
		return new ReviewEdge(review);
	}

	public String getReviewEdgeWithoutFieldName() {
		return this.reviewEdgeString.substring(REVIEW_EDGE_TITLE.length() + 1);
	}

	@Override
	public String toString() {
		return REVIEW_EDGE_TITLE;
	}
}
