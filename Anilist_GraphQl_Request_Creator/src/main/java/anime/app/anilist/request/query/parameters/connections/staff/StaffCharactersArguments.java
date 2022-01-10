package anime.app.anilist.request.query.parameters.connections.staff;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.connections.characters.CharacterSort;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;

@Getter
public class StaffCharactersArguments {
	private final String characterArgumentsString;

	private StaffCharactersArguments(String characterArgumentsString) {
		this.characterArgumentsString = characterArgumentsString;
	}

	public static StaffCharactersArgumentsBuilder getStaffCharactersArgumentsBuilder() {
		return new StaffCharactersArgumentsBuilder();
	}

	@Override
	public String toString() {
		return characterArgumentsString;
	}

	public static final class StaffCharactersArgumentsBuilder {
		private final Set<ParameterString> staffCharactersArguments = new OverwritingLinkedHashSet<>();

		public StaffCharactersArgumentsBuilder sort(CharacterSort... sorts) {
			staffCharactersArguments.add(ParameterString.fromString("sort: " + Arrays.toString(sorts)));
			return this;
		}

		public StaffCharactersArgumentsBuilder page(int page) {
			staffCharactersArguments.add(ParameterString.fromString("page: " + page));
			return this;
		}

		public StaffCharactersArgumentsBuilder perPage(int perPage) {
			staffCharactersArguments.add(ParameterString.fromString("perPage: " + perPage));
			return this;
		}

		public StaffCharactersArguments build() {
			if (staffCharactersArguments.isEmpty()) {
				throw new IllegalStateException("Staff Character Arguments should posses at least 1 parameter!");
			}

			return new StaffCharactersArguments(QueryParameterUtils.buildQueryFieldElementArgumentsString(
					staffCharactersArguments
			));
		}
	}
}
