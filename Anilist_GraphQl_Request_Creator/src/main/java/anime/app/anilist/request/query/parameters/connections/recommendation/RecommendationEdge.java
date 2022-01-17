package anime.app.anilist.request.query.parameters.connections.recommendation;

import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.common.CommonParameterFieldNames;
import lombok.Getter;

import java.util.Objects;

@Getter
public class RecommendationEdge {
	public static final String RECOMMENDATION_EDGE_TITLE = "recommendationEdgeTitle";

	private final String recommendationEdgeString;

	public RecommendationEdge(Recommendation recommendation) {
		Objects.requireNonNull(recommendation, "Recommendation cannot be null");

		this.recommendationEdgeString = QueryParameterUtils.buildFieldElement(
				RECOMMENDATION_EDGE_TITLE,
				QueryParameterUtils.combineIntoField(CommonParameterFieldNames.NODE, recommendation.getRecommendationStringWithoutFieldName())
		);
	}

	public String getRecommendationEdgeWithoutFieldName() {
		return this.recommendationEdgeString.substring(RECOMMENDATION_EDGE_TITLE.length() + 1);
	}

	public static RecommendationEdge fromRecommendation(Recommendation recommendation) {
		return new RecommendationEdge(recommendation);
	}

	@Override
	public String toString() {
		return recommendationEdgeString;
	}
}
