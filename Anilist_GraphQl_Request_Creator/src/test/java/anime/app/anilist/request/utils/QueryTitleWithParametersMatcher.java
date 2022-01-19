package anime.app.anilist.request.utils;

import anime.app.anilist.request.query.common.ParameterString;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;
import java.util.Set;

public class QueryTitleWithParametersMatcher extends TypeSafeMatcher<String> {
	public final static String divider = "\n";

	private final Set<String> elements;
	private final String title;

	QueryTitleWithParametersMatcher(String title, Set<String> elements) {
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

		return elements.containsAll(List.of(item.split(divider)));
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("starts with " + title + " and contains " + elements);
	}

	public static Matcher<String> containsTitleAndAllSetElements(Set<ParameterString> elements) {
		return new QueryTitleWithParametersMatcher("", QueryArgumentMatcher.fromParameterStringSetToStringSet(elements));
	}

	public static Matcher<String> containsTitleAndAllSetElements(String title, Set<ParameterString> elements) {
		return new QueryTitleWithParametersMatcher(title, QueryArgumentMatcher.fromParameterStringSetToStringSet(elements));
	}
}
