package com.charitydonation;

import com.charitydonation.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
       // httpServletResponse.setStatus(HttpServletResponse.SC_OK);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        //za pomoca lambdy
        int counter = 0;
        for(GrantedAuthority elem : authorities){
            if("ROLE_ADMIN".equals(elem.toString())){
                counter ++;
            }
        }

        if(counter > 0){
            httpServletResponse.sendRedirect("/adminpanel");
        } else {
            httpServletResponse.sendRedirect("/");
        }

       // SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(httpServletRequest, httpServletResponse);
       // httpServletResponse.sendRedirect(savedRequest.getRedirectUrl().isEmpty()? "/" :savedRequest.getRedirectUrl());
    }

}
