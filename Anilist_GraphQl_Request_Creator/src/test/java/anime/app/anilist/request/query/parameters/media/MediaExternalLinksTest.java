package anime.app.anilist.request.query.parameters.media;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class MediaExternalLinksTest {

	@Test
	void getMediaExternalLinkBuilder__ReturnValidBuilder() {
		//given

		//when
		MediaExternalLinks.MediaExternalLinkBuilder builder = MediaExternalLinks.getMediaExternalLinkBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(MediaExternalLinks.MediaExternalLinkBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Media External Links Builder")
	class MediaExternalLinksBuilderTest {
		@Test
		void mediaExternalLinksBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> MediaExternalLinks.getMediaExternalLinkBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void mediaExternalLinksBuilder_Id_ReturnValidString() {
			//given
			String expectedLinks = "externalLinks {\nid\n}";

			//when
			MediaExternalLinks actualLinks = MediaExternalLinks.getMediaExternalLinkBuilder().id().build();

			//then
			assertThat(actualLinks.getExternalLink(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedLinks)
			));
		}

		@Test
		void mediaExternalLinksBuilder_Url_ReturnValidString() {
			//given
			String expectedLinks = "externalLinks {\nurl\n}";

			//when
			MediaExternalLinks actualLinks = MediaExternalLinks.getMediaExternalLinkBuilder().url().build();

			//then
			assertThat(actualLinks.getExternalLink(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedLinks)
			));
		}

		@Test
		void mediaExternalLinksBuilder_Site_ReturnValidString() {
			//given
			String expectedLinks = "externalLinks {\nsite\n}";

			//when
			MediaExternalLinks actualLinks = MediaExternalLinks.getMediaExternalLinkBuilder().site().build();

			//then
			assertThat(actualLinks.getExternalLink(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedLinks)
			));
		}

		@Test
		void mediaExternalLinksBuilder_AllParameters_ReturnValidString() {
			//given
			String expectedLinks = "externalLinks {\nid\nurl\nsite\n}";

			//when
			MediaExternalLinks actualLinks = MediaExternalLinks.getMediaExternalLinkBuilder()
					.id()
					.url()
					.site()
					.build();

			//then
			assertThat(actualLinks.getExternalLink(), allOf(
					notNullValue(),
					instanceOf(String.class),
					equalTo(expectedLinks)
			));
		}
	}
}