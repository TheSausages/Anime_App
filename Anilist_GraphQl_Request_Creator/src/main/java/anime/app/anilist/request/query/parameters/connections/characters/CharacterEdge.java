package anime.app.anilist.request.query.parameters.connections.characters;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.media.Media;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.connections.staff.Staff;
import anime.app.anilist.request.query.parameters.connections.staff.StaffLanguage;
import anime.app.anilist.request.query.parameters.connections.staff.StaffRoleType;
import anime.app.anilist.request.query.parameters.connections.staff.StaffSort;
import lombok.Getter;

import java.util.Arrays;
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

		public CharacterEdgeBuilder voiceActors(Staff staff) {
			characterEdge.add(ParameterString.fromString("voiceActors " + staff.getStaffWithoutFieldName()));
			return this;
		}

		public CharacterEdgeBuilder voiceActors(Staff staff, StaffLanguage language) {
			characterEdge.add(ParameterString.fromString("voiceActors(language: " + language.name() + ") " + staff.getStaffWithoutFieldName()));
			return this;
		}

		public CharacterEdgeBuilder voiceActors(Staff staff, StaffSort... staffSort) {
			characterEdge.add(ParameterString.fromString("voiceActors(sort: " + Arrays.toString(staffSort) + ") " + staff.getStaffWithoutFieldName()));
			return this;
		}

		public CharacterEdgeBuilder voiceActors(Staff staff, StaffLanguage language, StaffSort... staffSort) {
			characterEdge.add(ParameterString.fromString("voiceActors(language: " + language.name() + ", sort: " + Arrays.toString(staffSort) + ") " + staff.getStaffWithoutFieldName()));
			return this;
		}

		public CharacterEdgeBuilder voiceActorsRoles(StaffRoleType roleType) {
			characterEdge.add(ParameterString.fromString("voiceActorsRoles " + roleType.getStaffRoleTypeStringWithoutFieldName()));
			return this;
		}

		public CharacterEdgeBuilder voiceActorsRoles(StaffRoleType roleType, StaffLanguage language) {
			characterEdge.add(ParameterString.fromString("voiceActorsRoles(language: " + language.name() + ") " + roleType.getStaffRoleTypeStringWithoutFieldName()));
			return this;
		}

		public CharacterEdgeBuilder voiceActorsRoles(StaffRoleType roleType, StaffSort... staffSort) {
			characterEdge.add(ParameterString.fromString("voiceActorsRoles(sort: " + Arrays.toString(staffSort) + ") " + roleType.getStaffRoleTypeStringWithoutFieldName()));
			return this;
		}

		public CharacterEdgeBuilder voiceActorsRoles(StaffRoleType roleType, StaffLanguage language, StaffSort... staffSort) {
			characterEdge.add(ParameterString.fromString("voiceActorsRoles(language: " + language.name() + ", sort: " + Arrays.toString(staffSort) + ") " + roleType.getStaffRoleTypeStringWithoutFieldName()));
			return this;
		}

		public CharacterEdgeBuilder media(Media media) {
			characterEdge.add(ParameterString.fromString("media " + media.getRequestedMediaFields()));
			return this;
		}

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
