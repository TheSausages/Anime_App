package anime.app.controllers;

import anime.app.openapi.api.AnimeApi;
import anime.app.openapi.model.AnilistPageDTO;
import anime.app.openapi.model.AnimeQueryDTO;
import anime.app.openapi.model.DetailedAnimeDTO;
import anime.app.openapi.model.LocalUserAnimeInformationDTO;
import anime.app.services.anilist.AnilistServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

/**
 * Controller for all endpoints connected to anime.
 */
@RestControllerWithBasePath
public class AnimeController implements AnimeApi {
	private final AnilistServiceInterface anilistService;

	@Autowired
	AnimeController(AnilistServiceInterface anilistService) {
		this.anilistService = anilistService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<DetailedAnimeDTO> getAnimeByAnimeId(Long animeId) {
		return AnimeApi.super.getAnimeByAnimeId(animeId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<AnilistPageDTO> getTopAnimeOfAllTime(Long pageNumber) {
		return AnimeApi.super.getTopAnimeOfAllTime(pageNumber);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<AnilistPageDTO> getTopAiringAnime(Long pageNumber) {
		return ResponseEntity.ok(anilistService.getTopAiring(pageNumber));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<AnilistPageDTO> getTopAnimeMovies(Long pageNumber) {
		return AnimeApi.super.getTopAnimeMovies(pageNumber);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<AnilistPageDTO> searchForAnimeUsingQuery(Long pageNumber, AnimeQueryDTO animeQueryDTO) {
		return AnimeApi.super.searchForAnimeUsingQuery(pageNumber, animeQueryDTO);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<LocalUserAnimeInformationDTO> updateUserAnimeInformation(LocalUserAnimeInformationDTO localUserAnimeInformationDTO) {
		return AnimeApi.super.updateUserAnimeInformation(localUserAnimeInformationDTO);
	}
}
