package anime.app.anilist.request.query.parameters.connections.media;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.media.Media;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.connections.characters.Character;
import anime.app.anilist.request.query.parameters.connections.staff.Staff;
import anime.app.anilist.request.query.parameters.connections.staff.StaffLanguage;
import anime.app.anilist.request.query.parameters.connections.staff.StaffRoleType;
import anime.app.anilist.request.query.parameters.connections.staff.StaffSort;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;

@Getter
public class MediaEdge {
	public final static String mediaEdgeTitle = "mediaEdge";

	private final String mediaEdgeString;

	private MediaEdge(String mediaEdgeString) {
		this.mediaEdgeString = mediaEdgeString;
	}

	public String getMediaEdgeWithoutFieldName() {
		return this.mediaEdgeString.substring(mediaEdgeTitle.length() + 1);
	}

	public static MediaEdgeBuilder getMediaEdgeBuilder() {
		return new MediaEdgeBuilder();
	}

	@Override
	public String toString() {
		return mediaEdgeString;
	}

	public static final class MediaEdgeBuilder {
		private final Set<ParameterString> mediaEdge = new OverwritingLinkedHashSet<>();

		public MediaEdgeBuilder node(Media media) {
			mediaEdge.add(ParameterString.fromString("node " + media.getRequestedMediaFields()));
			return this;
		}

		public MediaEdgeBuilder id() {
			mediaEdge.add(ParameterString.fromString("id"));
			return this;
		}

		public MediaEdgeBuilder relationType() {
			mediaEdge.add(ParameterString.fromString("relationType(version: 2)"));
			return this;
		}

		public MediaEdgeBuilder relationType(int version) {
			mediaEdge.add(ParameterString.fromString("relationType(version: " + version + ")"));
			return this;
		}

		public MediaEdgeBuilder isMainStudio() {
			mediaEdge.add(ParameterString.fromString("isMainStudio"));
			return this;
		}

		public MediaEdgeBuilder characters(Character character) {
			mediaEdge.add(ParameterString.fromString("characters " + character.getCharacterStringWithoutFieldName()));
			return this;
		}

		public MediaEdgeBuilder characterRole() {
			mediaEdge.add(ParameterString.fromString("characterRole"));
			return this;
		}

		public MediaEdgeBuilder characterName() {
			mediaEdge.add(ParameterString.fromString("characterName"));
			return this;
		}

		public MediaEdgeBuilder roleNotes() {
			mediaEdge.add(ParameterString.fromString("roleNotes"));
			return this;
		}

		public MediaEdgeBuilder dubGroup() {
			mediaEdge.add(ParameterString.fromString("dubGroup"));
			return this;
		}

		public MediaEdgeBuilder staffRole() {
			mediaEdge.add(ParameterString.fromString("staffRole"));
			return this;
		}

		public MediaEdgeBuilder voiceActors(Staff staff) {
			mediaEdge.add(ParameterString.fromString("voiceActors " + staff.getStaffWithoutFieldName()));
			return this;
		}

		public MediaEdgeBuilder voiceActors(Staff staff, StaffLanguage language) {
			mediaEdge.add(ParameterString.fromString("voiceActors(language: " + language.name() + ") " + staff.getStaffWithoutFieldName()));
			return this;
		}

		public MediaEdgeBuilder voiceActors(Staff staff, StaffSort... staffSort) {
			mediaEdge.add(ParameterString.fromString("voiceActors(sort: " + Arrays.toString(staffSort) + ") " + staff.getStaffWithoutFieldName()));
			return this;
		}

		public MediaEdgeBuilder voiceActors(Staff staff, StaffLanguage language, StaffSort... staffSort) {
			mediaEdge.add(ParameterString.fromString("voiceActors(language: " + language.name() + ", sort: " + Arrays.toString(staffSort) + ") " + staff.getStaffWithoutFieldName()));
			return this;
		}

		public MediaEdgeBuilder voiceActorsRoles(StaffRoleType roleType) {
			mediaEdge.add(ParameterString.fromString("voiceActorRoles " + roleType.getStaffRoleTypeStringWithoutFieldName()));
			return this;
		}

		public MediaEdgeBuilder voiceActorsRoles(StaffRoleType roleType, StaffLanguage language) {
			mediaEdge.add(ParameterString.fromString("voiceActorRoles(language: " + language.name() + ") " + roleType.getStaffRoleTypeStringWithoutFieldName()));
			return this;
		}

		public MediaEdgeBuilder voiceActorsRoles(StaffRoleType roleType, StaffSort... staffSort) {
			mediaEdge.add(ParameterString.fromString("voiceActorRoles(sort: " + Arrays.toString(staffSort) + ") " + roleType.getStaffRoleTypeStringWithoutFieldName()));
			return this;
		}

		public MediaEdgeBuilder voiceActorsRoles(StaffRoleType roleType, StaffLanguage language, StaffSort... staffSort) {
			mediaEdge.add(ParameterString.fromString("voiceActorRoles(language: " + language.name() + ", sort: " + Arrays.toString(staffSort) + ") " + roleType.getStaffRoleTypeStringWithoutFieldName()));
			return this;
		}

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
