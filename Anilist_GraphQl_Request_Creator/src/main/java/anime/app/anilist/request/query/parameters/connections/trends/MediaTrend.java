package anime.app.anilist.request.query.parameters.connections.trends;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.media.Media;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.common.CommonParameterFieldNames;
import lombok.Getter;

import java.util.Set;

@Getter
public class MediaTrend {
	public final static String MEDIA_TREND_TITLE = "mediaTrend";

	private final String mediaTrendString;

	private MediaTrend(String mediaTrendString) {
		this.mediaTrendString = mediaTrendString;
	}

	public String getMediaTrendWithoutFieldName() {
		return this.mediaTrendString.substring(MEDIA_TREND_TITLE.length() + 1);
	}

	public static MediaTrendBuilder getMediaTrendBuilder() {
		return new MediaTrendBuilder();
	}

	@Override
	public String toString() {
		return mediaTrendString;
	}

	public static final class MediaTrendBuilder {
		private final Set<ParameterString> mediaTrend = new OverwritingLinkedHashSet<>();

		public MediaTrendBuilder mediaId() {
			mediaTrend.add(ParameterString.fromString("mediaId"));
			return this;
		}

		public MediaTrendBuilder date() {
			mediaTrend.add(ParameterString.fromString("date"));
			return this;
		}

		public MediaTrendBuilder trending() {
			mediaTrend.add(ParameterString.fromString("trending"));
			return this;
		}

		public MediaTrendBuilder averageScore() {
			mediaTrend.add(ParameterString.fromString("averageScore"));
			return this;
		}

		public MediaTrendBuilder popularity() {
			mediaTrend.add(ParameterString.fromString("popularity"));
			return this;
		}

		public MediaTrendBuilder inProgress() {
			mediaTrend.add(ParameterString.fromString("inProgress"));
			return this;
		}

		public MediaTrendBuilder releasing() {
			mediaTrend.add(ParameterString.fromString("releasing"));
			return this;
		}

		public MediaTrendBuilder episode() {
			mediaTrend.add(ParameterString.fromString("episode"));
			return this;
		}

		public MediaTrendBuilder media(Media media) {
			mediaTrend.add(QueryParameterUtils.combineIntoStringField(CommonParameterFieldNames.MEDIA, media.getRequestedMediaFields()));
			return this;
		}

		public MediaTrend build() {
			if (mediaTrend.isEmpty()) {
				throw new IllegalStateException("Media Trend should posses at least 1 parameter!");
			}

			return new MediaTrend(QueryParameterUtils.buildStringField(
					MEDIA_TREND_TITLE,
					mediaTrend
			));
		}
	}
}
