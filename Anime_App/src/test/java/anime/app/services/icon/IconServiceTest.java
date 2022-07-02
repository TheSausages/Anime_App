package anime.app.services.icon;

import anime.app.entities.database.user.Achievement;
import anime.app.entities.database.user.AchievementIdEnum;
import anime.app.exceptions.exceptions.NotFoundException;
import anime.app.repositories.user.AchievementRepository;
import anime.app.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class IconServiceTest {

    @Mock
    AchievementRepository achievementRepository;

    @InjectMocks
    IconService iconService;


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
        int existingIdNoIcon = AchievementIdEnum.NrOfReviewsAchievement_1.id;
        Achievement achievement = Achievement.builder()
                .id(existingIdNoIcon)
                .iconPath("achievements/DoesNotExist.png")
                .build();
        byte[] defaultAchievementIcon = TestUtils.getIcon("achievements/Default.png");
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
        int existingIdWithIcon = AchievementIdEnum.NrOfPostsAchievement_1.id;
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
                .id(AchievementIdEnum.NrOfReviewsAchievement_1.id)
                .iconPath("achievements/DoesNotExist.png")
                .build();
        byte[] defaultAchievementIcon = TestUtils.getIcon("achievements/Default.png");

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
        int existingIdWithIcon = AchievementIdEnum.NrOfPostsAchievement_1.id;
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