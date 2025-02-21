package org.example.filmtitlequiz.service;

import org.example.filmtitlequiz.config.TmdbConfig;
import org.example.filmtitlequiz.model.BackdropResponse;
import org.example.filmtitlequiz.model.Movie;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

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

	public List<String> backdropData(Movie movie) {
		String placeholder = "https://via.placeholder.com/1280x720?text=No+backdrop";
		List<String> backdropList = getRandomMovieBackdrops(movie).block();

		return shuffle(backdropList, placeholder);
	}

	private List<String> shuffle(List<String> list, String placeholder) {
		if (list == null || list.isEmpty()) return List.of(placeholder, placeholder, placeholder);

		List<String> mutableBackdropList = new ArrayList<>(list);
		Collections.shuffle(mutableBackdropList);

		return IntStream.rangeClosed(0, 3)
				.mapToObj(i -> Optional.ofNullable(i < mutableBackdropList.size()
						? mutableBackdropList.get(i) : placeholder)
						.orElseThrow())
				.toList();
	}

	private Mono<String> randomBackdropUrl(Movie movie) {
		return getRandomMovieBackdrops(movie)
				.map(backdrops -> {
					if (backdrops.isEmpty()) return "https://via.placeholder.com/800x450?text=No+backdrop";
					int index = ThreadLocalRandom.current().nextInt(0, backdrops.size() - 1);

					return backdrops.get(index);
				});
	}

	private Mono<List<String>> getRandomMovieBackdrops(Movie movie) {
		return webClient
				.get()
				.uri("/movie/{movieId}/images?api_key={apiKey}", movie.id(), tmdbConfig.getApiKey())
				.retrieve()
				.bodyToMono(BackdropResponse.class)
				.map(response -> response.backdrops()
						.stream()
						.map(b -> "https://image.tmdb.org/t/p/w1280" + b.file_path())
						.toList()
				);
	}
}

