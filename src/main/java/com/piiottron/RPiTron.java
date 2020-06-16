package com.piiottron;

/**
 * Executive Order Corporation we make Things Smart
 * Executive Order Corporation - Raspberry Pi Tron MQTT Telemetry Transport Machine-to-Machine(M2M)/Internet of Things(IoT)
 *
 * Raspberry Pi IoT Tron :: Internet of Things Drools-jBPM Expert System using Raspberry Pi Tron AI-IoTBPM Processing
 * Raspberry Pi IoT Tron Drools-jBPM :: Executive Order Raspberry Pi Tron Sensor AI-IoTBPM Client using AI-IoTBPM Drools-jBPM
 *
 * Executive Order Corporation
 * Copyright © 1978, 2020: Executive Order Corporation, All Rights Reserved
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
// import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
// import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
// import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.piiottron.server.AgentConnect;

/**
 * This is the main class for Raspberry Pi Tron AI-IoTBPM Drools-jBPM Expert System
 */
public class RPiTron {

	// Update these with Raspberry Pi Tron service IP address and unique unit id values
	private String URL = "http://10.0.0.2:5055/";  // Set EOSpy server IP address
	private String id = "100111";  // Raspberry Pi Tron Device unique unit id

	// Above are all the fields you need to provide values, the remaining fields are used in the RPi Tron application
	private boolean Init_GPIO = false; // Init setup GPIO.setmode() once

