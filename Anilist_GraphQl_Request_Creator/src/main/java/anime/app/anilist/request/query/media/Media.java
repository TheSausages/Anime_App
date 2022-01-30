package anime.app.anilist.request.query.media;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.common.QueryElement;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.fuzzyDate.FuzzyDateValue;
import anime.app.anilist.request.query.parameters.media.*;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class Media extends QueryElement {
	public final static String MEDIA_TITLE = "Media";

	protected Media(String elementString, Set<String> queryParameters, Map<String, Object> variables) {
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

	public String getRequestedMediaFieldsWithArguments() {
		return elementString.substring(elementString.indexOf("("));
	}

	public static MediaArgumentBuilder getMediaArgumentBuilder(Field field) {
		Objects.requireNonNull(field, "Field for media cannot be null");

		return new MediaArgumentBuilder(field);
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public static class MediaArgumentBuilder {
		private final Field field;
		private final Set<ParameterString> mediaParameters = new OverwritingLinkedHashSet<>();
		private final Set<ParameterString> queryParameters = new OverwritingLinkedHashSet<>();
		private final Map<String, Object> parametersValue = new LinkedHashMap<>();

		public MediaArgumentBuilder(Field field) {
			this.field = field;
		}

		public Media.MediaArgumentBuilder id(int id) {
			mediaParameters.add(ParameterString.fromString("id: $id"));
			queryParameters.add(ParameterString.fromString("$id: Int"));
			parametersValue.putIfAbsent("id", id);
			return this;
		}

		public Media.MediaArgumentBuilder idMal(int idMal) {
			mediaParameters.add(ParameterString.fromString("idMal: $idMal"));
			queryParameters.add(ParameterString.fromString("$idMal: Int"));
			parametersValue.putIfAbsent("idMal", idMal);
			return this;
		}

		public Media.MediaArgumentBuilder startDate(FuzzyDateValue fuzzyDateValue) {
			mediaParameters.add(ParameterString.fromString("startDate: $startDate"));
			queryParameters.add(ParameterString.fromString("$startDate: FuzzyDateInt"));
			parametersValue.putIfAbsent("startDate", fuzzyDateValue.getFuzzyDateNumber());
			return this;
		}

		public Media.MediaArgumentBuilder endDate(FuzzyDateValue fuzzyDateValue) {
			mediaParameters.add(ParameterString.fromString("endDate: $endDate"));
			queryParameters.add(ParameterString.fromString("$endDate: FuzzyDateInt"));
			parametersValue.putIfAbsent("endDate", fuzzyDateValue.getFuzzyDateNumber());
			return this;
		}

		public Media.MediaArgumentBuilder currentSeason() {
			return season(MediaSeason.getCurrentSeason());
		}

		public Media.MediaArgumentBuilder season(MediaSeason season) {
			mediaParameters.add(ParameterString.fromString("season: $season"));
			queryParameters.add(ParameterString.fromString("$season: MediaSeason"));
			parametersValue.putIfAbsent("season", season);
			return this;
		}

		public Media.MediaArgumentBuilder seasonYear(int seasonYear) {
			mediaParameters.add(ParameterString.fromString("seasonYear: $seasonYear"));
			queryParameters.add(ParameterString.fromString("$seasonYear: Int"));
			parametersValue.putIfAbsent("seasonYear", seasonYear);
			return this;
		}

		public Media.MediaArgumentBuilder type(MediaType type) {
			mediaParameters.add(ParameterString.fromString("type: $type"));
			queryParameters.add(ParameterString.fromString("$type: MediaType"));
			parametersValue.putIfAbsent("type", type);
			return this;
		}

		public Media.MediaArgumentBuilder format(MediaFormat format) {
			mediaParameters.add(ParameterString.fromString("format: $format"));
			queryParameters.add(ParameterString.fromString("$format: MediaFormat"));
			parametersValue.put("format", format);
			return this;
		}

		public MediaArgumentBuilder status(MediaStatus status) {
			mediaParameters.add(ParameterString.fromString("status: $status"));
			queryParameters.add(ParameterString.fromString("$status: MediaStatus"));
			parametersValue.putIfAbsent("status", status);
			return this;
		}

		public MediaArgumentBuilder episodes(int episodes) {
			mediaParameters.add(ParameterString.fromString("episodes: $episodes"));
			queryParameters.add(ParameterString.fromString("$episodes: Int"));
			parametersValue.putIfAbsent("episodes", episodes);
			return this;
		}

		public MediaArgumentBuilder duration(int duration) {
			mediaParameters.add(ParameterString.fromString("duration: $duration"));
			queryParameters.add(ParameterString.fromString("$duration: Int"));
			parametersValue.putIfAbsent("duration", duration);
			return this;
		}

		public MediaArgumentBuilder chapters(int chapters) {
			mediaParameters.add(ParameterString.fromString("chapters: $chapters"));
			queryParameters.add(ParameterString.fromString("$chapters: Int"));
			parametersValue.putIfAbsent("chapters", chapters);
			return this;
		}

		public MediaArgumentBuilder volumes(int volumes) {
			mediaParameters.add(ParameterString.fromString("volumes: $volumes"));
			queryParameters.add(ParameterString.fromString("$volumes: Int"));
			parametersValue.putIfAbsent("volumes", volumes);
			return this;
		}

		public MediaArgumentBuilder isAdult(boolean isAdult) {
			mediaParameters.add(ParameterString.fromString("isAdult: $isAdult"));
			queryParameters.add(ParameterString.fromString("$isAdult: Boolean"));
			parametersValue.putIfAbsent("isAdult", isAdult);
			return this;
		}

		public MediaArgumentBuilder genre(MediaGenre genre) {
			mediaParameters.add(ParameterString.fromString("genre: $genre"));
			queryParameters.add(ParameterString.fromString("$genre: String"));
			parametersValue.putIfAbsent("genre", genre);
			return this;
		}

		public MediaArgumentBuilder tag(MediaTags tag) {
			mediaParameters.add(ParameterString.fromString("tag: $tag"));
			queryParameters.add(ParameterString.fromString("$tag: String"));
			parametersValue.putIfAbsent("tag", tag);
			return this;
		}

		public MediaArgumentBuilder onList(boolean onList) {
			mediaParameters.add(ParameterString.fromString("onList: $onList"));
			queryParameters.add(ParameterString.fromString("$onList: Boolean"));
			parametersValue.putIfAbsent("onList", onList);
			return this;
		}

		public MediaArgumentBuilder licensedBy(String licensedBy) {
			mediaParameters.add(ParameterString.fromString("licensedBy: $licensedBy"));
			queryParameters.add(ParameterString.fromString("$licensedBy: String"));
			parametersValue.putIfAbsent("licensedBy", licensedBy);
			return this;
		}

		public MediaArgumentBuilder averageScore(int averageScore) {
			mediaParameters.add(ParameterString.fromString("averageScore: $averageScore"));
			queryParameters.add(ParameterString.fromString("$averageScore: Int"));
			parametersValue.putIfAbsent("averageScore", averageScore);
			return this;
		}

		public MediaArgumentBuilder source(MediaSource source) {
			mediaParameters.add(ParameterString.fromString("source: $source"));
			queryParameters.add(ParameterString.fromString("$source: MediaSource"));
			parametersValue.putIfAbsent("source", source);
			return this;
		}

		//https://pl.wikipedia.org/wiki/ISO_3166-1_alfa-2
		public MediaArgumentBuilder countryOfOrigin(String countryCode) {
			countryCode = countryCode.toUpperCase(Locale.ROOT);
			if (!Arrays.asList(Locale.getISOCountries()).contains(countryCode)) {
				throw new IllegalArgumentException("The string is not a country code");
			}

			mediaParameters.add(ParameterString.fromString("countryOfOrigin: $countryOfOrigin"));
			queryParameters.add(ParameterString.fromString("$countryOfOrigin: CountryCode"));
			parametersValue.putIfAbsent("countryOfOrigin", countryCode);
			return this;
		}

		public MediaArgumentBuilder popularity(int popularity) {
			mediaParameters.add(ParameterString.fromString("popularity: $popularity"));
			queryParameters.add(ParameterString.fromString("$popularity: Int"));
			parametersValue.putIfAbsent("popularity", popularity);
			return this;
		}

		public MediaArgumentBuilder search(String search) {
			mediaParameters.add(ParameterString.fromString("search: $search"));
			queryParameters.add(ParameterString.fromString("$search: String"));
			parametersValue.putIfAbsent("search", search);
			return this;
		}

		public MediaArgumentBuilder idNot(int idNot) {
			mediaParameters.add(ParameterString.fromString("id_not: $id_not"));
			queryParameters.add(ParameterString.fromString("$id_not: Int"));
			parametersValue.putIfAbsent("id_not", idNot);
			return this;
		}

		public MediaArgumentBuilder idIn(int... ids) {
			mediaParameters.add(ParameterString.fromString("id_in: $id_in"));
			queryParameters.add(ParameterString.fromString("$id_in: [Int]"));
			parametersValue.putIfAbsent("id_in", Arrays.toString(ids));
			return this;
		}

		public MediaArgumentBuilder idNotIn(int... ids) {
			mediaParameters.add(ParameterString.fromString("id_not_in: $id_not_in"));
			queryParameters.add(ParameterString.fromString("$id_not_in: [Int]"));
			parametersValue.putIfAbsent("id_not_in", Arrays.toString(ids));
			return this;
		}

		public MediaArgumentBuilder idMalNot(int idNot) {
			mediaParameters.add(ParameterString.fromString("idMal_not: $idMal_not"));
			queryParameters.add(ParameterString.fromString("$idMal_not: Int"));
			parametersValue.putIfAbsent("idMal_not", idNot);
			return this;
		}

		public MediaArgumentBuilder idMalIn(int... ids) {
			mediaParameters.add(ParameterString.fromString("idMal_in: $idMal_in"));
			queryParameters.add(ParameterString.fromString("$idMal_in: [Int]"));
			parametersValue.putIfAbsent("idMal_in", Arrays.toString(ids));
			return this;
		}

		public MediaArgumentBuilder idMalNotIn(int... ids) {
			mediaParameters.add(ParameterString.fromString("idMal_not_in: $idMal_not_in"));
			queryParameters.add(ParameterString.fromString("$idMal_not_in: [Int]"));
			parametersValue.putIfAbsent("idMal_not_in", Arrays.toString(ids));
			return this;
		}

		public MediaArgumentBuilder startDateGreater(FuzzyDateValue fuzzyDateValue) {
			mediaParameters.add(ParameterString.fromString("startDate_greater: $startDate_greater"));
			queryParameters.add(ParameterString.fromString("$startDate_greater: FuzzyDateInt"));
			parametersValue.putIfAbsent("startDate_greater", fuzzyDateValue.getFuzzyDateNumber());
			return this;
		}

		public MediaArgumentBuilder startDateLesser(FuzzyDateValue fuzzyDateValue) {
			mediaParameters.add(ParameterString.fromString("startDate_lesser: $startDate_lesser"));
			queryParameters.add(ParameterString.fromString("$startDate_lesser: FuzzyDateInt"));
			parametersValue.putIfAbsent("startDate_lesser", fuzzyDateValue.getFuzzyDateNumber());
			return this;
		}

		public MediaArgumentBuilder endDateGreater(FuzzyDateValue fuzzyDateValue) {
			mediaParameters.add(ParameterString.fromString("endDate_greater: $endDate_greater"));
			queryParameters.add(ParameterString.fromString("$endDate_greater: FuzzyDateInt"));
			parametersValue.putIfAbsent("endDate_greater", fuzzyDateValue.getFuzzyDateNumber());
			return this;
		}

		public MediaArgumentBuilder endDateLesser(FuzzyDateValue fuzzyDateValue) {
			mediaParameters.add(ParameterString.fromString("endDate_lesser: $endDate_lesser"));
			queryParameters.add(ParameterString.fromString("$endDate_lesser: FuzzyDateInt"));
			parametersValue.putIfAbsent("endDate_lesser", fuzzyDateValue.getFuzzyDateNumber());
			return this;
		}

		public MediaArgumentBuilder formatNot(MediaFormat format) {
			mediaParameters.add(ParameterString.fromString("format_not: $format_not"));
			queryParameters.add(ParameterString.fromString("$format_not: MediaFormat"));
			parametersValue.putIfAbsent("format_not", format);
			return this;
		}

		public MediaArgumentBuilder formatIn(MediaFormat... formats) {
			mediaParameters.add(ParameterString.fromString("format_in: $format_in"));
			queryParameters.add(ParameterString.fromString("$format_in: [MediaFormat]"));
			parametersValue.putIfAbsent("format_in", formats);
			return this;
		}

		public MediaArgumentBuilder formatNotIn(MediaFormat... formats) {
			mediaParameters.add(ParameterString.fromString("format_not_in: $format_not_in"));
			queryParameters.add(ParameterString.fromString("$format_not_in: [MediaFormat]"));
			parametersValue.putIfAbsent("format_not_in", formats);
			return this;
		}

		public MediaArgumentBuilder statusNot(MediaStatus status) {
			mediaParameters.add(ParameterString.fromString("status_not: $status_not"));
			queryParameters.add(ParameterString.fromString("$status_not: MediaStatus"));
			parametersValue.putIfAbsent("status_not", status);
			return this;
		}

		public MediaArgumentBuilder statusIn(MediaStatus... statuses) {
			mediaParameters.add(ParameterString.fromString("status_in: $status_in"));
			queryParameters.add(ParameterString.fromString("$status_in: [MediaStatus]"));
			parametersValue.putIfAbsent("status_in", statuses);
			return this;
		}

		public MediaArgumentBuilder statusNotIn(MediaStatus... statuses) {
			mediaParameters.add(ParameterString.fromString("status_not_in: $status_not_in"));
			queryParameters.add(ParameterString.fromString("$status_not_in: [MediaStatus]"));
			parametersValue.putIfAbsent("status_not_in", statuses);
			return this;
		}

		public MediaArgumentBuilder episodesGreater(int episodes) {
			mediaParameters.add(ParameterString.fromString("episodes_greater: $episodes_greater"));
			queryParameters.add(ParameterString.fromString("$episodes_greater: Int"));
			parametersValue.putIfAbsent("episodes_greater", episodes);
			return this;
		}

		public MediaArgumentBuilder episodesLesser(int episodes) {
			mediaParameters.add(ParameterString.fromString("episodes_lesser: $episodes_lesser"));
			queryParameters.add(ParameterString.fromString("$episodes_lesser: Int"));
			parametersValue.putIfAbsent("episodes_lesser", episodes);
			return this;
		}

		public MediaArgumentBuilder durationGreater(int duration) {
			mediaParameters.add(ParameterString.fromString("duration_greater: $duration_greater"));
			queryParameters.add(ParameterString.fromString("$duration_greater: Int"));
			parametersValue.putIfAbsent("duration_greater", duration);
			return this;
		}

		public MediaArgumentBuilder durationLesser(int duration) {
			mediaParameters.add(ParameterString.fromString("duration_lesser: $duration_lesser"));
			queryParameters.add(ParameterString.fromString("$duration_lesser: Int"));
			parametersValue.putIfAbsent("duration_lesser", duration);
			return this;
		}

		public MediaArgumentBuilder chaptersGreater(int chapters) {
			mediaParameters.add(ParameterString.fromString("chapters_greater: $chapters_greater"));
			queryParameters.add(ParameterString.fromString("$chapters_greater: Int"));
			parametersValue.putIfAbsent("chapters_greater", chapters);
			return this;
		}

		public MediaArgumentBuilder chaptersLesser(int chapters) {
			mediaParameters.add(ParameterString.fromString("chapters_lesser: $chapters_lesser"));
			queryParameters.add(ParameterString.fromString("$chapters_lesser: Int"));
			parametersValue.putIfAbsent("chapters_lesser", chapters);
			return this;
		}

		public MediaArgumentBuilder volumesGreater(int volumes) {
			mediaParameters.add(ParameterString.fromString("volumes_greater: $volumes_greater"));
			queryParameters.add(ParameterString.fromString("$volumes_greater: Int"));
			parametersValue.putIfAbsent("volumes_greater", volumes);
			return this;
		}

		public MediaArgumentBuilder volumesLesser(int volumes) {
			mediaParameters.add(ParameterString.fromString("volumes_lesser: $volumes_lesser"));
			queryParameters.add(ParameterString.fromString("$volumes_lesser: Int"));
			parametersValue.putIfAbsent("volumes_lesser", volumes);
			return this;
		}

		public MediaArgumentBuilder genreIn(MediaGenre... genres) {
			mediaParameters.add(ParameterString.fromString("genre_in: $genre_in"));
			queryParameters.add(ParameterString.fromString("$genre_in: [String]"));
			parametersValue.putIfAbsent("genre_in", genres);
			return this;
		}

		public MediaArgumentBuilder genreNotIn(MediaGenre... genres) {
			mediaParameters.add(ParameterString.fromString("genre_not_in: $genre_not_in"));
			queryParameters.add(ParameterString.fromString("$genre_not_in: [String]"));
			parametersValue.putIfAbsent("genre_not_in", genres);
			return this;
		}

		public MediaArgumentBuilder tagIn(MediaTags... tags) {
			mediaParameters.add(ParameterString.fromString("tag_in: $tag_in"));
			queryParameters.add(ParameterString.fromString("$tag_in: [String]"));
			parametersValue.putIfAbsent("tag_in", tags);
			return this;
		}

		public MediaArgumentBuilder tagNotIn(MediaTags... tags) {
			mediaParameters.add(ParameterString.fromString("tag_not_in: $tag_not_in"));
			queryParameters.add(ParameterString.fromString("$tag_not_in: [String]"));
			parametersValue.putIfAbsent("tag_not_in", tags);
			return this;
		}

		public MediaArgumentBuilder licensedByIn(String... licensedBys) {
			mediaParameters.add(ParameterString.fromString("licensedBy_in: $licensedBy_in"));
			queryParameters.add(ParameterString.fromString("$licensedBy_in: [String]"));
			parametersValue.putIfAbsent("licensedBy_in", licensedBys);
			return this;
		}

		public MediaArgumentBuilder averageScoreNot(int averageScore) {
			mediaParameters.add(ParameterString.fromString("averageScore_not: $averageScore_not"));
			queryParameters.add(ParameterString.fromString("$averageScore_not: Int"));
			parametersValue.putIfAbsent("averageScore_not", averageScore);
			return this;
		}

		public MediaArgumentBuilder averageScoreGreater(int averageScore) {
			mediaParameters.add(ParameterString.fromString("averageScore_greater: $averageScore_greater"));
			queryParameters.add(ParameterString.fromString("$averageScore_greater: Int"));
			parametersValue.putIfAbsent("averageScore_greater", averageScore);
			return this;
		}

		public MediaArgumentBuilder averageScoreLesser(int averageScore) {
			mediaParameters.add(ParameterString.fromString("averageScore_lesser: $averageScore_lesser"));
			queryParameters.add(ParameterString.fromString("$averageScore_lesser: Int"));
			parametersValue.putIfAbsent("averageScore_lesser", averageScore);
			return this;
		}

		public MediaArgumentBuilder popularityNot(int popularity) {
			mediaParameters.add(ParameterString.fromString("popularity_not: $popularity_not"));
			queryParameters.add(ParameterString.fromString("$popularity_not: Int"));
			parametersValue.putIfAbsent("popularity_not", popularity);
			return this;
		}

		public MediaArgumentBuilder popularityGreater(int popularity) {
			mediaParameters.add(ParameterString.fromString("popularity_greater: $popularity_greater"));
			queryParameters.add(ParameterString.fromString("$popularity_greater: Int"));
			parametersValue.putIfAbsent("popularity_greater", popularity);
			return this;
		}

		public MediaArgumentBuilder popularityLesser(int popularity) {
			mediaParameters.add(ParameterString.fromString("popularity_lesser: $popularity_lesser"));
			queryParameters.add(ParameterString.fromString("$popularity_lesser: Int"));
			parametersValue.putIfAbsent("popularity_lesser", popularity);
			return this;
		}

		public MediaArgumentBuilder sourceIn(MediaSource... source) {
			mediaParameters.add(ParameterString.fromString("source_in: $source_in"));
			queryParameters.add(ParameterString.fromString("$source_in: [MediaSource]"));
			parametersValue.putIfAbsent("source_in", source);
			return this;
		}

		public MediaArgumentBuilder sort(MediaSort... source) {
			mediaParameters.add(ParameterString.fromString("sort: $sort"));
			queryParameters.add(ParameterString.fromString("$sort: [MediaSort]"));
			parametersValue.putIfAbsent("sort", source);
			return this;
		}

		public Media build() {
			if (mediaParameters.isEmpty()) {
				throw new IllegalStateException("Media must have at least 1 Parameter");
			}

			return new Media(
					QueryParameterUtils.combineIntoStringField(
							MEDIA_TITLE,
							QueryParameterUtils.buildStringArguments(mediaParameters),
							field.getFieldWithoutFieldName()
					).getField(),
					queryParameters.stream().map(ParameterString::getField).collect(Collectors.toSet()),
					parametersValue
			);
		}
	}
}
