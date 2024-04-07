package chat;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class ChatService {
    private Set<WebSocketChat> webSockets;

    public ChatService() {
        this.webSockets = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    public void sendMessage(String data) {
        for (WebSocketChat user : webSockets) {
            try {
                user.sendString(data);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void add(WebSocketChat webSocket) {webSockets.add(webSocket);}

    public void remove(WebSocketChat webSocket) {webSockets.remove(webSocket);}
}