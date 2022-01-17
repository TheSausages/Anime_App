package anime.app.anilist.request.query.parameters.connections.recommendation;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.media.Media;
import anime.app.anilist.request.query.parameters.AnilistUser;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import lombok.Getter;

import java.util.Set;

@Getter
public class Recommendation {
	public static final String recommendationTitle = "recommendation";

	private final String recommendationString;

	private Recommendation(String recommendationString) {
		this.recommendationString = recommendationString;
	}

	public String getRecommendationStringWithoutFieldName() {
		return this.recommendationString.substring(recommendationTitle.length() + 1);
	}

	public static RecommendationBuilder getRecommendationBuilder() {
		return new RecommendationBuilder();
	}

	@Override
	public String toString() {
		return recommendationString;
	}

	public static final class RecommendationBuilder {
		private final Set<ParameterString> recommendation = new OverwritingLinkedHashSet<>();

		public RecommendationBuilder id() {
			recommendation.add(ParameterString.fromString("id"));
			return this;
		}

		public RecommendationBuilder rating() {
			recommendation.add(ParameterString.fromString("rating"));
			return this;
		}

		public RecommendationBuilder media(Media media) {
			recommendation.add(ParameterString.fromString("media " + media.getRequestedMediaFields()));
			return this;
		}

		public RecommendationBuilder mediaRecommendation(Media media) {
			recommendation.add(ParameterString.fromString("mediaRecommendation " + media.getRequestedMediaFields()));
			return this;
		}

		public RecommendationBuilder user(AnilistUser user) {
			recommendation.add(ParameterString.fromString("user " + user.getAnilistUserWithoutFieldName()));
			return this;
		}

		public Recommendation build() {
			if (recommendation.isEmpty()) {
				throw new IllegalStateException("Recommendation should posses at least 1 parameter!");
			}

			return new Recommendation(QueryParameterUtils.buildQueryFieldElementString(
					recommendationTitle,
					recommendation
			));
		}
	}
}
