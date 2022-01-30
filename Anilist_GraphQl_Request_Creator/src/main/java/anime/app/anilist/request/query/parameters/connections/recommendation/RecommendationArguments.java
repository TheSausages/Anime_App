package anime.app.anilist.request.query.parameters.connections.recommendation;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.common.CommonParameterFieldNames;
import lombok.Getter;

import java.util.Set;

@Getter
public class RecommendationArguments {
	private final String recommendationArgumentsString;

	private RecommendationArguments(String recommendationArgumentsString) {
		this.recommendationArgumentsString = recommendationArgumentsString;
	}

	public static RecommendationArgumentsBuilder getRecommendationArgumentBuilder() {
		return new RecommendationArgumentsBuilder();
	}

	@Override
	public String toString() {
		return recommendationArgumentsString;
	}

	public static final class RecommendationArgumentsBuilder {
		private final Set<ParameterString> recommendationArgumentsArguments = new OverwritingLinkedHashSet<>();

		public RecommendationArgumentsBuilder sort(RecommendationSort... sorts) {
			recommendationArgumentsArguments.add(QueryParameterUtils.combineIntoStringArgumentNoBracket(CommonParameterFieldNames.SORT, sorts));
			return this;
		}

		public RecommendationArgumentsBuilder page(int page) {
			recommendationArgumentsArguments.add(QueryParameterUtils.combineIntoStringArgumentNoBracket(CommonParameterFieldNames.PAGE, page));
			return this;
		}

		public RecommendationArgumentsBuilder perPage(int perPage) {
			recommendationArgumentsArguments.add(QueryParameterUtils.combineIntoStringArgumentNoBracket(CommonParameterFieldNames.PER_PAGE, perPage));
			return this;
		}

		public RecommendationArguments build() {
			if (recommendationArgumentsArguments.isEmpty()) {
				throw new IllegalStateException("Recommendation Arguments should posses at least 1 parameter!");
			}

			return new RecommendationArguments(QueryParameterUtils.buildStringArguments(recommendationArgumentsArguments));
		}
	}
}
