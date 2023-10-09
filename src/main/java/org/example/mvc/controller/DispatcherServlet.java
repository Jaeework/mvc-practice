package org.example.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")    // 어떠한 경로를 입력하더라도 이 클래스에 접근
public class DispatcherServlet extends HttpServlet{ // 톰캣이 읽어올 수 있도로고 HttpServlet 상속

    private final static Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private RequestMappingHandler requestMappingHandler;

    @Override
    public void init() throws ServletException {
        requestMappingHandler = new RequestMappingHandler();
        requestMappingHandler.init();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("[DispatcherServlet] service started.");

        try {
            // handler mapping을 통해 요청 uri에 해당하는 handler를 찾음
            Controller handler = requestMappingHandler.findHandler(request.getRequestURI());

            // 해당 handler에 요청을 위임
            String viewName = handler.handlerRequest(request, response);

            // viewName를 이용해 requestDispatcher 객체를 얻어온 후 해당하는 view에 요청 전달
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewName);
            requestDispatcher.forward(request, response);

        } catch (Exception e) {
            logger.error("exception occurred : [{}]", e.getMessage(), e);
            throw new ServletException(e);
        }
    }
}
