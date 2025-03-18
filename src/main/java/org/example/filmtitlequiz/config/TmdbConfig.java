package org.example.filmtitlequiz.config;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "tmdb")
@Validated
public class TmdbConfig {

	@NotBlank(message = "API key cannot be blank")
	private String apiKey;

	@NotBlank(message = "URL cannot be blank")
	@URL(message = "Invalid TMDB URL format")
	private String url;
}
