package org.billing.api.controllers;


import org.billing.api.service.CommonsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ViewResolverConstroller {
    @Autowired
    private CommonsApiService commonsApiService;

    @GetMapping("/verify/{token}")
    public String verifyToken(@PathVariable String token){
        return commonsApiService.verifyToken(token);
    }
}
