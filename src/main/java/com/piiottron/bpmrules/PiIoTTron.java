package com.piiottron.bpmrules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.piiottron.config.Config;
import com.piiottron.database.DataManager;
import com.piiottron.server.IoTServer;
import com.piiottron.pi4j.Pi4jGPIO;
//import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
//import com.pi4j.io.gpio.event.GpioPinListenerDigital;

/**
 * Executive Order Corporation we make Things Smart
 *
 * Raspberry Pi IoT RPi (Pi IoT Tron) - Internet ofF Things (IoT) :: Internet of Things Drools-jBPM Expert System using Pi IoT Tron AI-IoTBPMServer
 * 
 * Executive Order Corp – AI-IoTBPM Server is IoT Internet of Things Drools-BPM (Business Process Management) engine for IoT Device Orchestration and 
 * IoT device ontology (AI-IoT device awareness, state of being, knowledge of real-world objects, events, situations, and abstract concepts). 
 * Executive Order Corp - AI-IoTBPM MQTT Telemetry Transport Machine-to-Machine(M2M) / Internet of Things (IoT). AI-IoTBPM
 * :: Executive Order AI-IoTBPM Tron Sensor Processor MQTT AI-IoTBPM Client using EOSpy AI-IoTBPM Drools-jBPM.
 *
 * Executive Order Corporation
 * Copyright (c) 1978, 2021: Executive Order Corporation, All Rights Reserved
 */

/**
 * This is the main class for AI-IoTBPM Tron Drools-jBPM Expert System
 */
public class PiIoTTron {

	PiIoTTron piiottron;
	private Config config;
	private static IoTServer iotServer = null;
	private static Pi4jGPIO pi4jgpio = null;

	private String base_path = "";
	private String appVer = "1.01A";
	private String buildDate = "0304";
	private boolean is64bitJMV = false;

	private int port = 5055;
	private String knowledgeDebug = "none"; // none, debug
	private String kSessionType = ""; // createKieSession
	private String kSessionName = ""; // ksession-iotcontrol
	private String processID = ""; // com.IoTControl
	private long startTime = 0; // Time the server started
	private String gpio = ""; // create gpio controller

	private DataManager dataManager;
	
	private final Logger logger = LoggerFactory.getLogger(PiIoTTron.class);

	public PiIoTTron(String configFile) {

		this.piiottron = this;
		System.out.println("Pi AI-IoTBPM Tron Server :: Internet of Things Drools-jBPM Expert System"
				+ " using Raspberry Pi AI-IoTBPM Tron Server Processing -version: " + appVer + " (" + buildDate + ")");

		getIPAddress();
		readProperties(configFile);

		initDataManager();
		logSystemInfo();

		startTime = System.currentTimeMillis();
	}

	public void init(final boolean exitOnClose) {
		if (kSessionType == "") {
			kSessionType = "createKieSession"; // Default kSessionType=createKieSession
		}
		if (kSessionName == "") {
			System.err.println("Error: Must set a kSessionName == defined in piiottron.xml file.");
			return;
		}
		if (processID == "") {
			System.err.println("Error: Must set a processID == defined in piiottron.xml file.");
			return;
		}

		final jBPMRules jbpmRules = new jBPMRules(kSessionType, kSessionName, processID, knowledgeDebug); // devices,
		startPi4jGPIO(); // Implementation for the Raspberry Pi4j GPIO example
		startIoTServer(jbpmRules);
		processConsole();
	}

	public void logSystemInfo() {
		if (knowledgeDebug.indexOf("none") == -1) {
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


	// Process server commands from the console
	private void processConsole() {
		boolean runServer = true;
		do {
			Scanner input = new Scanner(System.in);
			System.out.print("Server > ");
			String command = input.nextLine();

			if (command.equals("help") || command.equals("?")) {
				System.out.println();
				System.out.println("help | ?    - display IoT BPM Server help");
				System.out.println("stat | info - display server status information");
				System.out.println("exit | quit - terminates IoT BPM Server");
				System.out.println();
			}
			if (command.equals("exit") || command.equals("quit")) {
				runServer = false;
			}
			if (command.equals("stat") || command.equals("info")) {
				long currentTime = System.currentTimeMillis() - startTime;

				int seconds = (int) (currentTime / 1000) % 60;
				int minutes = (int) (currentTime / 60000) % 60;
				int hours = (int) (currentTime / 3600000);
				int days = (int) (currentTime / 86400000);
				System.out.println("- IoT BPM has serviced " + iotServer.getTotalConnection() + " connections");
				System.out.println("- Number of threads running " + iotServer.getCurrentConnection() + " connections");
				System.out.print("- Server running for " + days + " days, " + hours + " hours, ");
				System.out.println(minutes + " minutes, " + seconds + " seconds");
				System.out.println();
			}

		} while (runServer);
		
		stopPi4jGPIO();
		stopIoTServer();
		closeManager();
	}

	public void readProperties(String configFile) {
		try {
			config = new Config(configFile);
		} catch (Exception e) {
			config = new Config();
			e.printStackTrace();
		}

		try {
			config = new Config(configFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String portStr = config.getString("server.port");
		port = Integer.parseInt(portStr);

		knowledgeDebug = config.getString("knowledge.debug");
		kSessionType = config.getString("kSession.type");
		kSessionName = config.getString("kSession.name");
		processID = config.getString("process.id");
	}

	public void initDataManager() {
		try {
			dataManager = new DataManager(config);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void closeManager() {
		dataManager.closeDatabase();
        System.out.println("Database Disconnected..."); 
	}
	
	public void startIoTServer(jBPMRules jbpmRules) {
		iotServer = new IoTServer(jbpmRules, port);
		iotServer.start();
	}

	public static void stopIoTServer() {
		iotServer.killServer();
	}

	public void startPi4jGPIO() {
		pi4jgpio = new Pi4jGPIO(); // Implementation for the Raspberry Pi4j GPIO example

		if ((gpio == "") || (gpio.indexOf("none") != -1)) {
			System.err.println("Note: create gpio controller e.g. gpio=GPIO_01 not defined in iotbpm.properties file.");
		} else {
			pi4jgpio.gpioStartController();
	        System.out.println("Create GPIO Controller...");
		}
	}

	public void stopPi4jGPIO() {
		if (pi4jgpio.isPi4jActive()) {
			pi4jgpio.gpioShutdown();
			System.out.println("Stop all GPIO Activity / Threads"); 
		}
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
		Locale.setDefault(Locale.ENGLISH);
		String configFile = "";
		
		System.out.println("Pi IoT Tron :: Executive Order IoT Sensor Processor System"
				+ " - MQTT AI-IoTBPM Tron Server using AI-IoTBPM Drools-jBPM");

		if (args.length <= 0) {
			System.out.println("Configuration file is not provided, using default piiottron.xml filename.");
			configFile = "piiottron.xml";
		} else {
			configFile = args[args.length - 1];
		}
		
		new PiIoTTron(configFile).init(true);
	}
}
