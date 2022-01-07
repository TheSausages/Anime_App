package anime.app.anilist.request.query.parameters.media;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
			String expectedRank = "ranking {\nid\n}";

			//when
			MediaRank actualRank = MediaRank.getMediaRankBuilder().id().build();

			//then
			assertThat(actualRank.getRank(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedRank)
			));
		}

		@Test
		void mediaRankBuilder_Rank_ReturnValidString() {
			//given
			String expectedRank = "ranking {\nrank\n}";

			//when
			MediaRank actualRank = MediaRank.getMediaRankBuilder().rank().build();

			//then
			assertThat(actualRank.getRank(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedRank)
			));
		}

		@Test
		void mediaRankBuilder_Type_ReturnValidString() {
			//given
			String expectedRank = "ranking {\ntype\n}";

			//when
			MediaRank actualRank = MediaRank.getMediaRankBuilder().type().build();

			//then
			assertThat(actualRank.getRank(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedRank)
			));
		}

		@Test
		void mediaRankBuilder_Format_ReturnValidString() {
			//given
			String expectedRank = "ranking {\nformat\n}";

			//when
			MediaRank actualRank = MediaRank.getMediaRankBuilder().format().build();

			//then
			assertThat(actualRank.getRank(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedRank)
			));
		}

		@Test
		void mediaRankBuilder_Year_ReturnValidString() {
			//given
			String expectedRank = "ranking {\nyear\n}";

			//when
			MediaRank actualRank = MediaRank.getMediaRankBuilder().year().build();

			//then
			assertThat(actualRank.getRank(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedRank)
			));
		}

		@Test
		void mediaRankBuilder_Season_ReturnValidString() {
			//given
			String expectedRank = "ranking {\nseason\n}";

			//when
			MediaRank actualRank = MediaRank.getMediaRankBuilder().season().build();

			//then
			assertThat(actualRank.getRank(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedRank)
			));
		}

		@Test
		void mediaRankBuilder_AllTime_ReturnValidString() {
			//given
			String expectedRank = "ranking {\nallTime\n}";

			//when
			MediaRank actualRank = MediaRank.getMediaRankBuilder().allTime().build();

			//then
			assertThat(actualRank.getRank(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedRank)
			));
		}

		@Test
		void mediaRankBuilder_Context_ReturnValidString() {
			//given
			String expectedRank = "ranking {\ncontext\n}";

			//when
			MediaRank actualRank = MediaRank.getMediaRankBuilder().context().build();

			//then
			assertThat(actualRank.getRank(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedRank)
			));
		}

		@Test
		void mediaRankBuilder_AllParameters_ReturnValidString() {
			//given
			String expectedRank = "ranking {\nid\nrank\ntype\nformat\nyear\nseason\nallTime\ncontext\n}";

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
					equalTo(expectedRank)
			));
		}
	}
}