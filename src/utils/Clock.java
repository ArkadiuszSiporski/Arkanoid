package utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class Clock extends Thread {
	Logger LOG = Logger.getLogger(Clock.class);

	private static String time = "";
	private boolean getTime = true;

	public void run() {

		int sleep = 10;
		while (getTime) {
			try {
				URL url = new URL("https://www.onet.pl/");
				URLConnection urlConn = url.openConnection();
				HttpURLConnection conn = (HttpURLConnection) urlConn;
				conn.setConnectTimeout(10000);
				conn.setReadTimeout(10000);
				conn.setInstanceFollowRedirects(true);
				conn.connect();
				Map<String, List<String>> header = conn.getHeaderFields();
				for (String key : header.keySet()) {
					if (key != null && "Date".equals(key)) {
						List<String> data = header.get(key);
						String dateString = data.get(0);
						SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.US);
						sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
						Date remoteDate = sdf.parse(dateString);
						Calendar calendar = Calendar.getInstance();
						sleep = 60 - calendar.get(Calendar.SECOND);
						calendar.setTime(remoteDate);
						time = StringUtils.leftPad("" + calendar.get(Calendar.HOUR_OF_DAY), 2, "0") + ":" +StringUtils.leftPad("" + calendar.get(Calendar.MINUTE), 2, "0")  ;
					}
				}

			} catch (Exception e) {
				LOG.error("Failed to access web resource", e);
			}
			try {
				sleep(sleep * 1000);
			} catch (InterruptedException e) {
				LOG.error("Sleep interrupted", e);
			}
		}
	}

	public static String getTime() {
		return time;
	}
}
