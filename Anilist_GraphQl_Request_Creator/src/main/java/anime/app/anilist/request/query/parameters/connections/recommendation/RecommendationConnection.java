package anime.app.anilist.request.query.parameters.connections.recommendation;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.common.CommonParameterFieldNames;
import anime.app.anilist.request.query.parameters.connections.PageInfo;
import lombok.Getter;

import java.util.Set;

@Getter
public class RecommendationConnection {
	public final static String RECOMMENDATION_CONNECTION_TITLE = "recommendationConnection";

	private final String recommendationConnectionString;

	private RecommendationConnection(String recommendationConnectionString) {
		this.recommendationConnectionString = recommendationConnectionString;
	}

	public String getRecommendationConnectionWithoutFieldName() {
		return this.recommendationConnectionString.substring(RECOMMENDATION_CONNECTION_TITLE.length() + 1);
	}

	public static RecommendationConnectionBuilder getRecommendationConnectionBuilder() {
		return new RecommendationConnectionBuilder();
	}

	@Override
	public String toString() {
		return recommendationConnectionString;
	}

	public static final class RecommendationConnectionBuilder {
		private final Set<ParameterString> recommendationConnection = new OverwritingLinkedHashSet<>();

		public RecommendationConnectionBuilder edges(RecommendationEdge edge) {
			recommendationConnection.add(QueryParameterUtils.combineIntoField(CommonParameterFieldNames.EDGES, edge.getRecommendationEdgeWithoutFieldName()));
			return this;
		}

		public RecommendationConnectionBuilder nodes(Recommendation recommendation) {
			recommendationConnection.add(QueryParameterUtils.combineIntoField(CommonParameterFieldNames.NODES, recommendation.getRecommendationStringWithoutFieldName()));
			return this;
		}

		public RecommendationConnectionBuilder pageInfo(PageInfo pageInfo) {
			recommendationConnection.add(QueryParameterUtils.combineIntoField(CommonParameterFieldNames.PAGE_INFO, pageInfo.getPageInfoStringWithoutFieldName()));
			return this;
		}

		public RecommendationConnection build() {
			if (recommendationConnection.isEmpty()) {
				throw new IllegalStateException("Recommendation Connection should posses at least 1 parameter!");
			}

			return new RecommendationConnection(QueryParameterUtils.buildFieldElement(
					RECOMMENDATION_CONNECTION_TITLE,
					recommendationConnection
			));
		}
	}
}
