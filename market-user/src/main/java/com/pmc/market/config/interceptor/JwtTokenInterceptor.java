package com.pmc.market.config.interceptor;

import com.pmc.market.security.auth.AuthConstants;
import com.pmc.market.security.auth.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws IOException {
        final String header = request.getHeader(AuthConstants.AUTH_HEADER);

        log.info("prehandle"+header);
        if (header != null) {
            final String token = TokenUtils.getTokenFromHeader(header);
            if (TokenUtils.isValidToken(token)) {
                return true;
            }
        }
        throw new AuthorizationServiceException("권한이 없습니다 ");
//        response.sendRedirect("/error/unauthorized");
//        return false;
    }

}