package anime.app.controllers;

import anime.app.openapi.api.ForumApi;
import anime.app.openapi.model.ForumCategoryDTO;
import anime.app.openapi.model.TagDTO;
import anime.app.services.forum.forumcategories.ForumCategoriesService;
import anime.app.services.forum.tag.TagServiceInterface;
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
	private final TagServiceInterface tagService;
	private final Validator validator;

	@Autowired
	ForumController(ForumCategoriesService forumCategoriesService, TagServiceInterface tagService, Validator validator) {
		this.forumCategoriesService = forumCategoriesService;
		this.tagService = tagService;
		this.validator = validator;
	}

	@Override
	public ResponseEntity<Set<ForumCategoryDTO>> getForumCategories() {
		Set<ForumCategoryDTO> categories = forumCategoriesService.getAllCategories();
		validator.validate(categories);

		return ResponseEntity.ok(categories);
	}

	@Override
	public ResponseEntity<Set<TagDTO>> getForumTags() {
		Set<TagDTO> tags = tagService.getAllTags();
		validator.validate(tags);

		return ResponseEntity.ok(tags);
	}

	//TODO add threads and posts itp
}
