package anime.app.anilist.request.query.parameters.connections.media;

import anime.app.anilist.request.query.media.Field;
import anime.app.anilist.request.query.media.Media;
import anime.app.anilist.request.query.parameters.connections.PageInfo;
import anime.app.anilist.request.query.parameters.connections.characters.CharacterEdge;
import anime.app.anilist.request.query.parameters.media.MediaFormat;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static anime.app.anilist.request.utils.QueryTitleWithParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MediaConnectionTest {

	@Test
	void getMediaConnectionWithoutFieldName__ReturnCorrectString() {
		//given
		Media media = Media.getMediaArgumentBuilder(Field.getFieldBuilder().status().build()).format(MediaFormat.TV).build();
		List<String> expectedEdge = TestUtils.buildFieldParameterStringSet(
				"nodes " + media.getRequestedMediaFields()
		);


		//when
		MediaConnection actualConnection = MediaConnection.getMediaConnectionBuilder()
				.nodes(media)
				.build();

		//then
		assertThat(actualConnection.getMediaConnectionWithoutFieldName(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedEdge)
		));
	}

	@Test
	void getMediaConnectionBuilder__ReturnValidBuilder() {
		//given

		//when
		MediaConnection.MediaConnectionBuilder builder = MediaConnection.getMediaConnectionBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(MediaConnection.MediaConnectionBuilder.class)
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
					() -> CharacterEdge.getCharacterEdgeBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void mediaConnectionBuilder_Edge_ReturnCorrectString() {
			//given
			MediaEdge edge = MediaEdge.getMediaEdgeBuilder().id().build();
			List<String> expectedEdge = TestUtils.buildFieldParameterStringSet(
					"edges " + edge.getMediaEdgeWithoutFieldName()
			);


			//when
			MediaConnection actualConnection = MediaConnection.getMediaConnectionBuilder()
					.edge(edge)
					.build();

			//then
			assertThat(actualConnection.getMediaConnectionWithoutFieldName(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(expectedEdge)
			));
		}

		@Test
		void mediaConnectionBuilder_Nodes_ReturnCorrectString() {
			//given
			Media media = Media.getMediaArgumentBuilder(Field.getFieldBuilder().status().build()).format(MediaFormat.TV).build();
			List<String> expectedEdge = TestUtils.buildFieldParameterStringSet(
					"nodes " + media.getRequestedMediaFields()
			);


			//when
			MediaConnection actualConnection = MediaConnection.getMediaConnectionBuilder()
					.nodes(media)
					.build();

			//then
			assertThat(actualConnection.getMediaConnectionWithoutFieldName(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(expectedEdge)
			));
		}

		@Test
		void mediaConnectionBuilder_PageInfo_ReturnCorrectString() {
			//given
			PageInfo pageInfo = PageInfo.getPageInfoBuilder().perPage().build();
			List<String> expectedEdge = TestUtils.buildFieldParameterStringSet(
					pageInfo.getPageInfoString()
			);


			//when
			MediaConnection actualConnection = MediaConnection.getMediaConnectionBuilder()
					.pageInfo(pageInfo)
					.build();

			//then
			assertThat(actualConnection.getMediaConnectionWithoutFieldName(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(expectedEdge)
			));
		}

		@Test
		void mediaConnectionBuilder_AllParameters_ReturnCorrectString() {
			//given
			MediaEdge edge = MediaEdge.getMediaEdgeBuilder().id().build();
			Media media = Media.getMediaArgumentBuilder(Field.getFieldBuilder().status().build()).format(MediaFormat.TV).build();
			PageInfo pageInfo = PageInfo.getPageInfoBuilder().perPage().build();
			List<String> expectedEdge = TestUtils.buildFieldParameterStringSet(
					pageInfo.getPageInfoString(),
					"nodes " + media.getRequestedMediaFields(),
					"edges " + edge.getMediaEdgeWithoutFieldName()
			);


			//when
			MediaConnection actualConnection = MediaConnection.getMediaConnectionBuilder()
					.edge(edge)
					.nodes(media)
					.pageInfo(pageInfo)
					.build();

			//then
			assertThat(actualConnection.getMediaConnectionWithoutFieldName(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(expectedEdge)
			));
		}
	}
}