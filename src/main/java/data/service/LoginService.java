package data.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import data.dto.TokenDto;
import data.dto.UserDto;
import data.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserMapper userMapper;

    @Autowired
    public LoginService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void login(TokenDto tokenDto) throws FirebaseAuthException {
        String idToken = tokenDto.getIdToken();
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);

        if (userMapper.findByEmail(decodedToken.getEmail())) {
            System.out.println("로그인");
        } else {
            System.out.println("회원가입");
            UserDto userDto = UserDto.builder()
                    .nickname(decodedToken.getName())
                    .email(decodedToken.getEmail())
                    .profileImage(decodedToken.getPicture())
                    .providerId(decodedToken.getUid())
                    .provider("google")
                    .build();

            userMapper.join(userDto);
        }

    }
}