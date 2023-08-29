package data.mapper;

import data.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserDto emailDuplicateCheck(String email);
    //
    int insertUser(UserDto userDto);
}
