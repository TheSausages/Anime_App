package anime.app.services.icon;

import anime.app.entities.database.user.Achievement;
import anime.app.exceptions.exceptions.NotFoundException;
import anime.app.repositories.user.AchievementRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * Default implementation for the {@link IconServiceInterface} interface.
 */
@Log4j2
@Service
public class IconService implements IconServiceInterface {
    private final static String defaultAchievementIconPath = "achievements/Default.png";

    private final AchievementRepository achievementRepository;
    private final byte[] defaultAchievementIcon;

    IconService(AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;

        try (InputStream stream = this.getClass().getClassLoader().getResourceAsStream(defaultAchievementIconPath)) {
            if (Objects.isNull(stream)) {
                throw new IOException("The default achievement icon stream is null");
            }

            defaultAchievementIcon = IOUtils.toByteArray(stream);
        } catch (Exception e) {
            throw new BeanInitializationException("Could not find the default achievement icon", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public byte[] getAchievementIcon(int id) {
        return getAchievementIcon(
                achievementRepository.findById(id)
                        .orElseThrow(() -> NotFoundException.builder()
                                .userMessageTranslationKey("user.achievement-not-found")
                                .logMessage("Achievement with id " + id + " could not be found")
                                .build())
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] getAchievementIcon(Achievement achievement) {
        log.info("Try to get Icon with path:" + achievement.getIconPath());

        InputStream in = this.getClass().getClassLoader().getResourceAsStream(achievement.getIconPath());

        if (Objects.isNull(in)) {
            log.error("Could not find icon with path: {}. Using the default icon", achievement.getIconPath());

            return defaultAchievementIcon;
        }

        try {
            return IOUtils.toByteArray(in);
        } catch (Exception e) {
            log.error("Error during transformation from InputStream to byte array for achievement {}, use the default icon", achievement.getName());

            return defaultAchievementIcon;
        }
    }
}
