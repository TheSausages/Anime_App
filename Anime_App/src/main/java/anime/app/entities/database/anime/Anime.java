package anime.app.entities.database.anime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;
import java.util.Set;

/**
 * Class representing the <i>anime</i> table from the database.
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(name = "anime")
@Entity
public class Anime {
	@Id
	@Column(nullable = false, unique = true)
	@PositiveOrZero(message = "Anime Id cannot be negative")
	private int id;

	@Length(max = 200)
	@Column(nullable = false)
	private String title;

	@Max(value = 10, message = "Average score cannot exceed 10")
	@PositiveOrZero(message = "Average Score cannot be negative")
	@Column(nullable = false)
	@ColumnDefault("0")
	private double averageScore;

	@PositiveOrZero(message = "Number of scores cannot be negative")
	@Column(nullable = false, name = "nr_scores")
	@ColumnDefault("0")
	private int nrOfScores;

	@PositiveOrZero(message = "Number of favourites cannot be negative")
	@Column(nullable = false, name = "nr_favourites")
	@ColumnDefault("0")
	private int nrOfFavourites;

	@PositiveOrZero(message = "Number of reviews cannot be negative")
	@Column(nullable = false, name = "nr_reviews")
	@ColumnDefault("0")
	private int nrOfReviews;

	@PositiveOrZero(message = "Average episode length cannot be negative")
	@Column(nullable = false)
	@ColumnDefault("20")
	private int averageEpisodeLength;

	@OneToMany(
			mappedBy = "id.anime",
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.LAZY
	)
	private Set<AnimeUserInfo> animeUserInfos;

	public void incrementFavourites() {
		nrOfFavourites++;
	}

	public void decrementFavourites() {
		nrOfFavourites--;
	}

	/**
	 * Method used to update the average for a new score. used only when a user didn't have another score - if he did,
	 * please use {@link Anime#updateAverageForUpdatedScore}.
	 * @param grade the new grade
	 */
	public void updateAverageForNewScore(Integer grade) {
		if (Objects.nonNull(grade)) {
			nrOfScores++;

			//https://stackoverflow.com/questions/12636613/how-to-calculate-moving-average-without-keeping-the-count-and-data-total
			averageScore = (averageScore * ((double) (nrOfScores - 1)) / nrOfScores) + ((double) grade / nrOfScores);
		}
	}

	/**
	 * Method used to update the average score when a previous one exists. This will create an APROXIMATION, so we don't
	 * iterate all scores each time.
	 * @param newGrade The new grade
	 * @param oldGrade The old grade
	 */
	public void updateAverageForUpdatedScore(Integer newGrade, Integer oldGrade) {
		if (Objects.nonNull(newGrade) && !newGrade.equals(oldGrade)) {
			if (nrOfScores <= 1) {
				// If there is only 1 score, just update the value
				averageScore = newGrade;
			} else {
				if (Objects.nonNull(oldGrade)) {
					// If there are more, remove the old one and update with new one
					int nrOfScoresMinus1 = nrOfScores - 1;
					double averageWithoutOldScore = ((averageScore * nrOfScoresMinus1) - oldGrade) / (nrOfScoresMinus1 - 1);

					averageScore = (averageWithoutOldScore * ((double) (nrOfScores - 1)) / nrOfScores) + ((double) newGrade / nrOfScores);
				}
			}
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Anime)) return false;

		Anime anime = (Anime) o;

		if (id != anime.id) return false;
		if (Double.compare(anime.averageScore, averageScore) != 0) return false;
		if (nrOfScores != anime.nrOfScores) return false;
		if (nrOfFavourites != anime.nrOfFavourites) return false;
		if (nrOfReviews != anime.nrOfReviews) return false;
		if (averageEpisodeLength != anime.averageEpisodeLength) return false;
		return title.equals(anime.title);
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = id;
		result = 31 * result + title.hashCode();
		temp = Double.doubleToLongBits(averageScore);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + nrOfScores;
		result = 31 * result + nrOfFavourites;
		result = 31 * result + nrOfReviews;
		result = 31 * result + averageEpisodeLength;
		return result;
	}
}
