package anime.app.integrationTests.config;

import anime.app.anilist.request.query.common.QueryElements;
import anime.app.configuration.beans.ObjectMapperConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class GraphQlUtils {
	public static JsonNode addElementToCreateGraphQlQueryAnwser(JsonNode answer, QueryElements element, ObjectMapper mapper) {
		return mapper.createObjectNode().set("data", mapper.createObjectNode().set(element.name(), answer));
	}

	public JsonNode addElementToCreateGraphQlQueryAnwser(JsonNode answer, QueryElements element) {
		ObjectMapper mapper = ObjectMapperConfiguration.ObjectMapperFactory.getNewObjectMapper();
		return mapper.createObjectNode().set("data", mapper.createObjectNode().set(element.name(), answer));
	}

	public static <T> Map<T, Object> readStringIntoMap(
			String field,
			Class<T> keyType,
			ObjectMapper mapper) throws JsonProcessingException {
		return mapper.readValue(field, mapper.getTypeFactory().constructMapType(HashMap.class, keyType, Object.class));
	}

	public static Map<String, Object> readStringIntoStringKeyMap(
			String field,
			ObjectMapper mapper) throws JsonProcessingException {
		return mapper.readValue(field, mapper.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class));
	}
}
