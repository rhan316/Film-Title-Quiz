package org.example.filmtitlequiz.controller;

import org.example.filmtitlequiz.service.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class MovieController {
	private final QuizService quizService;

	public MovieController(QuizService quizService) {
		this.quizService = quizService;
	}

	@GetMapping("/movie")
	public String showRandomMovie(Model model) {
		Map<String, Object> data = quizService.quizData();
		model.addAllAttributes(data);

		return "movie";
	}
}
