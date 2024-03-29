/********************
  - Executive Order Corporation - Arduino Tron ESP8266 MQTT Telemetry Transport Machine-to-Machine(M2M)/Internet of Things(IoT)
  - Arduino Tron Drools-jBPM :: Executive Order Arduino Tron Sensor Processor MQTT AI-IoTBPM Client using AI-IoTBPM Drools-jBPM
  - Arduino Tron AI-IoTBPM :: Internet of Things Drools-jBPM Expert System using Arduino Tron AI-IoTBPM Processing
  - Executive Order Corporation
  - Copyright (c) 1978, 2021: Executive Order Corporation, All Rights Reserved
********************/

//#include <SimpleDHT.h> // <-- uncommit for dht11
//#include <IRrecv.h> // <-- uncommit for IR VS1838

#include <TinyGPS++.h>  // <-- gy-gps6mv2
#include <SoftwareSerial.h>

#include <ESP8266WiFi.h>

#define ADC0 A0 // NodeMCU pin Analog ADC0 (A0)

#define LED0 D0 // NodeMCU pin GPIO16 (D0)
#define LED1 D1 // NodeMCU pin GPIO5 (D1)
#define LED2 D2 // NodeMCU pin GPIO4 (D2)
#define LED3 D3 // NodeMCU pin GPIO0 (D3)
#define LED4 D4 // NodeMCU pin GPIO2 (D4-onboard)
#define LED5 D5 // NodeMCU pin GPIO14 (D5)
#define LED6 D6 // NodeMCU pin GPIO12 (D6)
#define LED7 D7 // NodeMCU pin GPIO13 (D7)
#define LED8 D8 // NodeMCU pin GPIO15 (D8)
#define LED9 D9 // NodeMCU pin GPIO3 (D9-RXD0)
#define LED10 D10 // NodeMCU pin GPIO1 (D10-TXD0)

#define BUTTON0 D0 // NodeMCU pin GPIO16 (D0)
#define BUTTON1 D1 // NodeMCU pin GPIO5 (D1)
#define BUTTON2 D2 // NodeMCU pin GPIO4 (D2)
#define BUTTON3 D3 // NodeMCU pin GPIO0 (D3)
#define BUTTON4 D4 // NodeMCU pin GPIO2 (D4)
#define BUTTON5 D5 // NodeMCU pin GPIO14 (D5)
#define BUTTON6 D6 // NodeMCU pin GPIO12 (D6)
#define BUTTON7 D7 // NodeMCU pin GPIO13 (D7)
#define BUTTON8 D8 // NodeMCU pin GPIO15 (D8)
#define BUTTON9 D9 // NodeMCU pin GPIO3 (D9-RXD0)
#define BUTTON10 D10 // NodeMCU pin GPIO1 (D10-TXD0)

// Update these with WiFi network values
const char* ssid     = "eodas2"; //  your network SSID (name)
const char* password = "SL550eodas"; // your network password

WiFiClient client;

// Update these with Arduino Tron service IP address and unique unit id values
byte server[] = { 10, 0, 0, 201 }; // Set EOSpy server IP address as bytes
String id = "100950"; // Arduino Tron Device unique unit id

TinyGPSPlus gps; // Uses TinyGPS++ (TinyGPSPlus) object.
// Requires SoftwareSerial 9600-baud serial GPS device on pins 4(rx) and 5(tx).
static const int RXPin = 4, TXPin = 5; 
static const uint32_t GPSBaud = 9600;
SoftwareSerial ss(RXPin, TXPin); // The serial connection to the GPS device 

// Update these with your LAT/LON GPS position values
float latitude , longitude;
// You can find LAT/LON from an address https://www.latlong.net/convert-address-to-lat-long.html
String address = "National_Air_Space_Museum_600_Independence_Ave_Washington_DC_20560";
String lat = "38.888160"; // position LAT National Air Space Museum
String lon = "-77.019868"; // position LON

