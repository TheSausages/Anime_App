package anime.app.anilist.request.query.parameters.connections.airingschedule;

import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.common.CommonParameterFieldNames;
import lombok.Getter;

import java.util.Objects;

@Getter
public class AiringScheduleEdge {
	public final static String airingScheduleEdgeTitle = "airingScheduleEdge";

	private final String airingScheduleEdgeString;

	public AiringScheduleEdge() {
		this.airingScheduleEdgeString = QueryParameterUtils.buildFieldElement(
				airingScheduleEdgeTitle, "id"
		);
	}

	public AiringScheduleEdge(AiringSchedule airingSchedule) {
		Objects.requireNonNull(airingSchedule, "The schedule cannot be null");

		this.airingScheduleEdgeString = QueryParameterUtils.buildFieldElement(
				airingScheduleEdgeTitle,
				"id",
				QueryParameterUtils.combineIntoField(CommonParameterFieldNames.NODE, airingSchedule.getAiringScheduleStringWithoutFieldName()).getField()
		);
	}

	public static AiringScheduleEdge fromNothing() {
		return new AiringScheduleEdge();
	}

	public static AiringScheduleEdge fromAiringSchedule(AiringSchedule schedule) {
		return new AiringScheduleEdge(schedule);
	}

	public String getAiringScheduleEdgeWithoutFieldName() {
		return this.airingScheduleEdgeString.substring(airingScheduleEdgeTitle.length() + 1);
	}
}
