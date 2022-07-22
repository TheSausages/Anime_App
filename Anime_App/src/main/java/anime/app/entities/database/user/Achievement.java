package anime.app.entities.database.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

/**
 * Class representing the <i>achievements</i> table from the database.
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@Table(name = "achievements")
@Entity
public class Achievement {
	@Id
	@Column(nullable = false, unique = true)
	@PositiveOrZero(message = "Achievement Id cannot be negative")
	private int id;

	@Length(max = 45, message = "Achievement name too long")
	@Column(nullable = false, unique = true)
	@NotBlank(message = "Achievement name cannot be blank")
	private String name;

	@Length(max = 100, message = "Achievement name too long")
	@ColumnDefault("No Achievement description given")
	private String description;

	@Length(max = 200, message = "Achievement icon path too long")
	@Column(nullable = false, unique = true)
	@NotBlank(message = "Achievement icon path cannot be blank")
	private String iconPath;

	@Column(nullable = false)
	@ColumnDefault("10")
	@PositiveOrZero(message = "Achievement points cannot be negative")
	private int points;

	@ManyToMany(mappedBy = "achievements")
	private Set<User> users;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Achievement)) return false;

		Achievement that = (Achievement) o;

		if (id != that.id) return false;
		if (points != that.points) return false;
		if (!name.equals(that.name)) return false;
		if (description != null ? !description.equals(that.description) : that.description != null) return false;
		return iconPath.equals(that.iconPath);
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + name.hashCode();
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + iconPath.hashCode();
		result = 31 * result + points;
		return result;
	}
}
