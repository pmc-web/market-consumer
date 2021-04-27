package com.pmc.market.error;

import com.pmc.market.entity.Status;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFailHandler implements AuthenticationFailureHandler {

    private String message;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof UsernameNotFoundException) {
            message = "아이디가 존재하지 않습니다.";
        } else if (exception instanceof LoginFailException) {
            if (Status.WAIT.getKey().equals(exception.getMessage())) {
                message = Status.WAIT.getTitle();
            } else if (Status.PAUSE.getKey().equals(exception.getMessage())) {
                message = Status.PAUSE.getTitle();
            } else if (Status.STOP.getKey().equals(exception.getMessage())){
                message = Status.STOP.getTitle();
            }
            message += " 입니다. 관리자에게 문의하세요.";
        } else {
            message = "로그인에 실패했습니다.";
        }
        request.setAttribute("message", "message");
        request.getRequestDispatcher("/loginFailed").forward(request, response);
    }
}
