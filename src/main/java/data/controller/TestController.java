package data.controller;

import data.dto.ApiResponse;
import data.dto.MessageTokenDto;
import data.service.UserService;
import data.util.JwtProvider;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final JwtProvider jwtProvider;
    private final UserService userService;

    @GetMapping("/test/token/{id}")
    @ApiOperation(value = "테스트용 토큰 발급", notes = "path variable로 id를 받아서 토큰을 발급합니다.")
    public ResponseEntity<ApiResponse<MessageTokenDto>> getTestToken(@PathVariable int id) {
        String token = jwtProvider.createToken(id);
        userService.findById(token);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created(new MessageTokenDto("테스트 토큰 생성", token, null)));
    }
}
