package org.example.filmtitlequiz.service;

import org.example.filmtitlequiz.model.Movie;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizService {
	private final MovieService movieService;
	private final BackdropsService backdropsService;

	public QuizService(MovieService movieService, BackdropsService backdropsService) {
		this.movieService = movieService;
		this.backdropsService = backdropsService;
	}

	public Map<String, Object> quizData() {

		var movie = movieService.getRandomMovie().block();
		if (movie == null) throw new NullPointerException("Movie is null in QuizService.quizData()");

		return Map.of(
				"title", movie.title(),
				"backdropUrl", getBackdropUrl(movie),
				"posterUrl", PosterService.buildImageUrl(movie.poster_path()),
				"puzzleTitle", puzzleTitle(movie));
	}

	private List<String> puzzleTitle(Movie movie) {
		return movie.title()
				.replaceAll("\\s{2,}", " ")
				.chars()
				.mapToObj(c -> format((char) c))
				.toList();
	}

	private String getBackdropUrl(Movie movie) {
		return Objects.requireNonNull(backdropsService.randomBackdropUrl(movie)).block();
	}

	private String currentGenre() {
		return movieService.currentGenre();
	}

	private static String format(char ch) {
		return String.valueOf(removePunctuationMarks(ch));
	}

	private static char removePunctuationMarks(char ch) {
		return switch (ch) {
			case ':', '!', '?', '"', '-' -> '\0';
			default -> ch;
		};
	}
}
