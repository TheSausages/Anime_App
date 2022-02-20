package anime.app.services.forumcategories;

import anime.app.openapi.model.ForumCategoryDTO;
import anime.app.repositories.forum.ForumCategoryRepository;
import anime.app.services.dto.conversion.DTOConversionServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Default implementation for the {@link ForumCategoriesService} interface.
 */
@Service
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
	public Set<ForumCategoryDTO> getAllCategories() {
		return forumCategoryRepository.findAll()
				.stream()
				.map(conversionService::convertToDTO)
				.collect(Collectors.toSet());
	}
}
