package anime.app.anilist.request.query.parameters.connections.airingschedule;

import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.utils.QueryTitleAndParametersMatcher;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static anime.app.anilist.request.utils.QueryTitleAndParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class AiringScheduleEdgeTest {

	@Test
	void getAiringScheduleEdge_WithoutSchedule_ReturnCorrectString() {
		//given
		Set<ParameterString> expectedAiringScheduleEdge = TestUtils.getParameterStringSet("id");

		//when
		AiringScheduleEdge actualAiringScheduleEdge = new AiringScheduleEdge();

		//then
		assertThat(actualAiringScheduleEdge.getAiringScheduleEdgeString(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(AiringScheduleEdge.airingScheduleEdgeTitle, expectedAiringScheduleEdge)
		));
	}

	@Test
	void getAiringScheduleEdge_WithSchedule_ReturnCorrectString() {
		//given
		AiringSchedule schedule = AiringSchedule.getAiringScheduleBuilder()
				.episode()
				.build();
		Set<ParameterString> expectedAiringScheduleEdge = TestUtils.getParameterStringSetWithDivide(
				QueryTitleAndParametersMatcher.divider,
				"id",
				"node " + schedule.getAiringScheduleStringWithoutFieldName()
		);

		//when
		AiringScheduleEdge actualAiringScheduleEdge = new AiringScheduleEdge(schedule);

		//then
		assertThat(actualAiringScheduleEdge.getAiringScheduleEdgeString(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(AiringScheduleEdge.airingScheduleEdgeTitle, expectedAiringScheduleEdge)
		));
	}

	@Test
	void getAiringScheduleEdgeWithoutFieldName_WithoutSchedule_ReturnCorrectString() {
		//given
		Set<ParameterString> expectedAiringScheduleEdge = TestUtils.getParameterStringSet("id");

		//when
		AiringScheduleEdge actualAiringScheduleEdge = new AiringScheduleEdge();

		//then
		assertThat(actualAiringScheduleEdge.getAiringScheduleEdgeWithoutFieldName(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedAiringScheduleEdge)
		));
	}

	@Test
	void getAiringScheduleEdgeWithoutFieldName_WithSchedule_ReturnCorrectString() {
		//given
		AiringSchedule schedule = AiringSchedule.getAiringScheduleBuilder()
				.episode()
				.build();
		Set<ParameterString> expectedAiringScheduleEdge = TestUtils.getParameterStringSetWithDivide(
				QueryTitleAndParametersMatcher.divider,
				"id",
				"node " + schedule.getAiringScheduleStringWithoutFieldName()
		);

		//when
		AiringScheduleEdge actualAiringScheduleEdge = new AiringScheduleEdge(schedule);

		//then
		assertThat(actualAiringScheduleEdge.getAiringScheduleEdgeWithoutFieldName(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedAiringScheduleEdge)
		));
	}
}