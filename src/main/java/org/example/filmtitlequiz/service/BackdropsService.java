package org.example.filmtitlequiz.service;

import org.example.filmtitlequiz.config.TmdbConfig;
import org.example.filmtitlequiz.model.BackdropResponse;
import org.example.filmtitlequiz.model.Movie;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class BackdropsService {
	private final WebClient webClient;
	private final TmdbConfig tmdbConfig;

	public BackdropsService( TmdbConfig tmdbConfig, WebClient.Builder webClientBuilder) {
		this.tmdbConfig = tmdbConfig;
		this.webClient = webClientBuilder
				.baseUrl(tmdbConfig.getUrl())
				.build();
	}

	public Mono<String> randomBackdropUrl(Movie movie) {
		return getRandomMovieBackdrops(movie.id())
				.map(backdrops -> {
					if (backdrops.isEmpty()) return "https://via.placeholder.com/800x450?text=No+backdrop";
					int index = ThreadLocalRandom.current().nextInt(0, backdrops.size());

					return backdrops.get(index);
				});
	}

	private Mono<List<String>> getRandomMovieBackdrops(long movieId) {
		return webClient
				.get()
				.uri("/movie/{movieId}/images?api_key={apiKey}", movieId, tmdbConfig.getApiKey())
				.retrieve()
				.bodyToMono(BackdropResponse.class)
				.map(response -> response.backdrops()
						.stream()
						.map(b -> "https://image.tmdb.org/t/p/w1280" + b.file_path())
						.toList()
				);
	}
}

