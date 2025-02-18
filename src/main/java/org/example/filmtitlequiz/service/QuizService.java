package org.example.filmtitlequiz.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class QuizService {
	private final MovieService movieService;

	public QuizService(MovieService movieService) {
		this.movieService = movieService;
	}

	public String title() {
		return Objects.requireNonNull(movieService
						.getRandomMovieTitle()
						.block());
	}

	public List<String> puzzleTitle() {
		return title()
				.replaceAll("\\s{2,}", " ")
				.chars()
				.mapToObj(c -> format((char) c))
				.collect(Collectors.toList());
	}

	public String currentGenre() {
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
