package anime.app.anilist.request.query.parameters.connections.recommendation;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.media.Media;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.common.AnilistUser;
import anime.app.anilist.request.query.parameters.common.CommonParameterFieldNames;
import lombok.Getter;

import java.util.Set;

@Getter
public class Recommendation {
	public static final String RECOMMENDATION_TITLE = "recommendation";

	private final String recommendationString;

	private Recommendation(String recommendationString) {
		this.recommendationString = recommendationString;
	}

	public String getRecommendationStringWithoutFieldName() {
		return this.recommendationString.substring(RECOMMENDATION_TITLE.length() + 1);
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
			recommendation.add(QueryParameterUtils.combineIntoStringField(CommonParameterFieldNames.MEDIA, media.getRequestedMediaFields()));
			return this;
		}

		public RecommendationBuilder mediaRecommendation(Media media) {
			recommendation.add(QueryParameterUtils.combineIntoStringField("mediaRecommendation", media.getRequestedMediaFields()));
			return this;
		}

		public RecommendationBuilder user(AnilistUser user) {
			recommendation.add(QueryParameterUtils.combineIntoStringField(CommonParameterFieldNames.USER, user.getAnilistUserWithoutFieldName()));
			return this;
		}

		public Recommendation build() {
			if (recommendation.isEmpty()) {
				throw new IllegalStateException("Recommendation should posses at least 1 parameter!");
			}

			return new Recommendation(QueryParameterUtils.buildStringField(
					RECOMMENDATION_TITLE,
					recommendation
			));
		}
	}
}
