package anime.app.anilist.request.query.media;

import lombok.Getter;

@Getter
public class Media {
	private final String media;

	public Media(String media) {
		this.media = media;
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
		return media.substring(media.indexOf(")") + 2);
	}
}
