package anime.app.anilist.request.query.parameters.connections.reviews;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.media.Media;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.common.AnilistUser;
import anime.app.anilist.request.query.parameters.common.CommonParameterFieldNames;
import lombok.Getter;

import java.util.Set;

@Getter
public class Review {
	public static final String reviewTitle = "review";

	private final String reviewString;

	private Review(String reviewString) {
		this.reviewString = reviewString;
	}

	public String getReviewWithoutFieldName() {
		return this.reviewString.substring(reviewTitle.length() + 1);
	}

	public static ReviewBuilder getReviewBuilder() {
		return new ReviewBuilder();
	}

	@Override
	public String toString() {
		return reviewString;
	}

	public static final class ReviewBuilder {
		private final Set<ParameterString> review = new OverwritingLinkedHashSet<>();

		public ReviewBuilder id() {
			review.add(ParameterString.fromString("id"));
			return this;
		}

		public ReviewBuilder userId() {
			review.add(ParameterString.fromString("userId"));
			return this;
		}

		public ReviewBuilder mediaId() {
			review.add(ParameterString.fromString("mediaId"));
			return this;
		}

		public ReviewBuilder mediaType() {
			review.add(ParameterString.fromString("mediaType"));
			return this;
		}

		public ReviewBuilder summary() {
			review.add(ParameterString.fromString("summary"));
			return this;
		}

		public ReviewBuilder body() {
			review.add(ParameterString.fromString("body"));
			return this;
		}

		public ReviewBuilder bodyAsHtml() {
			return bodyAsHtml(true);
		}

		public ReviewBuilder bodyAsHtml(boolean asHtml) {
			review.add(ParameterString.fromString("body" + QueryParameterUtils.combineIntoArgumentWithBracket("asHtml", asHtml)));
			return this;
		}

		public ReviewBuilder rating() {
			review.add(ParameterString.fromString("rating"));
			return this;
		}

		public ReviewBuilder ratingAmount() {
			review.add(ParameterString.fromString("ratingAmount"));
			return this;
		}

		public ReviewBuilder score() {
			review.add(ParameterString.fromString("score"));
			return this;
		}

		public ReviewBuilder isPrivate() {
			review.add(ParameterString.fromString("private"));
			return this;
		}

		public ReviewBuilder siteUrl() {
			review.add(ParameterString.fromString("siteUrl"));
			return this;
		}

		public ReviewBuilder createdAt() {
			review.add(ParameterString.fromString("createdAt"));
			return this;
		}

		public ReviewBuilder updatedAt() {
			review.add(ParameterString.fromString("updatedAt"));
			return this;
		}

		public ReviewBuilder user(AnilistUser anilistUser) {
			review.add(QueryParameterUtils.combineIntoField(CommonParameterFieldNames.USER, anilistUser.getAnilistUserWithoutFieldName()));
			return this;
		}

		public ReviewBuilder media(Media media) {
			review.add(QueryParameterUtils.combineIntoField(CommonParameterFieldNames.MEDIA, media.getRequestedMediaFields()));
			return this;
		}

		public Review build() {
			if (review.isEmpty()) {
				throw new IllegalStateException("Review should posses at least 1 parameter!");
			}

			return new Review(QueryParameterUtils.buildFieldElement(
					reviewTitle,
					review
			));
		}
	}
}
