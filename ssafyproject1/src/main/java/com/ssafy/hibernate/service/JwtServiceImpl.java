package com.ssafy.hibernate.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ssafy.hibernate.exception.UnauthorizedException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@PropertySource("classpath:application.properties")
public class JwtServiceImpl implements JwtService{

	@Value("${jwt.secret}")
	private String SECRET_KEY;
	/*
	@param userUid
	@return String
	*/
	@Override
	public String createJwt(String userUid) {
		Date now = new Date();

		return Jwts.builder()
				.setHeaderParam("type", "jwt")
				.claim("userUid", userUid)
				.setIssuedAt(now)
				.setExpiration(new Date(System.currentTimeMillis()+1*(1000*60*60*24*365)))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.compact();
	}

	/*
	 Header에서 X-ACCESS-TOKEN으로 jwt 추출
	 @return string
	 */

	@Override
	public String getJwt() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		return request.getHeader("X-ACCESS-TOKEN");
//		return request.getHeader("Authorization");

	}
	/*
    JWT에서 userIdx 추출
    @return int
    @throws BaseException
     */
	@Override
	public String getUserUid(String jwt) throws Exception{


		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser()
					.setSigningKey(SECRET_KEY).parseClaimsJws(jwt);
		}catch(Exception e) {
			throw new Exception("JWT가 유효하지 않음");
		}

		return claims.getBody().get("userUid").toString();
	}

	@Override
	public boolean isUsable(String jwt) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwt);
			return true;
		}catch(Exception e) {
			throw new UnauthorizedException();
		}
	}

}