	/**
	 *    Raspberry Pi Pinout
	 *      3V3  (1)  (2) 5V
	 *    GPIO2  (3)  (4) 5V
	 *    GPIO3  (5)  (6) GND
	 *    GPIO4  (7)  (8) GPIO14
	 *      GND  (9) (10) GPIO15
	 *   GPIO17 (11) (12) GPIO18
	 *   GPIO27 (13) (14) GND
	 *   GPIO22 (15) (16) GPIO23
	 *      3V3 (17) (18) GPIO24
	 *   GPIO10 (19) (20) GND
	 *    GPIO9 (21) (22) GPIO25
	 *   GPIO11 (23) (24) GPIO8
	 *      GND (25) (26) GPIO7
	 *    GPIO0 (27) (28) GPIO1
	 *    GPIO5 (29) (30) GND
	 *    GPIO6 (31) (32) GPIO12
	 *   GPIO13 (33) (34) GND
	 *   GPIO19 (35) (36) GPIO16
	 *   GPIO26 (37) (38) GPIO20
	 *      GND (39) (40) GPIO21
	 *
	 * BerryClip+ - 6 LED - 2 Switch - 1 Buzzer Board
	 * Hardware Reference
	 * =============================
	 * The components are connected to the main Pi GPIO header (P1)
	 * Component  Pin       BCM    WiringPi
	 * ---------|-------|--------|---------
	 * LED 1    - P1-07 - GPIO4  - GPIO. 7
	 * LED 2    - P1-11 - GPIO17 - GPIO. 0
	 * LED 3    - P1-15 - GPIO22 - GPIO. 3
	 * LED 4    - P1-19 - GPIO10
	 * LED 5    - P1-21 - GPIO9
	 * LED 6    - P1-23 - GPIO11
	 * Buzzer   - P1-24 - GPIO8
	 * Switch 1 - P1-26 - GPIO7
	 * Swtich 2 - P1-22 - GPIO25
	 *
	 * Jam HAT - 6 LED - 2 Switch - 1 Buzzer Board
	 * The table below shows the pin numbers for BCM, Board and the matching GPIO Zero objects.
	 * |Component |GPIO.BCM | BOARD  |GPIO Zero object |WiringPi | Notes 
	 * |----------|---------|--------|-----------------|---------|
	 * | LED1     | GPIO 5  | Pin 29 | lights_1.red    | GPIO.21 |
	 * | LED2     | GPIO 6  | Pin 31 | lights_2.red    | GPIO.22 |
	 * | LED3     | GPIO 12 | Pin 32 | lights_1.yellow | GPIO.26 |
	 * | LED4     | GPIO 13 | Pin 33 | lights_2.yellow | GPIO.23 |
	 * | LED5     | GPIO 16 | Pin 36 | lights_1.green  | GPIO.27 |
	 * | LED6     | GPIO 17 | Pin 11 | lights_2.green  | GPIO. 0 |
	 * | Button 1 | GPIO 19 | Pin 35 | button_1        | GPIO.24 | Connected to R8/R10 
	 * | Button 2 | GPIO 18 | Pin 12 | button_2        | GPIO. 1 | Connected to R7/R9 
	 * | Buzzer   | GPIO 20 | Pin 38 | buzzer          | GPIO.28 |
	 *
	 * Wiring Pi - GPIO Interface library for the Raspberry Pi
	 * +-----+-----+---------+------+---+---Pi 4B--+---+------+---------+-----+-----+
	 * | BCM | wPi |   Name  | Mode | V | Physical | V | Mode | Name    | wPi | BCM |
 	 * +-----+-----+---------+------+---+----++----+---+------+---------+-----+-----+
	 * |     |     |    3.3v |      |   |  1 || 2  |   |      | 5v      |     |     |
	 * |   2 |   8 |   SDA.1 |   IN | 1 |  3 || 4  |   |      | 5v      |     |     |
	 * |   3 |   9 |   SCL.1 |   IN | 1 |  5 || 6  |   |      | 0v      |     |     |
	 * |   4 |   7 | GPIO. 7 |   IN | 1 |  7 || 8  | 1 | IN   | TxD     | 15  | 14  |
	 * |     |     |      0v |      |   |  9 || 10 | 1 | IN   | RxD     | 16  | 15  |
	 * |  17 |   0 | GPIO. 0 |  OUT | 0 | 11 || 12 | 0 | OUT  | GPIO. 1 | 1   | 18  |
	 * |  27 |   2 | GPIO. 2 |   IN | 0 | 13 || 14 |   |      | 0v      |     |     |
	 * |  22 |   3 | GPIO. 3 |  OUT | 0 | 15 || 16 | 0 | IN   | GPIO. 4 | 4   | 23  |
	 * |     |     |    3.3v |      |   | 17 || 18 | 0 | OUT  | GPIO. 5 | 5   | 24  |
	 * |  10 |  12 |    MOSI |   IN | 0 | 19 || 20 |   |      | 0v      |     |     |
	 * |   9 |  13 |    MISO |   IN | 0 | 21 || 22 | 1 | OUT  | GPIO. 6 | 6   | 25  |
	 * |  11 |  14 |    SCLK |   IN | 0 | 23 || 24 | 1 | IN   | CE0     | 10  | 8   |
	 * |     |     |      0v |      |   | 25 || 26 | 1 | IN   | CE1     | 11  | 7   |
	 * |   0 |  30 |   SDA.0 |   IN | 1 | 27 || 28 | 1 | IN   | SCL.0   | 31  | 1   |
	 * |   5 |  21 | GPIO.21 |  OUT | 0 | 29 || 30 |   |      | 0v      |     |     |
	 * |   6 |  22 | GPIO.22 |  OUT | 0 | 31 || 32 | 0 | OUT  | GPIO.26 | 26  | 12  |
	 * |  13 |  23 | GPIO.23 |  OUT | 0 | 33 || 34 |   |      | 0v      |     |     |
	 * |  19 |  24 | GPIO.24 |   IN | 0 | 35 || 36 | 1 | OUT  | GPIO.27 | 27  | 16  |
	 * |  26 |  25 | GPIO.25 |   IN | 0 | 37 || 38 | 0 | IN   | GPIO.28 | 28  | 20  |
	 * |     |     |      0v |      |   | 39 || 40 | 0 | IN   | GPIO.29 | 29  | 21  |
	 * +-----+-----+---------+------+---+----++----+---+------+---------+-----+-----+
	 * | BCM | wPi |   Name  | Mode | V | Physical | V | Mode | Name    | wPi | BCM |
	 * +-----+-----+---------+------+---+---Pi 4B--+---+------+---------+-----+-----+
	 */

    GpioController gpio;

    // provision gpio pin #01 & #03 as an output pins and blink
    GpioPinDigitalOutput led1; // GPIO pin GPIO05 (LED pin29)
    GpioPinDigitalOutput led2; // GPIO pin GPIO12 (LED pin32)

    // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
    GpioPinDigitalInput switch1; // GPIO pin GPIO19 (Switch pin35)
    GpioPinDigitalInput switch2; // GPIO pin GPIO18 (Switch pin12)
	
