package com.iss.orm.log.websocket;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iss.orm.log.entity.Message;

/**
 * @function：socket处理器 
 */
@Component
public class WebsocketHandler extends TextWebSocketHandler {
	private final static Logger LOG = LoggerFactory.getLogger(WebsocketHandler.class);
	public static ConcurrentHashMap<String, WebSocketSession> websocketSessionsConcurrentHashMap;
	public static ConcurrentHashMap<String, WebSocketSession> websocketSessionsConcurrentHashMapForLog;

	static {
		websocketSessionsConcurrentHashMap = new ConcurrentHashMap<String, WebSocketSession>();
		websocketSessionsConcurrentHashMapForLog = new ConcurrentHashMap<String, WebSocketSession>();
	}

	/**
	 * 建立连接后
	 */
	public void afterConnectionEstablished(WebSocketSession session) {
		try {
			String uid = (String) session.getAttributes().get("uid");
			if (!(uid == null)) {
				if (uid.equals("log")) {
					if (websocketSessionsConcurrentHashMapForLog.get(uid) == null) {
						websocketSessionsConcurrentHashMapForLog.put(uid + Math.random(), session);
					} else {
						websocketSessionsConcurrentHashMapForLog.get(uid).close();
						websocketSessionsConcurrentHashMapForLog.put(uid + Math.random(), session);
					}
				} else {
					if (websocketSessionsConcurrentHashMap.get(uid) == null) {
						websocketSessionsConcurrentHashMap.put(uid, session);
					} else {
						websocketSessionsConcurrentHashMap.get(uid).close();
						websocketSessionsConcurrentHashMap.put(uid, session);
					}
				}
			}
			LOG.info("======websocket建立连接完成======");
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			LOG.error("afterConnectionEstablished出现异常");
			LOG.error(sw.toString());
			e.printStackTrace();
		}

	}

	/**
	 * 消息处理，在客户端通过Websocket API发送的消息会经过这里，然后进行相应的处理
	 */
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
		try {
			if (message.getPayloadLength() == 0)
				return;
			Message msg = new Gson().fromJson(message.getPayload().toString(), Message.class);
			msg.setDate(new Date());
			sendMessageToUser(msg.getTo(),
					new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));

