package anime.app.anilist.request.query.parameters.connections.staff;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.common.CommonParameterFieldNames;
import lombok.Getter;

import java.util.Set;

@Getter
public class StaffEdge {
	public static final String STAFF_EDGE_TITLE = "staffEdge";

	private final String staffEdgeString;

	private StaffEdge(String staffEdgeString) {
		this.staffEdgeString = staffEdgeString;
	}

	public static StaffEdgeBuilder getStaffEdgeBuilder() {
		return new StaffEdgeBuilder();
	}

	public String getStaffEdgeWithoutFieldName() {
		return this.staffEdgeString.substring(STAFF_EDGE_TITLE.length() + 1);
	}

	@Override
	public String toString() {
		return staffEdgeString;
	}

	public static final class StaffEdgeBuilder {
		private final Set<ParameterString> staffEdge = new OverwritingLinkedHashSet<>();

		public StaffEdgeBuilder node(Staff staff) {
			staffEdge.add(QueryParameterUtils.combineIntoField(CommonParameterFieldNames.NODE, staff.getStaffWithoutFieldName()));
			return this;
		}

		public StaffEdgeBuilder id() {
			staffEdge.add(ParameterString.fromString("id"));
			return this;
		}

		public StaffEdgeBuilder role() {
			staffEdge.add(ParameterString.fromString("role"));
			return this;
		}

		public StaffEdgeBuilder favouriteOrder() {
			staffEdge.add(ParameterString.fromString("favouriteOrder"));
			return this;
		}

		public StaffEdge build() {
			if (staffEdge.isEmpty()) {
				throw new IllegalStateException("Staff Edge should posses at least 1 parameter!");
			}

			return new StaffEdge(QueryParameterUtils.buildFieldElement(
					STAFF_EDGE_TITLE,
					staffEdge
			));
		}
	}
}
