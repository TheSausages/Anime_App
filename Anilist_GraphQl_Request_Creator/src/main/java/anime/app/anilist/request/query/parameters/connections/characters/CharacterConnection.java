package anime.app.anilist.request.query.parameters.connections.characters;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.connections.PageInfo;
import lombok.Getter;

import java.util.Set;

@Getter
public class CharacterConnection {
	public final static String characterConnectionTitle = "characterConnection";

	private final String characterConnectionString;

	private CharacterConnection(String characterConnectionString) {
		this.characterConnectionString = characterConnectionString;
	}

	public String getCharacterConnectionWithoutFieldName() {
		return this.characterConnectionString.substring(characterConnectionTitle.length() + 1);
	}

	public static CharacterConnectionBuilder getCharacterConnectionBuilder() {
		return new CharacterConnectionBuilder();
	}

	public static final class CharacterConnectionBuilder {
		private final Set<ParameterString> characterConnection = new OverwritingLinkedHashSet<>();

		public CharacterConnectionBuilder edges(CharacterEdge characterEdge) {

			characterConnection.add(ParameterString.fromString("edges " + characterEdge.getCharacterEdgeWithoutFieldName()));
			return this;
		}

		public CharacterConnectionBuilder nodes(Character character) {
			characterConnection.add(ParameterString.fromString("nodes " + character.getCharacterStringWithoutFieldName()));
			return this;
		}

		public CharacterConnectionBuilder pageInfo(PageInfo pageInfo) {
			characterConnection.add(ParameterString.fromString(pageInfo.getPageInfoString()));
			return this;
		}

		public CharacterConnection build() {
			if (characterConnection.isEmpty()) {
				throw new IllegalStateException("Character Connection should posses at least 1 parameter!");
			}

			return new CharacterConnection(QueryParameterUtils.buildQueryFieldElementString(
					characterConnectionTitle,
					characterConnection
			));
		}
	}
}
