package anime.app.anilist.request.query.parameters.connections.airingschedule;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import lombok.Getter;

import java.util.Set;

@Getter
public class AiringScheduleArguments {
	private final String airingScheduleArgumentsString;

	private AiringScheduleArguments(String airingScheduleArgumentsString) {
		this.airingScheduleArgumentsString = airingScheduleArgumentsString;
	}

	public static AiringScheduleArgumentsBuilder getAiringScheduleArgumentsBuilder() {
		return new AiringScheduleArgumentsBuilder();
	}

	@Override
	public String toString() {
		return airingScheduleArgumentsString;
	}

	public static final class AiringScheduleArgumentsBuilder {
		private final Set<ParameterString> airingScheduleArguments = new OverwritingLinkedHashSet<>();

		public AiringScheduleArgumentsBuilder notYetAired() {
			airingScheduleArguments.add(ParameterString.fromString("notYetAired: true"));
			return this;
		}

		public AiringScheduleArgumentsBuilder notYetAired(boolean didNotAir) {
			airingScheduleArguments.add(ParameterString.fromString("notYetAired: " + didNotAir));
			return this;
		}

		public AiringScheduleArgumentsBuilder page(int page) {
			airingScheduleArguments.add(ParameterString.fromString("page: " + page));
			return this;
		}

		public AiringScheduleArgumentsBuilder perPage(int perPage) {
			airingScheduleArguments.add(ParameterString.fromString("perPage: " + perPage));
			return this;
		}

		public AiringScheduleArguments build() {
			if (airingScheduleArguments.isEmpty()) {
				throw new IllegalStateException("Airing Schedule Arguments should posses at least 1 parameter!");
			}

			return new AiringScheduleArguments(
					QueryParameterUtils.buildQueryFieldElementArgumentsString(airingScheduleArguments)
			);
		}
	}
}
