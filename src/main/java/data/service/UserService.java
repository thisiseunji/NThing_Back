package data.service;

import data.dto.UserDto;
import data.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<UserDto> findAll() {
        return userMapper.findAll();
    }

    public void deleteUser(int id) {
        userMapper.deleteUser(id);
    }
}