// Above are all the fields you need to provide values, the remaining fields are used in the Arduino Tron application
const bool sendGPSPosition = true; // TinyGPS++ (TinyGPSPlus) send GPS sensor position LAT/LON
const bool readPushButton = false; // Values for the digitalRead value from gpiox button
const bool readDHT11Temp = false; // Values for the DHT11 digital temperature/humidity sensor
const bool readLDRLight = false; // Values for the LDR analog photocell light sensor
const bool readIRSensor = false; // Arduino values for IR sensor connected to GPIO2

const int httpPort = 5055; // Arduino Tron server is running on default port 5055
// OpenStreetMap Automated Navigation Directions is a map and navigation app for Android default port 5055

// Values ?id=334455&timestamp=1521212240&lat=38.888160&lon=-77.019868&speed=0.0&bearing=0.0&altitude=0.0&accuracy=0.0&batt=98.7
String timestamp = "1521212240"; // timestamp
String sats = "0";
String hdop = "0.0";
String speeds = "0.0";
String bearing = "0.0";
String altitude = "0.0";
String accuracy = "0.0"; // position accuracy
String batt = "89.7"; // battery value
String light = "53.4"; // photocell value

double satellites = 0;
double shdop = 0;
double course = 0;
double speedmph = 0;
double altitudemeters = 0;

// Arduino Tron currently supports these additional data fields in the Server Event data model:

// id=6&event=allEvents&protocol=osmand&servertime=<date>&timestamp=<date>&fixtime=<date>&outdated=false&valid=true
// &lat=38.85&lon=-84.35&altitude=27.0&speed=0.0&course=0.0&address=<street address>&accuracy=0.0&network=null
// &batteryLevel=78.3&textMessage=Message_Sent&temp=71.2&ir_temp=0.0&humidity=0.0&mbar=79.9
// &accel_x=-0.01&accel_y=-0.07&accel_z=9.79&gyro_x=0.0&gyro_y=-0.0&gyro_z=-0.0&magnet_x=-0.01&magnet_y=-0.07&magnet_z=9.81
// &light=91.0&keypress=0.0&alarm=Temperature&distance=1.6&totalDistance=3.79&motion=false

// You can add more additional fields to the data model and transmit via any device to the Arduino Tron Drools-jBPM processing

// Values for the DHT11 digital temperature/humidity sensor; &temp= and &humidity= fields
String temp = "0.0";
String humidity = "0.0";

// Values to send in &textMessage= filed
String textMessage = "text_message";

// Values to send in &keypress= field
const String TYPE_ALLEVENTS = "allEvents"; // allEvents
const String TYPE_KEYPRESS_1 = "1.0"; // keypress_1
const String TYPE_KEYPRESS_2 = "2.0"; // keypress_2
const String TYPE_REED_RELAY = "4.0"; // reedRelay
const String TYPE_PROXIMITY = "8.0"; // proximity

// Values to send in &alarm= field
String alarm = "general";

// Values to send in &alarm= field
const String ALARM_GENERAL = "general";
const String ALARM_SOS = "sos";
const String ALARM_VIBRATION = "vibration";
const String ALARM_MOVEMENT = "movement";
const String ALARM_LOW_SPEED = "lowspeed";
const String ALARM_OVERSPEED = "overspeed";
const String ALARM_FALL_DOWN = "fallDown";
const String ALARM_LOW_POWER = "lowPower";
const String ALARM_LOW_BATTERY = "lowBattery";
const String ALARM_FAULT = "fault";
const String ALARM_POWER_OFF = "powerOff";
const String ALARM_POWER_ON = "powerOn";
const String ALARM_DOOR = "door";
const String ALARM_GEOFENCE = "geofence";
const String ALARM_GEOFENCE_ENTER = "geofenceEnter";
const String ALARM_GEOFENCE_EXIT = "geofenceExit";
const String ALARM_GPS_ANTENNA_CUT = "gpsAntennaCut";
const String ALARM_ACCIDENT = "accident";
const String ALARM_TOW = "tow";
const String ALARM_ACCELERATION = "hardAcceleration";
const String ALARM_BRAKING = "hardBraking";
const String ALARM_CORNERING = "hardCornering";
const String ALARM_FATIGUE_DRIVING = "fatigueDriving";
const String ALARM_POWER_CUT = "powerCut";
const String ALARM_POWER_RESTORED = "powerRestored";
const String ALARM_JAMMING = "jamming";
const String ALARM_TEMPERATURE = "temperature";
const String ALARM_PARKING = "parking";
const String ALARM_SHOCK = "shock";
const String ALARM_BONNET = "bonnet";
const String ALARM_FOOT_BRAKE = "footBrake";
const String ALARM_OIL_LEAK = "oilLeak";
const String ALARM_TAMPERING = "tampering";
const String ALARM_REMOVING = "removing";

