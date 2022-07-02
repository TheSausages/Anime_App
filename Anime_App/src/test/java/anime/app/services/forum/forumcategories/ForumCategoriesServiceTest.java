package anime.app.services.forum.forumcategories;

import anime.app.entities.database.forum.ForumCategory;
import anime.app.openapi.model.ForumCategoryDTO;
import anime.app.repositories.forum.ForumCategoryRepository;
import anime.app.services.dto.conversion.DTOConversionService;
import anime.app.services.icon.IconService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ForumCategoriesServiceTest {

	@Mock
	ForumCategoryRepository forumCategoryRepository;

	@Mock
	IconService iconService;

	@Spy
	DTOConversionService conversionService = new DTOConversionService(iconService);

	@InjectMocks
	ForumCategoriesService categoriesService;

	@Test
	void getAllCategories_NoElementReturned_ReturnEmptySet() {
		//given
		List<ForumCategory> emptyCategoryList = Collections.emptyList();
		doReturn(emptyCategoryList).when(forumCategoryRepository).findAll();

		//when
		Set<ForumCategoryDTO> result = categoriesService.getAllCategories();

		//then
		assertThat(result, allOf(
				notNullValue(),
				instanceOf(Set.class),
				emptyIterable()
		));
	}

	@Test
	void getAllCategories_SingleElementReturned_ReturnSetWithSingleElement() {
		//given
		ForumCategory category = ForumCategory.builder()
				.id(1)
				.name("name")
				.description("description")
				.build();
		ForumCategoryDTO categoryDTO = conversionService.convertToDTO(category);
		List<ForumCategory> singleElementList = List.of(category);
		doReturn(singleElementList).when(forumCategoryRepository).findAll();

		//when
		Set<ForumCategoryDTO> result = categoriesService.getAllCategories();

		//then
		assertThat(result, allOf(
				notNullValue(),
				instanceOf(Set.class),
				containsInAnyOrder(categoryDTO)
		));

		// One time in given section, once in test
		verify(conversionService, times(2)).convertToDTO(category);
	}

	@Test
	void getAllCategories_ManyElementsReturned_ReturnSetWithManyElement() {
		//given
		ForumCategory firstCategory = ForumCategory.builder()
				.id(1)
				.name("name")
				.description("description")
				.build();
		ForumCategory secondCategory = ForumCategory.builder()
				.id(2)
				.name("name1")
				.description("description1")
				.build();
		ForumCategoryDTO firstCategoryDTO = conversionService.convertToDTO(firstCategory);
		ForumCategoryDTO secondCategoryDTO = conversionService.convertToDTO(secondCategory);
		List<ForumCategory> manyElementList = List.of(firstCategory, secondCategory);
		doReturn(manyElementList).when(forumCategoryRepository).findAll();

		//when
		Set<ForumCategoryDTO> result = categoriesService.getAllCategories();

		//then
		assertThat(result, allOf(
				notNullValue(),
				instanceOf(Set.class),
				containsInAnyOrder(firstCategoryDTO, secondCategoryDTO)
		));

		// One time in given section, once in test
		verify(conversionService, times(2)).convertToDTO(firstCategory);
		verify(conversionService, times(2)).convertToDTO(secondCategory);
	}
}