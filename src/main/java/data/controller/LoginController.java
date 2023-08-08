package data.controller;

import com.google.firebase.auth.FirebaseAuthException;
import data.dto.TokenDto;
import data.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login/google")
    public void googleLogin(@RequestBody TokenDto tokenDto) throws FirebaseAuthException {

        loginService.login(tokenDto);
    }
}
