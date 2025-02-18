package org.example.filmtitlequiz.model;

public enum MovieGenre {

	ACTION(28),
	ADVENTURE(12),
	ANIMATION(16),
	BIOGRAPHY(36),
	COMEDY(35),
	CRIME(80),
	DOCUMENTARY(99),
	DRAMA(18),
	FAMILY(10751),
	FANTASY(14),
	FILM_NOIR(9648),
	HISTORY(36),
	HORROR(27),
	MUSIC(10402),
	MUSICAL(10402),
	MYSTERY(9648),
	ROMANCE(10749),
	SCI_FI(878),
	SPORT(10770),
	THRILLER(53),
	WAR(10752),
	WESTERN(37);

	private final int tmdbId;

	MovieGenre(int tmdbId) {
		this.tmdbId = tmdbId;
	}

	public int getTmdbId() {
		return tmdbId;
	}

	public static MovieGenre getGenre(int index) {
		return MovieGenre.values()[index];
	}
}

