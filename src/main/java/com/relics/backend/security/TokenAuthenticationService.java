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

import com.relics.backend.model.ApplicationUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

class TokenAuthenticationService {
	static final long EXPIRATIONTIME = 900_000; // 15 minutes
	static final String SECRET = "TODO set uuid";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";
	static final ObjectMapper mapper = new ObjectMapper();

	static void addAuthentication(HttpServletResponse res, ApplicationUser applicationUser,
			Collection<? extends GrantedAuthority> roles) throws IOException {
		String userJson = mapper.writeValueAsString(applicationUser);
		Claims claims = Jwts.claims().setSubject(userJson);
		claims.put("scopes", roles);
		
		String JWT = Jwts.builder()
				.setClaims(claims)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, SECRET)
			.compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
        res.getWriter().write(applicationUser.getType().getCode());
		res.getWriter().flush();
		res.getWriter().close();
	}

	@SuppressWarnings("unchecked")
	static Authentication getAuthentication(HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
		String token = request.getHeader(HEADER_STRING);
		if (token != null) {
			Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
			String userJson = claims.getSubject();
			ApplicationUser applicationUser = null;
			try {
				applicationUser = mapper.readValue(userJson, ApplicationUser.class);

			} catch (Exception e) {
				e.printStackTrace();
			}
			ArrayList<LinkedHashMap<String, String>> scope = (ArrayList<LinkedHashMap<String, String>>)claims.get("scopes");
			List<GrantedAuthority> grantedAuthorities = scope
					.stream()
					.map(lh -> lh.get("authority"))
					.map(auth -> new SimpleGrantedAuthority(auth))
					.collect(Collectors.toList());

			return applicationUser != null ? new UsernamePasswordAuthenticationToken(applicationUser, null, grantedAuthorities) : null;
		}
		return null;
	}

}