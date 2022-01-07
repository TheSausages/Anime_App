package anime.app.anilist.request.query.parameters.media;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import lombok.Getter;

import java.util.Set;

@Getter
public class MediaExternalLinks {
	private final static String externalLinks = "externalLinks";

	private final String externalLink;

	private MediaExternalLinks(String ExternalLink) {
		this.externalLink = ExternalLink;
	}

	public static MediaExternalLinkBuilder getMediaExternalLinkBuilder() {
		return new MediaExternalLinkBuilder();
	}

	@Override
	public String toString() {
		return externalLink;
	}

	public static final class MediaExternalLinkBuilder {
		private final Set<ParameterString> mediaExternalLink = new OverwritingLinkedHashSet<>();

		public MediaExternalLinkBuilder id() {
			mediaExternalLink.add(ParameterString.fromString("id"));
			return this;
		}

		public MediaExternalLinkBuilder url() {
			mediaExternalLink.add(ParameterString.fromString("url"));
			return this;
		}

		public MediaExternalLinkBuilder site() {
			mediaExternalLink.add(ParameterString.fromString("site"));
			return this;
		}

		public MediaExternalLinks build() {
			if (mediaExternalLink.isEmpty()) {
				throw new IllegalStateException("External Links should posses at least 1 parameter!");
			}

			return new MediaExternalLinks(QueryParameterUtils.buildString(
					externalLinks,
					mediaExternalLink
			));
		}
	}
}
