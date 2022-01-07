package anime.app.anilist.request.query.common;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

import java.util.Set;

@Getter
public abstract class QueryElement {
	private final String elementString;
	private final Set<ParameterString> queryParameters;
	private final JsonNode variables;

	QueryElement(String elementString, Set<ParameterString> queryParameters, JsonNode variables) {
		this.elementString = elementString;
		this.queryParameters = queryParameters;
		this.variables = variables;
	}
}