String ver = "1.03E";
int loopCounter = 1; // loop counter
int timeCounter = 901; // time counter
int clientAvail = 0; // client.available() count
int switchState = 0; // digitalRead value from gpiox button
char readKeyboard = 0; // read serial command

// DHT11 digital temperature and humidity sensor pin Vout (sense)
int pinDHT11 = 2; // GPIO2
//SimpleDHT11 dht11; <-- uncommit for dht11

// LDR Photocell light interface for NodeMCU
int photocellChange = 10; // LDR and 10K pulldown resistor are connected to A0
float photocellLight; // Variable to hold last analog light value

// Arduino values for IR sensor connected to GPIO2
uint16_t RECV_PIN = D5; // D5 GPIO2
//IRrecv irrecv(RECV_PIN); // <-- uncommit for IR VS1838
//decode_results results; // <-- uncommit for IR VS1838
String irkey = "1.0";

// Set response jBPM Global Variable List
// kcontext.getKnowledgeRuntime().setGlobal("response", "");
String response = "";

// Required for LIGHT_SLEEP_T delay mode
extern "C" {
#include "user_interface.h"
}

void setup(void) {
  pinMode(LED0, OUTPUT); // Declaring Arduino LED pin as output
  pinMode(LED1, OUTPUT);
  pinMode(LED2, OUTPUT);
  pinMode(LED3, OUTPUT);
  pinMode(LED4, OUTPUT);
  digitalWrite(LED0, LOW); // turn the LED off

  pinMode(BUTTON5, INPUT); // Declaring Arduino pins as an inputs
  pinMode(BUTTON6, INPUT);
  pinMode(BUTTON7, INPUT);
  pinMode(BUTTON8, INPUT);

  // Arduino IDE Serial Monitor window to emulate what Arduino Tron sensors are reading
  Serial.begin(115200); // Serial connection from ESP-01 via 3.3v console cable
  ss.begin(GPSBaud); // Requires SoftwareSerial 9600-baud serial GPS device on pins 4(rx) and 5(tx).

  // Connect to WiFi network
  Serial.println("Executive Order Corporation - Arduino Tron ESP8266 MQTT Telemetry Transport Machine-to-Machine(M2M)/Internet of Things(IoT)");
  Serial.println("Arduino Tron Drools-jBPM :: Executive Order Arduino Tron Sensor Processor MQTT AI-IoTBPM Client using AI-IoTBPM Drools-jBPM");
  Serial.println("- Arduino Tron Sensor ver " + ver);
  Serial.println("Copyright (c) 1978, 2021: Executive Order Corporation, All Rights Reserved");
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  if (readIRSensor) {
    //  irrecv.enableIRIn(); // Start the receiver <-- uncommit for IR VS1838
  }
  delay(200);
}

