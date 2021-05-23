package com.task.movieApp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;


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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
