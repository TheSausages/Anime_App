package anime.app.entities.database.forum;

import anime.app.entities.database.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Class representing the <i>post_user_status</i> table from the database.
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(name = "post_user_status")
@Entity
public class PostUserStatus {
	@Embeddable
	@Setter
	@Getter
	@SuperBuilder
	@NoArgsConstructor
	@AllArgsConstructor
	static public class PostUserStatusId implements Serializable {
		@ManyToOne
		@JoinColumn(name = "user_id")
		private User user;

		@ManyToOne
		@JoinColumn(name = "post_id")
		private Post post;

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			PostUserStatusId that = (PostUserStatusId) o;
			return Objects.equals(post, that.post) && Objects.equals(user, that.user);
		}

		@Override
		public int hashCode() {
			return Objects.hash(user, post);
		}
	}

	@EmbeddedId
	private PostUserStatusId id;

	@Column(nullable = false)
	@ColumnDefault("false")
	private boolean liked;

	@Column(nullable = false)
	@ColumnDefault("false")
	private boolean disliked;

	@Column(nullable = false)
	@ColumnDefault("false")
	private boolean reported;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PostUserStatus)) return false;

		PostUserStatus that = (PostUserStatus) o;

		if (liked != that.liked) return false;
		if (disliked != that.disliked) return false;
		if (reported != that.reported) return false;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + (liked ? 1 : 0);
		result = 31 * result + (disliked ? 1 : 0);
		result = 31 * result + (reported ? 1 : 0);
		return result;
	}
}
