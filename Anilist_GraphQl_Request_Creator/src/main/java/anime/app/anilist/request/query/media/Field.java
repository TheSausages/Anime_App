package anime.app.anilist.request.query.media;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import anime.app.anilist.request.query.parameters.common.BasicQueryParameters;
import anime.app.anilist.request.query.parameters.connections.airingschedule.AiringScheduleArguments;
import anime.app.anilist.request.query.parameters.connections.airingschedule.AiringScheduleConnection;
import anime.app.anilist.request.query.parameters.connections.characters.CharacterArguments;
import anime.app.anilist.request.query.parameters.connections.characters.CharacterConnection;
import anime.app.anilist.request.query.parameters.connections.media.MediaConnection;
import anime.app.anilist.request.query.parameters.connections.recommendation.RecommendationArguments;
import anime.app.anilist.request.query.parameters.connections.recommendation.RecommendationConnection;
import anime.app.anilist.request.query.parameters.connections.reviews.ReviewArguments;
import anime.app.anilist.request.query.parameters.connections.reviews.ReviewConnection;
import anime.app.anilist.request.query.parameters.connections.staff.StaffArguments;
import anime.app.anilist.request.query.parameters.connections.staff.StaffConnection;
import anime.app.anilist.request.query.parameters.connections.studio.StudioConnection;
import anime.app.anilist.request.query.parameters.connections.studio.StudioSort;
import anime.app.anilist.request.query.parameters.connections.trends.MediaTrendConnection;
import anime.app.anilist.request.query.parameters.connections.trends.MediaTrendsArguments;
import anime.app.anilist.request.query.parameters.fuzzyDate.FuzzyDateField;
import anime.app.anilist.request.query.parameters.fuzzyDate.FuzzyDateFieldParameter;
import anime.app.anilist.request.query.parameters.media.MediaExternalLinks;
import anime.app.anilist.request.query.parameters.media.MediaRank;
import anime.app.anilist.request.query.parameters.media.MediaStreamingEpisodes;
import anime.app.anilist.request.query.parameters.media.MediaTitle;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;

@Getter
public class Field {
	public static final String FIELD_TITLE = "field";

	private final String field;

	private Field(String field) {
		this.field = field;
	}

	public String getFieldWithoutFieldName() {
		return field.substring(FIELD_TITLE.length() + 1);
	}

	public static FieldBuilder getFieldBuilder() {
		return new FieldBuilder();
	}

	@Override
	public String toString() {
		return field;
	}

	public static final class FieldBuilder {
		private final Set<ParameterString> fieldParameters = new OverwritingLinkedHashSet<>();

		public FieldBuilder parameter(BasicQueryParameters parameter) {
			fieldParameters.add(ParameterString.fromString(parameter.getFieldName()));
			return this;
		}

		public FieldBuilder title(MediaTitle titles) {
			fieldParameters.add(ParameterString.fromString(titles.getTitleLanguages()));
			return this;
		}

		public FieldBuilder trailer() {
			fieldParameters.add(ParameterString.fromString(QueryParameterUtils.buildStringField(
					"trailer",
					"id",
					"site",
					"thumbnail"
			)));
			return this;
		}

		public FieldBuilder coverImage() {
			fieldParameters.add(ParameterString.fromString(QueryParameterUtils.buildStringField(
					"coverImage",
					"extraLarge",
					"large",
					"medium",
					"color"
			)));
			return this;
		}

		public FieldBuilder tags() {
			fieldParameters.add(ParameterString.fromString(QueryParameterUtils.buildStringField(
					"tags",
					"id",
					"name",
					"description",
					"category",
					"rank",
					"isGeneralSpoiler",
					"isMediaSpoiler",
					"isAdult"
			)));
			return this;
		}

		public FieldBuilder nextAiringEpisode() {
			fieldParameters.add(ParameterString.fromString(QueryParameterUtils.buildStringField(
					"nextAiringEpisode",
					"id",
					"airingAt",
					"timeUntilAiring",
					"episode",
					"mediaId"
			)));
			return this;
		}

		public FieldBuilder status() {
			return status(2);
		}

		public FieldBuilder status(int version) {
			fieldParameters.add(ParameterString.fromString("status" + QueryParameterUtils.combineIntoStringArgumentWithBracket("version", version)));
			return this;
		}

		public FieldBuilder description() {
			return descriptionAsHtml(false);
		}

		public FieldBuilder descriptionAsHtml() {
			return descriptionAsHtml(true);
		}

		public FieldBuilder descriptionAsHtml(boolean asHtml) {
			fieldParameters.add(ParameterString.fromString("description" + QueryParameterUtils.combineIntoStringArgumentWithBracket("asHtml", asHtml)));
			return this;
		}

		public FieldBuilder source() {
			return source(2);
		}

		public FieldBuilder source(int version) {
			fieldParameters.add(ParameterString.fromString("source" + QueryParameterUtils.combineIntoStringArgumentWithBracket("version", version)));
			return this;
		}

		public FieldBuilder externalLinks(MediaExternalLinks externalLink) {
			fieldParameters.add(ParameterString.fromString(externalLink.getExternalLink()));
			return this;
		}

		public FieldBuilder ranking(MediaRank rank) {
			fieldParameters.add(ParameterString.fromString(rank.getRank()));
			return this;
		}

