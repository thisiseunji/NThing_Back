package data;

import data.constants.ErrorCode;
import data.exception.JsonProcessingException;
import data.exception.UnauthorizedException;
import data.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ChatPreHandler implements ChannelInterceptor {

    private final JwtProvider jwtProvider;
    // 임시변수
    int count = 0;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        System.out.println(++count);

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        StompCommand command = headerAccessor.getCommand();
        System.out.println(command);

        if (StompCommand.CONNECT.equals(command)) {
            String token;
            String authorizationHeader = String.valueOf(headerAccessor.getNativeHeader("Authorization"));

            if (authorizationHeader == null || authorizationHeader.equals("null")) {
                throw new UnauthorizedException("null token", ErrorCode.UNAUTHORIZED);
            } else {
                token = authorizationHeader.substring(1, authorizationHeader.length() - 1);
            }

            try {
                if (!jwtProvider.isValidToken(token)) {
                    throw new UnauthorizedException("invalid token", ErrorCode.UNAUTHORIZED);
                }
            } catch (RuntimeException e) {
                throw new JsonProcessingException("invalid input value", ErrorCode.INVALID_INPUT_VALUE);
            }
        }

        return message;

    }
}