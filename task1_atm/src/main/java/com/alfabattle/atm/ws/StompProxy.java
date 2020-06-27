package com.alfabattle.atm.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


@Component
public class StompProxy {

    private static final Logger logger = LoggerFactory.getLogger(StompProxy.class);

    private static final String WS_URL = "ws://130.193.50.254:8100";

    private static final int REQUEST_TIMEOUT = 30;

    private final WebSocketStompClient webSocketStompClient;

    private final ConcurrentMap<Integer, ResponseHandler> requestsInProgress = new ConcurrentHashMap<>();

    private volatile StompSession session;

    public StompProxy(WebSocketStompClient webSocketStompClient) {
        this.webSocketStompClient = webSocketStompClient;
    }

    @PostConstruct
    public void initStompSession() throws Exception {
        session = webSocketStompClient.connect(WS_URL, new StompSessionHandlerAdapter() {

            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                session.subscribe("/topic/alfik", this);
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                Response typedPayload = (Response) payload;

                final ResponseHandler responseHandler = requestsInProgress.remove(typedPayload.getDeviceId());
                if (responseHandler != null) {
                    responseHandler.setResponse(typedPayload);
                }
            }

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Response.class;
            }
        }).get();
    }

    public Response executeRequest(Integer deviceId) {
        final long start = System.currentTimeMillis();
        try {
            final ResponseHandler<Response> responseHandler = new ResponseHandler<>();
            requestsInProgress.put(deviceId, responseHandler);

            session.send("/", new Request(deviceId));

            return responseHandler.getResponse();
        } catch (Exception e) {
            throw new RuntimeException("Cant execute request", e);
        } finally {
            logger.info("{} ms", System.currentTimeMillis() - start);
        }
    }

    static class ResponseHandler<T> {

        private volatile T response;

        private final CountDownLatch latch = new CountDownLatch(1);

        public T getResponse() {
            try {
                latch.await(REQUEST_TIMEOUT, TimeUnit.SECONDS);
                return response;
            } catch (InterruptedException e) {
                throw new RuntimeException("Request timeout");
            }
        }

        public void setResponse(T response) {
            this.response = response;
            latch.countDown();
        }
    }

    static class RequestID {
        private final Integer deviceId;
        private final String topic;

        RequestID(Integer deviceId, String topic) {
            this.deviceId = deviceId;
            this.topic = topic;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RequestID requestID = (RequestID) o;
            return deviceId.equals(requestID.deviceId) &&
                    topic.equals(requestID.topic);
        }

        @Override
        public int hashCode() {
            return deviceId.hashCode();
        }
    }
}
