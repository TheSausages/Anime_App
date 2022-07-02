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

/**
 * Class representing the <i>forum_categories</i> table from the database.
 */
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
	@PositiveOrZero(message = "Forum Category Id cannot be negative")
	private int id;

	@Length(max = 45, message = "Forum Category name too long")
	@Column(nullable = false, unique = true)
	@NotBlank(message = "Forum Category name cannot be blank")
	private String name;

	@Length(max = 150, message = "Forum Category description too long")
	@Column(nullable = false, unique = true)
	@NotBlank(message = "Forum Category description cannot be blank")
	private String description;

	@OneToMany(mappedBy = "category")
	private Set<Thread> threads;
}
