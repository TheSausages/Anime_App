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
 * Class representing the <i>threads</i> table from the database.
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(name = "threads")
@Entity
public class Thread {
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

	public enum ThreadStatus {
		OPEN,
		CLOSED
	}

	@Column(nullable = false)
	private ThreadStatus status;

	@ColumnDefault("0")
	@PositiveOrZero
	@Column(name = "nr_posts")
	private int nrOfPosts;

	@Column(nullable = false)
	@PastOrPresent
	private LocalDateTime creation;

	@Column(nullable = false)
	@PastOrPresent
	private LocalDateTime modification;

	@ManyToOne
	@JoinColumn(name = "category", nullable = false)
	private ForumCategory category;

	@ManyToOne
	@JoinColumn(name = "creator", nullable = false)
	private User creator;

	@ManyToMany
	@JoinTable(
			name = "thread_tags",
			joinColumns = {@JoinColumn(name = "thread_id")},
			inverseJoinColumns = {@JoinColumn(name = "tag_id")}
	)
	private Set<Tag> tags;

	@OneToMany(
			mappedBy = "thread",
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.LAZY
	)
	private Set<Post> posts;
}
