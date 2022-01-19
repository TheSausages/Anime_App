package anime.app.anilist.request.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TestUtils {
	public static List<String> buildParameterStringWithDivider(String divider, String... elements) {
		return Arrays.stream(elements)
				.flatMap(str -> Arrays.stream(str.split(divider)))
				.collect(Collectors.toList());
	}

	public static List<String> buildFieldParameterStringSet(String... elements) {
		return buildParameterStringWithDivider(QueryTitleWithParametersMatcher.divider, elements);
	}

	public static List<String> buildArgumentParameterStringSet(String... elements) {
		return buildParameterStringWithDivider(QueryArgumentMatcher.divider, elements);
	}

	public static boolean containsOnly(Collection<String> shouldContain, Collection<String> actuallyContains) {
		if (shouldContain.size() != actuallyContains.size()) return false;

		for (String containedString : actuallyContains) {
			if (!shouldContain.contains(containedString)) return false;
		}
		return true;
	}
}