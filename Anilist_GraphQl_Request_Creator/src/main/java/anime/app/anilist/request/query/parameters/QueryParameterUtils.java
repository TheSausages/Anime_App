package anime.app.anilist.request.query.parameters;

import anime.app.anilist.request.query.common.ParameterString;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QueryParameterUtils {
	private QueryParameterUtils() {
		throw new UnsupportedOperationException("Don't initialize QueryParameterUtils");
	}

	public static String buildQueryFieldElementString(String name, Set<ParameterString> parameters) {
		Objects.requireNonNull(name, "Name cannot be null");
		Objects.requireNonNull(parameters, "Parameters cannot be null");

		StringBuilder parameterStringBuilder = new StringBuilder(name).append(" {\n");
		parameters.forEach(lan -> parameterStringBuilder.append(lan).append("\n"));
		parameterStringBuilder.append("}");

		return parameterStringBuilder.toString();
	}

	public static String buildQueryFieldElementString(String name, String... parameters) {
		return buildQueryFieldElementString(name, Stream.of(parameters).map(ParameterString::fromString).collect(Collectors.toSet()));
	}

	public static String buildQueryFieldElementArgumentsString(Set<ParameterString> arguments) {
		Objects.requireNonNull(arguments, "Arguments cannot be null");

		StringBuilder argumentStringBuilder = new StringBuilder("(");
		arguments.forEach(argument -> argumentStringBuilder.append(argument).append(", "));

		//get rid of last 2 characters - those are ", "
		argumentStringBuilder.setLength(argumentStringBuilder.length() - 2);

		return argumentStringBuilder.append(")").toString();
	}
}
