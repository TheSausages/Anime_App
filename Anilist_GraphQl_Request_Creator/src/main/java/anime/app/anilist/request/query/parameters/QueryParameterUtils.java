package anime.app.anilist.request.query.parameters;

import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.common.CommonFieldParameter;
import anime.app.anilist.request.query.parameters.common.CommonParameterFieldNames;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QueryParameterUtils {
	private QueryParameterUtils() {
		throw new UnsupportedOperationException("Don't initialize QueryParameterUtils");
	}

	public static String buildStringField(String name, Set<String> arguments, Set<String> parameters) {
		Objects.requireNonNull(name, "Name cannot be null");
		Objects.requireNonNull(parameters, "Parameters cannot be null");

		StringBuilder parameterStringBuilder = new StringBuilder(name);

		if (Objects.nonNull(arguments) && !arguments.isEmpty()) {
			arguments.forEach(parameterStringBuilder::append);
		}

		if (!name.isBlank()) parameterStringBuilder.append(" ");

		parameterStringBuilder.append("{\n");
		parameters.forEach(lan -> parameterStringBuilder.append(lan).append("\n"));
		parameterStringBuilder.append("}");

		return parameterStringBuilder.toString();
	}

	public static String buildStringField(String name, String arguments, Set<String> parameters) {
		Objects.requireNonNull(name, "Name cannot be null");
		Objects.requireNonNull(parameters, "Parameters cannot be null");

		StringBuilder parameterStringBuilder = new StringBuilder(name);

		if (Objects.nonNull(arguments) && !arguments.isBlank()) parameterStringBuilder.append(arguments);

		if (!name.isBlank()) parameterStringBuilder.append(" ");

		parameterStringBuilder.append("{\n");
		parameters.forEach(lan -> parameterStringBuilder.append(lan).append("\n"));
		parameterStringBuilder.append("}");

		return parameterStringBuilder.toString();
	}

	public static String buildStringField(String name, String... parameters) {
		return buildStringField(
				name,
				(Set<String>) null,
				Stream.of(parameters)
						.filter(Objects::nonNull)
						.collect(Collectors.toSet())
		);
	}

	public static String buildStringField(String name, Set<ParameterString> parameters) {
		Objects.requireNonNull(parameters, "Parameters cannot be null");

		return buildStringField(
				name,
				(Set<String>) null,
				parameters.stream().map(ParameterString::getField).collect(Collectors.toSet())
		);
	}

	public static String buildStringField(String name, ParameterString... parameters) {
		return buildStringField(
				name,
				Stream.of(parameters)
						.filter(Objects::nonNull)
						.collect(Collectors.toSet())
		);
	}

	public static <T, U> ParameterString combineIntoStringField(String fieldName, T arguments, U field) {
		if (Objects.isNull(arguments)) {
			return ParameterString.fromString(fieldName + " " + field.toString());
		} else {
			return ParameterString.fromString(fieldName + arguments.toString() + " " + field.toString());
		}
	}

	public static <T, U> ParameterString combineIntoStringField(CommonParameterFieldNames fieldName, T arguments, U field) {
		return combineIntoStringField(fieldName.getFieldName(), arguments, field);
	}

	public static <U> ParameterString combineIntoStringField(String fieldName, U field) {
		return combineIntoStringField(fieldName, null, field.toString());
	}

	public static <U> ParameterString combineIntoStringField(CommonFieldParameter fieldName, U field) {
		return combineIntoStringField(fieldName.getFieldName(), field);
	}

	public static String buildStringArguments(Set<ParameterString> arguments) {
		Objects.requireNonNull(arguments, "Arguments cannot be null");

		StringBuilder argumentStringBuilder = new StringBuilder("(");
		arguments.forEach(argument -> argumentStringBuilder.append(argument).append(", "));

		//get rid of last 2 characters - those are ", "
		argumentStringBuilder.setLength(argumentStringBuilder.length() - 2);

		return argumentStringBuilder.append(")").toString();
	}

	public static String buildStringArguments(String... arguments) {
		return buildStringArguments(Stream.of(arguments).map(ParameterString::fromString).collect(Collectors.toSet()));
	}

	public static String buildStringArguments(ParameterString... arguments) {
		return buildStringArguments(Stream.of(arguments).collect(Collectors.toSet()));
	}

	public static <T> ParameterString combineIntoStringArgumentNoBracket(String fieldName, T argument) {
		return ParameterString.fromString(fieldName + ": " + argument.toString());
	}

	public static <T> ParameterString combineIntoStringArgumentNoBracket(CommonFieldParameter fieldName, T argument) {
		return combineIntoStringArgumentNoBracket(fieldName.getFieldName(), argument);
	}

	public static <T> ParameterString combineIntoStringArgumentWithBracket(String fieldName, T argument) {
		return ParameterString.fromString("(" + combineIntoStringArgumentNoBracket(fieldName, argument).getField() + ")");
	}

	public static <T> ParameterString combineIntoStringArgumentWithBracket(CommonFieldParameter fieldName, T argument) {
		return combineIntoStringArgumentWithBracket(fieldName.getFieldName(), argument);
	}
}
