package data.service;

import data.dto.UserDto;
import data.mapper.UserMapper;
import data.util.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
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
}
