package org.example.filmtitlequiz.model;

import java.util.List;

public record BackdropResponse(

		List<Backdrop> backdrops,
		List<Poster> posters
) { }
