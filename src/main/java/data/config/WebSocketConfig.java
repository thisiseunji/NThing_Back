package data.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    // 사용할 수도 안 할수도 있음.
    // private final WebsocketSecurityInterceptor websocketSecurityInterceptor;
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                //.setErrorHandler(stompExceptionHandler) 애러 처리시
                .addEndpoint("/ws-stomp")
                //.addInterceptors(new CustomHandshakeInterceptor()) 인터셉터는 필요하면 거르자. - 예를 들어 채팅방 인원 초과시(거래인원 관련) + 추가로 메시지 전송시 비속어 필터 등
                .setAllowedOriginPatterns("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/room"); // 이 주소를 구독하고 있는 독자에게 전송
        registry.setApplicationDestinationPrefixes("/send"); // 클라이언트가 메시지를 보낼 주소의 접두사
    }
}
