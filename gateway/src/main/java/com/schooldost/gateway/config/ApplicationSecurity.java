package com.schooldost.gateway.config;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import com.schooldost.gateway.config.cors.CORSFilter;
import com.schooldost.gateway.service.auth.UserDetailsService;


@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

	@Resource
	private AuthenticationEntryPoint authenticationEntryPoint;
	
	@Resource
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	@Resource
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Resource
	private CORSFilter corsFilter;
	
	@Resource
	private LogoutSuccessHandler logoutSuccessHandler;
	
	@Resource
    private UserDetailsService userDetailsService;
	
	@Resource
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(userDetailsService);//.passwordEncoder(bCryptPasswordEncoder);
		
		/*builder.inMemoryAuthentication()
		.withUser("user").password("password").roles("USER")
	.and()
		.withUser("admin").password("admin").roles("USER", "ADMIN", "READER", "WRITER")
	.and()
		.withUser("audit").password("audit").roles("USER", "ADMIN", "READER");
		*/
		
	}
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.httpBasic().authenticationEntryPoint(authenticationEntryPoint)
		.and()
			.logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/")
		.and()
			.authorizeRequests()
				.antMatchers("/index.html", "/home.html", "/footer.html", "/header.html", "/login/login.html", "/login/registration.html", "/error/401.html", "/error/404.html", "/error/422.html", "/error/500.html", "/login", "/", "/resources/**").permitAll()
				.anyRequest().authenticated()
		.and()
			.csrf().csrfTokenRepository(csrfTokenRepository())
		.and()
			.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
		
		//http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
		//http.formLogin().successHandler(authenticationSuccessHandler);
		//http.formLogin().failureHandler(authenticationFailureHandler);
		
		// CORS
		//http.addFilterBefore(corsFilter, ChannelProcessingFilter.class);
		// @formatter:on
	}

	private Filter csrfHeaderFilter() {
		return new OncePerRequestFilter() {
			@Override
			protected void doFilterInternal(HttpServletRequest request,
					HttpServletResponse response, FilterChain filterChain)
					throws ServletException, IOException {
				CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
						.getName());
				if (csrf != null) {
					Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
					String token = csrf.getToken();
					if (cookie == null || token != null
							&& !token.equals(cookie.getValue())) {
						cookie = new Cookie("XSRF-TOKEN", token);
						cookie.setPath("/");
						response.addCookie(cookie);
					}
				}
				filterChain.doFilter(request, response);
			}
		};
	}

	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}
}
