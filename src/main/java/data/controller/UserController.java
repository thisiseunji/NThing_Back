package data.controller;

import data.dto.ApiResponse;
import data.dto.MessageTokenDto;
import data.dto.PurchaseDto;
import data.dto.UserDto;
import data.service.UserService;
import data.service.LikeService;
import data.util.JwtProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final LikeService likeService;
    private final JwtProvider jwtProvider;

    @GetMapping("")
    public ResponseEntity<ApiResponse<UserDto>> findById(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(ApiResponse.ok(userService.findById(token)));
    }

    @GetMapping("/like")
    public ResponseEntity<ApiResponse<List<PurchaseDto.Summary>>> findLikedPurchasesByUserId(
            @RequestHeader("Authorization") String token,
            String search_keyword,
            String sort
    ) {
        int userId = jwtProvider.parseJwt(token);
        Map<String, Object> map = new HashMap<>();
        map.put("search_keyword", search_keyword);
        map.put("sort", sort);
        map.put("user_id", userId);

        return ResponseEntity.ok(ApiResponse.ok(likeService.findLikedPurchasesByUserId(map)));
    }

    @DeleteMapping("")
    public ResponseEntity<ApiResponse<?>> deleteUser(@RequestHeader("Authorization") String token) {
        userService.deleteUser(token);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.noContent());
    }

    // 리프레시 토큰 검증
    @PostMapping("/reToken")
    public ResponseEntity<MessageTokenDto> checkRefreshToken(@RequestHeader("Authorization") String token) {
        return userService.checkRefreshToken(token);
    }
}
