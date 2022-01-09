package anime.app.anilist.request.query.parameters.connections.airingschedule;

import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.connections.PageInfo;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static anime.app.anilist.request.query.parameters.connections.airingschedule.AiringScheduleConnection.airingScheduleConnectionTitle;
import static anime.app.anilist.request.utils.QueryTitleAndParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class AiringScheduleConnectionTest {

	@Test
	void getAiringScheduleConnectionWithoutFieldName__ReturnCorrectString() {
		//given
		PageInfo info = PageInfo.getPageInfoBuilder().total().build();
		Set<ParameterString> expectedAiringScheduleEdge = TestUtils.getParameterStringSet(
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
			Set<ParameterString> expectedAiringScheduleEdge = TestUtils.getParameterStringSet(
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
					containsTitleAndAllSetElements(airingScheduleConnectionTitle, expectedAiringScheduleEdge)
			));
		}

		@Test
		void airingScheduleConnectionBuilder_Nodes_ReturnCorrectString() {
			//given
			AiringSchedule schedule = AiringSchedule.getAiringScheduleBuilder().id().build();
			Set<ParameterString> expectedAiringScheduleEdge = TestUtils.getParameterStringSet(
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
					containsTitleAndAllSetElements(airingScheduleConnectionTitle, expectedAiringScheduleEdge)
			));
		}

		@Test
		void airingScheduleConnectionBuilder_PageInfo_ReturnCorrectString() {
			//given
			PageInfo info = PageInfo.getPageInfoBuilder().total().build();
			Set<ParameterString> expectedAiringScheduleEdge = TestUtils.getParameterStringSet(
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
					containsTitleAndAllSetElements(airingScheduleConnectionTitle, expectedAiringScheduleEdge)
			));
		}
	}
}