package anime.app.anilist.request.query.common;

import lombok.Getter;

import java.util.Map;
import java.util.Set;

@Getter
public abstract class QueryElement {
	protected final String elementString;
	protected final Set<String> queryParameters;
	protected final Map<String, Object> variables;

	protected QueryElement(String elementString, Set<String> queryParameters, Map<String, Object> variables) {
		this.elementString = elementString;
		this.queryParameters = queryParameters;
		this.variables = variables;
	}

	@Override
	public String toString() {
		return elementString;
	}
}
