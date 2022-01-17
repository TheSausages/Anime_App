package anime.app.anilist.request.query.parameters.connections;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import lombok.Getter;

import java.util.Set;

@Getter
public class PageInfo {
	public final static String PAGE_INFO_TITLE = "pageInfo";

	private final String pageInfoString;

	private PageInfo(String pageInfoString) {
		this.pageInfoString = pageInfoString;
	}

	public static PageInfoBuilder getPageInfoBuilder() {
		return new PageInfoBuilder();
	}

	public String getPageInfoStringWithoutFieldName() {
		return this.pageInfoString.substring(PAGE_INFO_TITLE.length() + 1);
	}

	@Override
	public String toString() {
		return pageInfoString;
	}

	public static final class PageInfoBuilder {
		private final Set<ParameterString> info = new OverwritingLinkedHashSet<>();

		public PageInfoBuilder total() {
			info.add(ParameterString.fromString("total"));
			return this;
		}

		public PageInfoBuilder perPage() {
			info.add(ParameterString.fromString("perPage"));
			return this;
		}

		public PageInfoBuilder currentPage() {
			info.add(ParameterString.fromString("currentPage"));
			return this;
		}

		public PageInfoBuilder lastPage() {
			info.add(ParameterString.fromString("lastPage"));
			return this;
		}

		public PageInfoBuilder hasNextPage() {
			info.add(ParameterString.fromString("hasNextPage"));
			return this;
		}

		public PageInfo build() {
			if (info.isEmpty()) {
				throw new IllegalStateException("Page Info should posses at least 1 parameter!");
			}

			return new PageInfo(QueryParameterUtils.buildFieldElement(
					PAGE_INFO_TITLE,
					info
			));
		}
	}
}
