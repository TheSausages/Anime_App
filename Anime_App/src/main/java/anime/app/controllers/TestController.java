package anime.app.controllers;

import anime.app.openapi.api.AnimeApi;
import anime.app.openapi.model.DetailedAnimeDTO;
import anime.app.openapi.model.LocalAnimeInformationDTO;
import anime.app.openapi.model.LocalDetailedAnimeReviewDTO;
import anime.app.openapi.model.LocalSimpleAnimeReviewDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class TestController implements AnimeApi {
	@Override
	public ResponseEntity<DetailedAnimeDTO> getAnimeAnimeId(Integer animeId) {
		return AnimeApi.super.getAnimeAnimeId(animeId);
	}

	@GetMapping("/test")
	public String test() {
		LocalAnimeInformationDTO dto = LocalAnimeInformationDTO.builder().animeId(1L).build();
		LocalDetailedAnimeReviewDTO reviewDTO = LocalDetailedAnimeReviewDTO.builder()
				.title("title")
				.animeId(1L)
				.build();
		return reviewDTO.toString();
	}

	@PostMapping("/test")
	public String test2(@RequestBody @Valid LocalDetailedAnimeReviewDTO reviewDTO) {
		return reviewDTO.toString();
	}

	@PutMapping("/test")
	public String test3(@RequestBody @Valid LocalSimpleAnimeReviewDTO reviewDTO) {
		return reviewDTO.toString();
	}
}