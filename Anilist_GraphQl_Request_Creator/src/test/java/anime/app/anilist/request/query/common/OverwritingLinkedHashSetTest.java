package anime.app.anilist.request.query.common;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class OverwritingLinkedHashSetTest {

	@Test
	void add_AddTwoWithDifferentBeginning_DontOverwrite() {
		//given
		ParameterString firstStr = ParameterString.fromString("value: 1");
		ParameterString secondStr = ParameterString.fromString("otherValue: 20");
		Set<ParameterString> set = new OverwritingLinkedHashSet<>();

		//when
		set.add(firstStr);
		set.add(secondStr);

		//then
		assertThat(set, allOf(
				notNullValue(),
				instanceOf(Set.class),
				iterableWithSize(2),
				hasItems(firstStr, secondStr)
		));
	}

	@Test
	void add_AddTwoWithSameBeginning_Overwrite() {
		//given
		ParameterString firstStr = ParameterString.fromString("value: 1");
		ParameterString secondStr = ParameterString.fromString("value: 20");
		Set<ParameterString> set = new OverwritingLinkedHashSet<>();

		//when
		set.add(firstStr);
		set.add(secondStr);

		//then
		assertThat(set, allOf(
				notNullValue(),
				instanceOf(Set.class),
				iterableWithSize(1),
				hasItem(firstStr)
		));
	}

	@Test
	void addAll_SetAddTwoWithDifferentBeginning_DontOverwrite() {
		//given
		ParameterString firstStr = ParameterString.fromString("value: 1");
		ParameterString secondStr = ParameterString.fromString("otherValue: 20");
		Set<ParameterString> testerSet = Set.of(firstStr, secondStr);
		Set<ParameterString> testingSet = new OverwritingLinkedHashSet<>();

		//when
		testingSet.addAll(testerSet);

		//then
		assertThat(testingSet, allOf(
				notNullValue(),
				instanceOf(Set.class),
				iterableWithSize(2),
				hasItems(firstStr, secondStr)
		));
	}

	@Test
	void addAll_ListAddTwoWithDifferentBeginning_DontOverwrite() {
		//given
		ParameterString firstStr = ParameterString.fromString("value: 1");
		ParameterString secondStr = ParameterString.fromString("otherValue: 20");
		List<ParameterString> testerList = List.of(firstStr, secondStr);
		Set<ParameterString> testingSet = new OverwritingLinkedHashSet<>();

		//when
		testingSet.addAll(testerList);

		//then
		assertThat(testingSet, allOf(
				notNullValue(),
				instanceOf(Set.class),
				iterableWithSize(2),
				hasItems(firstStr, secondStr)
		));
	}

	@Test
	void addAll_SetAddTwoWithSameBeginning_DontOverwrite() {
		//given
		ParameterString firstStr = ParameterString.fromString("value: 1");
		ParameterString secondStr = ParameterString.fromString("value: 20");
		List<ParameterString> testerList = List.of(firstStr, secondStr);
		Set<ParameterString> testingSet = new OverwritingLinkedHashSet<>();

		//when
		testingSet.addAll(testerList);

		//then
		assertThat(testingSet, allOf(
				notNullValue(),
				instanceOf(Set.class),
				iterableWithSize(1),
				hasItem(firstStr)
		));
	}
}