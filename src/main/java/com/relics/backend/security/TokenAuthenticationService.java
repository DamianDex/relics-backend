package com.relics.backend.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.relics.backend.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

class TokenAuthenticationService {
	static final long EXPIRATIONTIME = 900_000; // 15 minutes
	static final String SECRET = "TODO set uuid";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";
	static final ObjectMapper mapper = new ObjectMapper();

	static void addAuthentication(HttpServletResponse res, User user,
			Collection<? extends GrantedAuthority> roles) throws JsonProcessingException {
		String userJson = mapper.writeValueAsString(user);
		Claims claims = Jwts.claims().setSubject(userJson);
		claims.put("scopes", roles);
		
		String JWT = Jwts.builder()
				.setClaims(claims)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, SECRET)
			.compact();
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
	}

	@SuppressWarnings("unchecked")
	static Authentication getAuthentication(HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
		String token = request.getHeader(HEADER_STRING);
		if (token != null) {
			Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
			String userJson = claims.getSubject();
			User user = null;
			try {
				user = mapper.readValue(userJson, User.class);

			} catch (Exception e) {
				e.printStackTrace();
			}
			ArrayList<LinkedHashMap<String, String>> scope = (ArrayList<LinkedHashMap<String, String>>)claims.get("scopes");
			List<GrantedAuthority> grantedAuthorities = scope
					.stream()
					.map(lh -> lh.get("authority"))
					.map(auth -> new SimpleGrantedAuthority(auth))
					.collect(Collectors.toList());

			return user != null ? new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities) : null;
		}
		return null;
	}
}