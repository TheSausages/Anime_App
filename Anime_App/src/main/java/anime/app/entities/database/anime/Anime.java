package anime.app.entities.database.anime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

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
	@PositiveOrZero(message = "Achievement Id cannot be negative")
	private int id;

	@Max(10)
	@PositiveOrZero
	@Column(nullable = false)
	@ColumnDefault("0")
	private double averageScore;

	@PositiveOrZero
	@Column(nullable = false, name = "nr_scores")
	@ColumnDefault("0")
	private int nrOfScores;

	@PositiveOrZero
	@Column(nullable = false, name = "nr_favourites")
	@ColumnDefault("0")
	private int nrOfFavourites;

	@PositiveOrZero
	@Column(nullable = false, name = "nr_reviews")
	@ColumnDefault("0")
	private int nrOfReviews;

	@PositiveOrZero
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
