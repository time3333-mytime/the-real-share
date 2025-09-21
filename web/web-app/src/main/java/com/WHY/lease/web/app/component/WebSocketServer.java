package com.WHY.lease.web.app.component;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.WHY.lease.web.app.service.InformationService;
import com.WHY.lease.web.app.vo.infomation.InformationVo;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Component
@ServerEndpoint(value = "/talkingserver/{sid}")
public class WebSocketServer {

        private static InformationService informationService;

    @Autowired
    public void setInformationService(InformationService informationService){
        WebSocketServer.informationService = informationService;
    }

        private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

        public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

        @OnOpen
        public void onOpen(Session session, @PathParam("sid") String sid) {
            sessionMap.put(sid, session);
            log.info("有新用户加入，userid={}, 当前在线人数为：{}", sid, sessionMap.size());
            JSONObject result = new JSONObject();
            JSONArray array = new JSONArray();
            result.set("users", array);
            for (Object key : sessionMap.keySet()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("sid", key);
                array.add(jsonObject);
            }
            sendAllMessage(JSONUtil.toJsonStr(result));  // 后台发送消息给所有的客户端
        }

        @OnClose
        public void onClose(Session session, @PathParam("sid") String sid) {
            sessionMap.remove(sid);
            log.info("有一连接关闭，移除userid={}的用户session, 当前在线人数为：{}", sid, sessionMap.size());
        }

        @OnMessage
        public void onMessage(String message, Session session, @PathParam("sid") String sid) {
            log.info("服务端收到用户userid={}的消息:{}", sid, message);
            JSONObject obj = JSONUtil.parseObj(message);
            String toUsername = obj.getStr("to"); // to表示发送给哪个用户
            String text = obj.getStr("text"); // 发送的消息文本  hello
            Session toSession = sessionMap.get(toUsername); // 根据 to用户名来获取 session，再通过session发送消息文本
            if (toSession != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("from", sid);
                jsonObject.set("text", text);
                this.sendMessage(jsonObject.toString(), toSession);
                InformationVo informationVo =new InformationVo(text,Long.parseLong(sid),Long.parseLong(toUsername),1);
                informationService.save(informationVo);
                log.info("发送给用户userid={}，消息：{}", toUsername, jsonObject.toString());
            } else {
                InformationVo informationVo =new InformationVo(text,Long.parseLong(sid),Long.parseLong(toUsername),0);
                informationService.save(informationVo);
                log.info("加入数据库，用户userid={}的已经离线", toUsername);
            }
        }
        @OnError
        public void onError(Session session, Throwable error) {
            log.error("发生错误");
            error.printStackTrace();
        }
        private void sendMessage(String message, Session toSession) {
            try {
                log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
                toSession.getBasicRemote().sendText(message);
            } catch (Exception e) {
                log.error("服务端发送消息给客户端失败", e);
            }
        }
        private void sendAllMessage(String message) {
            try {
                for (Session session : sessionMap.values()) {
                    log.info("服务端给客户端[{}]发送消息{}", session.getId(), message);
                    session.getBasicRemote().sendText(message);
                }
            } catch (Exception e) {
                log.error("服务端发送消息给客户端失败", e);
            }
        }



}
