package anime.app.controllers;

import anime.app.openapi.api.AnimeApi;
import anime.app.openapi.model.AnilistPageDTO;
import anime.app.openapi.model.AnimeQueryDTO;
import anime.app.openapi.model.DetailedAnimeDTO;
import anime.app.openapi.model.LocalUserAnimeInformationDTO;
import org.springframework.http.ResponseEntity;

@RestControllerWithBasePath
public class AnimeController implements AnimeApi {
	@Override
	public ResponseEntity<DetailedAnimeDTO> getAnimeByAnimeId(Long animeId) {
		return AnimeApi.super.getAnimeByAnimeId(animeId);
	}

	@Override
	public ResponseEntity<AnilistPageDTO> getTopAnimeOfAllTime(Long pageNumber) {
		return AnimeApi.super.getTopAnimeOfAllTime(pageNumber);
	}

	@Override
	public ResponseEntity<AnilistPageDTO> getTopAiringAnime(Long pageNumber) {
		return AnimeApi.super.getTopAiringAnime(pageNumber);
	}

	@Override
	public ResponseEntity<AnilistPageDTO> getTopAnimeMovies(Long pageNumber) {
		return AnimeApi.super.getTopAnimeMovies(pageNumber);
	}

	@Override
	public ResponseEntity<AnilistPageDTO> searchForAnimeUsingQuery(Long pageNumber, AnimeQueryDTO animeQueryDTO) {
		return AnimeApi.super.searchForAnimeUsingQuery(pageNumber, animeQueryDTO);
	}

	@Override
	public ResponseEntity<LocalUserAnimeInformationDTO> updateUserAnimeInformation(LocalUserAnimeInformationDTO localUserAnimeInformationDTO) {
		return AnimeApi.super.updateUserAnimeInformation(localUserAnimeInformationDTO);
	}
}
