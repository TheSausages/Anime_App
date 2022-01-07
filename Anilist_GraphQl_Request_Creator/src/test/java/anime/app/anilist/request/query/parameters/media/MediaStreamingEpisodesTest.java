package anime.app.anilist.request.query.parameters.media;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MediaStreamingEpisodesTest {

	@Test
	void getMediaStreamingEpisodesBuilder__ReturnValidBuilder() {
		//given

		//when
		MediaStreamingEpisodes.MediaStreamingEpisodesBuilder builder = MediaStreamingEpisodes.getMediaStreamingEpisodesBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(MediaStreamingEpisodes.MediaStreamingEpisodesBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Media Streaming Episodes Builder")
	class MediaStreamingEpisodesBuilderTest {
		@Test
		void mediaStreamingEpisodesBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> MediaStreamingEpisodes.getMediaStreamingEpisodesBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void mediaStreamingEpisodesBuilder_Site_ReturnValidString() {
			//given
			String expectedEpisodes = "streamingEpisodes {\nsite\n}";

			//when
			MediaStreamingEpisodes actualEpisodes = MediaStreamingEpisodes.getMediaStreamingEpisodesBuilder()
					.site()
					.build();

			//then
			assertThat(actualEpisodes.getStreamingEpisode(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedEpisodes)
			));
		}

		@Test
		void mediaStreamingEpisodesBuilder_Title_ReturnValidString() {
			//given
			String expectedEpisodes = "streamingEpisodes {\ntitle\n}";

			//when
			MediaStreamingEpisodes actualEpisodes = MediaStreamingEpisodes.getMediaStreamingEpisodesBuilder()
					.title()
					.build();

			//then
			assertThat(actualEpisodes.getStreamingEpisode(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedEpisodes)
			));
		}

		@Test
		void mediaStreamingEpisodesBuilder_Url_ReturnValidString() {
			//given
			String expectedEpisodes = "streamingEpisodes {\nurl\n}";

			//when
			MediaStreamingEpisodes actualEpisodes = MediaStreamingEpisodes.getMediaStreamingEpisodesBuilder()
					.url()
					.build();

			//then
			assertThat(actualEpisodes.getStreamingEpisode(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedEpisodes)
			));
		}

		@Test
		void mediaStreamingEpisodesBuilder_Thumbnail_ReturnValidString() {
			//given
			String expectedEpisodes = "streamingEpisodes {\nthumbnail\n}";

			//when
			MediaStreamingEpisodes actualEpisodes = MediaStreamingEpisodes.getMediaStreamingEpisodesBuilder()
					.thumbnail()
					.build();

			//then
			assertThat(actualEpisodes.getStreamingEpisode(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedEpisodes)
			));
		}

		@Test
		void mediaStreamingEpisodesBuilder_AllParameters_ReturnValidString() {
			//given
			String expectedEpisodes = "streamingEpisodes {\nsite\nurl\ntitle\nthumbnail\n}";

			//when
			MediaStreamingEpisodes actualEpisodes = MediaStreamingEpisodes.getMediaStreamingEpisodesBuilder()
					.site()
					.url()
					.title()
					.thumbnail()
					.build();

			//then
			assertThat(actualEpisodes.getStreamingEpisode(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedEpisodes)
			));
		}
	}
}