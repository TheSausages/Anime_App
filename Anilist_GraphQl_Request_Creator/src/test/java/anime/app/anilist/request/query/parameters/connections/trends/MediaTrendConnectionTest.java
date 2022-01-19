package anime.app.anilist.request.query.parameters.connections.trends;

import anime.app.anilist.request.query.parameters.connections.PageInfo;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static anime.app.anilist.request.query.parameters.connections.trends.MediaTrendConnection.MEDIA_TRENDS_CONNECTION_TITLE;
import static anime.app.anilist.request.utils.QueryTitleWithParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MediaTrendConnectionTest {

	@Test
	void getMediaConnectionWithoutFieldName__ReturnCorrectString() {
		//given
		PageInfo info = PageInfo.getPageInfoBuilder().total().build();
		List<String> expectedConnection = TestUtils.buildFieldParameterStringSet(
				info.getPageInfoString()
		);

		//when
		MediaTrendConnection actualConnection = MediaTrendConnection.getMediaConnectionBuilder()
				.pageInfo(info)
				.build();

		//then
		assertThat(actualConnection.getMediaConnectionWithoutFieldName(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedConnection)
		));
	}

	@Test
	void getMediaConnectionBuilder__ReturnValidBuilder() {
		//given

		//when
		MediaTrendConnection.MediaConnectionBuilder builder = MediaTrendConnection.getMediaConnectionBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(MediaTrendConnection.MediaConnectionBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Media Connection Builder")
	class MediaConnectionBuilderTest {
		@Test
		void mediaConnectionBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> MediaTrendConnection.getMediaConnectionBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void mediaConnectionBuilder_Edge_ReturnCorrectString() {
			//given
			MediaTrend trend = MediaTrend.getMediaTrendBuilder().mediaId().build();
			MediaTrendEdge edge = MediaTrendEdge.fromMediaTrend(trend);
			List<String> expectedAiringScheduleEdge = TestUtils.buildFieldParameterStringSet(
					"edges " + edge.getStudioEdgeWithoutFieldName()
			);

			//when
			MediaTrendConnection actualConnection = MediaTrendConnection.getMediaConnectionBuilder()
					.edges(edge)
					.build();

			//then
			assertThat(actualConnection.getMediaConnectionString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MEDIA_TRENDS_CONNECTION_TITLE, expectedAiringScheduleEdge)
			));
		}

		@Test
		void mediaConnectionBuilder_Nodes_ReturnCorrectString() {
			//given
			MediaTrend trend = MediaTrend.getMediaTrendBuilder().mediaId().build();
			List<String> expectedConnection = TestUtils.buildFieldParameterStringSet(
					"nodes " + trend.getMediaTrendWithoutFieldName()
			);

			//when
			MediaTrendConnection actualConnection = MediaTrendConnection.getMediaConnectionBuilder()
					.nodes(trend)
					.build();

			//then
			assertThat(actualConnection.getMediaConnectionString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MEDIA_TRENDS_CONNECTION_TITLE, expectedConnection)
			));
		}

		@Test
		void mediaConnectionBuilder_PageInfo_ReturnCorrectString() {
			//given
			PageInfo info = PageInfo.getPageInfoBuilder().total().build();
			List<String> expectedConnection = TestUtils.buildFieldParameterStringSet(
					info.getPageInfoString()
			);

			//when
			MediaTrendConnection actualConnection = MediaTrendConnection.getMediaConnectionBuilder()
					.pageInfo(info)
					.build();

			//then
			assertThat(actualConnection.getMediaConnectionString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MEDIA_TRENDS_CONNECTION_TITLE, expectedConnection)
			));
		}

		@Test
		void mediaConnectionBuilder_AllParameters_ReturnCorrectString() {
			//given
			MediaTrend trend = MediaTrend.getMediaTrendBuilder().mediaId().build();
			MediaTrendEdge edge = MediaTrendEdge.fromMediaTrend(trend);
			PageInfo info = PageInfo.getPageInfoBuilder().total().build();
			List<String>expectedAiringScheduleEdge = TestUtils.buildFieldParameterStringSet(
					"edges " + edge.getStudioEdgeWithoutFieldName(),
					"nodes " + trend.getMediaTrendWithoutFieldName(),
					info.getPageInfoString()
			);

			//when
			MediaTrendConnection actualConnection = MediaTrendConnection.getMediaConnectionBuilder()
					.edges(edge)
					.nodes(trend)
					.pageInfo(info)
					.build();

			//then
			assertThat(actualConnection.getMediaConnectionString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MEDIA_TRENDS_CONNECTION_TITLE, expectedAiringScheduleEdge)
			));
		}
	}
}