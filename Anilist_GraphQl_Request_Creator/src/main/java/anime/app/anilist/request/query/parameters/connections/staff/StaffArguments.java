package anime.app.anilist.request.query.parameters.connections.staff;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;

@Getter
public class StaffArguments {
	private final String staffArgumentsString;

	private StaffArguments(String staffArgumentsString) {
		this.staffArgumentsString = staffArgumentsString;
	}

	public static StaffArgumentsBuilder getStaffArgumentsBuilder() {
		return new StaffArgumentsBuilder();
	}

	@Override
	public String toString() {
		return staffArgumentsString;
	}

	public static final class StaffArgumentsBuilder {
		private final Set<ParameterString> staffMediaArguments = new OverwritingLinkedHashSet<>();

		public StaffArgumentsBuilder sort(StaffSort[] sorts) {
			staffMediaArguments.add(ParameterString.fromString("sort: " + Arrays.toString(sorts)));
			return this;
		}

		public StaffArgumentsBuilder page(int page) {
			staffMediaArguments.add(ParameterString.fromString("page: " + page));
			return this;
		}

		public StaffArgumentsBuilder perPage(int perPage) {
			staffMediaArguments.add(ParameterString.fromString("perPage: " + perPage));
			return this;
		}

		public StaffArguments build() {
			if (staffMediaArguments.isEmpty()) {
				throw new IllegalStateException("Staff Arguments should posses at least 1 parameter!");
			}

			return new StaffArguments(QueryParameterUtils.buildQueryFieldElementArgumentsString(staffMediaArguments));
		}
	}
}
