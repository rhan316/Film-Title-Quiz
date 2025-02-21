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

		List<String> backdrops = backdropsService.backdropData(movie);

		return Map.of(
				"title", movie.title(),
				"genre", movieService.currentGenre(),
				"backdrop1", backdrops.get(0),
				"backdrop2", backdrops.get(1),
				"backdrop3", backdrops.get(2),
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
