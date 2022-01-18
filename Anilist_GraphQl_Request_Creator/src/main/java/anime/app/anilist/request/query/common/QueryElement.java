package anime.app.anilist.request.query.common;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Set;

@Getter
public abstract class QueryElement {
	protected static final ObjectMapper MAPPER =
			new ObjectMapper()
					.enable(SerializationFeature.INDENT_OUTPUT)
					.setDefaultPrettyPrinter(new DefaultPrettyPrinter())
					.registerModule(new JavaTimeModule())
					.setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));

	protected final String elementString;
	protected final Set<ParameterString> queryParameters;
	protected final JsonNode variables;

	protected QueryElement(String elementString, Set<ParameterString> queryParameters, JsonNode variables) {
		this.elementString = elementString;
		this.queryParameters = queryParameters;
		this.variables = variables;
	}

	@Override
	public String toString() {
		return elementString;
	}
}
