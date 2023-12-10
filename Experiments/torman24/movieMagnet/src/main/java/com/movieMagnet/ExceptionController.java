package com.movieMagnet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

public class ExceptionController {

    @RequestMapping(method = RequestMethod.GET, path = "/oopsGET")
    public String getException() {
        throw new RuntimeException("Testing - GET");
    }

    @RequestMapping(method = RequestMethod.POST, path = "/oopsPOST")
    public String postException() {
        throw new RuntimeException("Testing - POST");
    }
}
