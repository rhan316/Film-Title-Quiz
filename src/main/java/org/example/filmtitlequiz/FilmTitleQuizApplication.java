package org.example.filmtitlequiz;

import org.example.filmtitlequiz.service.MovieService;
import org.example.filmtitlequiz.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class FilmTitleQuizApplication implements ApplicationRunner {
	private final Logger log = LoggerFactory.getLogger(FilmTitleQuizApplication.class);
	private final QuizService quizService;

	public FilmTitleQuizApplication(QuizService quizService) {
		this.quizService = quizService;
	}

	public static void main(String[] args) {
		SpringApplication.run(FilmTitleQuizApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Map<String, Object> quizData = quizService.quizData();

		log.info("puzzleTitle: {}", quizData.get("puzzleTitle"));
		log.info("title: {}", quizData.get("title"));
		log.info("posterUrl: {}", quizData.get("posterUrl"));
		log.info("backdropUrl: {}", quizData.get("backdropUrl"));
	}
}
