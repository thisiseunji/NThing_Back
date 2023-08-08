package data.mapper;

import data.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    void join(UserDto userDto);
    List<UserDto> findAll();
    boolean findByEmail(String email);
    void deleteUser(int id);
}
