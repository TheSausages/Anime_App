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
}
