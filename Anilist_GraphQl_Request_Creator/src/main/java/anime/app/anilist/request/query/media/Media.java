package anime.app.anilist.request.query.media;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.common.QueryElement;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.media.MediaFormat;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Getter
public class Media extends QueryElement {
	public final static String MEDIA_TITLE = "media";

	private Media(String elementString, Set<ParameterString> queryParameters, JsonNode variables) {
		super(elementString, queryParameters, variables);
	}

	/**
	 * Get only the requested field of the media. Ex. for
	 * <pre>{@code
	 *      Media(id: $id) {
	 *          format
	 *      }
	 * }</pre>
	 * the function will return
	 * <pre>{@code
	 *      {
	 *          format
	 *      }
	 * }</pre>
	 * @return The media string without the title and arguments
	 */
	public String getRequestedMediaFields() {
		return elementString.substring(elementString.indexOf(")") + 2);
	}

	public static MediaBuilder getMediaBuilder(Field field) {
		Objects.requireNonNull(field, "Field for media cannot be null");

		return new MediaBuilder(field);
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public static class MediaBuilder {
		private final Field field;
		private final Set<ParameterString> mediaParameters = new OverwritingLinkedHashSet<>();
		private final Set<ParameterString> queryParameters = new OverwritingLinkedHashSet<>();
		private final Map<String, Object> parametersValue = new LinkedHashMap<>();

		public MediaBuilder(Field field) {
			this.field = field;
		}

		public MediaBuilder format(MediaFormat format) {
			mediaParameters.add(ParameterString.fromString("format: $format"));
			queryParameters.add(ParameterString.fromString("$format: MediaFormat"));
			parametersValue.put("format", format);
			return this;
		}

		public Media build() {
			return new Media(
					QueryParameterUtils.combineIntoField(
							MEDIA_TITLE,
							QueryParameterUtils.buildArguments(mediaParameters),
							field.getField()
					).getField(),
					queryParameters,
					MAPPER.convertValue(parametersValue, JsonNode.class)
			);
		}
	}
}
