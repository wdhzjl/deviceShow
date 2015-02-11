package com.bosstun.localdata;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class SettingParamsParser{

	public SettingParams parse(InputStream is) throws Exception {
		SettingParams sp = null;

		// XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		// XmlPullParser parser = factory.newPullParser();
		XmlPullParser parser = Xml.newPullParser(); // 由android.util.Xml创建一个XmlPullParser实例
		parser.setInput(is, "UTF-8"); // 设置输入流 并指明编码方式

		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				break;
			case XmlPullParser.START_TAG:
				if (parser.getName().equals("SettingParams")) {
					sp = new SettingParams();
				} else if (parser.getName().equals("bSelfStart")) {
					eventType = parser.next();
					sp.setBSelfStart(Boolean.parseBoolean(parser.getText()));
				} else if (parser.getName().equals("waitTimeBeforeTest")) {
					eventType = parser.next();
					sp.setWaitTimeBeforeTest(Integer.parseInt(parser.getText()));
				} else if (parser.getName().equals("itvalTimeOfTest")) {
					eventType = parser.next();
					sp.setItvalTimeOfTest(Integer.parseInt(parser.getText()));
				}else if (parser.getName().equals("waitTimeToReboot")) {
					eventType = parser.next();
					sp.setWaitTimeToReboot(Integer.parseInt(parser.getText()));
				}
				break;
			case XmlPullParser.END_TAG:
				
				break;
			}
			eventType = parser.next();
		}
		return sp;
	}
	
	public String serialize(SettingParams sp) throws Exception {
		XmlSerializer serializer = Xml.newSerializer(); // 由android.util.Xml创建一个XmlSerializer实例
		StringWriter writer = new StringWriter();
		
		serializer.setOutput(writer); // 设置输出方向为writer
		serializer.startDocument("UTF-8", true);
		serializer.startTag("", "SettingParams");
		
		serializer.startTag("", "bSelfStart");
		serializer.text(sp.getBSelfStart() + "");
		serializer.endTag("", "bSelfStart");
		
		serializer.startTag("", "waitTimeBeforeTest");
		serializer.text(sp.getWaitTimeBeforeTest() + "");
		serializer.endTag("", "waitTimeBeforeTest");
		
		serializer.startTag("", "itvalTimeOfTest");
		serializer.text(sp.getItvalTimeOfTest() + "");
		serializer.endTag("", "itvalTimeOfTest");
		
		serializer.startTag("", "waitTimeToReboot");
		serializer.text(sp.getWaitTimeToReboot() + "");
		serializer.endTag("", "waitTimeToReboot");
		
		serializer.endTag("", "SettingParams");
		serializer.endDocument();

		return writer.toString();
	}

}
