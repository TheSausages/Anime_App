package anime.app.anilist.request.query.parameters.connections.trends;

import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static anime.app.anilist.request.query.parameters.connections.trends.MediaTrendEdge.MEDIA_TREND_EDGE_TITLE;
import static anime.app.anilist.request.utils.QueryTitleWithParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MediaTrendEdgeTest {

	@Test
	void getStudioEdgeWithoutFieldName__ReturnCorrectString() {
		//given
		MediaTrend trend = MediaTrend.getMediaTrendBuilder().mediaId().build();
		Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
				"mediaId"
		);

		//when
		MediaTrendEdge actualEdge = new MediaTrendEdge(trend);

		//then
		assertThat(actualEdge.getStudioEdgeWithoutFieldName(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedEdge)
		));
	}

	@Test
	void fromMediaTrend_NullArgument_ThrowException() {
		//given
		MediaTrend trend = null;

		//when
		Exception thrownException = Assertions.assertThrows(
				NullPointerException.class,
				() -> MediaTrendEdge.fromMediaTrend(trend)
		);

		//then
		assertThat(thrownException, instanceOf(NullPointerException.class));
		assertThat(thrownException.getMessage(), notNullValue());
	}

	@Test
	void fromMediaTrend_NonNullArgument_ReturnCorrectString() {
		//given
		MediaTrend trend = MediaTrend.getMediaTrendBuilder().mediaId().build();
		Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
				"mediaId"
		);

		//when
		MediaTrendEdge actualEdge = MediaTrendEdge.fromMediaTrend(trend);

		//then
		assertThat(actualEdge.getStudioEdgeString(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(MEDIA_TREND_EDGE_TITLE, expectedEdge)
		));
	}

	@Test
	void constructor_NullArgument_ThrowException() {
		//given
		MediaTrend trend = null;

		//when
		Exception thrownException = Assertions.assertThrows(
				NullPointerException.class,
				() -> new MediaTrendEdge(trend)
		);

		//then
		assertThat(thrownException, instanceOf(NullPointerException.class));
		assertThat(thrownException.getMessage(), notNullValue());
	}

	@Test
	void constructor_NonNullArgument_ReturnCorrectString() {
		//given
		MediaTrend trend = MediaTrend.getMediaTrendBuilder().mediaId().build();
		Set<ParameterString> expectedEdge = TestUtils.getParameterStringSetField(
				"mediaId"
		);

		//when
		MediaTrendEdge actualEdge = new MediaTrendEdge(trend);

		//then
		assertThat(actualEdge.getStudioEdgeString(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(MEDIA_TREND_EDGE_TITLE, expectedEdge)
		));
	}
}