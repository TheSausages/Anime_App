package anime.app.anilist.request.utils;

import anime.app.anilist.request.query.common.ParameterString;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class TestUtils {
	public static Set<ParameterString> buildParameterStringWithDivider(String divider, String... elements) {
		return Arrays.stream(elements)
				.flatMap(str -> Arrays.stream(str.split(divider)))
				.map(ParameterString::fromString)
				.collect(Collectors.toSet());
	}

	public static Set<ParameterString> buildFieldParameterStringSet(String... elements) {
		return buildParameterStringWithDivider(QueryTitleWithParametersMatcher.divider, elements);
	}

	public static Set<ParameterString> buildArgumentParameterStringSet(String... elements) {
		return buildParameterStringWithDivider(QueryArgumentMatcher.divider, elements);
	}

	public static boolean containsOnly(Collection<String> shouldContain, Collection<String> actuallyContains) {
		for (String containedString : actuallyContains) {
			if (!shouldContain.contains(containedString)) return false;
		}
		return true;
	}
}