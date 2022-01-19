package anime.app.anilist.request.query;

import anime.app.anilist.request.query.media.Field;
import anime.app.anilist.request.query.media.Media;
import anime.app.anilist.request.query.parameters.common.BasicQueryParameters;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class QueryTest {
	private static final ObjectMapper mapper = new ObjectMapper()
			.enable(SerializationFeature.INDENT_OUTPUT)
			.setDefaultPrettyPrinter(new DefaultPrettyPrinter())
			.registerModule(new JavaTimeModule())
			.setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));

	@Test
	void fromQueryElement_Media_ReturnCorrectNode() {
		//given
		Field field = Field.getFieldBuilder().parameter(BasicQueryParameters.ID).build();
		int id = 135485;
		JsonNode expectedVariables = mapper.convertValue(
				Map.of("id", id),
				JsonNode.class
		);
		String expectedQuery = "query($id: Int) {\nMedia(id: $id) {\nid\n}\n}";

		Media media = Media.getMediaArgumentBuilder(field).id(id).build();

		//when
		JsonNode actualNode = Query.buildQuery(media);

		//then
		//check class
		assertThat(actualNode.get("query"), allOf(
				notNullValue(),
				instanceOf(TextNode.class)
		));

		//check the value
		assertThat(actualNode.get("query").asText(), allOf(
				notNullValue(),
				instanceOf(String.class),
				equalTo(expectedQuery)
		));

		assertThat(actualNode.get("variables"), allOf(
				notNullValue(),
				instanceOf(JsonNode.class),
				equalTo(expectedVariables)
		));
	}
}