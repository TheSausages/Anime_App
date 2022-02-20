package anime.app.services.forumcategories;

import anime.app.openapi.model.ForumCategoryDTO;

import java.util.Set;

/**
 * Interface for a Forum Category Service. Each implementation must use this interface.
 */
public interface ForumCategoriesServiceInterface {
	/**
	 * Method used to get all categories in a set
	 * @return Set of categories
	 */
	Set<ForumCategoryDTO> getAllCategories();
}
