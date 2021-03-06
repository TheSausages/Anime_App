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
import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Review)) return false;

		Review review = (Review) o;

		if (id != review.id) return false;
		if (nrOfHelpful != review.nrOfHelpful) return false;
		if (nrOfPlus != review.nrOfPlus) return false;
		if (nrOfMinus != review.nrOfMinus) return false;
		if (!title.equals(review.title)) return false;
		if (!Objects.equals(text, review.text)) return false;
		return Objects.equals(modification, review.modification);
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + title.hashCode();
		result = 31 * result + (text != null ? text.hashCode() : 0);
		result = 31 * result + nrOfHelpful;
		result = 31 * result + nrOfPlus;
		result = 31 * result + nrOfMinus;
		result = 31 * result + (modification != null ? modification.hashCode() : 0);
		return result;
	}
}
