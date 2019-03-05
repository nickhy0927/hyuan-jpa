package com.iss.orm.log.filter;

import java.text.DateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.iss.common.utils.JsonMapper;
import com.iss.orm.log.entity.LoggerMessage;
import com.iss.orm.log.queue.LoggerQueue;
import com.iss.orm.log.websocket.WebsocketHandler;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

@Service
public class LogFilter extends Filter<ILoggingEvent> {

	@Override
	public FilterReply decide(ILoggingEvent event) {
		String exception = "";
		IThrowableProxy iThrowableProxy1 = event.getThrowableProxy();
		if (iThrowableProxy1 != null) {
			exception = "<span class='excehtext'>" + 
							iThrowableProxy1.getClassName() + 
							" " + 
							iThrowableProxy1.getMessage() + 
						"</span></br>";
			for (int i = 0; i < iThrowableProxy1.getStackTraceElementProxyArray().length; i++) {
				exception += "<span class='excetext'>" + 
								iThrowableProxy1.getStackTraceElementProxyArray()[i].toString() + 
							 "</span></br>";
			}
		}
		String format = DateFormat.getDateTimeInstance().format(new Date(event.getTimeStamp()));
		LoggerMessage loggerMessage = new LoggerMessage(event.getMessage(), format, event.getThreadName(),
				event.getLoggerName(), event.getLevel().levelStr, exception, "");
		LoggerQueue.getInstance().push(loggerMessage);
		WebsocketHandler.broadcastLog(new JsonMapper().toJson(loggerMessage));
		return FilterReply.ACCEPT;
	}
}
