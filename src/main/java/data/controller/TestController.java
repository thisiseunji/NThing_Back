package data.controller;

import data.util.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private JwtProvider jwtProvider;

    @GetMapping("/token")
    public String getTestToken() {
        return jwtProvider.createToken(1);
    }
}
