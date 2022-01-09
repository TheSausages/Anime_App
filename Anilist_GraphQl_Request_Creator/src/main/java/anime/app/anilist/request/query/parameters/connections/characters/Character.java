package anime.app.anilist.request.query.parameters.connections.characters;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.connections.media.MediaArguments;
import anime.app.anilist.request.query.parameters.connections.media.MediaConnection;
import anime.app.anilist.request.query.parameters.fuzzyDate.FuzzyDateField;
import anime.app.anilist.request.query.parameters.fuzzyDate.FuzzyDateFieldParameter;
import lombok.Getter;

import java.util.Set;

@Getter
public class Character {
	public final static String characterTitle = "character";

	private final String characterString;

	private Character(String characterString) {
		this.characterString = characterString;
	}

	public String getCharacterStringWithoutFieldName() {
		return this.characterString.substring(characterTitle.length() + 1);
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
			character.add(ParameterString.fromString("name {\nfirst\nmiddle\nlast\nfull\nnative\nalternative\nalternativeSpoiler\n}"));
			return this;
		}

		public CharacterBuilder image() {
			character.add(ParameterString.fromString("image {\nlarge\nmedium\n}"));
			return this;
		}

		public CharacterBuilder description() {
			character.add(ParameterString.fromString("description"));
			return this;
		}

		public CharacterBuilder description(boolean asHtml) {
			character.add(ParameterString.fromString("description(asHtml: " + asHtml + ")"));
			return this;
		}

		public CharacterBuilder gender() {
			character.add(ParameterString.fromString("gender"));
			return this;
		}

		public CharacterBuilder dateOfBirth(FuzzyDateField fuzzyDateField) {
			character.add(ParameterString.fromString(FuzzyDateFieldParameter.dateOfBirth + " " + fuzzyDateField.getFuzzyDateStringWithoutFieldName()));
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
			character.add(ParameterString.fromString("media " + mediaConnection.getMediaConnectionWithoutFieldName()));
			return this;
		}

		public CharacterBuilder media(MediaArguments mediaArguments, MediaConnection mediaConnection) {
			character.add(ParameterString.fromString("media" + mediaArguments.getMediaArgumentsString() + " " + mediaConnection.getMediaConnectionWithoutFieldName()));
			return this;
		}

		public Character build() {
			if (character.isEmpty()) {
				throw new IllegalStateException("Character should posses at least 1 parameter!");
			}

			return new Character(QueryParameterUtils.buildQueryFieldElementString(
					characterTitle,
					character
			));
		}
	}
}
