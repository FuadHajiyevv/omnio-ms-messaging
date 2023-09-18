package az.atl.msmessaging.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.security.Key;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {
    @Value("${jwt.token.secret}")
    private String SECRET_KEY;

    @Value("${jwt.token.issuer}")
    private String issuer;

    private final MessageSource messageSource;

    public JwtService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public <T> T extractClaim(String jwt, Function<Claims, T> claimsTFunction) {
        Claims getAll = getAllClaimsFromJwt(jwt);
        return claimsTFunction.apply(getAll);
    }

    public String getUsernameFromJwt(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }


    private Claims getAllClaimsFromJwt(String jwt) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private Key getSecretKey() {
        byte[] byteOfSecretKey = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(byteOfSecretKey);
    }


    public boolean isJwtExpired(String jwt) {
        return extractClaim(jwt, Claims::getExpiration).before(new Date());
    }

    public boolean checkRole(String jwt, UserDetails userDetails) throws AccessDeniedException {
        List<LinkedHashMap<String, String>> roles = (List<LinkedHashMap<String, String>>) getAllClaimsFromJwt(jwt).get("roles");
        List<SimpleGrantedAuthority> jwtRoles = roles.stream()
                .map(authorityMap -> authorityMap.get("authority"))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        List<? extends GrantedAuthority> jwtRole = jwtRoles.stream().filter(role -> role.getAuthority().startsWith("ROLE_")).toList();
        List<? extends GrantedAuthority> dbRole = userDetails.getAuthorities().stream().filter(role -> role.getAuthority().startsWith("ROLE_")).toList();
        if (jwtRole.equals(dbRole)) {
            int a = jwtRoles.stream().filter(role -> !role.getAuthority().startsWith("ROLE_")).mapToInt(authority -> authority.getAuthority().chars().sum()).sum();
            int b = userDetails.getAuthorities().stream().filter(role -> !role.getAuthority().startsWith("ROLE_")).mapToInt(authority -> authority.getAuthority().chars().sum()).sum();
            if (a == b) {
                return true;
            }
        }
        throw new AccessDeniedException(messageSource.getMessage("access_denied", null, LocaleContextHolder.getLocale()));
    }

    public boolean checkIssuer(String jwt) {
        return extractClaim(jwt, Claims::getIssuer).equals(issuer);
    }

    public boolean isValid(String jwt, UserDetails userDetails) {
        String usernameExtractFromJwt = extractClaim(jwt, Claims::getSubject);
        return usernameExtractFromJwt.equals(userDetails.getUsername()) && !isJwtExpired(jwt);
    }
}
