package data.controller;

import com.google.gson.Gson;
import data.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 사용자 정보, 서버토큰 모두 json으로 리턴
    @PostMapping(value = "/user/signin/kakao", produces = "application/json")
    // public String socialLogin(@RequestBody HashMap<String, String> params) {
    public  ResponseEntity<Map<String, Object>> socialLogin(@RequestBody HashMap<String, String> params) {

        String kakaoAccessToken = params.get("kakaoAccessToken");
        Map<String, Object> result = userService.socialLogin(kakaoAccessToken);

        return ResponseEntity.ok(result);
    }

}
