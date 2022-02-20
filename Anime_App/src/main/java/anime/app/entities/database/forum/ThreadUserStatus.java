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
 * Class representing the <i>thread_user_status</i> table from the database.
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(name = "thread_user_status")
@Entity
public class ThreadUserStatus {
	@Embeddable
	@Setter
	@Getter
	@SuperBuilder
	@NoArgsConstructor
	@AllArgsConstructor
	static class ThreadUserStatusId implements Serializable {
		@ManyToOne
		@JoinColumn(name = "user_id")
		private User user;

		@ManyToOne
		@JoinColumn(name = "thread_id")
		private Thread thread;

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			ThreadUserStatusId that = (ThreadUserStatusId) o;
			return Objects.equals(thread, that.thread) && Objects.equals(user, that.user);
		}

		@Override
		public int hashCode() {
			return Objects.hash(user, thread);
		}
	}

	@EmbeddedId
	private ThreadUserStatusId id;

	@Column(nullable = false)
	@ColumnDefault("false")
	private boolean watching;

	@Column(nullable = false)
	@ColumnDefault("false")
	private boolean blocking;
}
