package anime.app.services.icon;

import anime.app.entities.database.user.Achievement;

/**
 * Interface for an Icon Service. Each implementation must use this interface.
 */
public interface IconServiceInterface {
    /**
     * Get the icon for an achievement. Allows for custom error handling.
     *
     * @param achievement Achievement for which the icon should be found
     * @return Icon for the achievement in form of a byte array
     */
    byte[] getAchievementIcon(Achievement achievement);

    /**
     * Variant of {@link #getAchievementIcon(Achievement)} that searches for the achievement using its id.
     * @throws anime.app.exceptions.exceptions.NotFoundException When no achievement with the id was found
     */
    byte[] getAchievementIcon(int id);
}