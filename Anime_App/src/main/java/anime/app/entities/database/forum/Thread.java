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
	@PositiveOrZero(message = "Thread Id cannot be negative")
	private int id;

	@Length(max = 80, message = "Thread title too long")
	@Column(nullable = false)
	@NotBlank(message = "Thread title cannot be blank")
	private String title;

	@Length(max = 600, message = "Thread name too long")
	@ColumnDefault("No Thread description given")
	private String text;

	public enum ThreadStatus {
		OPEN,
		CLOSED
	}

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ThreadStatus status;

	@ColumnDefault("0")
	@PositiveOrZero(message = "Thread nr. of posts cannot be negative")
	@Column(name = "nr_posts")
	private int nrOfPosts;

	@CreationTimestamp
	@Column(nullable = false)
	@PastOrPresent(message = "Thread creation time cannot be negative")
	private LocalDateTime creation;

	@UpdateTimestamp
	@Column(nullable = false)
	@PastOrPresent(message = "Thread modification time cannot be negative")
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

	@OneToMany(
			mappedBy = "id.thread",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private Set<ThreadUserStatus> threadUserStatuses;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Thread)) return false;

		Thread thread = (Thread) o;

		if (id != thread.id) return false;
		if (nrOfPosts != thread.nrOfPosts) return false;
		if (title != null ? !title.equals(thread.title) : thread.title != null) return false;
		if (text != null ? !text.equals(thread.text) : thread.text != null) return false;
		if (status != thread.status) return false;
		if (!Objects.equals(creation, thread.creation)) return false;
		if (!Objects.equals(modification, thread.modification))
			return false;
		if (!Objects.equals(category, thread.category)) return false;
		return Objects.equals(creator, thread.creator);
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (text != null ? text.hashCode() : 0);
		result = 31 * result + (status != null ? status.hashCode() : 0);
		result = 31 * result + nrOfPosts;
		result = 31 * result + (creation != null ? creation.hashCode() : 0);
		result = 31 * result + (modification != null ? modification.hashCode() : 0);
		result = 31 * result + (category != null ? category.hashCode() : 0);
		result = 31 * result + (creator != null ? creator.hashCode() : 0);
		return result;
	}
}
