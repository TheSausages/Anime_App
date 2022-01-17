package anime.app.anilist.request.query.parameters.connections.airingschedule;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.media.Media;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.common.CommonParameterFieldNames;
import lombok.Getter;

import java.util.Set;

@Getter
public class AiringSchedule {
	public final static String AIRING_SCHEDULE_TITLE = "airingSchedule";

	private final String airingScheduleString;

	private AiringSchedule(String airingScheduleString) {
		this.airingScheduleString = airingScheduleString;
	}

	public String getAiringScheduleStringWithoutFieldName() {
		return this.airingScheduleString.substring(AIRING_SCHEDULE_TITLE.length() + 1);
	}

	public static AiringScheduleBuilder getAiringScheduleBuilder() {
		return new AiringScheduleBuilder();
	}

	@Override
	public String toString() {
		return airingScheduleString;
	}

	public static final class AiringScheduleBuilder {
		private final Set<ParameterString> airingSchedule = new OverwritingLinkedHashSet<>();

		public AiringScheduleBuilder id() {
			airingSchedule.add(ParameterString.fromString("id"));
			return this;
		}

		public AiringScheduleBuilder airingAt() {
			airingSchedule.add(ParameterString.fromString("airingAt"));
			return this;
		}

		public AiringScheduleBuilder timeUntilAiring() {
			airingSchedule.add(ParameterString.fromString("timeUntilAiring"));
			return this;
		}

		public AiringScheduleBuilder episode() {
			airingSchedule.add(ParameterString.fromString("episode"));
			return this;
		}

		public AiringScheduleBuilder mediaId() {
			airingSchedule.add(ParameterString.fromString("mediaId"));
			return this;
		}

		public AiringScheduleBuilder media(Media media) {
			airingSchedule.add(QueryParameterUtils.combineIntoField(CommonParameterFieldNames.MEDIA, media.getRequestedMediaFields()));
			return this;
		}

		public AiringSchedule build() {
			if (airingSchedule.isEmpty()) {
				throw new IllegalStateException("Airing Schedule should posses at least 1 parameter!");
			}

			return new AiringSchedule(QueryParameterUtils.buildFieldElement(
					AiringSchedule.AIRING_SCHEDULE_TITLE,
					airingSchedule
			));
		}
	}
}
