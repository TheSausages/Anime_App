package anime.app.anilist.request.query.parameters.connections.reviews;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.connections.PageInfo;
import lombok.Getter;

import java.util.Set;

@Getter
public class ReviewConnection {
	public final static String reviewConnectionTitle = "reviewConnection";

	private final String reviewConnectionString;

	private ReviewConnection(String reviewConnectionString) {
		this.reviewConnectionString = reviewConnectionString;
	}

	public String getReviewConnectionWithoutFieldName() {
		return this.reviewConnectionString.substring(reviewConnectionTitle.length() + 1);
	}

	@Override
	public String toString() {
		return reviewConnectionString;
	}

	public static ReviewConnectionBuilder getReviewConnectionBuilder() {
		return new ReviewConnectionBuilder();
	}

	public static final class ReviewConnectionBuilder {
		private final Set<ParameterString> reviewConnection = new OverwritingLinkedHashSet<>();

		public ReviewConnectionBuilder edge(ReviewEdge reviewEdge) {
			reviewConnection.add(ParameterString.fromString("edges " + reviewEdge.getReviewEdgeWithoutFieldName()));
			return this;
		}

		public ReviewConnectionBuilder nodes(Review review) {
			reviewConnection.add(ParameterString.fromString("nodes " + review.getReviewWithoutFieldName()));
			return this;
		}

		public ReviewConnectionBuilder pageInfo(PageInfo pageInfo) {
			reviewConnection.add(ParameterString.fromString("pageInfo " + pageInfo.getPageInfoStringWithoutFieldName()));
			return this;
		}

		public ReviewConnection build() {
			if (reviewConnection.isEmpty()) {
				throw new IllegalStateException("Review Connection should posses at least 1 parameter!");
			}

			return new ReviewConnection(QueryParameterUtils.buildQueryFieldElementString(
					reviewConnectionTitle,
					reviewConnection
			));
		}
	}
}
