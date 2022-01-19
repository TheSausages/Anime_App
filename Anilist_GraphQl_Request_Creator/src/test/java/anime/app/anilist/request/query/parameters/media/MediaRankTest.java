package anime.app.anilist.request.query.parameters.media;

import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static anime.app.anilist.request.utils.QueryTitleWithParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MediaRankTest {

	@Test
	void getMediaRankBuilder__ReturnValidBuilder() {
		//given

		//when
		MediaRank.MediaRankBuilder builder = MediaRank.getMediaRankBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(MediaRank.MediaRankBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Media Rank Builder")
	class MediaRankBuilderTest {
		@Test
		void mediaRankBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> MediaRank.getMediaRankBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void mediaRankBuilder_Id_ReturnValidString() {
			//given
			Set<ParameterString> expectedRank = TestUtils.getParameterStringSetField("id");

			//when
			MediaRank actualRank = MediaRank.getMediaRankBuilder().id().build();

			//then
			assertThat(actualRank.getRank(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MediaRank.RANKING_TITLE, expectedRank)
			));
		}

		@Test
		void mediaRankBuilder_Rank_ReturnValidString() {
			//given
			Set<ParameterString> expectedRank = TestUtils.getParameterStringSetField("rank");

			//when
			MediaRank actualRank = MediaRank.getMediaRankBuilder().rank().build();

			//then
			assertThat(actualRank.getRank(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MediaRank.RANKING_TITLE, expectedRank)
			));
		}

		@Test
		void mediaRankBuilder_Type_ReturnValidString() {
			//given
			Set<ParameterString> expectedRank = TestUtils.getParameterStringSetField("type");

			//when
			MediaRank actualRank = MediaRank.getMediaRankBuilder().type().build();

			//then
			assertThat(actualRank.getRank(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MediaRank.RANKING_TITLE, expectedRank)
			));
		}

		@Test
		void mediaRankBuilder_Format_ReturnValidString() {
			//given
			Set<ParameterString> expectedRank = TestUtils.getParameterStringSetField("format");

			//when
			MediaRank actualRank = MediaRank.getMediaRankBuilder().format().build();

			//then
			assertThat(actualRank.getRank(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MediaRank.RANKING_TITLE, expectedRank)
			));
		}

		@Test
		void mediaRankBuilder_Year_ReturnValidString() {
			//given
			Set<ParameterString> expectedRank = TestUtils.getParameterStringSetField("year");

			//when
			MediaRank actualRank = MediaRank.getMediaRankBuilder().year().build();

			//then
			assertThat(actualRank.getRank(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MediaRank.RANKING_TITLE, expectedRank)
			));
		}

		@Test
		void mediaRankBuilder_Season_ReturnValidString() {
			//given
			Set<ParameterString> expectedRank = TestUtils.getParameterStringSetField("season");

			//when
			MediaRank actualRank = MediaRank.getMediaRankBuilder().season().build();

			//then
			assertThat(actualRank.getRank(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MediaRank.RANKING_TITLE, expectedRank)
			));
		}

		@Test
		void mediaRankBuilder_AllTime_ReturnValidString() {
			//given
			Set<ParameterString> expectedRank = TestUtils.getParameterStringSetField("allTime");

			//when
			MediaRank actualRank = MediaRank.getMediaRankBuilder().allTime().build();

			//then
			assertThat(actualRank.getRank(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MediaRank.RANKING_TITLE, expectedRank)
			));
		}

		@Test
		void mediaRankBuilder_Context_ReturnValidString() {
			//given
			Set<ParameterString> expectedRank = TestUtils.getParameterStringSetField("context");

			//when
			MediaRank actualRank = MediaRank.getMediaRankBuilder().context().build();

			//then
			assertThat(actualRank.getRank(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MediaRank.RANKING_TITLE, expectedRank)
			));
		}

		@Test
		void mediaRankBuilder_AllParameters_ReturnValidString() {
			//given
			Set<ParameterString> expectedRank = TestUtils.getParameterStringSetField(
					"id",
					"rank",
					"type",
					"format",
					"year",
					"season",
					"allTime",
					"context"
			);

			//when
			MediaRank actualRank = MediaRank.getMediaRankBuilder()
					.id()
					.rank()
					.type()
					.format()
					.year()
					.season()
					.allTime()
					.context()
					.build();

			//then
			assertThat(actualRank.getRank(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(MediaRank.RANKING_TITLE, expectedRank)
			));
		}
	}
}