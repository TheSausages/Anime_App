package anime.app.controllers;

import anime.app.openapi.api.AnimeApi;
import anime.app.openapi.model.DetailedAnimeDTO;
import anime.app.openapi.model.LocalDetailedAnimeReviewDTO;
import anime.app.openapi.model.LocalSimpleAnimeReviewDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class TestController implements AnimeApi {
	@Override
	public ResponseEntity<DetailedAnimeDTO> getAnimeAnimeId(Long animeId) {
		return AnimeApi.super.getAnimeAnimeId(animeId);
	}

	@GetMapping("/test")
	public LocalDetailedAnimeReviewDTO test() {
		return LocalDetailedAnimeReviewDTO.builder()
				.title("title")
				.animeId(1L)
				.build();
	}

	@PostMapping("/test")
	public LocalDetailedAnimeReviewDTO test2(@RequestBody @Valid LocalDetailedAnimeReviewDTO reviewDTO) {
		return reviewDTO;
	}

	@PutMapping("/test")
	public LocalSimpleAnimeReviewDTO test3(@RequestBody @Valid LocalSimpleAnimeReviewDTO reviewDTO) {
		return reviewDTO;
	}
}