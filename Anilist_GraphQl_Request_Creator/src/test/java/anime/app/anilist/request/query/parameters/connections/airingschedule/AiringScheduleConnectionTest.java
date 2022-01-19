package anime.app.anilist.request.query.parameters.connections.airingschedule;

import anime.app.anilist.request.query.parameters.connections.PageInfo;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static anime.app.anilist.request.query.parameters.connections.airingschedule.AiringScheduleConnection.AIRING_SCHEDULE_CONNECTION_TITLE;
import static anime.app.anilist.request.utils.QueryTitleWithParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class AiringScheduleConnectionTest {

	@Test
	void getAiringScheduleConnectionWithoutFieldName__ReturnCorrectString() {
		//given
		PageInfo info = PageInfo.getPageInfoBuilder().total().build();
		List<String> expectedAiringScheduleEdge = TestUtils.buildFieldParameterStringSet(
				info.getPageInfoString()
		);

		//when
		AiringScheduleConnection actualConnection = AiringScheduleConnection.getAiringScheduleConnectionBuilder()
				.pageInfo(info)
				.build();

		//then
		assertThat(actualConnection.getAiringScheduleConnectionWithoutFieldName(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedAiringScheduleEdge)
		));
	}

	@Test
	void getAiringScheduleConnectionBuilder__ReturnValidBuilder() {
		//given

		//when
		AiringScheduleConnection.AiringScheduleConnectionBuilder builder = AiringScheduleConnection.getAiringScheduleConnectionBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(AiringScheduleConnection.AiringScheduleConnectionBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Airing Schedule Connection Builder")
	class AiringScheduleConnectionBuilderTest {
		@Test
		void airingScheduleConnectionBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> AiringScheduleConnection.getAiringScheduleConnectionBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void airingScheduleConnectionBuilder_Edge_ReturnCorrectString() {
			//given
			AiringScheduleEdge edge = new AiringScheduleEdge();
			List<String> expectedAiringScheduleEdge = TestUtils.buildFieldParameterStringSet(
					"edges " + edge.getAiringScheduleEdgeWithoutFieldName()
			);

			//when
			AiringScheduleConnection actualConnection = AiringScheduleConnection.getAiringScheduleConnectionBuilder()
					.edges(edge)
					.build();

			//then
			assertThat(actualConnection.getAiringScheduleConnectionString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(AIRING_SCHEDULE_CONNECTION_TITLE, expectedAiringScheduleEdge)
			));
		}

		@Test
		void airingScheduleConnectionBuilder_Nodes_ReturnCorrectString() {
			//given
			AiringSchedule schedule = AiringSchedule.getAiringScheduleBuilder().id().build();
			List<String> expectedAiringScheduleEdge = TestUtils.buildFieldParameterStringSet(
					"nodes " + schedule.getAiringScheduleStringWithoutFieldName()
			);

			//when
			AiringScheduleConnection actualConnection = AiringScheduleConnection.getAiringScheduleConnectionBuilder()
					.nodes(schedule)
					.build();

			//then
			assertThat(actualConnection.getAiringScheduleConnectionString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(AIRING_SCHEDULE_CONNECTION_TITLE, expectedAiringScheduleEdge)
			));
		}

		@Test
		void airingScheduleConnectionBuilder_PageInfo_ReturnCorrectString() {
			//given
			PageInfo info = PageInfo.getPageInfoBuilder().total().build();
			List<String> expectedAiringScheduleEdge = TestUtils.buildFieldParameterStringSet(
					info.getPageInfoString()
			);

			//when
			AiringScheduleConnection actualConnection = AiringScheduleConnection.getAiringScheduleConnectionBuilder()
					.pageInfo(info)
					.build();

			//then
			assertThat(actualConnection.getAiringScheduleConnectionString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(AIRING_SCHEDULE_CONNECTION_TITLE, expectedAiringScheduleEdge)
			));
		}

		@Test
		void airingScheduleConnectionBuilder_AllParameters_ReturnCorrectString() {
			//given
			AiringScheduleEdge edge = new AiringScheduleEdge();
			AiringSchedule schedule = AiringSchedule.getAiringScheduleBuilder().id().build();
			PageInfo info = PageInfo.getPageInfoBuilder().total().build();
			List<String> expectedAiringScheduleEdge = TestUtils.buildFieldParameterStringSet(
					"edges " + edge.getAiringScheduleEdgeWithoutFieldName(),
					"nodes " + schedule.getAiringScheduleStringWithoutFieldName(),
					info.getPageInfoString()
			);

			//when
			AiringScheduleConnection actualConnection = AiringScheduleConnection.getAiringScheduleConnectionBuilder()
					.edges(edge)
					.nodes(schedule)
					.pageInfo(info)
					.build();

			//then
			assertThat(actualConnection.getAiringScheduleConnectionString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(AIRING_SCHEDULE_CONNECTION_TITLE, expectedAiringScheduleEdge)
			));
		}
	}
}