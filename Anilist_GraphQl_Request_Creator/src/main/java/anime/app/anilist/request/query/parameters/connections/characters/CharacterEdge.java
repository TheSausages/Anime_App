package anime.app.anilist.request.query.parameters.connections.characters;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import lombok.Getter;

import java.util.Set;

@Getter
public class CharacterEdge {
	public final static String characterEdgeTitle = "characterEdge";

	private final String characterEdgeString;

	private CharacterEdge(String characterEdgeString) {
		this.characterEdgeString = characterEdgeString;
	}

	public static CharacterEdgeBuilder getCharacterEdgeBuilder() {
		return new CharacterEdgeBuilder();
	}

	public String getCharacterEdgeWithoutFieldName() {
		return this.characterEdgeString.substring(characterEdgeTitle.length() + 1);
	}

	@Override
	public String toString() {
		return characterEdgeString;
	}

	public static final class CharacterEdgeBuilder {
		private final Set<ParameterString> characterEdge = new OverwritingLinkedHashSet<>();

		public CharacterEdgeBuilder node(Character character) {
			characterEdge.add(ParameterString.fromString("node " + character.getCharacterStringWithoutFieldName()));
			return this;
		}

		public CharacterEdgeBuilder id() {
			characterEdge.add(ParameterString.fromString("id"));
			return this;
		}

		public CharacterEdgeBuilder role() {
			characterEdge.add(ParameterString.fromString("role"));
			return this;
		}

		public CharacterEdgeBuilder name() {
			characterEdge.add(ParameterString.fromString("name"));
			return this;
		}

		/*public CharacterEdgeBuilder voiceActors(Staff staff) {
			characterEdge.add(new ParameterString("voiceActors " + staff.getStaffWithoutFieldName() + "\n"));
			return this;
		}

		public CharacterEdgeBuilder voiceActors(StaffLanguage language, Staff staff) {
			characterEdge.add(new ParameterString("voiceActors(language: " + language.name() + ") " + staff.getStaffWithoutFieldName() + "\n"));
			return this;
		}

		public CharacterEdgeBuilder voiceActors(StaffSort[] staffSort, Staff staff) {
			characterEdge.add(new ParameterString("voiceActors(sort: " + Arrays.toString(staffSort) + ") " + staff.getStaffWithoutFieldName() + "\n"));
			return this;
		}

		public CharacterEdgeBuilder voiceActors(StaffLanguage language, StaffSort[] staffSort, Staff staff) {
			characterEdge.add(new ParameterString("voiceActors(language: " + language.name() + ", sort: " + Arrays.toString(staffSort) + ") " + staff.getStaffWithoutFieldName() + "\n"));
			return this;
		}

		public CharacterEdgeBuilder voiceActorsRoles(StaffRoleType roleType) {
			characterEdge.add(new ParameterString("voiceActorsRoles " + roleType.getStaffRoleTypeStringWithoutFieldName() + "\n"));
			return this;
		}

		public CharacterEdgeBuilder voiceActorsRoles(StaffLanguage language, StaffRoleType roleType) {
			characterEdge.add(new ParameterString("voiceActorsRoles(language: " + language.name() + ") " + roleType.getStaffRoleTypeStringWithoutFieldName() + "\n"));
			return this;
		}

		public CharacterEdgeBuilder voiceActorsRoles(StaffSort[] staffSort, StaffRoleType roleType) {
			characterEdge.add(new ParameterString("voiceActorsRoles(sort: " + Arrays.toString(staffSort) + ") " + roleType.getStaffRoleTypeStringWithoutFieldName() + "\n"));
			return this;
		}

		public CharacterEdgeBuilder voiceActorsRoles(StaffLanguage language, StaffSort[] staffSort, StaffRoleType roleType) {
			characterEdge.add(new ParameterString("voiceActorsRoles(language: " + language.name() + ", sort: " + Arrays.toString(staffSort) + ") " + roleType.getStaffRoleTypeStringWithoutFieldName() + "\n"));
			return this;
		}

		public CharacterEdgeBuilder media(Media media) {
			characterEdge.add(new ParameterString("media " + media + "\n"));
			return this;
		}*/

		public CharacterEdgeBuilder favouriteOrder() {
			characterEdge.add(ParameterString.fromString("favouriteOrder"));
			return this;
		}

		public CharacterEdge build() {
			if (characterEdge.isEmpty()) {
				throw new IllegalStateException("Character Edge should posses at least 1 parameter!");
			}

			return new CharacterEdge(QueryParameterUtils.buildQueryFieldElementString(
					characterEdgeTitle,
					characterEdge
			));
		}
	}
}
