package anime.app.anilist.request.query.parameters.connections.staff;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.connections.characters.CharacterConnection;
import anime.app.anilist.request.query.parameters.connections.media.MediaArguments;
import anime.app.anilist.request.query.parameters.connections.media.MediaConnection;
import anime.app.anilist.request.query.parameters.fuzzyDate.FuzzyDateField;
import anime.app.anilist.request.query.parameters.fuzzyDate.FuzzyDateFieldParameter;
import lombok.Getter;

import java.util.Set;

@Getter
public class Staff {
	public static final String STAFF_TITLE = "staff";

	private final String staffString;

	private Staff(String staffString) {
		this.staffString = staffString;
	}

	public String getStaffWithoutFieldName() {
		return this.staffString.substring(STAFF_TITLE.length() + 1);
	}

	public static StaffBuilder getStaffBuilder() {
		return new StaffBuilder();
	}

	@Override
	public String toString() {
		return staffString;
	}

	public static final class StaffBuilder {
		private final Set<ParameterString> staff = new OverwritingLinkedHashSet<>();

		public StaffBuilder id() {
			staff.add(ParameterString.fromString("id"));
			return this;
		}

		public StaffBuilder name() {
			staff.add(ParameterString.fromString(QueryParameterUtils.buildStringField(
					"name",
					"first",
					"middle",
					"last",
					"full",
					"native"
			)));
			return this;
		}

		public StaffBuilder languageV2() {
			staff.add(ParameterString.fromString("languageV2"));
			return this;
		}

		public StaffBuilder image() {
			staff.add(ParameterString.fromString(QueryParameterUtils.buildStringField("image", "large", "medium")));
			return this;
		}

		public StaffBuilder description() {
			staff.add(ParameterString.fromString("description"));
			return this;
		}

		public StaffBuilder description(boolean asHtml) {
			staff.add(ParameterString.fromString("description" + QueryParameterUtils.combineIntoStringArgumentWithBracket("asHtml", asHtml)));
			return this;
		}

		public StaffBuilder primaryOccupations() {
			staff.add(ParameterString.fromString("primaryOccupations"));
			return this;
		}

		public StaffBuilder gender() {
			staff.add(ParameterString.fromString("gender"));
			return this;
		}

		public StaffBuilder dateOfBirth(FuzzyDateField fuzzyDateField) {
			staff.add(QueryParameterUtils.combineIntoStringField(FuzzyDateFieldParameter.DATE_OF_BIRTH, fuzzyDateField.getFuzzyDateStringWithoutFieldName()));
			return this;
		}

		public StaffBuilder dateOfDeath(FuzzyDateField fuzzyDateField) {
			staff.add(QueryParameterUtils.combineIntoStringField(FuzzyDateFieldParameter.DATE_OF_DEATH, fuzzyDateField.getFuzzyDateStringWithoutFieldName()));
			return this;
		}

		public StaffBuilder age() {
			staff.add(ParameterString.fromString("age"));
			return this;
		}

		public StaffBuilder yearsActive() {
			staff.add(ParameterString.fromString("yearsActive"));
			return this;
		}

		public StaffBuilder homeTown() {
			staff.add(ParameterString.fromString("homeTown"));
			return this;
		}

		public StaffBuilder siteUrl() {
			staff.add(ParameterString.fromString("siteUrl"));
			return this;
		}

		public StaffBuilder staffMedia(MediaConnection mediaConnection) {
			staff.add(QueryParameterUtils.combineIntoStringField("staffMedia", mediaConnection.getMediaConnectionWithoutFieldName()));
			return this;
		}

		public StaffBuilder staffMedia(MediaArguments mediaArguments, MediaConnection mediaConnection) {
			staff.add(QueryParameterUtils.combineIntoStringField(
					"staffMedia",
					mediaArguments.getMediaArgumentsString(),
					mediaConnection.getMediaConnectionWithoutFieldName()
			));
			return this;
		}

		public StaffBuilder characters(CharacterConnection characterConnection) {
			staff.add(QueryParameterUtils.combineIntoStringField("characters", characterConnection.getCharacterConnectionWithoutFieldName()));
			return this;
		}

		public StaffBuilder characters(StaffCharactersArguments staffCharactersArguments, CharacterConnection characterConnection) {
			staff.add(QueryParameterUtils.combineIntoStringField(
					"characters",
					staffCharactersArguments.getCharacterArgumentsString(),
					characterConnection.getCharacterConnectionWithoutFieldName()
			));
			return this;
		}

		public StaffBuilder characterMedia(MediaConnection connection) {
			staff.add(QueryParameterUtils.combineIntoStringField("characterMedia", connection.getMediaConnectionWithoutFieldName()));
			return this;
		}

		public StaffBuilder characterMedia(StaffCharacterMediaArguments arguments, MediaConnection connection) {
			staff.add(QueryParameterUtils.combineIntoStringField(
					"characterMedia",
					arguments.getCharacterMediaArgumentsString(),
					connection.getMediaConnectionWithoutFieldName()
			));
			return this;
		}

		public StaffBuilder favourites() {
			staff.add(ParameterString.fromString("favourites"));
			return this;
		}

		public Staff build() {
			if (staff.isEmpty()) {
				throw new IllegalStateException("Staff should posses at least 1 parameter!");
			}

			return new Staff(QueryParameterUtils.buildStringField(
					STAFF_TITLE,
					staff
			));
		}
	}
}