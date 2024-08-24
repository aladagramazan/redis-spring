package com.rem.redis_spring.chat.service;

import org.redisson.api.RListReactive;
import org.redisson.api.RTopicReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class ChatRoomService implements WebSocketHandler {

    @Autowired
    private RedissonReactiveClient client;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        String room = getRoomName(session);
        RTopicReactive topic = this.client.getTopic(room, StringCodec.INSTANCE);
        RListReactive<String> history = this.client.getList("history:" + room, StringCodec.INSTANCE);

        // subscribe
        session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .flatMap(msg -> history.add(msg).then(topic.publish(msg)))
                .doOnError(System.out::println)
                .doFinally(signalType -> System.out.println("subscriber finally " + signalType))
                .subscribe();

        // publish
        Flux<WebSocketMessage> message = topic.getMessages(String.class)
                .startWith(history.iterator())
                .map(session::textMessage)
                .doOnError(System.out::println)
                .doFinally(signalType -> System.out.println("publisher finally " + signalType));

        return session.send(message);
    }

    private String getRoomName(WebSocketSession session) {
        URI uri = session.getHandshakeInfo().getUri();
        return UriComponentsBuilder.fromUri(uri)
                .build()
                .getQueryParams()
                .toSingleValueMap()
                .getOrDefault("room", "default");
    }
}
