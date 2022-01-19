package anime.app.anilist.request.query.parameters.connections.airingschedule;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.common.CommonParameterFieldNames;
import anime.app.anilist.request.query.parameters.connections.PageInfo;
import lombok.Getter;

import java.util.Set;

@Getter
public class AiringScheduleConnection {
	public static final String AIRING_SCHEDULE_CONNECTION_TITLE = "airingScheduleConnection";

	private final String airingScheduleConnectionString;

	private AiringScheduleConnection(String airingScheduleConnectionString) {
		this.airingScheduleConnectionString = airingScheduleConnectionString;
	}

	public String getAiringScheduleConnectionWithoutFieldName() {
		return this.airingScheduleConnectionString.substring(AIRING_SCHEDULE_CONNECTION_TITLE.length() + 1);
	}

	@Override
	public String toString() {
		return airingScheduleConnectionString;
	}

	public static AiringScheduleConnectionBuilder getAiringScheduleConnectionBuilder() {
		return new AiringScheduleConnectionBuilder();
	}

	public static final class AiringScheduleConnectionBuilder {
		private final Set<ParameterString> airingScheduleConnection = new OverwritingLinkedHashSet<>();

		public AiringScheduleConnectionBuilder edges(AiringScheduleEdge edge) {
			airingScheduleConnection.add(QueryParameterUtils.combineIntoStringField(CommonParameterFieldNames.EDGES, edge.getAiringScheduleEdgeWithoutFieldName()));
			return this;
		}

		public AiringScheduleConnectionBuilder nodes(AiringSchedule schedule) {
			airingScheduleConnection.add(QueryParameterUtils.combineIntoStringField(CommonParameterFieldNames.NODES, schedule.getAiringScheduleStringWithoutFieldName()));
			return this;
		}

		public AiringScheduleConnectionBuilder pageInfo(PageInfo pageInfo) {
			airingScheduleConnection.add(QueryParameterUtils.combineIntoStringField(CommonParameterFieldNames.PAGE_INFO, pageInfo.getPageInfoStringWithoutFieldName()));
			return this;
		}

		public AiringScheduleConnection build() {
			if (airingScheduleConnection.isEmpty()) {
				throw new IllegalStateException("Airing Schedule Connection should posses at least 1 parameter!");
			}

			return new AiringScheduleConnection(QueryParameterUtils.buildStringField(
					AIRING_SCHEDULE_CONNECTION_TITLE,
					airingScheduleConnection
			));
		}
	}
}
