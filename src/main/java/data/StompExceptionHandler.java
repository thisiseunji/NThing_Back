package data;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.constants.ErrorCode;
import data.dto.ErrorResponse;
import data.exception.JsonProcessingException;
import data.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class StompExceptionHandler extends StompSubProtocolErrorHandler {
    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage, Throwable ex) {

        Throwable e = ex.getCause();
        log.error(e.getMessage());

        if (e instanceof UnauthorizedException) {
            ErrorResponse response = new ErrorResponse((ErrorCode.UNAUTHORIZED));
            return errorMessage(response);

        } else if (e instanceof  JsonProcessingException) {
            ErrorResponse response = new ErrorResponse((ErrorCode.JSON_PROCESSING_ERROR));
            return errorMessage(response);
        } else {
            ErrorResponse resppnse = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
            return errorMessage(resppnse);
        } // 이후 추가
    }

    @Override
    public Message<byte[]> handleErrorMessageToClient(Message<byte[]> errorMessage) {
        return super.handleErrorMessageToClient(errorMessage);
    }

    @Override
    protected Message<byte[]> handleInternal(StompHeaderAccessor errorHeaderAccessor, byte[] errorPayload, Throwable cause, StompHeaderAccessor clientHeaderAccessor) {
        return super.handleInternal(errorHeaderAccessor, errorPayload, cause, clientHeaderAccessor);
    }

    public Message<byte[]> errorMessage(ErrorResponse response) {
        String errorMessage = convertErrorResponseToJson(response);
        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);
        accessor.setLeaveMutable(true);

        return MessageBuilder.createMessage(errorMessage.getBytes(StandardCharsets.UTF_8),
                accessor.getMessageHeaders());
    }

    private String convertErrorResponseToJson(ErrorResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(response);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new JsonProcessingException("json processing error", ErrorCode.JSON_PROCESSING_ERROR);
        }
    }

}