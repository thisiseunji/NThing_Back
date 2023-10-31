package data.controller;

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

import java.util.List;

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
    public ResponseEntity<UserDto> findById(@RequestHeader("Authorization") String token) {
        UserDto user = userService.findById(token);
        if (user == null) {
            throw new UserNotFoundException("User not found for the provided token.");
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/like")
    public ResponseEntity<List<PurchaseDto.Summary>> findLikedPurchasesByUserId(
            @RequestHeader("Authorization") String token
    ) {
        int userId = jwtProvider.parseJwt(token);
        List<PurchaseDto.Summary> likedPurchases = likeService.findLikedPurchasesByUserId(userId);
        return ResponseEntity.ok(likedPurchases);
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteUser(@RequestHeader("Authorization") String token) {
        userService.deleteUser(token);
        return ResponseEntity.noContent().build();
    }

    // 리프레시 토큰 검증
    @PostMapping("/reToken")
    public ResponseEntity<MessageTokenDto> checkRefreshToken(@RequestHeader("Authorization") String token) {
        return userService.checkRefreshToken(token);
    }
}
