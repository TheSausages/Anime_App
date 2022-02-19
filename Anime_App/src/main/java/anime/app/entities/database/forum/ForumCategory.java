package anime.app.entities.database.forum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(name = "forum_categories")
@Entity
public class ForumCategory {
	@Id
	@Column(nullable = false, unique = true)
	@PositiveOrZero(message = "Achievement Id cannot be negative")
	private int id;

	@Length(max = 45, message = "Name too long")
	@Column(nullable = false, unique = true)
	@NotBlank(message = "Username cannot be blank")
	private String name;

	@Length(max = 150, message = "Description too long")
	@Column(nullable = false, unique = true)
	@NotBlank(message = "Username cannot be blank")
	private String description;

	@OneToMany(mappedBy = "category")
	private Set<Thread> threads;
}
