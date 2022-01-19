package anime.app.anilist.request.query.parameters.connections.media;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.media.Media;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.common.CommonParameterFieldNames;
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
	public final static String MEDIA_EDGE_TITLE = "mediaEdge";

	private final String mediaEdgeString;

	private MediaEdge(String mediaEdgeString) {
		this.mediaEdgeString = mediaEdgeString;
	}

	public String getMediaEdgeWithoutFieldName() {
		return this.mediaEdgeString.substring(MEDIA_EDGE_TITLE.length() + 1);
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
			mediaEdge.add(QueryParameterUtils.combineIntoStringField(CommonParameterFieldNames.NODE, media.getRequestedMediaFields()));
			return this;
		}

		public MediaEdgeBuilder id() {
			mediaEdge.add(ParameterString.fromString("id"));
			return this;
		}

		public MediaEdgeBuilder relationType() {
			return relationType(2);
		}

		public MediaEdgeBuilder relationType(int version) {
			mediaEdge.add(ParameterString.fromString("relationType" + QueryParameterUtils.combineIntoStringArgumentWithBracket("version", version)));
			return this;
		}

		public MediaEdgeBuilder isMainStudio() {
			mediaEdge.add(ParameterString.fromString("isMainStudio"));
			return this;
		}

		public MediaEdgeBuilder characters(Character character) {
			mediaEdge.add(QueryParameterUtils.combineIntoStringField("characters", character.getCharacterStringWithoutFieldName()));
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
			mediaEdge.add(QueryParameterUtils.combineIntoStringField("voiceActors", staff.getStaffWithoutFieldName()));
			return this;
		}

		public MediaEdgeBuilder voiceActors(Staff staff, StaffLanguage language) {
			mediaEdge.add(QueryParameterUtils.combineIntoStringField(
					"voiceActors",
					QueryParameterUtils.combineIntoStringArgumentWithBracket("language", language.name()),
					staff.getStaffWithoutFieldName()
			));
			return this;
		}

		public MediaEdgeBuilder voiceActors(Staff staff, StaffSort... staffSort) {
			mediaEdge.add(QueryParameterUtils.combineIntoStringField(
					"voiceActors",
					QueryParameterUtils.combineIntoStringArgumentWithBracket(CommonParameterFieldNames.SORT, Arrays.toString(staffSort)),
					staff.getStaffWithoutFieldName()
			));
			return this;
		}

		public MediaEdgeBuilder voiceActors(Staff staff, StaffLanguage language, StaffSort... staffSort) {
			mediaEdge.add(QueryParameterUtils.combineIntoStringField(
					"voiceActors",
					QueryParameterUtils.buildStringArguments(
							QueryParameterUtils.combineIntoStringArgumentNoBracket("language", language.name()),
							QueryParameterUtils.combineIntoStringArgumentNoBracket("sort", Arrays.toString(staffSort))
					),
					staff.getStaffWithoutFieldName()
			));
			return this;
		}

		public MediaEdgeBuilder voiceActorsRoles(StaffRoleType roleType) {
			mediaEdge.add(QueryParameterUtils.combineIntoStringField("voiceActorRoles", roleType.getStaffRoleTypeStringWithoutFieldName()));
			return this;
		}

		public MediaEdgeBuilder voiceActorsRoles(StaffRoleType roleType, StaffLanguage language) {
			mediaEdge.add(QueryParameterUtils.combineIntoStringField(
					"voiceActorRoles",
					QueryParameterUtils.combineIntoStringArgumentWithBracket("language", language.name()),
					roleType.getStaffRoleTypeStringWithoutFieldName()
			));
			return this;
		}

		public MediaEdgeBuilder voiceActorsRoles(StaffRoleType roleType, StaffSort... staffSort) {
			mediaEdge.add(QueryParameterUtils.combineIntoStringField(
					"voiceActorRoles",
					QueryParameterUtils.combineIntoStringArgumentWithBracket(CommonParameterFieldNames.SORT, Arrays.toString(staffSort)),
					roleType.getStaffRoleTypeStringWithoutFieldName()
			));
			return this;
		}

		public MediaEdgeBuilder voiceActorsRoles(StaffRoleType roleType, StaffLanguage language, StaffSort... staffSort) {
			mediaEdge.add(QueryParameterUtils.combineIntoStringField(
					"voiceActorRoles",
					QueryParameterUtils.buildStringArguments(
							QueryParameterUtils.combineIntoStringArgumentNoBracket("language", language.name()),
							QueryParameterUtils.combineIntoStringArgumentNoBracket("sort", Arrays.toString(staffSort))
					),
					roleType.getStaffRoleTypeStringWithoutFieldName()
			));
			return this;
		}

		public MediaEdge build() {
			if (mediaEdge.isEmpty()) {
				throw new IllegalStateException("Media Edge should posses at least 1 parameter!");
			}

			return new MediaEdge(QueryParameterUtils.buildStringField(
					MEDIA_EDGE_TITLE,
					mediaEdge
			));
		}
	}
}
