package data.service;

import data.dto.UserDto;
import data.jwt.TokenUtils;
import data.mapper.UserMapper;
import data.util.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserService {

    private final UserMapper userMapper;
    private final TokenUtils tokenUtils;
    private final JwtProvider jwtProvider;

    @Autowired
    public UserService(UserMapper userMapper, TokenUtils tokenUtils, JwtProvider jwtProvider) {
        this.userMapper = userMapper;
        this.tokenUtils = tokenUtils;
        this.jwtProvider = jwtProvider;
    }
    
    public ResponseEntity<Map<String, Object>> socialLogin(String kakaoAccessToken) {

        // HashMap<String, Object> result = new HashMap<>();
        Map<String, Object> result = new HashMap<>();

        // 대강 이런식으로 넣으면 됨. 아니면 객체 생성해서 넣던지 ResponseEntity.ok(result)
        // 1. 유저 정보 가져오기 - 리턴 데이터 확인
        JsonNode jsonNode = getUserResource(kakaoAccessToken);
        UserDto userDto = null;

        // 2. create or select
        if(!jsonNode.get("kakao_account").get("has_email").asText().equals("true")) {
            // 카카오에서 넘어온 이메일 정보가 없으면 => 오류 리턴
            // result.put({"message"...)
            result.put("message", "이메일 정보가 없습니다.");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else { // 넘어온 이메일 정보가 있는 경우

            userDto = emailDuplicateCheck(jsonNode.get("kakao_account").get("email").asText());

            if(userDto == null) { // 현재 가입되지 않은 사용자의 경우 insert => 명국님 코드로 cnt가 0 인 경우면,
                userDto = new UserDto();
                userDto.setProvider("kakao");
                userDto.setProviderId(jsonNode.at("/id").asText());
                userDto.setNickname(jsonNode.at("/kakao_account/profile/nickname").asText());
                userDto.setEmail(jsonNode.at("/kakao_account/email").asText());
                userDto.setProfileImage(jsonNode.at("/properties/profile_image").asText());

                userDto = emailDuplicateCheck(jsonNode.get("kakao_account").get("email").asText());

            }
        }

        String accessToken = tokenUtils.generateJwtToken(userDto);
        result.put("message", "카카오 로그인 성공");
        result.put("accessToken", accessToken);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // join과 통일 해야함 - 통일 전에 jwtTokenUtil에서 필요한 정보 확인할 것
    public void insertUser(UserDto userDto) {
        userMapper.insertUser(userDto);
    }
    public UserDto emailDuplicateCheck(String email) {
        return userMapper.emailDuplicateCheck(email);
    }

    // 유저 정보 가져오기(카카오)
    private JsonNode getUserResource(String kakaoAccessToken) {
        String resourceUrl = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + kakaoAccessToken);
        HttpEntity entity = new HttpEntity(headers);

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(resourceUrl, HttpMethod.GET, entity, JsonNode.class).getBody();
    }

    public List<UserDto> findAll() {
        return userMapper.findAll();
    }

    public UserDto findById(String token) {
        int loginId = jwtProvider.parseJwt(token);
        return userMapper.findById(loginId);
    }

    public void deleteUser(String token) {
        int loginId = jwtProvider.parseJwt(token);
        userMapper.deleteUser(loginId);
    }
}
