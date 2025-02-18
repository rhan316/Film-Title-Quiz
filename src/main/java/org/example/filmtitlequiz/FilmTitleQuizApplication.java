package org.example.filmtitlequiz;

import org.example.filmtitlequiz.service.QuizService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FilmTitleQuizApplication implements ApplicationRunner {
	private final QuizService quizService;

	public FilmTitleQuizApplication(QuizService quizService) {
		this.quizService = quizService;
	}

	public static void main(String[] args) {
		SpringApplication.run(FilmTitleQuizApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println(quizService.puzzleTitle());
	}
}
