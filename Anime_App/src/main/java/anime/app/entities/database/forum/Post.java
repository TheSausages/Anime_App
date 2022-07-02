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
	@PositiveOrZero(message = "Post Id cannot be negative")
	private int id;

	@Length(max = 80, message = "Post title too long")
	@Column(nullable = false)
	@NotBlank(message = "Post title cannot be blank")
	private String title;

	@Length(max = 600, message = "Post text too long")
	@ColumnDefault("No post description given")
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
	@Enumerated(EnumType.STRING)
	private PostStatus status;

	@PositiveOrZero(message = "Nr. of plus cannot be negative")
	@Column(nullable = false, name = "nr_plus")
	@ColumnDefault("0")
	private int nrOfPlus;

	@PositiveOrZero(message = "Nr. of minus cannot be negative")
	@Column(nullable = false, name = "nr_minus")
	@ColumnDefault("0")
	private int nrOfMinus;

	@PositiveOrZero(message = "Nr. of reports cannot be negative")
	@Column(nullable = false, name = "nr_reports")
	@ColumnDefault("0")
	private int nrOfReports;

	@Column(nullable = false)
	@PastOrPresent(message = "Cannot create a post in the future")
	private LocalDateTime creation;

	@Column(nullable = false)
	@PastOrPresent(message = "Cannot modify a post in the future")
	private LocalDateTime modification;

	@ManyToOne
	@JoinColumn(name = "creator", nullable = false)
	private User creator;

	@ManyToOne
	@JoinColumn(name = "thread", nullable = false)
	private Thread thread;

	@OneToMany(
			mappedBy = "id.post",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private Set<PostUserStatus> postUserStatuses;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "answer_to")
	private Post postWithResponses;

	@OneToMany(mappedBy = "postWithResponses")
	private Set<Post> subCategories;
}
