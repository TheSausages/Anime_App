package anime.app.anilist.request.query.parameters.fuzzyDate;

import anime.app.anilist.request.query.parameters.common.CommonFieldParameter;
import lombok.Getter;

@Getter
public enum FuzzyDateFieldParameter implements CommonFieldParameter {
	START_DATE("startDate"),
	DATE_OF_BIRTH("dateOfBirth"),
	DATE_OF_DEATH("dateOfDeath"),
	END_DATE("endDate");

	private final String fieldName;

	FuzzyDateFieldParameter(String fieldName) {
		this.fieldName = fieldName;
	}
}
