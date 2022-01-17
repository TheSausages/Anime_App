package anime.app.anilist.request.query.parameters.connections.reviews;

import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.common.CommonParameterFieldNames;
import lombok.Getter;

import java.util.Objects;

@Getter
public class ReviewEdge {
	public static final String reviewEdgeTitle = "reviewEdge";

	private final String reviewEdgeString;

	public ReviewEdge(Review review) {
		Objects.requireNonNull(review, "Review cannot be null");

		this.reviewEdgeString = QueryParameterUtils.buildFieldElement(
				reviewEdgeTitle,
				QueryParameterUtils.combineIntoField(CommonParameterFieldNames.NODE, review.getReviewWithoutFieldName())
		);
	}

	public static ReviewEdge fromReview(Review review) {
		return new ReviewEdge(review);
	}

	public String getReviewEdgeWithoutFieldName() {
		return this.reviewEdgeString.substring(reviewEdgeTitle.length() + 1);
	}

	@Override
	public String toString() {
		return reviewEdgeTitle;
	}
}
