//package com.task.movieApp.controller;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.task.movieApp.dao.UserDao;
//import com.task.movieApp.model.User;
//
//
//@Controller
//public class UserController {
//	
//	@Autowired
//	private Environment env;
//	
//	@Autowired
//	private UserDao dao;
//	
//	@Autowired
//	HttpServletRequest request;
//	@GetMapping("/user/registration")
//	   public ModelAndView registration() {
//		   
//		   ModelAndView mv = new ModelAndView();
//		   mv.setViewName("user/registration");
//		   
//		   HomeController hc = new HomeController();
//		   hc.setAppName(mv, env);
//		   
//		   return mv;
//	   }
//	@PostMapping("/user/registration")
//	 public ModelAndView registration(User user) {
//
//		 
//		 ModelAndView mv = new ModelAndView();
//		 mv.setViewName("home/index");
//		 
//		 HomeController hc = new HomeController();
//		 hc.setAppName(mv, env);
//		 
//		 // Check to user is already registered or not
//		 
//		 var it = dao.findAll();
//		 
//		 for(User dbUser : it) {
//			 if(dbUser.getEmailAddress().equals(user.getEmailAddress())) {
//				 mv.addObject("message", "User already exists");
//				 return mv;
//			 }
//		 }
//		 
//		 
//		 // Password Encryption
//		 BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
//		 String newPassword = bCrypt.encode(user.getPassword());
//		 user.setPassword(newPassword);
//		 
//		 dao.save(user);
//		 mv.addObject("message", "User registered successfully");
//		 
//		 return mv;
//		 
//	 }
//	
//	@GetMapping("/login")
//	public ModelAndView login() {
//		ModelAndView mv = new ModelAndView();
//		mv.setViewName("user/login");
//		
//		HomeController hc = new HomeController();
//		hc.setAppName(mv, env);
//		
//		return mv;
//	}
//	
//	
//	
//
//	
//}
