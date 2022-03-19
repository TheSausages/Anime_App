package anime.app.services.forum.tag;

import anime.app.entities.database.forum.Tag;
import anime.app.exceptions.exceptions.NotFoundException;
import anime.app.openapi.model.TagDTO;
import anime.app.repositories.forum.TagRepository;
import anime.app.services.dto.conversion.DTOConversionServiceInterface;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Default implementation for the {@link TagServiceInterface} interface.
 */
@Service
@Log4j2
public class TagService implements TagServiceInterface {
    private final TagRepository tagRepository;
    private final DTOConversionServiceInterface conversionService;

    @Autowired
    TagService(TagRepository repository, DTOConversionServiceInterface conversionService) {
        this.tagRepository = repository;
        this.conversionService = conversionService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(label = "Get all tags", readOnly = true)
    public Set<TagDTO> getAllTags() {
        log.info("Get all tags");

        return tagRepository.findAll()
                .stream()
                .map(conversionService::convertToDTO)
                .collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(label = "Get tag by it's id", readOnly = true)
    public Tag getTagById(int id) {
        log.info("Get tag with id: {}", id);

        return tagRepository.findById(id)
                .orElseThrow(() -> NotFoundException.builder()
                        .userMessageTranslationKey("forum.tag-not-found")
                        .translationParameter(id)
                        .build());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(label = "Get tag by it's id and transform it into the DTO", readOnly = true)
    public TagDTO getTagDTOById(int id) {
        return conversionService.convertToDTO(getTagById(id));
    }
}
