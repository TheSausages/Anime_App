package anime.app.anilist.request.utils;

import anime.app.anilist.request.query.common.ParameterString;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QueryArgumentMatcher extends TypeSafeMatcher<String> {
	public final static String divider = ", ";

	private final List<String> elements;

	QueryArgumentMatcher(List<String> elements) {
		this.elements = elements;
	}

	@Override
	protected boolean matchesSafely(String item) {
		//delete first and last element (the brackets)
		item = item.substring(1, item.length() - 1);

		List<String> dividedItem = Stream.of(item.split(divider))
				.map(String::strip)
				.collect(Collectors.toList());

		return TestUtils.containsOnly(elements, dividedItem);
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("contains elements " + elements);
	}

	public static Matcher<String> containsAllSetElements(List<String> elements) {
		return new QueryArgumentMatcher(elements);
	}

	public static Matcher<String> containsAllSetElements(Set<ParameterString> elements) {
		return new QueryArgumentMatcher(elements.stream().map(ParameterString::getField).collect(Collectors.toList()));
	}
}
