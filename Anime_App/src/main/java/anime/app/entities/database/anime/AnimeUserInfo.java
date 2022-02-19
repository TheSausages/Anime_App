package anime.app.entities.database.anime;

import anime.app.entities.database.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class representing the <i>anime user infos</i> table from the database.
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(name = "anime_user_infos")
@Entity
public class AnimeUserInfo {
	@Embeddable
	@Setter
	@Getter
	@SuperBuilder
	@NoArgsConstructor
	@AllArgsConstructor
	static class AnimeUserInfoId implements Serializable {
		@ManyToOne
		@JoinColumn(name = "user_id")
		private User user;

		@ManyToOne
		@JoinColumn(name = "anime_id")
		private Anime anime;

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			AnimeUserInfoId that = (AnimeUserInfoId) o;
			return Objects.equals(anime, that.anime) && Objects.equals(user, that.user);
		}

		@Override
		public int hashCode() {
			return Objects.hash(user, anime);
		}
	}

	@EmbeddedId
	private AnimeUserInfoId id;

	public enum AnimeUserStatus {
		NO_STATUS,
		WATCHING,
		COMPLETED,
		DROPPED,
		PLAN_TO_WATCH
	}

	@Column(nullable = false)
	private AnimeUserStatus status;

	@PastOrPresent
	private LocalDate watchStart;

	@PastOrPresent
	private LocalDate endStart;

	@Column(nullable = false)
	@ColumnDefault("0")
	@PositiveOrZero
	private int episodesSeen;

	@Column(nullable = false)
	@ColumnDefault("false")
	private boolean favourite;

	@Column(nullable = false)
	@Range(max = 10, min = 0)
	private int gradle;

	@Column(nullable = false)
	@PastOrPresent
	private LocalDateTime modification;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "review")
	private Review review;
}
