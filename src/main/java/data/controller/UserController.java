package data.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import data.dto.UserDto;
import data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 사용자 정보, 서버토큰 모두 json으로 리턴
    // response entity를 서비스에서 설정해야 할 듯
    @PostMapping(value = "/user/signin/kakao", produces = "application/json")
    public ResponseEntity<Map<String, Object>> socialLogin(@RequestBody HashMap<String, String> params) {

        //String kakaoAccessToken = params.get("kakaoAccessToken");
        //Map<String, Object> result = userService.socialLogin(params.get("kakaoAccessToken"));

        // ResponseEntity<Map<String, Object>> result = userService.socialLogin(params.get("kakaoAccessToken"));

        // return ResponseEntity.ok(result);
        return userService.socialLogin(params.get("kakaoAccessToken"));
    }
    @GetMapping("/users")
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/user")
    public UserDto findById(@RequestHeader("Authorization") String token) {
        return userService.findById(token);
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestHeader("Authorization") String token) {
        userService.deleteUser(token);
    }
}
