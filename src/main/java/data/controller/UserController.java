package data.controller;

import data.dto.JwtToken;
import data.dto.MessageTokenDto;
import lombok.extern.slf4j.Slf4j;
import data.dto.UserDto;
import data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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

    // 리프레시 토큰 검증
    @PostMapping("/reToken")
    public ResponseEntity<MessageTokenDto> checkRefreshToken(@RequestHeader("Authorization") String token) { return userService.checkRefreshToken(token); }
}
