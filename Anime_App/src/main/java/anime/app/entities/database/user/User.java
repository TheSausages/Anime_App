package anime.app.entities.database.user;

import anime.app.entities.database.anime.AnimeUserInfo;
import anime.app.entities.database.forum.Post;
import anime.app.entities.database.forum.Thread;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;
import java.util.UUID;

/**
 * Class representing the <i>users</i> table from the database.
 * The {@link #id} field is the keycloak id of a user.
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(name = "users")
@Entity
public class User {
	@Id
	@NotNull(message = "User ID cannot be null")
	@Column(nullable = false, unique = true)
	private UUID id;

	@Length(max = 45, message = "Username too long")
	@Column(nullable = false, unique = true)
	@NotBlank(message = "Username cannot be blank")
	private String username;

	@ColumnDefault("0")
	@Column(nullable = false)
	@PositiveOrZero(message = "Watch time cannot be negative")
	private int watchTime;

	@ColumnDefault("0")
	@Column(nullable = false)
	@PositiveOrZero(message = "Watch time cannot be negative")
	private int achievementPoints;

	@OneToMany(mappedBy = "creator")
	private Set<Thread> threads;

	@OneToMany(mappedBy = "creator")
	private Set<Post> posts;

	@ManyToMany
	@JoinTable(
			name = "user_achievements",
			joinColumns = {@JoinColumn(name = "user_id")},
			inverseJoinColumns = {@JoinColumn(name = "achievement_id")}
	)
	private Set<Achievement> achievements;

	@OneToMany(
			mappedBy = "id.user",
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.LAZY
	)
	private Set<AnimeUserInfo> animeUserInfo;
}
