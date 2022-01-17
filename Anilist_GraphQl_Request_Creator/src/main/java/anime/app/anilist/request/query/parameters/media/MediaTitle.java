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
	public static final String titleTitle = "title";
	private static final String stylised = "(stylised: true)";

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

			public String getProperFieldString() {
				return this.name().toLowerCase(Locale.ROOT);
			}
		}

		public MediaTitleBuilder romajiLanguage() {
			languages.add(ParameterString.fromString(TitleLanguages.Romaji.getProperFieldString()));
			return this;
		}

		public MediaTitleBuilder romajiLanguageStylized() {
			languages.add(ParameterString.fromString(TitleLanguages.Romaji.getProperFieldString() + stylised));
			return this;
		}

		public MediaTitleBuilder englishLanguage() {
			languages.add(ParameterString.fromString(TitleLanguages.English.getProperFieldString()));
			return this;
		}

		public MediaTitleBuilder englishLanguageStylized() {
			languages.add(ParameterString.fromString(TitleLanguages.English.getProperFieldString() + stylised));
			return this;
		}

		public MediaTitleBuilder nativeLanguage() {
			languages.add(ParameterString.fromString((TitleLanguages.Native.getProperFieldString())));
			return this;
		}

		public MediaTitleBuilder nativeLanguageStylized() {
			languages.add(ParameterString.fromString(TitleLanguages.Native.getProperFieldString() + stylised));
			return this;
		}

		public MediaTitle build() {
			if (languages.isEmpty()) {
				throw new IllegalStateException("At least 1 language must be selected!");
			}

			return new MediaTitle(QueryParameterUtils.buildFieldElement(
					titleTitle,
					languages
			));
		}
	}
}
