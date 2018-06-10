package com.js.wxd.api;

import com.js.wxd.Constant;
import com.js.wxd.api.entity.SocketResult;
import com.js.wxd.api.websocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class SocketComponent {

    @Autowired
    WebSocket webSocket;

    @Autowired
    RedisTemplate redisTemplate;

    @Scheduled(fixedRate = 2 * 1000)
    public void parseOrder() {
        try {
            String url = (String) redisTemplate.opsForValue().get(Constant.KEY_IMAGE_URL);
            if (url == null) {
                url = "../../websocket/default-image.jpg";
            }
            webSocket.sendMessage(new SocketResult(url, false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
