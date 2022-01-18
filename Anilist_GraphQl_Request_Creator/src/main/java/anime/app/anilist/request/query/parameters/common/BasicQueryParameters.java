package anime.app.anilist.request.query.parameters.common;

import lombok.Getter;

@Getter
public enum BasicQueryParameters implements CommonFieldParameter {
	ID("id"),
	ID_MAL("idMal"),
	TYPE("type"),
	FORMAT("format"),
	SEASON("season"),
	SEASON_YEAR("seasonYear"),
	SEASON_INT("seasonInt"),
	EPISODES("episodes"),
	DURATION("duration"),
	CHAPTERS("chapters"),
	VOLUMES("volumes"),
	COUNTRY_OF_ORIGIN("countryOfOrigin"),
	IS_LICENSED("isLicensed"),
	HASHTAG("hashtag"),
	UPDATED_AT("updatedAt"),
	BANNER_IMAGE("bannerImage"),
	GENRES("genres"),
	SYNONYMS("synonyms"),
	AVERAGE_SCORE("averageScore"),
	MEAN_SCORE("meanScore"),
	POPULARITY("popularity"),
	IS_LOCKED("isLocked"),
	TRENDING("trending"),
	FAVOURITES("favourites"),
	IS_ADULT("isAdult"),
	STATS("stats"),
	SITE_URL("siteUrl"),
	MOD_NOTES("modNotes");


	private final String fieldName;

	BasicQueryParameters(String fieldType) {
		this.fieldName = fieldType;
	}
}
