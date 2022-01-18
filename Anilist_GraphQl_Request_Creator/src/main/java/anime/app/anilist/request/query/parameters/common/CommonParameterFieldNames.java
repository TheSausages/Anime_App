package anime.app.anilist.request.query.parameters.common;

import lombok.Getter;

@Getter
public enum CommonParameterFieldNames implements CommonFieldParameter {
	MEDIA("media"),
	SORT("sort"),
	ROLE("role"),
	PAGE("page"),
	PER_PAGE("perPage"),
	EDGE("edge"),
	EDGES("edges"),
	NODE("node"),
	NODES("nodes"),
	PAGE_INFO("pageInfo"),
	USER("user");

	private final String fieldName;

	CommonParameterFieldNames(String fieldName) {
		this.fieldName = fieldName;
	}
}