	// Update these with your LAT/LON GPS position values
	// You can find LAT/LON from an address https://www.latlong.net/convert-address-to-lat-long.html
	private String address = "National_Air_Space_Museum_600_Independence_Ave_Washington_DC_20560";
	private String lat = "38.888160"; // position LAT National Air Space Museum
	private String lon = "-77.019868"; // position LON
	 
	// Values ?id=334455&timestamp=1521212240&lat=38.888160&lon=-77.019868&speed=0.0&bearing=0.0&altitude=0.0&accuracy=0.0&batt=98.7
	private String speed = "0"; // speed Km - Speed over ground, knots
	private String course = "0.00"; // track true - Track made good, degrees True as bearing
	private String altitude = "0"; // altitude Antenna altitude above/below mean sea level
	private String accuracy = "0.0"; // GPS device accuracy
	private String valid = "V"; // data status - Data status: A = Data valid, V = Data invalid
	private String batt = "89.7"; // GPS device batteryLevel
	private String light = "53.4"; // photocell value

	 /**
	  * Raspberry Pi Tron currently supports these additional data fields in the Server Event data model:
	  *
	  * id=6&event=allEvents&protocol=osmand&servertime=<date>&timestamp=<date>&fixtime=<date>&outdated=false&valid=true
	  * &lat=38.85&lon=-84.35&altitude=27.0&speed=0.0&course=0.0&address=<street address>&accuracy=0.0&network=null
	  * &batteryLevel=78.3&textMessage=Message_Sent&temp=71.2&ir_temp=0.0&humidity=0.0&mbar=79.9
	  * &accel_x=-0.01&accel_y=-0.07&accel_z=9.79&gyro_x=0.0&gyro_y=-0.0&gyro_z=-0.0&magnet_x=-0.01&magnet_y=-0.07&magnet_z=9.81
	  * &light=91.0&keypress=0.0&alarm=Temperature&distance=1.6&totalDistance=3.79&motion=false
	  *
	  * You can add more additional fields to the data model and transmit via any device to the Raspberry Pi Tron Drools-jBPM processing
 	  */
	
	// Values for the DHT11 digital temperature/humidity sensor; &temp= and &humidity= fields
	private String temp = "0.0";
	private String humidity = "0.0";

	// Values to send in &textMessage= filed
	private String textMessage = "text_message";

	// Values to send in &event= field
	private String event = "allEvents";

	// Values to send in &keypress= field
	final String TYPE_ALLEVENTS = "allEvents"; // allEvents
	final String TYPE_KEYPRESS_1 = "1.0"; // keypress_1
	final String TYPE_KEYPRESS_2 = "2.0"; // keypress_2
	final String TYPE_REED_RELAY = "4.0"; // reedRelay
	final String TYPE_PROXIMITY = "8.0"; // proximity

	// Values to send in &alarm= field
	private String alarm = "general";

	// Values to send in &alarm= field
	final String ALARM_GENERAL = "general";
	final String ALARM_SOS = "sos";
	final String ALARM_VIBRATION = "vibration";
	final String ALARM_MOVEMENT = "movement";
	final String ALARM_LOW_SPEED = "lowspeed";
	final String ALARM_OVERSPEED = "overspeed";
	final String ALARM_FALL_DOWN = "fallDown";
	final String ALARM_LOW_POWER = "lowPower";
	final String ALARM_LOW_BATTERY = "lowBattery";
	final String ALARM_FAULT = "fault";
	final String ALARM_POWER_OFF = "powerOff";
	final String ALARM_POWER_ON = "powerOn";
	final String ALARM_DOOR = "door";
	final String ALARM_GEOFENCE = "geofence";
	final String ALARM_GEOFENCE_ENTER = "geofenceEnter";
	final String ALARM_GEOFENCE_EXIT = "geofenceExit";
	final String ALARM_GPS_ANTENNA_CUT = "gpsAntennaCut";
	final String ALARM_ACCIDENT = "accident";
	final String ALARM_TOW = "tow";
	final String ALARM_ACCELERATION = "hardAcceleration";
	final String ALARM_BRAKING = "hardBraking";
	final String ALARM_CORNERING = "hardCornering";
	final String ALARM_FATIGUE_DRIVING = "fatigueDriving";
	final String ALARM_POWER_CUT = "powerCut";
	final String ALARM_POWER_RESTORED = "powerRestored";
	final String ALARM_JAMMING = "jamming";
	final String ALARM_TEMPERATURE = "temperature";
	final String ALARM_PARKING = "parking";
	final String ALARM_SHOCK = "shock";
	final String ALARM_BONNET = "bonnet";
	final String ALARM_FOOT_BRAKE = "footBrake";
	final String ALARM_OIL_LEAK = "oilLeak";
	final String ALARM_TAMPERING = "tampering";
	final String ALARM_REMOVING = "removing";

