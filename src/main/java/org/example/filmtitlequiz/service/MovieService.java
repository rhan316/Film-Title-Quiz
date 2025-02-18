package org.example.filmtitlequiz.service;

import org.example.filmtitlequiz.config.TmdbConfig;
import org.example.filmtitlequiz.model.Movie;
import org.example.filmtitlequiz.model.MovieGenre;
import org.example.filmtitlequiz.model.MovieResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class MovieService {

	private final TmdbConfig tmdbConfig;
	private final WebClient webClient;
	private MovieGenre currentGenre;

	public MovieService(WebClient.Builder webClientBuilder,  TmdbConfig tmdbConfig) {
		this.webClient = webClientBuilder
				.baseUrl(tmdbConfig.getUrl())
				.build();
		this.tmdbConfig = tmdbConfig;
	}

	public Mono<String> getRandomMovieTitle() {
		currentGenre = getRandomMovieGenre();

		return webClient
				.get()
				.uri(createURI(currentGenre.getTmdbId()))
				.retrieve()
				.bodyToMono(MovieResponse.class)
				.flatMap(response -> {

					List<Movie> filteredMovies = getFilteredMovies(response);

					if (!filteredMovies.isEmpty()) {
						return Mono.just(filteredMovies
										.get(getRandomNumber(filteredMovies.size() - 1))
										.getTitle());
					}

					return getRandomMovieTitle();
				});

	}

	public String currentGenre() {
		return currentGenre.name().toUpperCase();
	}

	private static MovieGenre getRandomMovieGenre() {
		return MovieGenre.getGenre(getRandomNumber(MovieGenre.values().length - 1));
	}

	private String createURI(int genreId) {
		return UriComponentsBuilder
				.fromUriString(tmdbConfig.getUrl() + "/discover/movie")
				.queryParam("api_key", tmdbConfig.getApiKey())
				.queryParam("language", "en-US")
				.queryParam("with_genres", genreId)
				.queryParam("page", getRandomNumber(10) + 1)
				.toUriString();

	}

	private static List<Movie> getFilteredMovies(MovieResponse response) {
		if (response.getResults() == null) return List.of();

		return response
				.getResults()
				.stream()
				.filter(movie -> countWords(movie.getTitle()) <= 4)
				.toList();
	}

	private static int countWords(String title) {
		return title.split("\\s+").length;
	}

	private static int getRandomNumber(int max) {
		return ThreadLocalRandom.current().nextInt(0, max + 1);
	}
}
