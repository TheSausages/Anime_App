package anime.app.anilist.request.query.parameters.connections.airingschedule;

import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static anime.app.anilist.request.utils.QueryTitleWithParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class AiringScheduleEdgeTest {

	@Test
	void getAiringScheduleEdge_NullSchedule_ReturnCorrectString() {
		//given
		AiringSchedule schedule = null;

		//when
		Exception thrownException = Assertions.assertThrows(
				NullPointerException.class,
				() -> new AiringScheduleEdge(schedule)
		);

		//then
		assertThat(thrownException, instanceOf(NullPointerException.class));
		assertThat(thrownException.getMessage(), notNullValue());
	}

	@Test
	void getAiringScheduleEdge_WithoutSchedule_ReturnCorrectString() {
		//given
		Set<ParameterString> expectedAiringScheduleEdge = TestUtils.getParameterStringSetField("id");

		//when
		AiringScheduleEdge actualAiringScheduleEdge = new AiringScheduleEdge();

		//then
		assertThat(actualAiringScheduleEdge.getAiringScheduleEdgeString(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(AiringScheduleEdge.AIRING_SCHEDULE_EDGE_TITLE, expectedAiringScheduleEdge)
		));
	}

	@Test
	void getAiringScheduleEdge_WithSchedule_ReturnCorrectString() {
		//given
		AiringSchedule schedule = AiringSchedule.getAiringScheduleBuilder()
				.episode()
				.build();
		Set<ParameterString> expectedAiringScheduleEdge = TestUtils.getParameterStringSetField(
				"id",
				"node " + schedule.getAiringScheduleStringWithoutFieldName()
		);

		//when
		AiringScheduleEdge actualAiringScheduleEdge = new AiringScheduleEdge(schedule);

		//then
		assertThat(actualAiringScheduleEdge.getAiringScheduleEdgeString(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(AiringScheduleEdge.AIRING_SCHEDULE_EDGE_TITLE, expectedAiringScheduleEdge)
		));
	}

	@Test
	void fromAiringSchedule_NullSchedule_ReturnCorrectString() {
		//given
		AiringSchedule schedule = null;

		//when
		Exception thrownException = Assertions.assertThrows(
				NullPointerException.class,
				() -> AiringScheduleEdge.fromAiringSchedule(schedule)
		);

		//then
		assertThat(thrownException, instanceOf(NullPointerException.class));
		assertThat(thrownException.getMessage(), notNullValue());
	}

	@Test
	void fromNothing_WithoutSchedule_ReturnCorrectString() {
		//given
		Set<ParameterString> expectedAiringScheduleEdge = TestUtils.getParameterStringSetField("id");

		//when
		AiringScheduleEdge actualAiringScheduleEdge = AiringScheduleEdge.fromNothing();

		//then
		assertThat(actualAiringScheduleEdge.getAiringScheduleEdgeString(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(AiringScheduleEdge.AIRING_SCHEDULE_EDGE_TITLE, expectedAiringScheduleEdge)
		));
	}

	@Test
	void fromAiringSchedule_WithSchedule_ReturnCorrectString() {
		//given
		AiringSchedule schedule = AiringSchedule.getAiringScheduleBuilder()
				.episode()
				.build();
		Set<ParameterString> expectedAiringScheduleEdge = TestUtils.getParameterStringSetField(
				"id",
				"node " + schedule.getAiringScheduleStringWithoutFieldName()
		);

		//when
		AiringScheduleEdge actualAiringScheduleEdge = AiringScheduleEdge.fromAiringSchedule(schedule);

		//then
		assertThat(actualAiringScheduleEdge.getAiringScheduleEdgeString(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(AiringScheduleEdge.AIRING_SCHEDULE_EDGE_TITLE, expectedAiringScheduleEdge)
		));
	}

	@Test
	void getAiringScheduleEdgeWithoutFieldName_WithoutSchedule_ReturnCorrectString() {
		//given
		Set<ParameterString> expectedAiringScheduleEdge = TestUtils.getParameterStringSetField("id");

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
		Set<ParameterString> expectedAiringScheduleEdge = TestUtils.getParameterStringSetField(
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