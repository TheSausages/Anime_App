package anime.app.anilist.request.utils;

import anime.app.anilist.request.query.common.ParameterString;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class QueryTitleWithParametersMatcher extends TypeSafeMatcher<String> {
	public final static String divider = "\n";

	private final List<String> elements;
	private final String title;

	QueryTitleWithParametersMatcher(String title, List<String> elements) {
		this.title = title;
		this.elements = elements;
	}

	@Override
	protected boolean matchesSafely(String item) {
		if (item.startsWith(title)) {
			//remove the title
			item = item.substring(title.isBlank() ? title.length() : title.length() + 1);
		} else {
			return false;
		}

		//delete first, second and last element (these are newline, '{' and '}')
		item = item.substring(2, item.length() - 1);

		return TestUtils.containsOnly(elements, List.of(item.split(QueryTitleWithParametersMatcher.divider)));
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("starts with " + title + " and contains " + elements);
	}

	public static Matcher<String> containsTitleAndAllSetElements(List<String> elements) {
		return new QueryTitleWithParametersMatcher("", elements);
	}

	public static Matcher<String> containsTitleAndAllSetElements(String title, List<String> elements) {
		return new QueryTitleWithParametersMatcher(title, elements);
	}
	public static Matcher<String> containsTitleAndAllSetElements(String title, Set<ParameterString> elements) {
		return new QueryTitleWithParametersMatcher(title, elements.stream().map(ParameterString::getField).collect(Collectors.toList()));
	}

}