void loop(void)
{
  switchState = 0;
  readKeyboard = 0;
  timeCounter++;

  if (timeCounter > 900000) {
    timeCounter = 0;
    if (readDHT11Temp) {
      readDHT11(); // read DHT11 digital temperature and humidity sensor
      // arduinoTronSend(); <-- uncommit for dht11
    }
    if (sendGPSPosition) {
      if (gps.location.isValid()) {
        switchState = 1;
      }
    }
  }

  if (readLDRLight) {
    readLDRPhotocell(); // read LDR analog photocell light sensor
  }

  if (readIRSensor) {
    readIRDetector(); // Arduino values for IR sensor connected to GPIO2
  }

  // Use the Serial Monitor keyboard to emulate sensor inputs to Arduino Tron sketch
  // This will allow you prototype the Arduino Tron IoT device before custom-designing and building printed circuit board (PCB)
  if (Serial.available() > 0) { // is a character available?
    readKeyboard = Serial.read(); // get keyboard character
    switchState = int( readKeyboard );
    switchState = switchState - 48; // 0-9:;<=>
  }

  if (readPushButton) {
    if (digitalRead(BUTTON5) == HIGH) { // read the pushButton state
      switchState = 1;
    }
    if (digitalRead(BUTTON6) == HIGH) {
      switchState = 2;
    }
    if (digitalRead(BUTTON7) == HIGH) {
      switchState = 3;
    }
    if (digitalRead(BUTTON8) == HIGH) {
      switchState = 4;
    }
  }

  if (ss.available() > 0) {
    if (gps.encode(ss.read()))
    {
      arduinoTronGPS();
    }
  }

  if (switchState != 0) {
    arduinoTronSend();
  }
}

void arduinoTronGPS()
{
  if (gps.location.isValid())
  {
    satellites = gps.satellites.value();
    sats = String(satellites, 2);

    shdop = gps.hdop.hdop();
    hdop = String(shdop, 6);

    latitude = gps.location.lat();
    lat = String(latitude , 6);
    longitude = gps.location.lng();
    lon = String(longitude , 6);

    speedmph = gps.speed.mph();
    speeds = String(speedmph, 2);

    altitudemeters = gps.altitude.meters();
    altitude = String(altitudemeters, 5);

    course = gps.course.deg();
    bearing = String(course, 5);

    calcDateTime(gps.date, gps.time);
  }
}

