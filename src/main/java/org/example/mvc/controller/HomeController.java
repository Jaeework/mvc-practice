package org.example.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeController implements Controller{

    @Override
    public String handlerRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "home.jsp";  // HomeController가 호출되면 home 화면을 리턴
    }
}
