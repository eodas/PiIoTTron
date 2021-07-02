package com.piiottron.server;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import com.piiottron.database.DataManager;

public class IoTCommand {

	public static final String TYPE_CUSTOM = "custom";
	public static final String TYPE_IDENTIFICATION = "deviceIdentification";
	public static final String TYPE_POSITION_SINGLE = "positionSingle";
	public static final String TYPE_POSITION_PERIODIC = "positionPeriodic";
	public static final String TYPE_POSITION_STOP = "positionStop";
	public static final String TYPE_ENGINE_STOP = "engineStop";
	public static final String TYPE_ENGINE_RESUME = "engineResume";
	public static final String TYPE_ALARM_ARM = "alarmArm";
	public static final String TYPE_ALARM_DISARM = "alarmDisarm";
	public static final String TYPE_SET_TIMEZONE = "setTimezone";
	public static final String TYPE_REQUEST_PHOTO = "requestPhoto";
	public static final String TYPE_POWER_OFF = "powerOff";
	public static final String TYPE_REBOOT_DEVICE = "rebootDevice";
	public static final String TYPE_SEND_SMS = "sendSms";
	public static final String TYPE_SEND_USSD = "sendUssd";
	public static final String TYPE_SOS_NUMBER = "sosNumber";
	public static final String TYPE_SILENCE_TIME = "silenceTime";
	public static final String TYPE_SET_PHONEBOOK = "setPhonebook";
	public static final String TYPE_MESSAGE = "message";
	public static final String TYPE_VOICE_MESSAGE = "voiceMessage";
	public static final String TYPE_OUTPUT_CONTROL = "outputControl";
	public static final String TYPE_VOICE_MONITORING = "voiceMonitoring";
	public static final String TYPE_SET_AGPS = "setAgps";
	public static final String TYPE_SET_INDICATOR = "setIndicator";
	public static final String TYPE_CONFIGURATION = "configuration";
	public static final String TYPE_GET_VERSION = "getVersion";
	public static final String TYPE_FIRMWARE_UPDATE = "firmwareUpdate";
	public static final String TYPE_SET_CONNECTION = "setConnection";
	public static final String TYPE_SET_ODOMETER = "setOdometer";
	public static final String TYPE_GET_MODEM_STATUS = "getModemStatus";
	public static final String TYPE_GET_DEVICE_STATUS = "getDeviceStatus";

	public static final String TYPE_MODE_POWER_SAVING = "modePowerSaving";
	public static final String TYPE_MODE_DEEP_SLEEP = "modeDeepSleep";

	public static final String TYPE_ALARM_GEOFENCE = "movementAlarm";
	public static final String TYPE_ALARM_BATTERY = "alarmBattery";
	public static final String TYPE_ALARM_SOS = "alarmSos";
	public static final String TYPE_ALARM_REMOVE = "alarmRemove";
	public static final String TYPE_ALARM_CLOCK = "alarmClock";
	public static final String TYPE_ALARM_SPEED = "alarmSpeed";
	public static final String TYPE_ALARM_FALL = "alarmFall";
	public static final String TYPE_ALARM_VIBRATION = "alarmVibration";

	public static final String KEY_UNIQUE_ID = "uniqueId";
	public static final String KEY_FREQUENCY = "frequency";
	public static final String KEY_LANGUAGE = "language";
	public static final String KEY_TIMEZONE = "timezone";
	public static final String KEY_DEVICE_PASSWORD = "devicePassword";
	public static final String KEY_RADIUS = "radius";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_ENABLE = "enable";
	public static final String KEY_DATA = "data";
	public static final String KEY_INDEX = "index";
	public static final String KEY_PHONE = "phone";
	public static final String KEY_SERVER = "server";
	public static final String KEY_PORT = "port";
	
	private String command;
	private String deviceID;
	private DataManager dataManager;
	private String knowledgeDebug = "none";
	private final String USER_AGENT = "Mozilla/5.0";
	private static IoTCommand IOTCOMMAND_INSTANCE = null;

	private final Logger logger = LoggerFactory.getLogger(IoTCommand.class);

	public IoTCommand(DataManager dataManager, String knowledgeDebug) {
		this.dataManager = dataManager;
		this.knowledgeDebug = knowledgeDebug;
		IoTCommand.IOTCOMMAND_INSTANCE = this;
	}

	public static IoTCommand getInstance() {
		return IOTCOMMAND_INSTANCE;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
	
	// HTTP GET request
	public void sendGet(String deviceID, String command) {
		if ((deviceID == null) || (deviceID.isEmpty())) {
			System.err.println("Note: Send IoTCommand " + deviceID + " not defined for command.");
			return;
		}
		String deviceIP = dataManager.selectDeviceIP(deviceID); 
		if ((deviceIP == null) || (deviceIP.isEmpty()) || (deviceIP.indexOf("0.0.0.0") != -1)) {
			System.err.println("Note: Send IoTCommand " + deviceID + " IP Address not defined for command.");
			return;
		}

		String urlString = deviceIP + command;
		try {
			URL url = new URL(urlString);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			// By default it is GET request
			con.setRequestMethod("GET");

			// add request header
			con.setRequestProperty("User-Agent", USER_AGENT);

			int responseCode = con.getResponseCode();
			System.out.println("Send GET request: " + url);
			System.out.println("Response code: " + responseCode);

			// Reading response from input Stream
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuffer response = new StringBuffer();
			String output;

			while ((output = in.readLine()) != null) {
				response.append(output);
			}
			in.close();

			// printing result from response
			System.out.println(response.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	// HTTP Post request
	public void sendPost(String deviceID, String command) {
		if ((deviceID == null) || (deviceID.isEmpty())) {
			System.err.println("Note: Send IoTCommand " + deviceID + " not defined for command.");
			return;
		}
		String deviceIP = dataManager.selectDeviceIP(deviceID); 
		if ((deviceIP == null) || (deviceIP.isEmpty()) || (deviceIP.indexOf("0.0.0.0") != -1)) {
			System.err.println("Note: Send IoTCommand " + deviceID + " IP Address not defined for command.");
			return;
		}

		String url = deviceIP + command;
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// Setting basic post request
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setRequestProperty("Content-Type", "application/json");

			String postJsonData = "AI-IoTBPM";

			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postJsonData);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			if (knowledgeDebug.indexOf("none") == -1) {
				System.out.println("Send 'POST' request to URL: " + url);
				System.out.println("Post Data: " + postJsonData);
				System.out.println("Response Code: " + responseCode);
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuffer response = new StringBuffer();
			String output;

			while ((output = in.readLine()) != null) {
				response.append(output);
			}
			in.close();

			// printing result from response
			if (knowledgeDebug.indexOf("none") == -1) {
				System.out.println(response.toString());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
