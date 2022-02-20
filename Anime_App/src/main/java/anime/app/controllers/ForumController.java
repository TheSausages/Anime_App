package anime.app.controllers;

import anime.app.openapi.api.ForumApi;
import anime.app.openapi.model.ForumCategoryDTO;
import anime.app.services.forumcategories.ForumCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.validation.Validator;
import java.util.Set;

/**
 * Controller for all endpoints connected to the forum.
 */
@RestControllerWithBasePath
public class ForumController implements ForumApi {
	private final ForumCategoriesService forumCategoriesService;
	private final Validator validator;

	@Autowired
	ForumController(ForumCategoriesService forumCategoriesService, Validator validator) {
		this.forumCategoriesService = forumCategoriesService;
		this.validator = validator;
	}

	@Override
	public ResponseEntity<Set<ForumCategoryDTO>> getForumCategories() {
		Set<ForumCategoryDTO> categories = forumCategoriesService.getAllCategories();
		validator.validate(categories);

		return ResponseEntity.ok(categories);
	}
}
