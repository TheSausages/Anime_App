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

	public static String buildFieldElement(String name, Set<ParameterString> parameters) {
		Objects.requireNonNull(name, "Name cannot be null");
		Objects.requireNonNull(parameters, "Parameters cannot be null");

		StringBuilder parameterStringBuilder = new StringBuilder(name);

		if (!name.isBlank()) {
			parameterStringBuilder.append(" ");
		}

		parameterStringBuilder.append("{\n");
		parameters.forEach(lan -> parameterStringBuilder.append(lan).append("\n"));
		parameterStringBuilder.append("}");

		return parameterStringBuilder.toString();
	}

	public static String buildFieldElement(String name, String... parameters) {
		return buildFieldElement(name, Stream.of(parameters).map(ParameterString::fromString).collect(Collectors.toSet()));
	}

	public static String buildFieldElement(String name, ParameterString... parameters) {
		return buildFieldElement(name, Stream.of(parameters).collect(Collectors.toSet()));
	}

	public static String buildArguments(Set<ParameterString> arguments) {
		Objects.requireNonNull(arguments, "Arguments cannot be null");

		StringBuilder argumentStringBuilder = new StringBuilder("(");
		arguments.forEach(argument -> argumentStringBuilder.append(argument).append(", "));

		//get rid of last 2 characters - those are ", "
		argumentStringBuilder.setLength(argumentStringBuilder.length() - 2);

		return argumentStringBuilder.append(")").toString();
	}

	public static String buildArguments(String... arguments) {
		return buildArguments(Stream.of(arguments).map(ParameterString::fromString).collect(Collectors.toSet()));
	}

	public static String buildArguments(ParameterString... arguments) {
		return buildArguments(Stream.of(arguments).collect(Collectors.toSet()));
	}

	public static <T> ParameterString combineIntoArgumentWithoutBracket(String fieldName, T argument) {
		return ParameterString.fromString(fieldName + ": " + argument.toString());
	}

	public static <T> ParameterString combineIntoArgumentWithoutBracket(CommonFieldParameter fieldName, T argument) {
		return combineIntoArgumentWithoutBracket(fieldName.getFieldName(), argument);
	}

	public static <T> ParameterString combineIntoArgumentWithBracket(String fieldName, T argument) {
		return ParameterString.fromString("(" + fieldName + ": " + argument.toString() + ")");
	}

	public static <T> ParameterString combineIntoArgumentWithBracket(CommonFieldParameter fieldName, T argument) {
		return combineIntoArgumentWithBracket(fieldName.getFieldName(), argument);
	}

	public static <U> ParameterString combineIntoField(String fieldName, U field) {
		return ParameterString.fromString(fieldName + " " + field.toString());
	}

	public static <U> ParameterString combineIntoField(CommonFieldParameter fieldName, U field) {
		return combineIntoField(fieldName.getFieldName(), field);
	}

	public static <T, U> ParameterString combineIntoField(String fieldName, T arguments, U field) {
		return ParameterString.fromString(fieldName + arguments.toString() + " " + field.toString());
	}

	public static <T, U> ParameterString combineIntoField(CommonParameterFieldNames fieldName, T arguments, U field) {
		return combineIntoField(fieldName.getFieldName(), arguments, field);
	}
}
