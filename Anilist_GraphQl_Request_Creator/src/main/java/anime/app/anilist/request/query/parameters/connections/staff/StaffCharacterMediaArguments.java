package anime.app.anilist.request.query.parameters.connections.staff;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
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
			staffCharacterMediaArguments.add(ParameterString.fromString("sort: " + Arrays.toString(sorts)));
			return this;
		}

		public StaffCharacterMediaArgumentsBuilder onList() {
			staffCharacterMediaArguments.add(ParameterString.fromString("onList: true"));
			return this;
		}

		public StaffCharacterMediaArgumentsBuilder onList(boolean onList) {
			staffCharacterMediaArguments.add(ParameterString.fromString("onList: " + onList));
			return this;
		}

		public StaffCharacterMediaArgumentsBuilder page(int page) {
			staffCharacterMediaArguments.add(ParameterString.fromString("page: " + page));
			return this;
		}

		public StaffCharacterMediaArgumentsBuilder perPage(int perPage) {
			staffCharacterMediaArguments.add(ParameterString.fromString("perPage: " + perPage));
			return this;
		}

		public StaffCharacterMediaArguments build() {
			if (staffCharacterMediaArguments.isEmpty()) {
				throw new IllegalStateException("Staff Media Character Arguments should posses at least 1 parameter!");
			}

			return new StaffCharacterMediaArguments(QueryParameterUtils.buildQueryFieldElementArgumentsString(
					staffCharacterMediaArguments
			));
		}
	}
}
