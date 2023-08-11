package data.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import data.dto.IdToken;
import data.dto.JwtToken;
import data.dto.UserDto;
import data.mapper.UserMapper;
import data.util.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;

    @Autowired
    public LoginService(UserMapper userMapper, JwtProvider jwtProvider) {
        this.userMapper = userMapper;
        this.jwtProvider = jwtProvider;
    }

    public JwtToken login(IdToken token) throws FirebaseAuthException {

        String idToken = token.getIdToken();
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);

        if (duplicatedEmail(decodedToken.getEmail())) {
            System.out.println("로그인");
            int findId = userMapper.findByEmail(decodedToken.getEmail());
            return JwtToken.builder()
                    .token(jwtProvider.createToken(findId))
                    .build();
        } else {
            System.out.println("회원가입");
            return JwtToken.builder()
                    .token(signUp(decodedToken))
                    .build();
        }
    }

    public String signUp(FirebaseToken decodedToken) {

        UserDto userDto = UserDto.builder()
                .nickname(decodedToken.getName())
                .email(decodedToken.getEmail())
                .profileImage(decodedToken.getPicture())
                .providerId(decodedToken.getUid())
                .provider("google")
                .build();

        return jwtProvider.createToken(userMapper.join(userDto));
    }

    private boolean duplicatedEmail(String email) {
        return userMapper.isValidEmail(email);
    }
}