			LOG.info("======websocket消息处理完成======");
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			LOG.info("handleMessage出现异常");
			LOG.info(sw.toString());
			e.printStackTrace();
		}

	}

	/**
	 * 消息传输错误处理
	 */
	public void handleTransportError(WebSocketSession session, Throwable exception) {
		try {
			if (session.isOpen()) {
				session.close();
			}
			Iterator<Entry<String, WebSocketSession>> it = websocketSessionsConcurrentHashMap.entrySet().iterator();
			LOG.info("======websocket消息传输错误======");
			// 移除Socket会话
			while (it.hasNext()) {
				Entry<String, WebSocketSession> entry = it.next();
				if (entry.getValue().getId().equals(session.getId())) {
					websocketSessionsConcurrentHashMap.remove(entry.getKey());
					System.out.println("Socket会话已经移除:用户ID:" + entry.getKey());
					break;
				}
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			LOG.info("handleTransportError出现异常");
			LOG.info(sw.toString());
			e.printStackTrace();
		}

	}

	/**
	 * 关闭连接后
	 */
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
		try {
			LOG.info("Websocket:" + session.getId() + "已经关闭");
			Iterator<Entry<String, WebSocketSession>> it = websocketSessionsConcurrentHashMap.entrySet().iterator();
			// 移除Socket会话
			LOG.info("======websocket关闭连接完成======");
			while (it.hasNext()) {
				Entry<String, WebSocketSession> entry = it.next();
				if (entry.getValue().getId().equals(session.getId())) {
					websocketSessionsConcurrentHashMap.remove(entry.getKey());
					System.out.println("Socket会话已经移除:用户ID：" + entry.getKey());
					break;
				}
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			LOG.info("afterConnectionClosed出现异常");
			LOG.info(sw.toString());
			e.printStackTrace();
		}

	}

	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 给所有在线用户发送消息
	 * 
	 * @param message
	 * @throws IOException
	 */
	public static void broadcast(final TextMessage message) {
		try {
			Set<Entry<String,WebSocketSession>> entrySet = websocketSessionsConcurrentHashMap.entrySet();
			for (final Entry<String, WebSocketSession> entry : entrySet) {
				String key = entry.getKey();
				System.out.println(key);
				WebSocketSession socketSession = entry.getValue();
				System.out.println(socketSession);
				if (entry.getValue().isOpen()) {
					new Thread(new Runnable() {
						public void run() {
							try {
								if (entry.getValue().isOpen()) {
									entry.getValue().sendMessage(message);
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}

					}).start();
				}
			}
			LOG.info("======websocket群发======");
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			LOG.info("broadcast出现异常");
			LOG.info(sw.toString());
			e.printStackTrace();
		}

	}

	/**
	 * 给某个用户发送消息
	 * 
	 * @param userName
	 * @param message
	 * @throws IOException
	 */
	public static void sendMessageToUser(Object uid, TextMessage message) {
		try {
			WebSocketSession session = websocketSessionsConcurrentHashMap.get(uid);
			LOG.info("======websocket给某个用户发送消息======");
			if (session != null && session.isOpen()) {
				session.sendMessage(message);
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			LOG.info("send message to user出现异常");
			LOG.info(sw.toString());
			e.printStackTrace();
		}
	}

	public static void sendMessageToUser(Object uid, String message) {
		try {
			WebSocketSession session = websocketSessionsConcurrentHashMap.get(uid);
			if (session != null && session.isOpen()) {
				LOG.info("======websocket给某个用户发送消息======");
				session.sendMessage(new TextMessage(
						new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(message)));
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			LOG.info("send message to user出现异常");
			LOG.info(sw.toString());
			e.printStackTrace();
		}
	}

	/**
	 * @function：广播日志log
	 * @parameter: @return：
	 * @date：2016-8-5 上午10:28:01
	 * @author:Administrator
	 * @return
	 * @notice:
	 */
	public static void broadcastLog(final String log) {
		try {
			Iterator<Entry<String, WebSocketSession>> it = websocketSessionsConcurrentHashMapForLog.entrySet().iterator();
			// 多线程群发 LOG.info("开始websocket群发:" + log);
			while (it.hasNext()) {
				final Entry<String, WebSocketSession> entry = it.next();
				Object uid = entry.getKey();
				WebSocketSession session = websocketSessionsConcurrentHashMapForLog.get(uid);
				if (session != null && session.isOpen()) {
					session.sendMessage(new TextMessage(log));
				}
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			LOG.info("广播log出现异常");
			LOG.info(sw.toString());
			e.printStackTrace();
		}

	}

	/**
	 * @function：向不同的map广播
	 * @parameter: @return：
	 * @date：2016-8-5 上午11:25:38
	 * @author:Administrator
	 * @notice:
	 */
	public static void broadcast(ConcurrentHashMap<String, WebSocketSession> hashMap, final String message) {
		try {
			Iterator<Entry<String, WebSocketSession>> it = hashMap.entrySet().iterator();

			LOG.info("=======websocket群发======");
			// 多线程群发
			while (it.hasNext()) {

				final Entry<String, WebSocketSession> entry = it.next();

				if (entry.getValue().isOpen()) {
					// entry.getValue().sendMessage(message);
					new Thread(new Runnable() {

						public void run() {
							if (entry.getValue().isOpen()) {
								sendMessageToUser(entry.getValue(), message);
							}
						}

					}).start();
				}
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			LOG.info("广播log出现异常");
			LOG.info(sw.toString());
			e.printStackTrace();
		}
	}

}
