package anime.app.anilist.request.query.parameters.media;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Locale;
import java.util.Set;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MediaTitle {
	public static final String TITLE_TITLE = "title";

	private final String titleLanguages;

	public static MediaTitleBuilder getMediaTitleBuilder() {
		return new MediaTitleBuilder();
	}

	@Override
	public String toString() {
		return titleLanguages;
	}

	public static final class MediaTitleBuilder {
		private final Set<ParameterString> languages = new OverwritingLinkedHashSet<>();

		private enum TitleLanguages {
			Romaji,
			English,
			Native;

			public ParameterString getProperFieldString() {
				return ParameterString.fromString(this.name().toLowerCase(Locale.ROOT));
			}

			public ParameterString getProperFieldStringStylized() {
				return ParameterString.fromString(this.name().toLowerCase(Locale.ROOT) + "(stylised: true)");
			}
		}

		public MediaTitleBuilder romajiLanguage() {
			languages.add(TitleLanguages.Romaji.getProperFieldString());
			return this;
		}

		public MediaTitleBuilder romajiLanguageStylized() {
			languages.add(TitleLanguages.Romaji.getProperFieldStringStylized());
			return this;
		}

		public MediaTitleBuilder englishLanguage() {
			languages.add(TitleLanguages.English.getProperFieldString());
			return this;
		}

		public MediaTitleBuilder englishLanguageStylized() {
			languages.add(TitleLanguages.English.getProperFieldStringStylized());
			return this;
		}

		public MediaTitleBuilder nativeLanguage() {
			languages.add(TitleLanguages.Native.getProperFieldString());
			return this;
		}

		public MediaTitleBuilder nativeLanguageStylized() {
			languages.add(TitleLanguages.Native.getProperFieldStringStylized());
			return this;
		}

		public MediaTitle build() {
			if (languages.isEmpty()) {
				throw new IllegalStateException("At least 1 language must be selected!");
			}

			return new MediaTitle(QueryParameterUtils.buildFieldElement(
					TITLE_TITLE,
					languages
			));
		}
	}
}
