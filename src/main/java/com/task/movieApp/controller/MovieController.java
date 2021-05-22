package com.task.movieApp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
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
}
