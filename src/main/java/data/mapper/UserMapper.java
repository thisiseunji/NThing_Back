package data.mapper;

import data.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    // 확인 후 중복 부분 삭제
    UserDto emailDuplicateCheck(String email);
    int insertUser(UserDto userDto);

    int join(UserDto userDto);
    List<UserDto> findAll();
    UserDto findById(int id);
    int findByEmail(String email);
    boolean isValidEmail(String email);
    void deleteUser(int id);
}
