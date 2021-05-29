package com.task.movieApp.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name="Movie")
public class Movie {
	@Id
	@GeneratedValue
	private int id;
	
	private int movieId;
	
	private String name;
	
	private String poster;
	
	@Column(length = 100000)
	private String movieDescription;
	
	private String genres;
	
	@Column(name="createdAt", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createAt;
	
	@Column(name="updatedat", nullable = false, updatable = true)
	@UpdateTimestamp
	private LocalDateTime updateAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getMovieDescription() {
		return movieDescription;
	}

	public void setMovieDescription(String movieDescription) {
		this.movieDescription = movieDescription;
	}

	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}
	
//	public static Movie fromJson(JSONObject jsonObject) {
//		Movie m = new Movie();
////		JSONObject jsonObject2 =(JSONObject)jsonObject;
////		m.movieDescription = jsonObject2.get
//		try {
//			m.name = jsonObject.getString("title");
//			m.poster = jsonObject.getString("poster_path");
////			m.genres = jsonObject.getString("genres");
//			m.movieDescription = jsonObject.getString("overview");
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return m;
//	}
//	public static Movie fromStringToMovie(Movie movie, String string) {
//		ObjectMapper mapper = new ObjectMapper();
//		try {
//		JsonNode root = mapper.readTree(string);
//		JsonNode title = root.path("title");
//		JsonNode poster = root.path("poster_path");
//		//https://www.themoviedb.org/t/p/w1280
//		JsonNode overview = root.path("overview");
//		
//		JsonNode genres = root.path("genres");
//		
////		JsonNode title = root.path("title");
//		System.out.println("result "+title.asText() + poster+ genres+ overview);
//		movie.setName(title.asText());
//		movie.setPoster("https://www.themoviedb.org/t/p/w1280"+poster.asText());
//		movie.setMovieDescription(overview.asText());
//		}catch (JsonMappingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		 return movie;
//	}
//	public static Movie fromString(String string) {
//		Movie m = new Movie();
////		JSONObject jsonObject2 =(JSONObject)jsonObject;
////		m.movieDescription = jsonObject2.get
//		try {
//			string.
//			m.name = string.getString("title");
//			m.poster = jsonObject.getString("poster_path");
////			m.genres = jsonObject.getString("genres");
//			m.movieDescription = jsonObject.getString("overview");
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return m;
//	}
	
	
	

}
