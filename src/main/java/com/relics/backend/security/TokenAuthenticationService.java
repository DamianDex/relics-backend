package com.relics.backend.security;

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

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

class TokenAuthenticationService {
	static final long EXPIRATIONTIME = 900_000; // 15 minutes
	static final String SECRET = "TODO set uuid";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";

	static void addAuthentication(HttpServletResponse res, String username,
			Collection<? extends GrantedAuthority> roles) {
		for (GrantedAuthority ga : roles) {
			System.out.println(ga.getAuthority());
		}

		Claims claims = Jwts.claims().setSubject(username);
		claims.put("scopes", roles);
		
		String JWT = Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, SECRET)
			.compact();
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
	}

	@SuppressWarnings("unchecked")
	static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if (token != null) {
			Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
			String username = claims.getSubject();
			ArrayList<LinkedHashMap<String, String>> scope = (ArrayList<LinkedHashMap<String, String>>)claims.get("scopes");
			List<GrantedAuthority> grantedAuthorities = scope
					.stream()
					.map(lh -> lh.get("authority"))
					.map(auth -> new SimpleGrantedAuthority(auth))
					.collect(Collectors.toList());

			return username != null ? new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities) : null;
		}
		return null;
	}
}