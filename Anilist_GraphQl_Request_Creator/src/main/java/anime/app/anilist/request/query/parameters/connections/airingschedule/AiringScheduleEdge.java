package anime.app.anilist.request.query.parameters.connections.airingschedule;

import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import lombok.Getter;

@Getter
public class AiringScheduleEdge {
	public final static String airingScheduleEdgeTitle = "airingScheduleEdge";

	private final String airingScheduleEdgeString;

	public AiringScheduleEdge() {
		this.airingScheduleEdgeString = QueryParameterUtils.buildQueryFieldElementString(
				airingScheduleEdgeTitle, "id"
		);
	}

	public AiringScheduleEdge(AiringSchedule airingSchedule) {
		this.airingScheduleEdgeString = QueryParameterUtils.buildQueryFieldElementString(
				airingScheduleEdgeTitle, "id", "node " + airingSchedule.getAiringScheduleStringWithoutFieldName()
		);
	}

	public String getAiringScheduleEdgeWithoutFieldName() {
		return this.airingScheduleEdgeString.substring(airingScheduleEdgeTitle.length() + 1);
	}
}
