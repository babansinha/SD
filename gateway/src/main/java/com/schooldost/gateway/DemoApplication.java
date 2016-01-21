package com.schooldost.gateway;
 
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.schooldost.gateway.mongo.model.User;
import com.schooldost.gateway.service.user.UserService;
 
 @SpringBootApplication
 //@RestController
 
 @Configuration
 @ComponentScan
 @EnableAutoConfiguration
 @Controller
 @EnableRedisHttpSession
 @EnableZuulProxy
 public class DemoApplication {
	 
	@Autowired
	private UserService service;
	
	public DemoApplication() {
		
	}
	
	@RequestMapping("/user")
	@ResponseBody
	public User user(Principal user) {
		for (User user1 : service.readAll()) {
			System.out.println("user : " + user1);
		}
		//return Collections.<String, Object> singletonMap("name", user.getName());
		return service.findByUsername(user.getName());
	}
	
	/*@RequestMapping("/user")
	@ResponseBody
	public Map<String, Object> user(Principal user) {
		for (User user1 : service.readAll()) {
			System.out.println("user : " + user1);
		}
		return Collections.<String, Object> singletonMap("name", user.getName());
	}*/

	@RequestMapping("/login")
	public String login() {
		return "forward:/";
	}

	@RequestMapping("/resource")
	public Map<String, Object> home() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", UUID.randomUUID().toString());
		model.put("content", "Hello World");
		return model;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
 }