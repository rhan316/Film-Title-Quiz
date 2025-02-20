package org.example.filmtitlequiz.service;

import org.springframework.stereotype.Service;

@Service
public class PosterService {
	private static final String TMDB_IMAGE_URL = "https://image.tmdb.org/t/p/w500";

	private PosterService() { }

	public static String buildImageUrl(String posterPath) {
		return (posterPath == null || posterPath.isEmpty())
				? "No poster image found!" : TMDB_IMAGE_URL + posterPath;
	}
}
