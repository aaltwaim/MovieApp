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
import org.springframework.web.servlet.view.RedirectView;

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
	public ModelAndView addMovie(Movie movie) throws JsonMappingException, JsonProcessingException {
		System.out.println("heeeee");
		var favMovie = url+"/"+ movie.getMovieId()+api;
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		System.out.println("result"+entity);
		try {
		ResponseEntity<String> result = restTemplate.exchange(favMovie, HttpMethod.GET, entity, String.class);
//		System.out.println("result"+result.getStatusCodeValue());

		System.out.println("name "+ movie.getName());
		
		//deserialize using ObjectMapper
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode root = mapper.readTree(result.getBody());
		JsonNode title = root.path("title");
		JsonNode poster = root.path("poster_path");
	
		JsonNode overview = root.path("overview");
		String gen ="";
		
//		JsonNode genres = root.path("genres").get(0).path("name");
		for(int i=0; i< root.path("genres").size(); i++) {
			System.out.println("geners"+ gen);
			 gen =gen.concat(root.path("genres").get(i).path("name").asText() + ", ");
			System.out.println("geners"+ gen);
		}
		
		movie.setName(title.asText());
		movie.setPoster("https://www.themoviedb.org/t/p/w1280"+poster.asText());
		movie.setMovieDescription(overview.asText());
		movie.setGenres(gen);
		dao.save(movie);
		}catch(Exception e){
			System.out.println("error movie does not exist"+e);
			
		}
		


		
		return new ModelAndView("redirect:/movie/fav");
	}
	
	
	
	@GetMapping("/movie/fav")
	public 	ModelAndView getMovie() {
		var id = dao.findAll();
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("movie/fav");
		mv.addObject("movies", id);
		
		
		HomeController hc = new HomeController();
		hc.setAppName(mv, env);
		return mv;
		
	}
	
	@GetMapping("/movie/details")
	public ModelAndView movieDetails(@RequestParam int id) {
		System.out.println("name "+ id);
		
		Movie movie = dao.findById(id);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("movie/details");
		mv.addObject("movie", movie);
		
		HomeController hc = new HomeController();
		hc.setAppName(mv, env);
		
		return mv;
		
	}
	@GetMapping("/movie/remove")
	public ModelAndView removeFavMovie(@RequestParam int id) {
		dao.deleteById(id);
		return new ModelAndView("redirect:/movie/fav");
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
