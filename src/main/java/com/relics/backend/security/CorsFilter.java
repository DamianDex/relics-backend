package com.relics.backend.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class CorsFilter extends OncePerRequestFilter {
	static final String ORIGIN = "Origin";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println(request.getHeader(ORIGIN));
		System.out.println(request.getMethod());
		if (request.getHeader(ORIGIN).equals("http://localhost:3000")) {
			response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
			response.setHeader("Access-Control-Allow-Credentials", "true");
			response.setHeader("Access-Control-Expose-Headers", "Authorization, Uid");
			response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
		}
		if (request.getMethod().equals("OPTIONS")) {
			try {
				response.getWriter().print("OK");
				response.getWriter().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			filterChain.doFilter(request, response);
		}
	}
}