void arduinoTronSend()
{
  digitalWrite(LED0, HIGH); // turn the LED on
  
  // Explicitly set the ESP8266 to be a WiFi-client
  WiFi.mode(WIFI_STA);

  // Connect to WiFi network
  WiFi.begin(ssid, password);
  Serial.print("Executive Order Corporation - Arduino Tron ESP8266 MQTT Telemetry Transport Machine-to-Machine(M2M)/Internet of Things(IoT) ");
  Serial.println(loopCounter);
  loopCounter++;

  // Wait for connection
  while (WiFi.status() != WL_CONNECTED) {
    delay(200);
    Serial.print(".");
  }

  if (!client.connect(server, httpPort)) { // http server is running on default port 5055
    Serial.print("Connection Failed Status: ");
    Serial.println(WiFi.status());
    return;
  }

  Serial.println("Connected");
  client.print("GET /?id=" + id);
  client.print("&timestamp=" + timestamp);
  client.print("&lat=" + lat); // GPS location with LAT/LON position
  client.print("&lon=" + lon);
  client.print("&hdop=" + hdop);
  client.print("&sats=" + sats);
  client.print("&speed=" + speeds);
  client.print("&bearing=" + bearing);
  client.print("&altitude=" + altitude);
  // client.print("&accuracy=" + accuracy);
  client.print("&batt=" + batt);

  if (readDHT11Temp) {
    client.print("&temp=" + temp);
    client.print("&humidity=" + humidity);
  }

  switch (switchState) {
    case 1: // switchState NodeMCU gpio pinMode()
      client.print("&keypress=" + TYPE_KEYPRESS_1);
      break;
    case 2: // send IoT message data for keypress_2 (2.0)
      client.print("&keypress=" + TYPE_KEYPRESS_2);
      break;
    case 3:
      textMessage = "Server_Room_Temperature";
      client.print("&textMessage=" + textMessage);
      client.print("&alarm=" + ALARM_TEMPERATURE);
      break;
    case 4: // digitalRead GPIO15(D8) send values for textMessage, keypress and alarm
      textMessage = "Movement_Security_Alarm";
      client.print("&textMessage=" + textMessage);
      client.print("&keypress=" + TYPE_REED_RELAY);
      client.print("&alarm=" + ALARM_MOVEMENT);
      break;
    case 5:
      client.print("&light=" + light);
      textMessage = "Illuminance_Alert_Message";
      client.print("&textMessage=" + textMessage);
      client.print("&keypress=" + TYPE_KEYPRESS_1);
      break;
    case 6:
      client.print("&event=allEvents&protocol=osmand&outdated=false&valid=true&lat=38.85&lon=-84.35&altitude=27.0&speed=65.5&course=0.0");
      break;
    case 7: // send gryo sensor data
      client.print("&accel_x=-0.10&accel_y=-0.70&accel_z=9.79&gyro_x=1.0&gyro_y=-2.0&gyro_z=-3.0&magnet_x=-0.10&magnet_y=-0.70&magnet_z=9.81");
      break;
    case 8: // send IoT light sensor reading
      client.print("&light=" + light);
      client.print("&address=" + address);
      client.print("&keypress=" + TYPE_PROXIMITY);
      break;
    case 9: // send every sensor reading
      client.print("&event=allEvents&protocol=osmand&outdated=false&valid=true&lat=38.85&lon=-84.35&altitude=27.0&speed=0.0&course=0.0");
      client.print("&address=The_Street_Address&accuracy=0.0&network=null&batteryLevel=78.3&textMessage=Message_Sent&ir_temp=0.0&mbar=79.9");
      client.print("&accel_x=-0.01&accel_y=-0.07&accel_z=9.79&gyro_x=0.0&gyro_y=-0.0&gyro_z=-0.0&magnet_x=-0.01&magnet_y=-0.07&magnet_z=9.81");
      client.print("&light=91.0&alarm=Temperature&distance=1.6&totalDistance=3.79&motion=false");
      break;
    case 10: // Arduino values for keypress IR sensor connected to GPIO2
      client.print("&keypress=" + irkey); // keypress=irkey
      break;
    case 11: // Arduino event from IR sensor connected to GPIO2
      client.print("&event=" + irkey);
      break;
  }
  client.println(" HTTP/1.1");

  client.println("User-Agent: Arduino Tron ver " + ver);
  client.println("Content-Length: 0");

  client.println(); // empty line for apache server

  clientAvail = 0;
  // Wait 1 seconds for server to respond then read response
  while ((!client.available()) && (clientAvail < 1000)) {
    delay(1); // wait 1 seconds
    clientAvail++;
  }

  response = "";
  clientAvail = 0;
  while ((client.available()) && (clientAvail < 5))
  {
    String line = client.readStringUntil('\r');
    if ((line.indexOf("HTTP") == -1) && (clientAvail == 0)) {
      response = line;
    }
    if (line.length() == 1) {
      clientAvail = 6;
    }
    Serial.print(line);
    clientAvail++;
  }
  delay(10); //
  // client.stop();

  Serial.print("Connection Status: ");
  if (WiFi.status() == WL_CONNECTED) {
    Serial.println("Ok");
  } else {
    Serial.print("Error ");
    Serial.println(WiFi.status());
  }
  // WL_NO_SHIELD = 255
  // WL_IDLE_STATUS = 0
  // WL_NO_SSID_AVAIL = 1
  // WL_SCAN_COMPLETED = 2
  // WL_CONNECTED = 3
  // WL_CONNECT_FAILED = 4
  // WL_CONNECTION_LOST = 5
  // WL_DISCONNECTED = 6

  // WiFi.disconnect(); // DO NOT DISCONNECT WIFI IF YOU WANT TO LOWER YOUR POWER DURING LIGHT_SLEEP_T DELLAY !
  // wifi_set_sleep_type(LIGHT_SLEEP_T);
  digitalWrite(LED0, LOW); // turn the LED off
  if (response.length() > 0) {
    arduinoTronAction();
  }
  Serial.println("");
}

void arduinoTronAction() {
}

// Arduino values for the DHT11 digital temperature/humidity sensor; &temp= and &humidity= fields
// DHT11 digital temperature and humidity sensor pin Vout (sense)
void readDHT11() {
  byte _temp = 0;
  byte _humidity = 0;
  //int err = SimpleDHTErrSuccess; <-- uncommit for dht11

  //if ((err = dht11.read(pinDHT11, &_temp, &_humidity, NULL)) != SimpleDHTErrSuccess) { <-- uncommit for dht11
  //  Serial.print("Read DHT11 failed, error = ");
  //  Serial.println(err);
  //  return;
  //}

  // convert to Fahrenheit
  // float _tempF = 0; // (_temp * 9.0 / 5.0) + 32.0; <-- uncommit for dht11
  temp = "72.2"; // String(_tempF);
  humidity = "41.6"; // String(_humidity);
}

