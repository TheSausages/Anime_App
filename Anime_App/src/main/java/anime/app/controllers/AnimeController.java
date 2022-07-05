package anime.app.controllers;

import anime.app.openapi.api.AnimeApi;
import anime.app.openapi.model.AnilistPageDTO;
import anime.app.openapi.model.AnimeQueryDTO;
import anime.app.openapi.model.DetailedAnimeDTO;
import anime.app.openapi.model.LocalUserAnimeInformationDTO;
import anime.app.services.anilist.AnilistServiceInterface;
import anime.app.services.anime.animeuserinfo.AnimeUserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.validation.Validator;


/**
 * Controller for all endpoints connected to anime.
 */
@RestControllerWithBasePath
public class AnimeController implements AnimeApi {
	private final AnilistServiceInterface anilistService;
	private final AnimeUserServiceInterface animeUserService;
	private final Validator validator;

	@Autowired
	AnimeController(AnilistServiceInterface anilistService, Validator validator, AnimeUserServiceInterface animeUserService) {
		this.anilistService = anilistService;
		this.validator = validator;
		this.animeUserService = animeUserService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<DetailedAnimeDTO> getAnimeByAnimeId(Long animeId) {
		DetailedAnimeDTO animeInfo = anilistService.getAnimeById(animeId);
		validator.validate(animeInfo);

		return ResponseEntity.ok(animeInfo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<AnilistPageDTO> getTopAnimeOfAllTime(Long pageNumber) {
		AnilistPageDTO pageDTO = anilistService.getTopAnimeOfAllTime(pageNumber);
		validator.validate(pageDTO);

		return ResponseEntity.ok(pageDTO);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<AnilistPageDTO> getTopAiringAnime(Long pageNumber) {
		AnilistPageDTO pageDTO = anilistService.getTopAiring(pageNumber);
		validator.validate(pageDTO);

		return ResponseEntity.ok(pageDTO);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<AnilistPageDTO> getTopAnimeMovies(Long pageNumber) {
		AnilistPageDTO pageDTO = anilistService.getTopAnimeMovies(pageNumber);
		validator.validate(pageDTO);

		return ResponseEntity.ok(pageDTO);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<AnilistPageDTO> searchForAnimeUsingQuery(Long pageNumber, AnimeQueryDTO animeQueryDTO) {
		AnilistPageDTO pageDTO = anilistService.searchUsingQuery(animeQueryDTO, pageNumber);
		validator.validate(pageDTO);

		return ResponseEntity.ok(pageDTO);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<LocalUserAnimeInformationDTO> updateUserAnimeInformation(LocalUserAnimeInformationDTO localUserAnimeInformationDTO) {
		LocalUserAnimeInformationDTO updatedInfo = animeUserService.updateCurrentUserAnimeInfo(localUserAnimeInformationDTO);
		validator.validate(updatedInfo);

		return ResponseEntity.ok(updatedInfo);
	}
}
