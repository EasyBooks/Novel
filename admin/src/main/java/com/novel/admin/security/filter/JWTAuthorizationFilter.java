/*
 * 作者：刘时明
 * 时间：2020/3/5-11:40
 * 作用：
 */
package com.novel.admin.security.filter;

import com.novel.admin.security.bean.JwtTokenUtils;
import com.novel.admin.security.bean.SecurityConstants;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter
{
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager)
    {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException
    {
        String authorization = request.getHeader(SecurityConstants.TOKEN_HEADER);
        // 如果请求头中没有token信息则直接放行了
        if (authorization == null || !authorization.startsWith(SecurityConstants.TOKEN_PREFIX))
        {
            chain.doFilter(request, response);
            return;
        }
        // 如果请求头中有token，则进行解析，并且设置授权信息
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(authorization));
        super.doFilterInternal(request, response, chain);
    }

    /**
     * 获取用户认证信息 Authentication
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String authorization)
    {
        String token = authorization.replace(SecurityConstants.TOKEN_PREFIX, "");
        try
        {
            String username = JwtTokenUtils.getUsernameByToken(token);
            log.info("checking username:" + username);
            // 通过 token 获取用户具有的角色
            List<SimpleGrantedAuthority> userRolesByToken = JwtTokenUtils.getUserRolesByToken(token);
            if (!StringUtils.isEmpty(username))
            {
                return new UsernamePasswordAuthenticationToken(username, null, userRolesByToken);
            }
        } catch (SignatureException | ExpiredJwtException | MalformedJwtException | IllegalArgumentException exception)
        {
            log.warn("Request to parse JWT with invalid signature . Detail : " + exception.getMessage());
        }
        return null;
    }
}
