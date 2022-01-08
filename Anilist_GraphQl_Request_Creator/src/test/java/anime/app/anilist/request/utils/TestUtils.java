package anime.app.anilist.request.utils;

import anime.app.anilist.request.query.common.ParameterString;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class TestUtils {
	public static Set<ParameterString> getParameterStringSetWithDivide(String divider, String... elements) {
		return Arrays.stream(elements)
				.flatMap(str -> Arrays.stream(str.split(divider)))
				.map(ParameterString::fromString)
				.collect(Collectors.toSet());
	}

	public static Set<ParameterString> getParameterStringSet(String... elements) {
		return Arrays.stream(elements)
				.map(ParameterString::fromString)
				.collect(Collectors.toSet());
	}
}