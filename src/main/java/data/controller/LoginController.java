package data.controller;

import com.google.firebase.auth.FirebaseAuthException;
import data.dto.IdToken;
import data.dto.JwtToken;
import data.dto.MessageTokenDto;
import data.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    //autowired 추가함
    @Autowired
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login/google")
    public JwtToken googleLogin(@RequestBody IdToken token) throws FirebaseAuthException {
        return loginService.login(token);
    }

    @PostMapping("/login/kakao")
    // param : IdToken -> kakaoAccessToken
    // 추가로 '메시지' 객체가 필요함
    public ResponseEntity<MessageTokenDto> kakaoLogin(@RequestBody IdToken token) {
        return loginService.kakaoLogin(token);
    }

}
