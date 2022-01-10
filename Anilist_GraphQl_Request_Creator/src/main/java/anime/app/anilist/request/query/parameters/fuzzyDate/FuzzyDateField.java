package anime.app.anilist.request.query.parameters.fuzzyDate;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import lombok.Getter;

import java.util.Objects;
import java.util.Set;

@Getter
public class FuzzyDateField {
	private final String fuzzyDateString;
	private final FuzzyDateFieldParameter parameter;

	private FuzzyDateField(String fuzzyDateString, FuzzyDateFieldParameter parameter) {
		this.fuzzyDateString = fuzzyDateString;
		this.parameter = parameter;
	}

	public static FuzzyDateFieldBuilder getFuzzyDateFieldBuilder() {
		return new FuzzyDateFieldBuilder(null);
	}

	public static FuzzyDateFieldBuilder getFuzzyDateFieldBuilder(FuzzyDateFieldParameter parameter) {
		Objects.requireNonNull(parameter, "The FieldParameters param cannot be null!");
		return new FuzzyDateFieldBuilder(parameter);
	}

	public String getFuzzyDateStringWithoutFieldName() {
		return this.getFuzzyDateString().substring(
				(Objects.nonNull(parameter) ? parameter.name().length() : 0) + 1
		);
	}

	@Override
	public String toString() {
		return fuzzyDateString;
	}

	public final static class FuzzyDateFieldBuilder {
		private final Set<ParameterString> fuzzyDate = new OverwritingLinkedHashSet<>();
		private final FuzzyDateFieldParameter parameter;

		private FuzzyDateFieldBuilder(FuzzyDateFieldParameter parameter) {
			this.parameter = parameter;
		}

		public FuzzyDateFieldBuilder year() {
			fuzzyDate.add(ParameterString.fromString("year"));
			return this;
		}

		public FuzzyDateFieldBuilder month() {
			fuzzyDate.add(ParameterString.fromString("month"));
			return this;
		}

		public FuzzyDateFieldBuilder day() {
			fuzzyDate.add(ParameterString.fromString("day"));
			return this;
		}

		public FuzzyDateField allAndBuild() {
			fuzzyDate.add(ParameterString.fromString("year\nmonth\nday"));
			return build();
		}

		public FuzzyDateField build() {
			if (fuzzyDate.isEmpty()) {
				throw new IllegalStateException("Fuzzy Date should posses at least 1 parameter!");
			}

			return new FuzzyDateField(QueryParameterUtils.buildQueryFieldElementString(
					Objects.nonNull(parameter) ? parameter.name() : "",
					fuzzyDate
			), parameter);
		}
	}
}
