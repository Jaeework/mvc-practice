package org.example.mvc;

import org.example.mvc.controller.Controller;
import org.example.mvc.controller.RequestMethod;
import org.example.mvc.view.JspViewResolver;
import org.example.mvc.view.ModelAndView;
import org.example.mvc.view.View;
import org.example.mvc.view.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet("/")    // 어떠한 경로를 입력하더라도 이 클래스에 접근
public class DispatcherServlet extends HttpServlet{ // 톰캣이 읽어올 수 있도로고 HttpServlet 상속

    private final static Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private RequestMappingHandler requestMappingHandler;

    private List<HandlerAdapter> handlerAdapters;

    private List<ViewResolver> viewResolvers;

    @Override
    public void init() throws ServletException {
        requestMappingHandler = new RequestMappingHandler();
        requestMappingHandler.init();

        handlerAdapters = List.of(new SimpleControllerHandlerAdapter());
        viewResolvers = Collections.singletonList(new JspViewResolver());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("[DispatcherServlet] service started.");

        try {
            // handler mapping을 통해 요청 uri에 해당하는 handler를 찾음
            Controller handler = requestMappingHandler.findHandler(new HandlerKey(RequestMethod.valueOf(request.getMethod()),request.getRequestURI()));

            // 해당 handler에 요청을 위임
            // redirect vs forward
            // String viewName = handler.handlerRequest(request, response);

            HandlerAdapter handlerAdapter = handlerAdapters.stream()
                    .filter(ha -> ha.supports(handler))
                    .findFirst()
                    .orElseThrow(() -> new ServletException("No adapter for handler [" + handler + "]"));

            ModelAndView modelAndView = handlerAdapter.handle(request, response, handler);

            // 해당하는 view에 요청 전달
            for(ViewResolver viewResolver : viewResolvers) {
                View view = viewResolver.resoveView(modelAndView.getViewName());
                view.render(modelAndView.getModel(), request, response);
            }


        } catch (Exception e) {
            logger.error("exception occurred : [{}]", e.getMessage(), e);
            throw new ServletException(e);
        }
    }
}