// Arduino values for the LDR analog photocell light sensor
// LDR and 10K pulldown resistor are connected to A0
void readLDRPhotocell() {
  int photocellReading = analogRead(ADC0); // read the LDR input on analog pin A0:
  float floatLight = photocellReading * (100.0 / 1023.0);

  if ((photocellLight > floatLight) && ((photocellLight - floatLight) > photocellChange)) {
    photocellLight = floatLight;
    light = String(floatLight);
    switchState = 5;
  }
  if ((photocellLight < floatLight) && ((floatLight - photocellLight) > photocellChange)) {
    photocellLight = floatLight;
    light = String(floatLight);
    switchState = 5;
  }
}

// Arduino values for IR sensor connected to GPIO2
void readIRDetector() {
  //if (!irrecv.decode(&results)) { // <-- uncommit for IR VS1838
  return;
  //} // <-- uncommit for IR VS1838
  //unsigned int ircode = results.value; // <-- uncommit for IR VS1838

  //irrecv.resume(); // Receive the next value // <-- uncommit for IR VS1838

  //if (ircode > 0xFFFFFF) { // IR Detector = REPEAT <-- uncommit for IR VS1838
  return;
  //} // <-- uncommit for IR VS1838

  switchState = 10;
  unsigned int ircodekey = 0; // = results.value; // <-- uncommit for IR VS1838
  switch (ircodekey) { // Declaring IR remote codes
    case 0xFFA25D:
      irkey = "POWER";
      switchState = 11;
      break;
    case 0xFF629D:
      irkey = "MODE";
      switchState = 11;
      break;
    case 0xFFE21D:
      irkey = "MUTE";
      switchState = 11;
      break;
    case 0xFF02FD:
      irkey = "FORWARD";
      switchState = 11;
      break;
    case 0xFFC23D:
      irkey = "BACKWARD";
      switchState = 11;
      break;
    case 0xFF22DD:
      irkey = "PLAY";
      switchState = 11;
      break;
    case 0xFFE01F:
      irkey = "EQ";
      switchState = 11;
      break;
    case 0xFFA857:
      irkey = "DOWN";
      switchState = 11;
      break;
    case 0xFF906F:
      irkey = "UP";
      switchState = 11;
      break;
    case 0xFF9867:
      irkey = "SWAP";
      switchState = 11;
      break;
    case 0xFFB04F:
      irkey = "U/SD";
      switchState = 11;
      break;
    case 0xFF30CF:
      irkey = "1.0";
      break;
    case 0xFF18E7:
      irkey = "2.0";
      break;
    case 0xFF7A85:
      irkey = "3.0";
      break;
    case 0xFF10EF:
      irkey = "4.0";
      break;
    case 0xFF38C7:
      irkey = "5.0";
      break;
    case 0xFF5AA5:
      irkey = "6.0";
      break;
    case 0xFF42BD:
      irkey = "7.0";
      break;
    case 0xFF4AB5:
      irkey = "8.0";
      break;
    case 0xFF52AD:
      irkey = "9.0";
      break;
    case 0xFF6897:
      irkey = "0.0";
      break;
  }
}

static void calcDateTime(TinyGPSDate &d, TinyGPSTime &t)
{
  String m1;
  String m2;

  if (!d.isValid())
  {
    Serial.print(F("********** "));
  }
  else
  {
    char sz1[32];
    sprintf(sz1, "%02d-%02d-%02d", d.year(), d.month(), d.day());
    m1 = String(sz1);
  }
  
  if (!t.isValid())
  {
    Serial.print(F("******** "));
  }
  else
  {
    char sz2[32];
    sprintf(sz2, "%02d:%02d:%02d.%02d", t.hour(), t.minute(), t.second(), t.centisecond());
    m2 = String(sz2);
  }

  timestamp = m1 + "_" + m2;
}