	private boolean alive = true;
	private String base_path = "";
	private String appVer = "1.01A";
	private String buildDate = "0304";
	private boolean is64bitJMV = false;
	private boolean knowledgeDebug = false;

	private static String keyinput = "";
	private Thread inputThread = null;
	
	// void setup(void)
	public RPiTron(String[] args) {

		System.out.println("Raspberry Pi IoT Tron :: Internet of Things Drools-jBPM Expert System"
				+ " - Raspberry Pi IoT Tron AI-IoTBPM Processing -version: " + appVer + " (" + buildDate + ")");

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

		gpioController();
		keyboardThread();
	}

	/**
	 * This code demonstrates how to perform simple blinking LED logic of a
	 * GPIO pin on the Raspberry Pi using the Pi4J library.
	 */
	public void gpioController() {
		if (Init_GPIO == false) {
			System.err.println(
					"Note: No GPIO controller; boolean Init_GPIO defined as false in class properties."); 
			return;
		}

		if (gpio == null) {
			// create gpio controller
			gpio = GpioFactory.getInstance();

			// provision gpio pin #01 & #03 as an output pins and blink
			led1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_21);
			led2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_22);

			// provision gpio pin #02 as an input pin with its internal pull down resistor enabled
			switch1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_24, PinPullResistance.PULL_DOWN);
			switch2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_DOWN);
		}
	}

	public void keyboardThread() {
			inputThread = new Thread(new Runnable() {
			@Override
			public void run() {

				@SuppressWarnings("resource")
				Scanner scan = new Scanner(System.in);
				while (alive) {
					keyinput = scan.nextLine();
				}
			}
		});
		inputThread.start();
	}
	
	// void loop(void)
	public void init() {
		int switchState = 0;
		int timeCounter = 0;

		// keep program running until user aborts (CTRL-C)
		while (alive) {

			switch (keyinput) {
			case "1":
				switchState = 1;
				break;
			case "2":
				switchState = 2;
				break;
			case "3":
				switchState = 3;
				break;
			case "4":
				switchState = 4;
				break;
			case "5":
				switchState = 5;
				break;
			case "6":
				switchState = 6;
				break;
			case "7":
				switchState = 7;
				break;
			case "8":
				switchState = 8;
				break;
			case "q":
				alive = false;
				inputThread.stop();
				inputThread = null;
				// stop all GPIO activity/threads
				// (this method will forcefully shutdown all GPIO monitoring threads and
				// scheduled tasks)
				if (Init_GPIO == true) {
					// Pi4J GPIO controller
					gpio.shutdown(); // <--- implement this method call if you wish to terminate the
				}
				System.out.println("Press <enter> to exit.");
				break;
			}

			if (Init_GPIO == true) {
				if (switch1.getState().isHigh()) {
					switchState = 4;
				}

				if (switch2.getState().isHigh()) {
					switchState = 5;
				}
			}

			if (timeCounter > 1) {
				timeCounter++;
				keyinput = "";
				switchState = 0;
				if (timeCounter > 10) {
					timeCounter = 0;
				}
			}

			if (switchState != 0) {
				timeCounter = 1;
				raspberryPiTronSend(switchState);
			}

			keyinput = "";
			switchState = 0;
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void raspberryPiTronSend(int state) {
		// continuously blink the led every 1/2 second for 5 seconds
		if (Init_GPIO == true) {
			led1.blink(500, 5000);
		}

		java.util.Date date = new Date();
		long fixtime = date.getTime();
		fixtime = (long) (fixtime * 0.001);

		String postMsg = "/?id=" + id;
		postMsg = postMsg + "&timestamp=" + Long.toString(fixtime);
		// postMsg = postMsg + "&lat=" + lat; // <-- no GPS location needed in demo
		// postMsg = postMsg + "&lon=" + lon;
		// postMsg = postMsg + "&speed=" + speed; // speed Km - Speed over ground, knots
		// postMsg = postMsg + "&bearing=" + course; // track true - Track made good, degrees True as bearing
		// postMsg = postMsg + "&altitude=" + altitude; // altitude Antenna altitude above/below mean sea level
		// postMsg = postMsg + "&accuracy=" + accuracy; // GPS device accuracy
		// postMsg = postMsg + "&light=" + light; // photocell value
		// postMsg = postMsg + "&batt=" + batt; // GPS device batteryLevel

		switch (state) {
		case 1: // switchState gpio pinMode()
			postMsg = postMsg + "&keypress=" + TYPE_KEYPRESS_1;
			break;
		case 2: // send IoT message data for keypress_2 (2.0)
			postMsg = postMsg + "&keypress=" + TYPE_KEYPRESS_2;
			break;
		case 3:
			textMessage = "Server_Room_Temperature";
			postMsg = postMsg + "&textMessage=" + textMessage;
			postMsg = postMsg + "&alarm=" + ALARM_TEMPERATURE;
			break;
		case 4: // digitalRead GPIO15(D8) send values for textMessage, keypress and alarm
			textMessage = "Movement_Security_Alarm";
			postMsg = postMsg + "&textMessage=" + textMessage;
			postMsg = postMsg + "&keypress=" + TYPE_REED_RELAY;
			postMsg = postMsg + "&alarm=" + ALARM_MOVEMENT;
			break;
		case 5:
			postMsg = postMsg + "&light=" + light;
			textMessage = "Illuminance_Alert_Message";
			postMsg = postMsg + "&textMessage=" + textMessage;
			postMsg = postMsg + "&keypress=" + TYPE_KEYPRESS_1;
			break;
		case 6:
			postMsg = postMsg + "&event=allEvents&protocol=osmand&outdated=false&valid=true&lat=38.85&lon=-84.35&altitude=27.0&speed=65.5&course=0.0";
			break;
		case 7: // send gryo sensor data
			postMsg = postMsg + "&accel_x=-0.10&accel_y=-0.70&accel_z=9.79&gyro_x=1.0&gyro_y=-2.0&gyro_z=-3.0&magnet_x=-0.10&magnet_y=-0.70&magnet_z=9.81";
			break;
		case 8: // send IoT light sensor reading
			postMsg = postMsg + "&light=" + light;
			postMsg = postMsg + "&address=" + address;
			postMsg = postMsg + "&keypress=" + TYPE_PROXIMITY;
			break;
		case 9: // send every sensor reading
			postMsg = postMsg + "&event=allEvents&protocol=osmand&outdated=false&valid=true&lat=38.85&lon=-84.35&altitude=27.0&speed=0.0&course=0.0";
			postMsg = postMsg + "&address=The_Street_Address&accuracy=0.0&network=null&batteryLevel=78.3&textMessage=Message_Sent&ir_temp=0.0&mbar=79.9";
			postMsg = postMsg + "&accel_x=-0.01&accel_y=-0.07&accel_z=9.79&gyro_x=0.0&gyro_y=-0.0&gyro_z=-0.0&magnet_x=-0.01&magnet_y=-0.07&magnet_z=9.81";
			postMsg = postMsg + "&light=91.0&alarm=Temperature&distance=1.6&totalDistance=3.79&motion=false";
			break;
		}
		
		serverIoTSendPost(URL, postMsg);
	}

	public void serverIoTSendPost(String server, String postMsg) {
		String postURL = server;
		if ((postURL == "") || (postURL.indexOf("0.0.0.0") != -1)) {
			System.err.println("Note: Set EOSpy server IP address " + postURL
					+ " in String URL = http://10.0.0.2:5055 not defined class properties.");
			return;
		}

		AgentConnect agentConnect = new AgentConnect();
		agentConnect.sendPost(postURL, postMsg);
		// agentConnect.sendGet(postURL, postMsg);
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
		System.out.println("Executive Order Corporation - Raspberry Pi Tron MQTT Telemetry Transport"
				+ " - Raspberry Pi IoT Tron Machine-to-Machine(M2M)/Internet of Things(IoT)");
		
		new RPiTron(args).init();
	}
}
