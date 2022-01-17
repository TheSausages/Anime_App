package anime.app.anilist.request.query.parameters.connections.staff;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.common.CommonParameterFieldNames;
import anime.app.anilist.request.query.parameters.connections.characters.CharacterSort;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;

@Getter
public class StaffCharacterMediaArguments {
	private final String characterMediaArgumentsString;

	private StaffCharacterMediaArguments(String characterMediaArgumentsString) {
		this.characterMediaArgumentsString = characterMediaArgumentsString;
	}

	public static StaffCharacterMediaArgumentsBuilder getStaffCharacterMediaArgumentsBuilder() {
		return new StaffCharacterMediaArgumentsBuilder();
	}

	@Override
	public String toString() {
		return characterMediaArgumentsString;
	}

	public static final class StaffCharacterMediaArgumentsBuilder {
		private final Set<ParameterString> staffCharacterMediaArguments = new OverwritingLinkedHashSet<>();

		public StaffCharacterMediaArgumentsBuilder sort(CharacterSort... sorts) {
			staffCharacterMediaArguments.add(QueryParameterUtils.combineIntoArgumentWithoutBracket(CommonParameterFieldNames.SORT, Arrays.toString(sorts)));
			return this;
		}

		public StaffCharacterMediaArgumentsBuilder onList() {
			return onList(true);
		}

		public StaffCharacterMediaArgumentsBuilder onList(boolean onList) {
			staffCharacterMediaArguments.add(QueryParameterUtils.combineIntoArgumentWithoutBracket("onList", onList));
			return this;
		}

		public StaffCharacterMediaArgumentsBuilder page(int page) {
			staffCharacterMediaArguments.add(QueryParameterUtils.combineIntoArgumentWithoutBracket(CommonParameterFieldNames.PAGE, page));
			return this;
		}

		public StaffCharacterMediaArgumentsBuilder perPage(int perPage) {
			staffCharacterMediaArguments.add(QueryParameterUtils.combineIntoArgumentWithoutBracket(CommonParameterFieldNames.PER_PAGE, perPage));
			return this;
		}

		public StaffCharacterMediaArguments build() {
			if (staffCharacterMediaArguments.isEmpty()) {
				throw new IllegalStateException("Staff Media Character Arguments should posses at least 1 parameter!");
			}

			return new StaffCharacterMediaArguments(QueryParameterUtils.buildArguments(
					staffCharacterMediaArguments
			));
		}
	}
}
