package com.task.movieApp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.movieApp.dao.MovieDao;
import com.task.movieApp.model.Movie;

import java.util.Arrays;

import org.json.*;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;


@RestController
public class MovieController {
	@Autowired 
	private Environment env;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static String url="https://api.themoviedb.org/3/movie";
	private static String api="?api_key=9e9da8e4e6818f27dda6f4446c173a65";
	private static String searchUrl="https://api.themoviedb.org/3/search/movie";
	
	
	//Showing the latest movie
	@GetMapping("/movie/latest")
	public ModelAndView getLatestMovie(){
		var latestUrl = url+"/latest"+api;
		Object movie = restTemplate.getForObject(latestUrl, Object.class);
		System.out.println(movie);
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("movie/latest");
		mv.addObject("movie", movie);
		HomeController hc = new HomeController();
		hc.setAppName(mv, env);
		
		return mv;
		
	}
	//https://api.themoviedb.org/3/search/movie?api_key=9e9da8e4e6818f27dda6f4446c173a65&query=Jack+Reacher
	
//	@GetMapping("/movie/search")
//	public ModelAndView searchMovie(@RequestParam String name) {
//		var movieUrl = searchUrl+api+"&query="+name;
//		Object[] movies = restTemplate.getForObject(movieUrl, Object[].class);
//		System.out.println(movies);
//		return null;
//	}
	@RequestMapping("/movie/search")
	public ModelAndView searchMovie(@RequestParam("freeText") String freeText) {
		var movieUrl = searchUrl+api+"&query="+freeText;
		Object movies = restTemplate.getForObject(movieUrl, Object.class);
	
		System.out.println("------>"+movies);
		System.out.println("------>"+movies.toString());
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("movie/search");
		mv.addObject("movies", movies);
		HomeController hc = new HomeController();
		hc.setAppName(mv, env);
		return mv;
	}
	
	@GetMapping("/movie/add")
	public ModelAndView addMovie() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("movie/add");
		
		HomeController hc = new HomeController();
		hc.setAppName(mv, env);
		return mv;
	}
	
	@Autowired
	private MovieDao dao;
	
	@PostMapping("/movie/add")
	public String addMovie(Movie movie) throws JsonMappingException, JsonProcessingException {
		var favMovie = url+"/"+ movie.getMovieId()+api;
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
//		ResponseEntity<String> result = new ResponseEntity<String>(favMovie, null);
//		try {
		ResponseEntity<String> result = restTemplate.exchange(favMovie, HttpMethod.GET, entity, String.class);
		
		System.out.println("result"+result.getBody());
//		}
//		catch (final HttpClientErrorException e) {
//		    System.out.println(e.getStatusCode());
//		    System.out.println(e.getResponseBodyAsString());
//		}
//		movie = Movie.fromStringToMovie(movie, result.getBody());
		System.out.println("name "+ movie.getName());
		ObjectMapper mapper = new ObjectMapper();
		
//		try {
		JsonNode root = mapper.readTree(result.getBody());
		JsonNode title = root.path("title");
		JsonNode poster = root.path("poster_path");
		//https://www.themoviedb.org/t/p/w1280
		JsonNode overview = root.path("overview");
		
		JsonNode genres = root.path("genres");
		
//		JsonNode title = root.path("title");
		System.out.println("result "+title.asText() + poster+ genres+ overview);
		movie.setName(title.asText());
		movie.setPoster("https://www.themoviedb.org/t/p/w1280"+poster.asText());
		movie.setMovieDescription(overview.asText());
//		}catch (JsonMappingException e){
//			e.printStackTrace();
//		}
//		catch (JsonProcessingException e){
//			e.printStackTrace();
//		}
//		assertThat()
//		assertThat(title.asText(), );
//		movie = restTemplate.getForObject(favMovie, Movie.class);
//		System.out.println("result "+movie.getName());
//		assertThat
//		movie = Movie.fromJson(result.getBody());
		
//		System.out.println("link"+favMovie);
//		Object movieDB =  restTemplate.getForObject(favMovie, Object.class);
//		System.out.println("this is Movie"+movieDB.toString());
//		movie = Movie.class.cast(movieDB);
		
		
//		ObjectMapper mapper = new ObjectMapper();
//		movie = mapper.convertValue(movieDB, Movie.class);
//		movie = Movie.class.cast(movieDB);
//		movie = Movie.fromJson(movieDB);
//		System.out.println("before"+newObj.toString());
//		System.out.println("this is Movie"+movieDB);
		System.out.println("after");
		
		
		dao.save(movie);
		return "";
		//return "redirect:/movie/fav";
	}
	
	
	
	@GetMapping("/movie/fav")
	public 	ModelAndView getMovie() {
		var id =dao.findAll();
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("movie/fav");
		mv.addObject("movies", id);
		
		
		HomeController hc = new HomeController();
		hc.setAppName(mv, env);
		return mv;
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
