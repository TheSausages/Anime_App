package anime.app.services.icon;

import anime.app.entities.database.user.Achievement;
import anime.app.exceptions.exceptions.NotFoundException;
import anime.app.repositories.user.AchievementRepository;
import anime.app.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class IconServiceTest {

    @Mock
    static AchievementRepository achievementRepository;

    String defaultAchievementIconPath = "achievements/Default.png";

    IconService iconService;

    // We can't use @BeforeAll, because it would need to be static
    @BeforeEach
    void setUp() {
        if (Objects.isNull(iconService)) {
            iconService = new IconService(achievementRepository, defaultAchievementIconPath);
        }
    }

    @Nested
    @DisplayName("Icon Service Creation")
    class IconServiceCreationTest {
        @Test
        void iconServiceCreation_DefaultIconNotExists_ThrowException() {
            //given
            String nonExistIcon = "NonExistingIconPath";

            //when
            Exception exception = assertThrows(BeanInitializationException.class, () ->
                    new IconService(achievementRepository, nonExistIcon)
            );

            //then
            assertThat(exception, allOf(
                    notNullValue(),
                    instanceOf(BeanInitializationException.class)
            ));
        }

        @Test
        void iconServiceCreation_DefaultIconExists_NoException() throws IOException {
            //given
            byte[] defaultIconByteArray = TestUtils.getIcon(defaultAchievementIconPath);

            //when
            IconService testService = new IconService(achievementRepository, defaultAchievementIconPath);

            //then
            byte[] testServiceIconByteArray = (byte[]) ReflectionTestUtils.getField(testService, "defaultAchievementIcon");

            assertThat(testServiceIconByteArray, allOf(
                    notNullValue(),
                    instanceOf(byte[].class),
                    equalTo(defaultIconByteArray)
            ));
        }
    }

    @Nested
    @DisplayName("Get Achievement Icon")
    class GetAchievementIconTest {
        @Test
        void getAchievementIcon_NonExistingId_ThrowException() {
            //given
            int nonExistingId = -1;
            doReturn(Optional.empty()).when(achievementRepository).findById(nonExistingId);

            //when
            Exception exception = assertThrows(NotFoundException.class, () ->
                    iconService.getAchievementIcon(nonExistingId)
            );

            //then
            assertThat(exception, allOf(
                    notNullValue(),
                    instanceOf(NotFoundException.class)
            ));
        }

        @Test
        void getAchievementIcon_IdExistsNoIcon_ReturnDefaultIcon() throws IOException {
            //given
            int existingIdNoIcon = 1;
            Achievement achievement = Achievement.builder()
                    .id(existingIdNoIcon)
                    .iconPath("achievements/DoesNotExist.png")
                    .build();
            byte[] defaultAchievementIcon = TestUtils.getIcon(defaultAchievementIconPath);
            doReturn(Optional.of(achievement)).when(achievementRepository).findById(existingIdNoIcon);

            //when
            byte[] actualIcon = iconService.getAchievementIcon(existingIdNoIcon);

            //then
            assertThat(actualIcon, allOf(
                    notNullValue(),
                    instanceOf(byte[].class),
                    equalTo(defaultAchievementIcon)
            ));
        }

        @Test
        void getAchievementIcon_IdExistsWithIcon_ReturnDefaultIcon() throws IOException {
            //given
            int existingIdWithIcon = 1;
            String iconPath = "achievements/NrOfPosts-1.png";
            Achievement achievement = Achievement.builder()
                    .id(existingIdWithIcon)
                    .iconPath(iconPath)
                    .build();
            byte[] expectedIcon = TestUtils.getIcon(iconPath);
            doReturn(Optional.of(achievement)).when(achievementRepository).findById(existingIdWithIcon);

            //when
            byte[] actualIcon = iconService.getAchievementIcon(existingIdWithIcon);

            //then
            assertThat(actualIcon, allOf(
                    notNullValue(),
                    instanceOf(byte[].class),
                    equalTo(expectedIcon)
            ));
        }

        @Test
        void getAchievementIcon_PathDoesNotExist_ReturnDefaultIcon() throws IOException {
            //given
            Achievement achievement = Achievement.builder()
                    .id(4)
                    .iconPath("achievements/DoesNotExist.png")
                    .build();
            byte[] defaultAchievementIcon = TestUtils.getIcon(defaultAchievementIconPath);

            //when
            byte[] actualIcon = iconService.getAchievementIcon(achievement);

            //then
            assertThat(actualIcon, allOf(
                    notNullValue(),
                    instanceOf(byte[].class),
                    equalTo(defaultAchievementIcon)
            ));
        }

        @Test
        void getAchievementIcon_PathExists_ReturnDefaultIcon() throws IOException {
            //given
            int existingIdWithIcon = 1;
            String iconPath = "achievements/NrOfPosts-1.png";
            Achievement achievement = Achievement.builder()
                    .id(existingIdWithIcon)
                    .iconPath(iconPath)
                    .build();
            byte[] expectedIcon = TestUtils.getIcon(iconPath);
            doReturn(Optional.of(achievement)).when(achievementRepository).findById(existingIdWithIcon);

            //when
            byte[] actualIcon = iconService.getAchievementIcon(existingIdWithIcon);

            //then
            assertThat(actualIcon, allOf(
                    notNullValue(),
                    instanceOf(byte[].class),
                    equalTo(expectedIcon)
            ));
        }
    }
}