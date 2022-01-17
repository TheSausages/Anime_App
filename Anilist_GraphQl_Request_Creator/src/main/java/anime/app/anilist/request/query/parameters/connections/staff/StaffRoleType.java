package anime.app.anilist.request.query.parameters.connections.staff;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import lombok.Getter;

import java.util.Set;

@Getter
public class StaffRoleType {
	public final static String staffRoleTypeTitle = "staffRoleType";

	private final String staffRoleTypeString;

	private StaffRoleType(String staffRoleTypeString) {
		this.staffRoleTypeString = staffRoleTypeString;
	}

	public String getStaffRoleTypeStringWithoutFieldName() {
		return this.staffRoleTypeString.substring(staffRoleTypeTitle.length() + 1);
	}

	@Override
	public String toString() {
		return staffRoleTypeString;
	}

	public static StaffRoleTypeBuilder getStaffRoleTypeBuilder() {
		return new StaffRoleTypeBuilder();
	}

	public static final class StaffRoleTypeBuilder {
		private final Set<ParameterString> staffRoleType = new OverwritingLinkedHashSet<>();

		public StaffRoleTypeBuilder voiceActor(Staff staff) {
			staffRoleType.add(QueryParameterUtils.combineIntoField("voiceActor", staff.getStaffWithoutFieldName()));
			return this;
		}

		public StaffRoleTypeBuilder roleNotes() {
			staffRoleType.add(ParameterString.fromString("roleNotes"));
			return this;
		}

		public StaffRoleTypeBuilder dubGroup() {
			staffRoleType.add(ParameterString.fromString("dubGroup"));
			return this;
		}

		public StaffRoleType build() {
			if (staffRoleType.isEmpty()) {
				throw new IllegalStateException("Staff Role Type should posses at least 1 parameter!");
			}

			return new StaffRoleType(QueryParameterUtils.buildFieldElement(
					staffRoleTypeTitle,
					staffRoleType
			));
		}
	}
}
