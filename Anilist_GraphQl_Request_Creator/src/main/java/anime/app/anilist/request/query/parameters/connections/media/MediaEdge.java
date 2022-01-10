package anime.app.anilist.request.query.parameters.connections.media;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.media.Media;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.connections.characters.Character;
import lombok.Getter;

import java.util.Set;

@Getter
public class MediaEdge {
	public final static String mediaEdgeTitle = "mediaEdge";

	private final String mediaEdgeString;

	private MediaEdge(String mediaEdgeString) {
		this.mediaEdgeString = mediaEdgeString;
	}

	public String getMediaEdgeWithoutFieldName() {
		return this.mediaEdgeString.substring(10);
	}

	public static MediaEdgeBuilder getMediaEdgeBuilder() {
		return new MediaEdgeBuilder();
	}

	public static final class MediaEdgeBuilder {
		private final Set<ParameterString> mediaEdge = new OverwritingLinkedHashSet<>();

		public MediaEdgeBuilder node(Media media) {
			mediaEdge.add(new ParameterString("node " + media.getRequestedMediaFields()));
			return this;
		}

		public MediaEdgeBuilder id() {
			mediaEdge.add(new ParameterString("id"));
			return this;
		}

		public MediaEdgeBuilder relationType() {
			mediaEdge.add(new ParameterString("relationType(version: 2)"));
			return this;
		}

		public MediaEdgeBuilder relationType(int version) {
			mediaEdge.add(new ParameterString("relationType(version: " + version + ")"));
			return this;
		}

		public MediaEdgeBuilder isMainStudio() {
			mediaEdge.add(new ParameterString("isMainStudio"));
			return this;
		}

		public MediaEdgeBuilder characters(Character character) {
			mediaEdge.add(new ParameterString("characters " + character.getCharacterStringWithoutFieldName()));
			return this;
		}

		public MediaEdgeBuilder characterRole() {
			mediaEdge.add(new ParameterString("characterRole"));
			return this;
		}

		public MediaEdgeBuilder characterName() {
			mediaEdge.add(new ParameterString("characterName"));
			return this;
		}

		public MediaEdgeBuilder roleNotes() {
			mediaEdge.add(new ParameterString("roleNotes"));
			return this;
		}

		public MediaEdgeBuilder dubGroup() {
			mediaEdge.add(new ParameterString("dubGroup"));
			return this;
		}

		public MediaEdgeBuilder staffRole() {
			mediaEdge.add(new ParameterString("staffRole"));
			return this;
		}
/*
		public MediaEdgeBuilder voiceActors(Staff staff) {
			mediaEdge.add(new ParameterString("voiceActors " + staff.getStaffWithoutFieldName() + "\n"));
			return this;
		}

		public MediaEdgeBuilder voiceActors(StaffLanguage language, Staff staff) {
			mediaEdge.add(new ParameterString("voiceActors(language: " + language.name() + ") " + staff.getStaffWithoutFieldName() + "\n"));
			return this;
		}

		public MediaEdgeBuilder voiceActors(StaffSort[] staffSort, Staff staff) {
			mediaEdge.add(new ParameterString("voiceActors(sort: " + Arrays.toString(staffSort) + ") " + staff.getStaffWithoutFieldName() + "\n"));
			return this;
		}

		public MediaEdgeBuilder voiceActors(StaffLanguage language, StaffSort[] staffSort, Staff staff) {
			mediaEdge.add(new ParameterString("voiceActors(language: " + language.name() + ", sort: " + Arrays.toString(staffSort) + ") " + staff.getStaffWithoutFieldName() + "\n"));
			return this;
		}

		public MediaEdgeBuilder voiceActorsRoles(StaffRoleType roleType) {
			mediaEdge.add(new ParameterString("voiceActorRoles " + roleType.getStaffRoleTypeStringWithoutFieldName() + "\n"));
			return this;
		}

		public MediaEdgeBuilder voiceActorsRoles(StaffLanguage language, StaffRoleType roleType) {
			mediaEdge.add(new ParameterString("voiceActorRoles(language: " + language.name() + ") " + roleType.getStaffRoleTypeStringWithoutFieldName() + "\n"));
			return this;
		}

		public MediaEdgeBuilder voiceActorsRoles(StaffSort[] staffSort, StaffRoleType roleType) {
			mediaEdge.add(new ParameterString("voiceActorRoles(sort: " + Arrays.toString(staffSort) + ") " + roleType.getStaffRoleTypeStringWithoutFieldName() + "\n"));
			return this;
		}

		public MediaEdgeBuilder voiceActorsRoles(StaffLanguage language, StaffSort[] staffSort, StaffRoleType roleType) {
			mediaEdge.add(new ParameterString("voiceActorRoles(language: " + language.name() + ", sort: " + Arrays.toString(staffSort) + ") " + roleType.getStaffRoleTypeStringWithoutFieldName() + "\n"));
			return this;
		}
*/
		public MediaEdge build() {
			if (mediaEdge.isEmpty()) {
				throw new IllegalStateException("Media Edge should posses at least 1 parameter!");
			}

			return new MediaEdge(QueryParameterUtils.buildQueryFieldElementString(
					mediaEdgeTitle,
					mediaEdge
			));
		}
	}
}
