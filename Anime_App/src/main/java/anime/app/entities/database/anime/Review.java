package anime.app.entities.database.anime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

/**
 * Class representing the <i>review</i> table from the database.
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(name = "reviews")
@Entity
public class Review {
	@Id
	@Column(nullable = false, unique = true)
	@PositiveOrZero(message = "Review Id cannot be negative")
	private int id;

	@Length(max = 100, message = "Review title cannot exceed 100 characters")
	@Column(nullable = false)
	@NotBlank(message = "Review Title cannot be blank")
	private String title;

	@Length(max = 300, message = "Review text cannot exceed 300 characters")
	@ColumnDefault("No text given")
	private String text;

	@PositiveOrZero(message = "Nr. of helpful cannot be negative")
	@Column(nullable = false, name = "nr_helpful")
	@ColumnDefault("0")
	private int nrOfHelpful;

	@PositiveOrZero(message = "Nr. of plus cannot be negative")
	@Column(nullable = false, name = "nr_plus")
	@ColumnDefault("0")
	private int nrOfPlus;

	@PositiveOrZero(message = "Nr. of minus cannot be negative")
	@Column(nullable = false, name = "nr_minus")
	@ColumnDefault("0")
	private int nrOfMinus;

	@UpdateTimestamp
	@Column(nullable = false)
	@PastOrPresent(message = "Cannot modify in the future")
	private LocalDateTime modification;

	@OneToOne(mappedBy = "review")
	private AnimeUserInfo animeUserInfo;
}
