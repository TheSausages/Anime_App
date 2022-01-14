package anime.app.anilist.request.query.parameters.connections.staff;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.connections.PageInfo;
import lombok.Getter;

import java.util.Set;

@Getter
public class StaffConnection {
	public static final String staffConnectionTitle = "staffConnection";

	private final String staffConnectionString;

	private StaffConnection(String staffConnectionString) {
		this.staffConnectionString = staffConnectionString;
	}

	public String getStaffConnectionWithoutFieldName() {
		return this.staffConnectionString.substring(staffConnectionTitle.length() + 1);
	}

	public static StaffConnectionBuilder getMediaConnectionBuilder() {
		return new StaffConnectionBuilder();
	}

	public static final class StaffConnectionBuilder {
		private final Set<ParameterString> staffConnection = new OverwritingLinkedHashSet<>();

		public StaffConnectionBuilder edges(StaffEdge edge) {
			staffConnection.add(ParameterString.fromString("edges " + edge.getStaffEdgeWithoutFieldName()));
			return this;
		}

		public StaffConnectionBuilder nodes(Staff staff) {
			staffConnection.add(ParameterString.fromString("nodes " + staff.getStaffWithoutFieldName()));
			return this;
		}

		public StaffConnectionBuilder pageInfo(PageInfo pageInfo) {
			staffConnection.add(ParameterString.fromString(pageInfo.getPageInfoString()));
			return this;
		}

		public StaffConnection build() {
			if (staffConnection.isEmpty()) {
				throw new IllegalStateException("Staff Connection should posses at least 1 parameter!");
			}

			return new StaffConnection(QueryParameterUtils.buildQueryFieldElementString(
					staffConnectionTitle,
					staffConnection
			));
		}
	}
}
