package anime.app.anilist.request.query.parameters.connections.characters;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.common.CommonParameterFieldNames;
import lombok.Getter;

import java.util.Set;

@Getter
public class CharacterArguments {
	private final String characterArgumentsString;

	private CharacterArguments(String characterArgumentsString) {
		this.characterArgumentsString = characterArgumentsString;
	}

	public static CharacterArgumentsBuilder getCharacterArgumentsBuilder() {
		return new CharacterArgumentsBuilder();
	}

	@Override
	public String toString() {
		return characterArgumentsString;
	}

	public static final class CharacterArgumentsBuilder {
		private final Set<ParameterString> characterMediaArguments = new OverwritingLinkedHashSet<>();

		public CharacterArgumentsBuilder sortBy(CharacterSort... sorts) {
			characterMediaArguments.add(QueryParameterUtils.combineIntoStringArgumentNoBracket(CommonParameterFieldNames.SORT, sorts));
			return this;
		}

		public CharacterArgumentsBuilder role(CharacterRole characterRole) {
			characterMediaArguments.add(QueryParameterUtils.combineIntoStringArgumentNoBracket(CommonParameterFieldNames.ROLE, characterRole.name()));
			return this;
		}

		public CharacterArgumentsBuilder page(int page) {
			characterMediaArguments.add(QueryParameterUtils.combineIntoStringArgumentNoBracket(CommonParameterFieldNames.PAGE, page));
			return this;
		}

		public CharacterArgumentsBuilder perPage(int perPage) {
			characterMediaArguments.add(QueryParameterUtils.combineIntoStringArgumentNoBracket(CommonParameterFieldNames.PER_PAGE, perPage));
			return this;
		}

		public CharacterArguments build() {
			if (characterMediaArguments.isEmpty()) {
				throw new IllegalStateException("Character Arguments should posses at least 1 parameter!");
			}

			return new CharacterArguments(QueryParameterUtils.buildStringArguments(characterMediaArguments));
		}
	}
}
