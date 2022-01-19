package anime.app.anilist.request.utils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;

public class QueryTitleWithArgumentsAndParametersMatcher extends TypeSafeMatcher<String> {

	private final List<String> elements;
	private final List<String> arguments;
	private final String title;

	QueryTitleWithArgumentsAndParametersMatcher(String title, List<String> arguments, List<String> elements) {
		this.elements = elements;
		this.arguments = arguments;
		this.title = title;
	}


	@Override
	protected boolean matchesSafely(String item) {
		//Check title
		if (item.startsWith(title)) {
			//remove the title
			item = item.substring(title.length());
		} else {
			return false;
		}

		//Check arguments
		int indexOfClosingArgumentBracket = item.indexOf(")");
		String argumentString = item.substring(1, indexOfClosingArgumentBracket);

		if (!arguments.containsAll(List.of(argumentString.split(QueryArgumentMatcher.divider)))) return false;

		//Check field
		//Get rid of everything except the field
		item = item.substring(indexOfClosingArgumentBracket + 4, item.length() - 2);
		var a = List.of(item.split(QueryTitleWithParametersMatcher.divider));

		return TestUtils.containsOnly(elements, List.of(item.split(QueryTitleWithParametersMatcher.divider)));
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("starts with " + title + ",\n contains " + arguments + " arguments,\n contains " + elements + " elements");
	}

	public static Matcher<String> containsTitleWithArgumentsAndParameters(String title, List<String> arguments, List<String> elements) {
		return new QueryTitleWithArgumentsAndParametersMatcher(
				title,
				arguments,
				elements
		);
	}
}
