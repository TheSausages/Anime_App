package anime.app.entities.database.forum;

import anime.app.entities.database.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Class representing the <i>posts</i> table from the database.
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(name = "posts")
@Entity
public class Post {
	@Id
	@Column(nullable = false, unique = true)
	@PositiveOrZero(message = "Achievement Id cannot be negative")
	private int id;

	@Length(max = 80, message = "Name too long")
	@Column(nullable = false)
	@NotBlank(message = "Username cannot be blank")
	private String name;

	@Length(max = 600, message = "Name too long")
	@ColumnDefault("No description given")
	private String text;

	@Column(nullable = false)
	@ColumnDefault("false")
	private boolean blocked;

	public enum PostStatus {
		NO_PROBLEM,
		PENDING,
		DELETED
	}

	@Column(nullable = false)
	private PostStatus status;

	@PositiveOrZero
	@Column(nullable = false, name = "nr_plus")
	@ColumnDefault("0")
	private int nrOfPlus;

	@PositiveOrZero
	@Column(nullable = false, name = "nr_minus")
	@ColumnDefault("0")
	private int nrOfMinus;

	@PositiveOrZero
	@Column(nullable = false, name = "nr_reports")
	@ColumnDefault("0")
	private int nrOfReports;

	@Column(nullable = false)
	@PastOrPresent
	private LocalDateTime creation;

	@Column(nullable = false)
	@PastOrPresent
	private LocalDateTime modification;

	@ManyToOne
	@JoinColumn(name = "creator", nullable = false)
	private User creator;

	@ManyToOne
	@JoinColumn(name = "thread", nullable = false)
	private Thread thread;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "answer_to")
	private Post postWithResponses;

	@OneToMany(mappedBy = "postWithResponses")
	private Set<Post> subCategories;
}
