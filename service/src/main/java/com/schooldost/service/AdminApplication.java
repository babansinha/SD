package com.schooldost.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@RestController
@EnableRedisHttpSession
public class AdminApplication extends WebSecurityConfigurerAdapter {
	
	String message = "Hello World";
	String changes[];
	ArrayList<Map<String, Object>> ar = new ArrayList<>();

	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public Map<String, Object> user(Principal user) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("id", UUID.randomUUID().toString());
		map.put("content", message);
		return map;
	}

	@RequestMapping(value = "/changes", method = RequestMethod.GET)
	public ArrayList<Map<String, Object>> changes1() {
		return ar;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public Map<String, String> update1(@RequestBody Map<String,String> map, Principal principal) {
		System.out.println("map " + map);
		Map<String, Object> map1 = new LinkedHashMap<String, Object>();
		map1.put("content", map.get("content"));
		map1.put("timestamp", new Date());
		map1.put("user", principal.getName());
		ar.add(map1);
		map.put("id", UUID.randomUUID().toString());
		return map;
	}

	
	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// We need this to prevent the browser from popping up a dialog on a 401
		http.httpBasic().disable()
		.authorizeRequests().antMatchers(HttpMethod.POST, "/**").hasRole("WRITER").anyRequest().authenticated();
	}

}
