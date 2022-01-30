package anime.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class AnimeAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(AnimeAppApplication.class, args);
	}
}
