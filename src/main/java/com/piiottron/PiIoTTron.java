package com.eospy;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Locale;
import com.eospy.ui.EOSpyWindow;

/**
 * Executive Order Corporation we make Things Smart
 *
 * EOSpy AI-IoT :: Internet of Things Drools-jBPM Expert System using EOSpy Arduino Tron AI-IoT Processing
 * Arduino Tron :: EOSPY-Executive Order Sensor Processor System - Arduino Tron MQTT AI-IoT Client using EOSpy AI-IoT Drools-jBPM
 * Executive Order Corporation - Arduino Tron - Arduino ESP8266 MQTT Telemetry Transport Machine-to-Machine(M2M)/Internet of Things(IoT)
 *
 * Executive Order Corporation
 * Copyright � 1978, 2018: Executive Order Corporation, All Rights Reserved
 */

/**
 * Update these with your LAT/LON GPS position values
 * 
 * You can find LAT/LON from an address https://www.latlong.net/convert-address-to-lat-long.html
 * String address = "National_Air_Space_Museum_600_Independence_Ave_Washington_DC_20560";
 * 
 * Values ?id=334455&timestamp=1521212240&lat=38.888160&lon=-77.019868&speed=0.0&bearing=0.0&altitude=0.0&accuracy=0.0&batt=98.7
 * String timestamp = "1521212240"; // timestamp
 * String speeds = "0.0";
 * String bearing = "0.0";
 * String altitude = "0.0";
 * String accuracy = "0.0"; // position accuracy
 * String batt = "89.7"; // battery value
 * String light = "53.4"; // photocell value
 * 
 * Arduino Tron currently supports these additional data fields in the Server Event data model:
 * 
 * id=6&event=allEvents&protocol=osmand&servertime=<date>&timestamp=<date>&fixtime=<date>&outdated=false&valid=true
 * &lat=38.85&lon=-84.35&altitude=27.0&speed=0.0&course=0.0&address=<street address>&accuracy=0.0&network=null
 * &batteryLevel=78.3&textMessage=Message_Sent&temp=71.2&ir_temp=0.0&humidity=0.0&mbar=79.9
 * &accel_x=-0.01&accel_y=-0.07&accel_z=9.79&gyro_x=0.0&gyro_y=-0.0&gyro_z=-0.0&magnet_x=-0.01&magnet_y=-0.07&magnet_z=9.81
 * &light=91.0&keypress=0.0&alarm=Temperature&distance=1.6&totalDistance=3.79&motion=false
 * 
 * You can add more additional fields to the data model and transmit via any device to the Arduino Tron Drools-jBPM processing.
 */

/**
 * This is the main class for EOSpy AI-IoT Drools-jBPM Expert System
 */
public class EOSpy_AI_IoT {

	EOSpy_AI_IoT eospy_ai_iot;

	private String base_path = "";
	private String appVer = "1.01A";
	private String buildDate = "0304";
	private boolean is64bitJMV = false;
	private boolean knowledgeDebug = false;

	public EOSpy_AI_IoT(String[] args) {

		this.eospy_ai_iot = this;
		System.out.println("EOSpy AI-IoT :: Internet of Things Drools-jBPM Expert System"
				+ " using EOSpy Arduino Tron AI-IoT Processing -version: " + appVer + " (" + buildDate + ")");

		getIPAddress();

		if (knowledgeDebug) {
			System.out.println("os.name: " + System.getProperty("os.name"));
			System.out.println("os.arch: " + System.getProperty("os.arch"));
			is64bitJMV = (System.getProperty("os.arch").indexOf("64") != -1);
			String result = (is64bitJMV == true) ? "64bit" : "32bit";

			System.out.println("java.home: " + System.getProperty("java.home"));
			System.out.println("java.vendor: " + System.getProperty("java.vendor"));
			System.out.println("java.version: " + System.getProperty("java.version") + " " + result);
			long maxHeapBytes = Runtime.getRuntime().maxMemory();
			System.out.println("Max heap memory: " + maxHeapBytes / 1024L / 1024L + "M");
			System.out.println("java.io.tmpdir: " + System.getProperty("java.io.tmpdir"));
			System.out.println("user.home: " + System.getProperty("user.home"));

			base_path = (System.getProperty("user.home") + File.separator);

			System.out.println("base_path: " + base_path);
			System.out.println("File.separator: " + File.separator);
			System.out.println("Local language: " + Locale.getDefault().getLanguage());
		}
	}

	public void init(final boolean exitOnClose) {
		// set up and show main window
		Locale.setDefault(Locale.US);

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					EOSpyWindow window = new EOSpyWindow(exitOnClose);
					window.show(); // window.frmEo.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void getIPAddress() {
		// Returns the instance of InetAddress containing local host name and address
		InetAddress localhost = null;
		try {
			localhost = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		System.out.print("System IP: " + (localhost.getHostAddress()).trim());

		// Find public IP address
		String systemipaddress = "";
		try {
			URL url_name = new URL("http://bot.whatismyipaddress.com");
			BufferedReader sc = new BufferedReader(new InputStreamReader(url_name.openStream()));

			// reads system IPAddress
			systemipaddress = sc.readLine().trim();
		} catch (Exception e) {
			systemipaddress = "Cannot Execute Properly";
		}
		System.out.println("  Public IP: " + systemipaddress);
	}

	public static void main(String[] args) {
		System.out.println("EOSpy AI-IoT :: Internet of Things Drools-jBPM Expert System"
				+ " - Arduino Tron MQTT AI-IoT Client using EOSpy AI-IoT Drools-jBPM");

		new EOSpy_AI_IoT(args).init(true);
	}
}
