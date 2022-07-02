package anime.app.services.forum.tag;

import anime.app.entities.database.forum.Tag;
import anime.app.exceptions.exceptions.NotFoundException;
import anime.app.openapi.model.TagDTO;
import anime.app.repositories.forum.TagRepository;
import anime.app.services.dto.conversion.DTOConversionService;
import anime.app.services.icon.IconService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    TagRepository tagRepository;

    @Mock
    IconService iconService;

    @Spy
    DTOConversionService conversionService = new DTOConversionService(iconService);

    @InjectMocks
    TagService service;

    @Nested
    @DisplayName("Get all tags tests")
    class GetAllTagsTest {
        @Test
        void getAllTags_NoElementReturned_ReturnEmptySet() {
            //given
            List<Tag> emptyTagList = Collections.emptyList();
            doReturn(emptyTagList).when(tagRepository).findAll();

            //when
            Set<TagDTO> result = service.getAllTags();

            //then
            assertThat(result, allOf(
                    notNullValue(),
                    instanceOf(Set.class),
                    emptyIterable()
            ));
        }

        @Test
        void getAllTags_SingleElementReturned_ReturnSetWithSingleElement() {
            //given
            Tag tag = Tag.builder()
                    .id(1)
                    .name("name")
                    .color("color")
                    .importance(Tag.TagImportance.HIGH)
                    .build();
            TagDTO dto = conversionService.convertToDTO(tag);
            List<Tag> singleElementList = List.of(tag);
            doReturn(singleElementList).when(tagRepository).findAll();

            //when
            Set<TagDTO> result = service.getAllTags();

            //then
            assertThat(result, allOf(
                    notNullValue(),
                    instanceOf(Set.class),
                    containsInAnyOrder(dto)
            ));

            // One time in given section, once in test
            verify(conversionService, times(2)).convertToDTO(tag);
        }

        @Test
        void getAllTags_ManyElementsReturned_ReturnSetWithManyElement() {
            //given
            Tag firstTag = Tag.builder()
                    .id(1)
                    .name("name")
                    .color("color")
                    .importance(Tag.TagImportance.HIGH)
                    .build();
            Tag secondTag = Tag.builder()
                    .id(2)
                    .name("name2")
                    .color("color2")
                    .importance(Tag.TagImportance.LOW)
                    .build();
            TagDTO firstDTO = conversionService.convertToDTO(firstTag);
            TagDTO secondDTO = conversionService.convertToDTO(secondTag);
            List<Tag> manyElementList = List.of(firstTag, secondTag);
            doReturn(manyElementList).when(tagRepository).findAll();

            //when
            Set<TagDTO> result = service.getAllTags();

            //then
            assertThat(result, allOf(
                    notNullValue(),
                    instanceOf(Set.class),
                    containsInAnyOrder(firstDTO, secondDTO)
            ));

            // One time in given section, once in test
            verify(conversionService, times(2)).convertToDTO(firstTag);
            verify(conversionService, times(2)).convertToDTO(secondTag);
        }
    }

    @Nested
    @DisplayName("Get Tag by it's ID tests")
    class GetTagByIDTest {
        @Test
        void getTagById_NoSuchTag_ThrowException() {
            //given
            int id = 1;
            doReturn(Optional.empty()).when(tagRepository).findById(id);

            //when
            NotFoundException exception = assertThrows(NotFoundException.class, () -> service.getTagById(id));

            //then
            assertThat(exception, allOf(
                    notNullValue(),
                    instanceOf(NotFoundException.class)
            ));

            assertThat(exception.getMessage(), allOf(
                    notNullValue(),
                    not(equalToCompressingWhiteSpace(""))
            ));

            assertThat(exception.getLogMessage(), allOf(
                    notNullValue(),
                    not(equalToCompressingWhiteSpace(""))
            ));
        }

        @Test
        void getTagById_TagExists_ReturnCorrectTag() {
            //given
            int id = 1;
            Tag expectedTag = Tag.builder()
                    .id(id)
                    .name("name")
                    .color("Color")
                    .importance(Tag.TagImportance.HIGH)
                    .build();
            doReturn(Optional.of(expectedTag)).when(tagRepository).findById(id);

            //when
            Tag actualTag = service.getTagById(id);

            //then
            assertThat(actualTag, allOf(
                    notNullValue(),
                    instanceOf(Tag.class),
                    equalTo(expectedTag)
            ));
        }
    }

    @Nested
    @DisplayName("Get Tag DTO By it's ID test")
    class GetTagDToByIDTest {
        @Test
        void getTagDTOById_NoSuchTag_ThrowException() {
            //given
            int id = 1;

            //No need for stubbing, because it will return null by default
            doReturn(Optional.empty()).when(tagRepository).findById(id);

            //when
            NotFoundException exception = assertThrows(NotFoundException.class, () -> service.getTagDTOById(id));

            //then
            assertThat(exception, allOf(
                    notNullValue(),
                    instanceOf(NotFoundException.class)
            ));

            assertThat(exception.getMessage(), allOf(
                    notNullValue(),
                    not(equalToCompressingWhiteSpace(""))
            ));

            assertThat(exception.getLogMessage(), allOf(
                    notNullValue(),
                    not(equalToCompressingWhiteSpace(""))
            ));
        }

        @Test
        void getTagDTOById_TagExists_ReturnCorrectTag() {
            //given
            int id = 1;
            Tag tag = Tag.builder()
                    .id(id)
                    .name("name")
                    .color("Color")
                    .importance(Tag.TagImportance.HIGH)
                    .build();
            doReturn(Optional.of(tag)).when(tagRepository).findById(id);
            TagDTO expectedDTO = conversionService.convertToDTO(tag);

            //when
            TagDTO actualTagDTO = service.getTagDTOById(id);

            //then
            assertThat(actualTagDTO, allOf(
                    notNullValue(),
                    instanceOf(TagDTO.class),
                    equalTo(expectedDTO)
            ));

            // One time in given section, once in test
            verify(conversionService, times(2)).convertToDTO(tag);
        }
    }
}