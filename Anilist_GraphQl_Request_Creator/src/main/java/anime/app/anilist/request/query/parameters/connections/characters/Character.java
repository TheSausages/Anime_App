package anime.app.anilist.request.query.parameters.connections.characters;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.common.CommonParameterFieldNames;
import anime.app.anilist.request.query.parameters.connections.media.MediaArguments;
import anime.app.anilist.request.query.parameters.connections.media.MediaConnection;
import anime.app.anilist.request.query.parameters.fuzzyDate.FuzzyDateField;
import anime.app.anilist.request.query.parameters.fuzzyDate.FuzzyDateFieldParameter;
import lombok.Getter;

import java.util.Set;

@Getter
public class Character {
	public final static String CHARACTER_TITLE = "character";

	private final String characterString;

	private Character(String characterString) {
		this.characterString = characterString;
	}

	public String getCharacterStringWithoutFieldName() {
		return this.characterString.substring(CHARACTER_TITLE.length() + 1);
	}

	public static CharacterBuilder getCharacterBuilder() {
		return new CharacterBuilder();
	}

	@Override
	public String toString() {
		return characterString;
	}

	public static final class CharacterBuilder {
		private final Set<ParameterString> character = new OverwritingLinkedHashSet<>();

		public CharacterBuilder id() {
			character.add(ParameterString.fromString("id"));
			return this;
		}

		public CharacterBuilder name() {
			character.add(ParameterString.fromString(QueryParameterUtils.buildFieldElement(
					"name",
					"first",
					"middle",
					"last",
					"full",
					"native",
					"alternative",
					"alternativeSpoiler"
			)));
			return this;
		}

		public CharacterBuilder image() {
			character.add(ParameterString.fromString(QueryParameterUtils.buildFieldElement(
					"image",
					"large",
					"medium"
			)));
			return this;
		}

		public CharacterBuilder description() {
			return description(false);
		}

		public CharacterBuilder description(boolean asHtml) {
			character.add(ParameterString.fromString("description" + QueryParameterUtils.combineIntoArgumentWithBracket("asHtml", asHtml)));
			return this;
		}

		public CharacterBuilder gender() {
			character.add(ParameterString.fromString("gender"));
			return this;
		}

		public CharacterBuilder dateOfBirth(FuzzyDateField fuzzyDateField) {
			character.add(QueryParameterUtils.combineIntoField(FuzzyDateFieldParameter.DATE_OF_BIRTH, fuzzyDateField.getFuzzyDateStringWithoutFieldName()));
			return this;
		}

		public CharacterBuilder age() {
			character.add(ParameterString.fromString("age"));
			return this;
		}

		public CharacterBuilder aniListSiteUrl() {
			character.add(ParameterString.fromString("siteUrl"));
			return this;
		}

		public CharacterBuilder favourites() {
			character.add(ParameterString.fromString("favourites"));
			return this;
		}

		public CharacterBuilder modNotes() {
			character.add(ParameterString.fromString("modNotes"));
			return this;
		}

		public CharacterBuilder media(MediaConnection mediaConnection) {
			character.add(QueryParameterUtils.combineIntoField(CommonParameterFieldNames.MEDIA, mediaConnection.getMediaConnectionWithoutFieldName()));
			return this;
		}

		public CharacterBuilder media(MediaArguments mediaArguments, MediaConnection mediaConnection) {
			character.add(QueryParameterUtils.combineIntoField(
					CommonParameterFieldNames.MEDIA,
					mediaArguments.getMediaArgumentsString(),
					mediaConnection.getMediaConnectionWithoutFieldName()
			));
			return this;
		}

		public Character build() {
			if (character.isEmpty()) {
				throw new IllegalStateException("Character should posses at least 1 parameter!");
			}

			return new Character(QueryParameterUtils.buildFieldElement(
					CHARACTER_TITLE,
					character
			));
		}
	}
}
