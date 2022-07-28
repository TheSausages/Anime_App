package anime.app.repositories.anime;

import anime.app.entities.database.anime.Anime;
import anime.app.openapi.model.AnilistMediaTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Integer> {
    /**
     * Check if te database holds the current Anilist Data for an Anime. The checked values include:
     * <ul>
     *    <li>The Anilist ID</li>
     *    <li>Average Episode Length</li>
     *    <li>The first available title, aquired from {@link anime.app.services.anilist.AnilistService#getFirstAvailableTitle(AnilistMediaTitle)}</li>
     * </ul>
     * @param anime Object containing current data from Anilist
     * @return True if the contained data is current, False otherwise
     */
    @Query("select case when count(anime)> 0 then true else false end from Anime anime where anime.id = :#{#checking.id} and " +
            "anime.title = :#{#checking.title} and anime.averageEpisodeLength = :#{#checking.averageEpisodeLength}")
    boolean existsByAnilistData(@Param("checking") Anime anime);
}
