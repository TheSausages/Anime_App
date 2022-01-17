package anime.app.anilist.request.query.parameters.connections.staff;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.common.CommonParameterFieldNames;
import anime.app.anilist.request.query.parameters.connections.PageInfo;
import lombok.Getter;

import java.util.Set;

@Getter
public class StaffConnection {
	public static final String STAFF_CONNECTION_TITLE = "staffConnection";

	private final String staffConnectionString;

	private StaffConnection(String staffConnectionString) {
		this.staffConnectionString = staffConnectionString;
	}

	public String getStaffConnectionWithoutFieldName() {
		return this.staffConnectionString.substring(STAFF_CONNECTION_TITLE.length() + 1);
	}

	public static StaffConnectionBuilder getMediaConnectionBuilder() {
		return new StaffConnectionBuilder();
	}

	public static final class StaffConnectionBuilder {
		private final Set<ParameterString> staffConnection = new OverwritingLinkedHashSet<>();

		public StaffConnectionBuilder edges(StaffEdge edge) {
			staffConnection.add(QueryParameterUtils.combineIntoField(CommonParameterFieldNames.EDGES, edge.getStaffEdgeWithoutFieldName()));
			return this;
		}

		public StaffConnectionBuilder nodes(Staff staff) {
			staffConnection.add(QueryParameterUtils.combineIntoField(CommonParameterFieldNames.NODES, staff.getStaffWithoutFieldName()));
			return this;
		}

		public StaffConnectionBuilder pageInfo(PageInfo pageInfo) {
			staffConnection.add(QueryParameterUtils.combineIntoField(CommonParameterFieldNames.PAGE_INFO, pageInfo.getPageInfoStringWithoutFieldName()));
			return this;
		}

		public StaffConnection build() {
			if (staffConnection.isEmpty()) {
				throw new IllegalStateException("Staff Connection should posses at least 1 parameter!");
			}

			return new StaffConnection(QueryParameterUtils.buildFieldElement(
					STAFF_CONNECTION_TITLE,
					staffConnection
			));
		}
	}
}
