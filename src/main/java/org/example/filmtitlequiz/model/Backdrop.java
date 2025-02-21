package org.example.filmtitlequiz.model;

public record Backdrop(

		String file_path,
		float  aspect_ratio,
		int    width,
		int    height,
		String iso_639_1,
		double vote_average,
		int vote_count
)
{ }
