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
	static class PostUserStatusId implements Serializable {
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
}
