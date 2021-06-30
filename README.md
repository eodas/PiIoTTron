# Raspberry Pi IoT RPi (Pi IoT Tron) - Internet ofF Things (IoT) :: Internet of Things Drools-jBPM Expert System using Pi IoT Tron AI-IoTBPMServer

![alt tag](http://iotbpm.com/wp-content/uploads/2019/07/RaspberryPiArduino.3.png "Raspberry Pi IoT Tron")

### Raspberry Pi IoT (Pi IoT Tron) - Internet of Things (IoT)

Executive Order Corp – AI-IoTBPM Server is IoT Internet of Things Drools-BPM (Business Process Management) engine for IoT Device Orchestration and IoT device ontology (AI-IoT device awareness, state of being, knowledge of real-world objects, events, situations, and abstract concepts).
Executive Order Corp - AI-IoTBPM MQTT Telemetry Transport Machine-to-Machine(M2M) / Internet of Things (IoT).
AI-IoTBPM :: Executive Order AI-IoTBPM Tron Sensor Processor MQTT AI-IoTBPM Client using EOSpy AI-IoTBPM Drools-jBPM.

The power of the IoT (Internet of Things) device increases greatly when business process (jBPM) can use them to provide information
about our real-world as well as execute IoT (devices) as part of our business process. The jBPM-BPMN modular allow us to define 
both the Business Processes and IoT Devices Behavior at the same time using one diagram. With EOSpy adding Drools-jBPM to IoT,
we make the IoT devices “smart”. Moving beyond just collecting IoT data and transitioning, to leveraging the new wealth of IoT data, 
to improving the smart decision making is the key. Executive Order Pi AI-IoT Tron will help these IoT devices, environments 
and products to self-monitor, self-diagnose and eventually, self-direct.

AI-IoTBPM Tron allows you to send IoT sensor data and information directly to the AI-IoTBPMServer Drools-jBPM Expert System from the RPi device.
This provides a very lite streamline IoT to Drools-jBPM (Business Process Management) application process with sensor and GPS positioning information.
Executive Order Pi IoT Tron - This quick guide will help you install and configure the Pi IoT Tron - Executive Order Sensor Processor System components.
 
The Raspberry Pi IoT Tron Java Client application allows you to transmit automation and remote monitoring system information directly to the EOSPY Server 
from your own Java application. Monitor buildings, servers, vehicles and people from anywhere in the world and post there status and condition 
directly to the EOSPY Server from your own application. Stay connected and informed to what’s important. The EOSPY Executive Order Spy Java Client
is a GPS tracking automation and remote monitoring system that is a complete custom package for your business or office. Its wireless GPS tracking 
allows you to monitor your office, systems, personal property, and business from anywhere in the world. 

Receive information from any number of events like when an employee arrives on-site, where a vehicle is located and even receive remote ambient light intensity, 
temperature, humidity and other information. An alarm is triggered if the SOS button is pressed or the vehicle has exceeded the speed you defined. 
The RPi IoT Tron Java ties all location and environment monitoring information on one GPS Map Screen.

Monitor buildings, servers, vehicles and people from anywhere in the world and post there status and condition directly to the EOSPY Server from your own application. 
Stay connected and informed to what’s important. The EOSPY Executive Order Spy Java Client is a GPS tracking automation and remote monitoring system that is a complete
custom package for your business or office. Its wireless GPS tracking allows you to monitor your office, systems, personal property, and business from anywhere in the world. Receive information from any number of events like when an employee arrives on-site, where a vehicle is located and even receive remote ambient light intensity, temperature, humidity and other information. An alarm is triggered if the SOS button is pressed or the vehicle has exceeded the speed you defined. The EOSPY Java ties all location and environment monitoring information on one GPS Map Screen.

Executive Order Spy — This quick guide will help you install and configure the EOSPY – Executive Order Sensor Processor System components.

Executive Order Spy has several components:
1. The EOSPY AI-IoT, the Internet of Things Drools-jBPM Expert System.
2. The EOSPY Server, the live map GPS tracking Windows program.
3. The EOSPY Client, the Pi IoT Tron or Android application you install on an Android phone.
4. Configure GSM/GPRS/GPS Tracking Devices to use with EOSPY – Executive Order Sensor Processor System Server.

You can have an unlimited number and combination of EOSPY Clients and/or GPS tracking devices in use with EOSPY Server.
(Download EOSPY Server from our website http://www.eospy.com and Download EOSPY Client from the Google Store, standard or TI-SensorTag version)

(1) On the Raspberry Pi: Upgrade wiringPi to version 2.52 for the Raspberry Pi 4B.<br />
To upgrade wiringPi on the Raspberry Pi 4B:<br />
$ cd /tmp<br />
$ wget https://project-downloads.drogon.net/wiringpi-latest.deb<br />
$ sudo dpkg -i wiringpi-latest.deb<br />
<br />
Check wiringPi version with:<br />
$ gpio -v<br />
The wiringPi version 2.52 after the upgrade.<br />

(2) EOSPY AI-IoT – To install the EOSPY AI-IoT program on your Windows computer, download and install the "Eclipse IDE for Java Developers."
Use the Eclipse feature to Add new software, available on the Eclipse menu “Help -> Install New Software”. Select the “Add” option and install these packages:
1. Drools + jBPM Update Site 7.48.0 - http://downloads.jboss.org/jbpm/release/7.48.0.Final/updatesite/
2. BPMN2-Modeler 1.5.1 - http://download.eclipse.org/bpmn2-modeler/updates/photon/1.5.1/
3. Eclipse Marketplace: Code RASPIde: IDE for Raspberry Pi GPIO - CodeRASPIde is our small contribution to the IoT and Java Eclipse Community.
4. GIT the RPi IoT Tron AI-IoT from the source code repository, and Import Existing Maven project.

(3) EOSPY Server – To install the EOSPY Server program on your Windows computer, download the eospy.exe installation program and click on the eospy.exe install program.
EOSPY will by default install in the destination location: C:\Program Files\EOSpy Server and create a Start Menu folder: EOSPY on your desktop.
To start the EOSPY Server, click on the Eagle icon on your desktop. The EOSPY login and map will appear in your browser. The default email and password are both: admin

(4.A) EOSPY Client (Pi IoT Tron) – To install the EOSPY Pi IoT Tron application on your RPi Device, download the EOSPY Pi IoT Tron Sensor application from GIT.
Update the with WiFi network values for network SSID (name) and network password. Update the EOSPY Server IP address and unique unit ID values and add in EOSPY Server.
Also, you may use a DHT11 digital temperature and humidity sensor see the Pi IoT Tron Sensor sketch for more details and information.

(4.B) EOSPY Client (Android) – To install the EOSPY Client application on your phone, download the EOSPY application from the Google App Store.
To start the EOSPY Client, click on the Eagle icon on your phone. The EOSPY Client screen will appear. You can also download the EOSPY TI-SensorTag Client version.

To configure a new EOSPY Client you will need to enter the EOSPY Server address, Domain name, or IP address into the Server address.
Next add this device in the EOSPY Server by entering the Device name and the Device identifier. Swipe the Service Status On and YOU’RE DONE.
The device will appear on the EOSPY Server map the next time the EOSPY Client sends GPS position information. 

(4) GPS Tracking Devices – Many companies make various off-the-shelf GPS Tracking devices. Configuring these devices will vary a little from vendors.
First, add the new device with a unique identifier into the EOSPY – Executive Order Sensor Processor System Server.
Next, configure your device to use the appropriate EOSPY Server IP address and port number. If the device fails to report, check the IP Address and Device ID.

Device Unique Identifier<br />
For most devices you should use an IMEI (International Mobile Equipment Identity) number as a unique identifier.
However, some devices have vendor specific unique identifiers, for example TK-103 devices use 12-digit identifier.

If you don’t know your device identifier you can configure your device first and look at the server log file.
When the server receives a message from an unknown device it writes a record containing a unique identifier of a new device.
Look for records like “Unknown device – 123456789012345”; “Unknown device” 123456789012345 is your new Device identifier.

Address and Port<br />
To select the correct port, find your device in the list of supported devices. The Port column of the corresponding row contains default port numbers for your device.
If you want to use variations from the default ports you can change them in the configuration file.

EOSPY supports more than 90 GPS communication protocols and more than 800 models of GPS tracking devices from popular GPS vendors.
Review the list of supported devices for information about your GPS Tracking Device.

---

[] EOSPY currently supports these data fields in the Server Event data model:
id=6&event=allEvents&protocol=osmand&servertime=<date>&timestamp=<date>&fixtime=<date>&outdated=false&valid=true
&lat=38.85&lon=-84.35&altitude=27.0&speed=0.0&course=0.0&address=<street address>&accuracy=0.0&network=null
&batteryLevel=78.3&textMessage=Message_Sent&temp=71.2&ir_temp=0.0&humidity=0.0&mbar=79.9
&accel_x=-0.01&accel_y=-0.07&accel_z=9.79&gyro_x=0.0&gyro_y=-0.0&gyro_z=-0.0&magnet_x=-0.01&magnet_y=-0.07&magnet_z=9.81
&light=91.0&keypress=0.0&alarm=Temperature&distance=1.6&totalDistance=3.79&motion=false
* You can add additional fields to the data model and transmit via any device for EOSpy AI-IoT Drools-jBPM processing.

[] The EOSPY AI-IoT Pi IoT Tron Server software interface allows you to send commands with the EOSPY AI-IoT software to control external RPi connected devices.
The AI-IoT Pi IoT Tron Server software uses a WiFi Wireless Transceiver interface to control and interact with module sensors and remote controls devices. You can 
control any device form the EOSpy AI-IoT Pi IoT Tron Server software or stream any interface over the WiFi internet. With the EOSpy AI-IoT Pi IoT Tron Server software
you can automatically turn on lights, appliances, cameras, and open doors from the Drools-jBPM Expert System processing model.

[] The EOSpy AI-IoT Drools-jBPM Expert System provides sophisticated jBPM and drools processing. i.e. On a monitoring application: take an action if the temperature 
on the server room increases X degrees in Y minutes, where sensor readings are usually denoted by events example drools.drl file:

- Executive Order Corporation
- Copyright - 1978, 2021: Executive Order Corporation, All Rights Reserved
