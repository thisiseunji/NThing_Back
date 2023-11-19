package data.service;

import data.constants.ErrorCode;
import data.dto.JwtToken;
import data.dto.MessageTokenDto;
import data.dto.UserDto;
import data.exception.UnauthorizedException;
import data.mapper.UserMapper;
import data.util.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@Slf4j
public class UserService {

    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;

    @Autowired
    public UserService(UserMapper userMapper, JwtProvider jwtProvider) {
        this.userMapper = userMapper;
        this.jwtProvider = jwtProvider;
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

    public ResponseEntity<MessageTokenDto> checkRefreshToken(String token) {
        if (token != null){
            throw new UnauthorizedException("토큰 정보 없음", ErrorCode.UNAUTHORIZED);
        } else { // 토큰이 없을 경우
            /*
             * 2-1. refresh token 이 있는 경우 -> 유효성 체크 -> 통과시 엑세스 토큰 리턴 그 후 다시 1-1로
             * 저장된 토큰 정보와 일치해야함
             */
            if (!jwtProvider.isValidToken(token)) {
                throw new UnauthorizedException("유효하지 않은 토큰", ErrorCode.UNAUTHORIZED);
            } else  {
                int id = jwtProvider.parseJwt(token);
                // 저장된 리프레시 토큰과 비교
                String saved_token = userMapper.getTokenById(id);
                if (!saved_token.equals(token)) {
                    throw new UnauthorizedException("유효하지 않은 토큰", ErrorCode.UNAUTHORIZED);
                } else {
                    JwtToken jwtToken = JwtToken.builder().token(jwtProvider.createToken(id)).build();

                    return new ResponseEntity<>(new MessageTokenDto("엑세스 토큰 재발급 요청 성공", jwtToken.getToken(), null), HttpStatus.OK);
                }
            }

        }
    }
}
