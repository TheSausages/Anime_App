package anime.app.entities.database.forum;

import anime.app.entities.database.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.Objects;
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

	@CreationTimestamp
	@Column(nullable = false)
	@PastOrPresent(message = "Cannot create a post in the future")
	private LocalDateTime creation;

	@UpdateTimestamp
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Post)) return false;

		Post post = (Post) o;

		if (id != post.id) return false;
		if (blocked != post.blocked) return false;
		if (nrOfPlus != post.nrOfPlus) return false;
		if (nrOfMinus != post.nrOfMinus) return false;
		if (nrOfReports != post.nrOfReports) return false;
		if (!title.equals(post.title)) return false;
		if (!Objects.equals(text, post.text)) return false;
		if (status != post.status) return false;
		if (!Objects.equals(creation, post.creation)) return false;
		if (!Objects.equals(modification, post.modification)) return false;
		if (!creator.equals(post.creator)) return false;
		return thread.equals(post.thread);
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + title.hashCode();
		result = 31 * result + (text != null ? text.hashCode() : 0);
		result = 31 * result + (blocked ? 1 : 0);
		result = 31 * result + status.hashCode();
		result = 31 * result + nrOfPlus;
		result = 31 * result + nrOfMinus;
		result = 31 * result + nrOfReports;
		result = 31 * result + (creation != null ? creation.hashCode() : 0);
		result = 31 * result + (modification != null ? modification.hashCode() : 0);
		result = 31 * result + creator.hashCode();
		result = 31 * result + thread.hashCode();
		return result;
	}
}
