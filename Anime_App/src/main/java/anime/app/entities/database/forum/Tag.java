package anime.app.entities.database.forum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

/**
 * Class representing the <i>tags</i> table from the database.
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(name = "tags")
@Entity
public class Tag {
	@Id
	@Column(nullable = false, unique = true)
	@PositiveOrZero(message = "Achievement Id cannot be negative")
	private int id;

	@Length(max = 45, message = "Name too long")
	@Column(nullable = false)
	@NotBlank(message = "Username cannot be blank")
	private String name;

	public enum TagImportance {
		LOW,
		MEDIUM,
		HIGH,
		ADMIN
	}

	@Column(nullable = false)
	private TagImportance importance;

	@Pattern(regexp = "^rgb(\\d{1,3}, \\d{1,3}, \\d{1,3})$")
	private String color;

	@ManyToMany(mappedBy = "tags")
	private Set<Thread> threads;
}
