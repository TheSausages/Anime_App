package anime.app.anilist.request.query.parameters;

import anime.app.anilist.request.query.common.ParameterString;

import java.util.Objects;
import java.util.Set;

public class QueryParameterUtils {
	private QueryParameterUtils() {
		throw new UnsupportedOperationException("Don't initialize QueryParameterUtils");
	}

	public static String buildString(String name, Set<ParameterString> parameters) {
		Objects.requireNonNull(name, "Name cannot be null");
		Objects.requireNonNull(parameters, "Parameters cannot be null");

		StringBuilder parameterStringBuilder = new StringBuilder(name).append(" {\n");
		parameters.forEach(lan -> parameterStringBuilder.append(lan).append("\n"));
		parameterStringBuilder.append("}");

		return parameterStringBuilder.toString();
	}
}
