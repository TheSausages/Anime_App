package anime.app.anilist.request.query.common;

import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Getter
public class ParameterString {
	private final String field;

	public ParameterString(String field) {
		this.field = field;
	}

	public static ParameterString fromString(String parameter) { return new ParameterString(parameter); }

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(field.split("[ (\n]")[0]).toHashCode();
	}

	@Override
	public String toString() {
		return field;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		ParameterString that = (ParameterString) o;

		return new EqualsBuilder().append(field.split("[ (\n]")[0], that.getField().split("[ (\n]")[0]).isEquals();
	}
}
