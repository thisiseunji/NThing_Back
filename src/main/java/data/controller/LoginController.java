package data.controller;

import com.google.firebase.auth.FirebaseAuthException;
import data.dto.ApiResult;
import data.dto.IdToken;
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
    public ResponseEntity<ApiResult<MessageTokenDto>> googleLogin(@RequestBody IdToken token) {
        try {
            return ResponseEntity.ok(ApiResult.ok(loginService.googleLogin(token)));
        } catch (FirebaseAuthException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login/kakao")
    public ResponseEntity<MessageTokenDto> kakaoLogin(@RequestBody IdToken token) {
        return loginService.kakaoLogin(token);
    }
}
