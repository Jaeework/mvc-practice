package org.example.mvc.controller;

import java.util.HashMap;
import java.util.Map;

public class RequestMappingHandler {
    // [key] /users [value] UserController  경로에 맞는 컨트롤러를 저장하기 위해...
    private Map<String, Controller> mappings = new HashMap<>();

    void init() {
        mappings.put("/", new HomeController());
    }

    public Controller findHandler(String uriPath) {
        return mappings.get(uriPath);
    }

}
