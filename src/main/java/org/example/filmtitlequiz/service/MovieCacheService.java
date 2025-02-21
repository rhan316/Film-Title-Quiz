package org.example.filmtitlequiz.service;

import org.example.filmtitlequiz.model.Movie;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class MovieCacheService {
	private final RedisTemplate<String, Object> redisTemplate;
	private static final long CACHE_TTL_MINUTES = 30;

	public MovieCacheService(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	// Sprawdza, czy film znajduje się w cache
	public Movie getCacheMovie(long movieId) {
		return (Movie) redisTemplate.opsForValue().get("movie:" +  movieId);
	}

	// Zapisuje film do cache
	public void cacheMovie(Movie movie) {
		redisTemplate.opsForValue().set("movie:" + movie.id(), movie, CACHE_TTL_MINUTES, TimeUnit.MINUTES);
		redisTemplate.opsForList().leftPush("guessedMovie", movie.id());
	}

	// Pobiera backdropy dla filmu z cache
	public List<String> getCachedBackdrops(long movieId) {
		return List.of(
				(String) Objects.requireNonNull(redisTemplate.opsForValue().get("backdrop:" + movieId + ":0")),
				(String) Objects.requireNonNull(redisTemplate.opsForValue().get("backdrop:" + movieId + ":1")),
				(String) Objects.requireNonNull(redisTemplate.opsForValue().get("backdrop:" + movieId + ":2"))
		);
	}

	// Zapisuje backdropy w cache
	public void cacheBackdrops(long movieId, List<String> backdrops) {
		for (int i = 0; i < backdrops.size(); i++) {
			redisTemplate.opsForValue().set(
					"backdrop:" + movieId + ":" + i, backdrops.get(i), CACHE_TTL_MINUTES, TimeUnit.MINUTES);
		}
	}
}
