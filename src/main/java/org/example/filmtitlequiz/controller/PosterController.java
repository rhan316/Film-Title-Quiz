package org.example.filmtitlequiz.controller;

import org.example.filmtitlequiz.service.PosterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posters")
public class PosterController {
	private final PosterService posterService;

	public PosterController(PosterService posterService) {
		this.posterService = posterService;
	}

}
