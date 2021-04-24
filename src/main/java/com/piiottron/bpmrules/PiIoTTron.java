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

import com.piiottron.model.AgentsList;
import com.piiottron.model.StateList;
import com.piiottron.server.AgentConnect;
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

	AgentsList agentsList;
	PiIoTTron piiottron;
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
	private String gpio = ""; // create gpio controller

	private long startTime = 0; // Time the server started

	private final Logger logger = LoggerFactory.getLogger(PiIoTTron.class);

	public PiIoTTron(String[] args) {

		this.piiottron = this;
		agentsList = new AgentsList();
		System.out.println("Pi AI-IoTBPM Tron Server :: Internet of Things Drools-jBPM Expert System"
				+ " using Raspberry Pi AI-IoTBPM Tron Server Processing -version: " + appVer + " (" + buildDate + ")");

		getIPAddress();
		readProperties();

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
		startTime = System.currentTimeMillis();
	}

	public void init(final boolean exitOnClose) {
		AgentConnect agentConnect = new AgentConnect(agentsList, knowledgeDebug);

		if (kSessionType == "") {
			kSessionType = "createKieSession"; // Default kSessionType=createKieSession
		}
		if (kSessionName == "") {
			System.err.println("Error: Must set a kSessionName == defined in piiottron.properties file.");
			return;
		}
		if (processID == "") {
			System.err.println("Error: Must set a processID == defined in piiottron.properties file.");
			return;
		}

		StateList stateList = new StateList();

		final jBPMRules jbpmRules = new jBPMRules(kSessionType, kSessionName, processID, stateList, knowledgeDebug); // devices,

		if ((gpio == "") || (gpio.indexOf("none") != -1)) {
			System.err.println("Note: create gpio controller e.g. gpio=GPIO_01 not defined in iotbpm.properties file.");
		} else {
			pi4jgpio = new Pi4jGPIO();
			pi4jgpio.gpioStartController();
		}

		startIoTServer(jbpmRules);

		processConsole();
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
		if ((gpio == "") || (gpio.indexOf("none") != -1)) {
			System.err.println("Note: create gpio controller e.g. gpio=GPIO_01 not defined in iotbpm.properties file.");
		} else {
			stopPi4jGPIO();
		}
		stopIoTServer();
	}

	public void readProperties() {
		try {
			File file = new File("piiottron.properties");
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();

			Enumeration<?> enuKeys = properties.keys();
			while (enuKeys.hasMoreElements()) {
				String key = (String) enuKeys.nextElement();
				String value = properties.getProperty(key);

				if (key.indexOf("port") != -1) {
					String portStr = value;
					port = Integer.parseInt(portStr);
				}
				if (key.indexOf("knowledgeDebug") != -1) {
					knowledgeDebug = value;
				}
				if (key.indexOf("kSessionType") != -1) {
					kSessionType = value;
				}
				if (key.indexOf("kSessionName") != -1) {
					kSessionName = value;
				}
				if (key.indexOf("processID") != -1) {
					processID = value;
				}
				if (key.indexOf("agentDevice") != -1) {
					agentsList.parseAgents(value);
				}
				if (key.indexOf("gpio") != -1) {
					gpio = value;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startIoTServer(jBPMRules jbpmRules) {
		iotServer = new IoTServer(jbpmRules, port);
		iotServer.start();
	}

	public static void stopIoTServer() {
		iotServer.killServer();
	}

	public void stopPi4jGPIO() {
		pi4jgpio.gpioStopAction();
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
		System.out.println("Arduino Tron :: Executive Order IoT Sensor Processor System"
				+ " - MQTT AI-IoTBPM Tron Server using AI-IoTBPM Drools-jBPM");

		new PiIoTTron(args).init(true);
	}
}
