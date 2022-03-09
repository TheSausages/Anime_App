package anime.app.services.forum.tag;

import anime.app.entities.database.forum.Tag;
import anime.app.exceptions.exceptions.NotFoundException;
import anime.app.openapi.model.TagDTO;
import anime.app.repositories.forum.TagRepository;
import anime.app.services.dto.conversion.DTOConversionService;
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
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    TagRepository tagRepository;

    @Spy
    DTOConversionService conversionService = new DTOConversionService();

    @InjectMocks
    TagService service;

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
    }

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
    }
}