		public FieldBuilder startDate(FuzzyDateField fuzzyDate) {
			if (!FuzzyDateFieldParameter.START_DATE.equals(fuzzyDate.getParameter())) {
				throw new IllegalStateException("The date must use the 'Start_Date' parameter");
			}

			fieldParameters.add(ParameterString.fromString(fuzzyDate.getFuzzyDateString()));
			return this;
		}

		public FieldBuilder endDate(FuzzyDateField fuzzyDate) {
			if (!FuzzyDateFieldParameter.END_DATE.equals(fuzzyDate.getParameter())) {
				throw new IllegalStateException("The date must use the 'Start_Date' parameter");
			}

			fieldParameters.add(ParameterString.fromString(fuzzyDate.getFuzzyDateString()));
			return this;
		}

		public FieldBuilder streamingEpisodes(MediaStreamingEpisodes episode) {
			fieldParameters.add(ParameterString.fromString(episode.getStreamingEpisode()));
			return this;
		}

		public FieldBuilder relations(MediaConnection connection) {
			fieldParameters.add(QueryParameterUtils.combineIntoStringField("relations", connection.getMediaConnectionWithoutFieldName()));
			return this;
		}

		public FieldBuilder characters(CharacterConnection characterConnection) {
			fieldParameters.add(QueryParameterUtils.combineIntoStringField("characters", characterConnection.getCharacterConnectionWithoutFieldName()));
			return this;
		}

		public FieldBuilder characters(CharacterConnection characterConnection, CharacterArguments characterArguments) {
			fieldParameters.add(
					QueryParameterUtils.combineIntoStringField("characters", characterArguments.getCharacterArgumentsString(), characterConnection.getCharacterConnectionWithoutFieldName())
			);
			return this;
		}

		public FieldBuilder staff(StaffConnection staffConnection) {
			fieldParameters.add(QueryParameterUtils.combineIntoStringField("staff", staffConnection.getStaffConnectionWithoutFieldName()));
			return this;
		}

		public FieldBuilder staff(StaffConnection staffConnection, StaffArguments staffArguments) {
			fieldParameters.add(
					QueryParameterUtils.combineIntoStringField("staff", staffArguments.getStaffArgumentsString(), staffConnection.getStaffConnectionWithoutFieldName())
			);
			return this;
		}

		public FieldBuilder studios(StudioConnection studioConnection) {
			fieldParameters.add(QueryParameterUtils.combineIntoStringField("studios", studioConnection.getStudioConnectionWithoutFieldName()));
			return this;
		}

		public FieldBuilder studios(StudioConnection studioConnection, StudioSort... sorts) {
			fieldParameters.add(QueryParameterUtils.combineIntoStringField(
					"studios",
					QueryParameterUtils.combineIntoStringArgumentWithBracket("sort",  Arrays.toString(sorts)),
					studioConnection.getStudioConnectionWithoutFieldName()
			));
			return this;
		}

		public FieldBuilder airingSchedule(AiringScheduleConnection airingScheduleConnection) {
			fieldParameters.add(QueryParameterUtils.combineIntoStringField(
					"airingSchedule",
					airingScheduleConnection.getAiringScheduleConnectionWithoutFieldName()
			));
			return this;
		}

		public FieldBuilder airingSchedule(AiringScheduleConnection airingScheduleConnection, AiringScheduleArguments airingScheduleArguments) {
			fieldParameters.add(QueryParameterUtils.combineIntoStringField(
					"airingSchedule",
					airingScheduleArguments.getAiringScheduleArgumentsString(),
					airingScheduleConnection.getAiringScheduleConnectionWithoutFieldName()
			));
			return this;
		}

		public FieldBuilder trends(MediaTrendConnection connection) {
			fieldParameters.add(QueryParameterUtils.combineIntoStringField("trends", connection.getMediaConnectionWithoutFieldName()));
			return this;
		}

		public FieldBuilder trends(MediaTrendConnection connection, MediaTrendsArguments arguments) {
			fieldParameters.add(QueryParameterUtils.combineIntoStringField(
					"trends",
					arguments.getMediaTrendsArgumentsString(),
					connection.getMediaConnectionWithoutFieldName()
			));
			return this;
		}

		public FieldBuilder reviews(ReviewConnection reviewConnection) {
			fieldParameters.add(QueryParameterUtils.combineIntoStringField("reviews", reviewConnection.getReviewConnectionWithoutFieldName()));
			return this;
		}

		public FieldBuilder reviews(ReviewConnection reviewConnection, ReviewArguments reviewArguments) {
			fieldParameters.add(QueryParameterUtils.combineIntoStringField(
					"reviews",
					reviewArguments.getReviewArgumentsString(),
					reviewConnection.getReviewConnectionWithoutFieldName()
			));
			return this;
		}

		public FieldBuilder recommendation(RecommendationConnection recommendationConnection) {
			fieldParameters.add(QueryParameterUtils.combineIntoStringField("recommendation", recommendationConnection.getRecommendationConnectionWithoutFieldName()));
			return this;
		}

		public FieldBuilder recommendation(RecommendationConnection recommendationConnection, RecommendationArguments recommendationArguments) {
			fieldParameters.add(QueryParameterUtils.combineIntoStringField(
					"recommendation",
					recommendationArguments.getRecommendationArgumentsString(),
					recommendationConnection.getRecommendationConnectionWithoutFieldName()
			));
			return this;
		}

		public Field build() {
			if (fieldParameters.isEmpty()) {
				throw new IllegalStateException("Field must have at least 1 Parameter");
			}

			return new Field(QueryParameterUtils.buildStringField(
					FIELD_TITLE,
					fieldParameters
			));
		}
	}
}
