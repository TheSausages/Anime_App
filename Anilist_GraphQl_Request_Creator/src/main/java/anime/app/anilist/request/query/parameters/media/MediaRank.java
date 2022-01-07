package anime.app.anilist.request.query.parameters.media;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import lombok.Getter;

import java.util.Set;

@Getter
public class MediaRank {
	private static final String ranking = "ranking";

	private final String rank;

	private MediaRank(String rank) {
		this.rank = rank;
	}

	public static MediaRankBuilder getMediaRankBuilder() {
		return new MediaRankBuilder();
	}

	@Override
	public String toString() {
		return rank;
	}

	public static final class MediaRankBuilder {
		private final Set<ParameterString> mediaRank = new OverwritingLinkedHashSet<>();

		public MediaRankBuilder id() {
			mediaRank.add(ParameterString.fromString("id"));
			return this;
		}

		public MediaRankBuilder rank() {
			mediaRank.add(ParameterString.fromString("rank"));
			return this;
		}

		public MediaRankBuilder type() {
			mediaRank.add(ParameterString.fromString("type"));
			return this;
		}

		public MediaRankBuilder format() {
			mediaRank.add(ParameterString.fromString("format"));
			return this;
		}

		public MediaRankBuilder year() {
			mediaRank.add(ParameterString.fromString("year"));
			return this;
		}

		public MediaRankBuilder season() {
			mediaRank.add(ParameterString.fromString("season"));
			return this;
		}

		public MediaRankBuilder allTime() {
			mediaRank.add(ParameterString.fromString("allTime"));
			return this;
		}

		public MediaRankBuilder context() {
			mediaRank.add(ParameterString.fromString("context"));
			return this;
		}

		public MediaRank build() {
			if (mediaRank.isEmpty()) {
				throw new IllegalStateException("Ranking should posses at least 1 parameter!");
			}

			return new MediaRank(QueryParameterUtils.buildString(
					ranking,
					mediaRank
			));
		}
	}
}
