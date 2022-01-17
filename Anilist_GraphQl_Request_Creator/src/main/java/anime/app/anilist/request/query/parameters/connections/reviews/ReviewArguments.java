package anime.app.anilist.request.query.parameters.connections.reviews;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;

@Getter
public class ReviewArguments {
	private final String reviewArgumentsString;

	private ReviewArguments(String reviewArgumentsString) {
		this.reviewArgumentsString = reviewArgumentsString;
	}

	public static ReviewArgumentsBuilder getReviewArgumentsBuilder() {
		return new ReviewArgumentsBuilder();
	}

	@Override
	public String toString() {
		return reviewArgumentsString;
	}

	public static final class ReviewArgumentsBuilder {
		private final Set<ParameterString> reviewArguments = new OverwritingLinkedHashSet<>();

		public ReviewArgumentsBuilder sort(ReviewSort... sorts) {
			reviewArguments.add(ParameterString.fromString("sort: " + Arrays.toString(sorts)));
			return this;
		}

		public ReviewArgumentsBuilder limit(int limit) {
			reviewArguments.add(ParameterString.fromString("limit: " + limit));
			return this;
		}

		public ReviewArgumentsBuilder page(int page) {
			reviewArguments.add(ParameterString.fromString("page: " + page));
			return this;
		}

		public ReviewArgumentsBuilder perPage(int perPage) {
			reviewArguments.add(ParameterString.fromString("perPage: " + perPage));
			return this;
		}

		public ReviewArguments build() {
			if (reviewArguments.isEmpty()) {
				throw new IllegalStateException("Review Arguments should posses at least 1 parameter!");
			}

			return new ReviewArguments(QueryParameterUtils.buildQueryFieldElementArgumentsString(reviewArguments));
		}
	}
}
