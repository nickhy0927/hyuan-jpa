package com.iss.orm.log;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iss.orm.log.websocket.WebsocketHandler;

/**
 * 测试
 * @author Hyuan
 *
 */
public class WebSocketServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final Logger LOG = LoggerFactory.getLogger(WebSocketServlet.class);

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// WebsocketHandler.broadcast(new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson("请求数据websocket测试")));
			WebsocketHandler.broadcastLog("请求数据websocket测试");
			LOG.warn("=========test=========");
			LOG.warn("syso ================test==============");
			LOG.warn("=====================test==========================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
