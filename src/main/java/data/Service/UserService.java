package data.Service;

import com.fasterxml.jackson.databind.JsonNode;
import data.dto.UserDto;
import data.mapper.UserMapper;
import data.jwt.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
@Slf4j
public class UserService {

    private final UserMapper userMapper;

    private final TokenUtils tokenUtils;

//    @Autowired
//    public UserService(UserMapper userMapper, TokenUtils tokenUtils) {
//        this.userMapper = userMapper;
//        this.tokenUtils = tokenUtils;
//    }

    @Autowired
    public UserService(UserMapper userMapper, TokenUtils tokenUtils) {
        this.userMapper = userMapper;
        this.tokenUtils = tokenUtils;
    }

    public HashMap<String, Object> socialLogin(String kakaoAccessToken) {

        HashMap<String, Object> result = new HashMap<>();
        // 1. 유저 정보 가져오기 - 리턴 데이터 확인
        JsonNode jsonNode = getUserResource(kakaoAccessToken);
        System.out.println("jsonNode : " + jsonNode); // 여기까지 ok
        // 2. create or select
        if(!jsonNode.get("kakao_account").get("has_email").asText().equals("true")) {
            System.out.println("if로 왔다");
          // 카카오에서 넘어온 이메일 정보가 없으면 => 오류 리턴
        } else { // 넘어온 이메일 정보가 있음
            UserDto userDto = emailDuplicateCheck(jsonNode.get("kakao_account").get("email").asText());
            if(userDto == null) { // 현재 가입되지 않은 사용자의 경우 insert
                userDto = new UserDto();
                userDto.setProvider("kakao");
                userDto.setProviderId(jsonNode.at("/id").asText());
                userDto.setNickname(jsonNode.at("/kakao_account/profile/nickname").asText());
                userDto.setEmail(jsonNode.at("/kakao_account/email").asText());
                userDto.setProfileImage(jsonNode.at("/properties/profile_image").asText());

                int createdUserId = userMapper.insertUser(userDto);

                userDto = emailDuplicateCheck(jsonNode.get("kakao_account").get("email").asText());

            }

            String accessToken = tokenUtils.generateJwtToken(userDto);


            result.put("accessToken", accessToken);

        }
        return result;
    }

    public void insertUser(UserDto userDto) {
        userMapper.insertUser(userDto);
    }
    public UserDto emailDuplicateCheck(String email) {
        return userMapper.emailDuplicateCheck(email);
    }

    // 유저 정보 가져오기
    private JsonNode getUserResource(String kakaoAccessToken) {

        String resourceUrl = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + kakaoAccessToken);
        HttpEntity entity = new HttpEntity(headers);

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(resourceUrl, HttpMethod.GET, entity, JsonNode.class).getBody();
    }

}
