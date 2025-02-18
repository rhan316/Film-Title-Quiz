package org.example.filmtitlequiz.controller;

import org.example.filmtitlequiz.service.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MovieController {
	private final QuizService quizService;

	public MovieController(QuizService quizService) {
		this.quizService = quizService;
	}

	@GetMapping("/movie")
	public String showRandomMovie(Model model) {
		List<String> titleCharList = quizService.puzzleTitle();
		String genre = quizService.currentGenre();

		model.addAttribute("genre", genre);
		model.addAttribute("movieTitle", titleCharList);

		return "movie";
	}
}
