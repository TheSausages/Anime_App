package anime.app.controllers;

import anime.app.anilist.request.query.parameters.media.MediaRank;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@GetMapping("/test")
	public String test() {
		return MediaRank.getMediaRankBuilder().id().build().getRank();
	}
}
