package anime.app.services.forum.forumcategories;

import anime.app.openapi.model.ForumCategoryDTO;
import anime.app.repositories.forum.ForumCategoryRepository;
import anime.app.services.dto.conversion.DTOConversionServiceInterface;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Default implementation for the {@link ForumCategoriesService} interface.
 */
@Service
@Log4j2
public class ForumCategoriesService implements ForumCategoriesServiceInterface {
	private final ForumCategoryRepository forumCategoryRepository;
	private final DTOConversionServiceInterface conversionService;

	@Autowired
	ForumCategoriesService(ForumCategoryRepository forumCategoryRepository,
	                       DTOConversionServiceInterface conversionService) {
		this.forumCategoryRepository = forumCategoryRepository;
		this.conversionService = conversionService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(label = "Get all categories", readOnly = true)
	public Set<ForumCategoryDTO> getAllCategories() {
		log.info("Find all forum categories");

		return forumCategoryRepository.findAll()
				.stream()
				.map(conversionService::convertToDTO)
				.collect(Collectors.toSet());
	}
}
