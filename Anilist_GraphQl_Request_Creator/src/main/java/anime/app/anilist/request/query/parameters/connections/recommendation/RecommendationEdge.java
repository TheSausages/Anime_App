package anime.app.anilist.request.query.parameters.connections.recommendation;

import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import lombok.Getter;

import java.util.Objects;

@Getter
public class RecommendationEdge {
	public static final String recommendationEdgeTitle = "recommendationEdgeTitle";

	private final String recommendationEdgeString;

	public RecommendationEdge(Recommendation recommendation) {
		Objects.requireNonNull(recommendation, "Recommendation cannot be null");

		this.recommendationEdgeString = QueryParameterUtils.buildQueryFieldElementString(
				recommendationEdgeTitle,
				"node " + recommendation.getRecommendationStringWithoutFieldName()
		);
	}

	public String getRecommendationEdgeWithoutFieldName() {
		return this.recommendationEdgeString.substring(recommendationEdgeTitle.length() + 1);
	}

	public static RecommendationEdge fromRecommendation(Recommendation recommendation) {
		return new RecommendationEdge(recommendation);
	}

	@Override
	public String toString() {
		return recommendationEdgeString;
	}
}
