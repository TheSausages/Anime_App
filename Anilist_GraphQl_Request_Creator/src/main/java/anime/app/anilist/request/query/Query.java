package anime.app.anilist.request.query;

import anime.app.anilist.request.query.common.QueryElement;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;

import java.text.SimpleDateFormat;

@Getter
public class Query {
	public final static String QUERY_TITLE = "query";

	private static final ObjectMapper mapper = new ObjectMapper()
				.enable(SerializationFeature.INDENT_OUTPUT)
				.setDefaultPrettyPrinter(new DefaultPrettyPrinter())
				.registerModule(new JavaTimeModule())
				.setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));

	private Query() {
		throw new UnsupportedOperationException();
	}

	public static JsonNode buildQuery(QueryElement queryElement) {
		StringBuilder query = new StringBuilder(QUERY_TITLE);

		//build the parameters
		query.append("(");
		queryElement.getQueryParameters().forEach(param -> query.append(param).append(", "));
		//get rid og the ', ' at the end
		query.setLength(query.length() - 2);
		query.append(")");

		query.append(" ");

		//build the field
		query.append("{\n");
		query.append(queryElement.getElementString());
		query.append("\n}");

		//Insert it into a JsonNode
		ObjectNode queryNode = mapper.createObjectNode();
		queryNode.put("query", query.toString());
		queryNode.set("variables", mapper.convertValue(queryElement.getVariables(), JsonNode.class));

		return queryNode;
	}
}
