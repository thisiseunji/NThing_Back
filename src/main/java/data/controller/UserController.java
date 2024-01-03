package data.controller;

import data.constants.ErrorCode;
import data.dto.ApiResponseEntity;
import data.dto.MessageTokenDto;
import data.dto.PurchaseDto;
import data.dto.UserDto;
import data.exception.UserNotFoundException;
import data.service.UserService;
import data.service.LikeService;
import data.util.JwtProvider;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final LikeService likeService;
    private final JwtProvider jwtProvider;

    @Autowired
    public UserController(UserService userService, LikeService likeService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.likeService = likeService;
        this.jwtProvider = jwtProvider;
    }

    @GetMapping("")
    public ApiResponseEntity<UserDto> findById(@RequestHeader("Authorization") String token) {
        UserDto user = userService.findById(token);
        if (user == null) {
            throw new UserNotFoundException("User not found for the provided token.", ErrorCode.USER_NOT_FOUND);
        }
        return ApiResponseEntity.ok(user);
    }

    @GetMapping("/like")
    public ApiResponseEntity<List<PurchaseDto.Summary>> findLikedPurchasesByUserId(
            @RequestHeader("Authorization") String token,
            String search_keyword,
            String sort
    ) {
        int userId = jwtProvider.parseJwt(token);
        Map<String, Object> map = new HashMap<>();
        map.put("search_keyword", search_keyword);
        map.put("sort", sort);
        map.put("user_id", userId);
        List<PurchaseDto.Summary> likedPurchases = likeService.findLikedPurchasesByUserId(map);
        return ApiResponseEntity.ok(likedPurchases);
    }

    @DeleteMapping("")
    public ApiResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token) {
        userService.deleteUser(token);
        return ApiResponseEntity.noContent();
    }

    // 리프레시 토큰 검증
    @PostMapping("/reToken")
    public ResponseEntity<MessageTokenDto> checkRefreshToken(@RequestHeader("Authorization") String token) {
        return userService.checkRefreshToken(token);
    }
}
