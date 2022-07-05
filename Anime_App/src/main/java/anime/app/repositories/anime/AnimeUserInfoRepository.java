package anime.app.repositories.anime;

import anime.app.entities.database.anime.AnimeUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimeUserInfoRepository extends JpaRepository<AnimeUserInfo, AnimeUserInfo.AnimeUserInfoId> {
    List<AnimeUserInfo> findTop5ByReviewIsNotNullAndId_Anime_IdIsOrderByReview_ModificationDesc(int animeId);
}
