package com.piiottron.pi4j;

import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
// import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
// import com.pi4j.io.gpio.event.GpioPinListenerDigital;

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

/**
 * Main window implementation for the Raspberry GPIO example
 */
public class Pi4jGPIO {
	private static Pi4jGPIO PI4JGPIO_INSTANCE = null;
	private boolean pi4jActive = false;

	GpioController gpio;

	// provision led gpio pins as an output pins and blink
	GpioPinDigitalOutput redled1; // LED GPIO pin GPIO.21
	GpioPinDigitalOutput redled2; // LED GPIO pin GPIO.22
	GpioPinDigitalOutput yellowled1; // LED GPIO pin GPIO.26
	GpioPinDigitalOutput yellowled2; // LED GPIO pin GPIO.23
	GpioPinDigitalOutput greenled1; // LED GPIO pin GPIO.27
	GpioPinDigitalOutput greenled2; // LED GPIO pin GPIO.0

	// provision switch gpio pins as an input pin with its internal pull down
	// resistor enabled
	GpioPinDigitalInput button1; // Button GPIO pin GPIO.24
	GpioPinDigitalInput button2; // Button GPIO pin GPIO.1

	// provision buzzer gpio pin as an output pins and buzz
	GpioPinDigitalOutput buzzer1; // Buzzer GPIO pin GPIO.28

	public Pi4jGPIO() {
		Pi4jGPIO.PI4JGPIO_INSTANCE = this;
	}

	public static Pi4jGPIO getInstance() {
		return PI4JGPIO_INSTANCE;
	}

	public boolean isPi4jActive() {
		return pi4jActive;
	}

	public void setPi4jActive(boolean pi4jActive) {
		this.pi4jActive = pi4jActive;
	}

	// LED GPIO pin GPIO.21
	public void redled1On() {
		if (pi4jActive) {
			redled1.setState(PinState.HIGH);
		}
	}

	public void redled1Off() {
		if (pi4jActive) {
			redled1.setState(PinState.LOW);
		}
	}

	public void redled1Blink(long delay, long duration) {
		if (pi4jActive) {
			redled1.blink(delay, duration);
		}
	}

	// LED GPIO pin GPIO.22
	public void redled2On() {
		if (pi4jActive) {
			redled2.setState(PinState.HIGH);
		}
	}

	public void redled2Off() {
		if (pi4jActive) {
			redled2.setState(PinState.LOW);
		}
	}

	public void redled2Blink(long delay, long duration) {
		if (pi4jActive) {
			redled2.blink(delay, duration);
		}
	}

	// LED GPIO pin GPIO.26
	public void yellowled1On() {
		if (pi4jActive) {
			yellowled1.setState(PinState.HIGH);
		}
	}

	public void yellowled1Off() {
		if (pi4jActive) {
			yellowled1.setState(PinState.LOW);
		}
	}

	public void yellowled1Blink(long delay, long duration) {
		if (pi4jActive) {
			yellowled1.blink(delay, duration);
		}
	}

	// LED GPIO pin GPIO.23
	public void yellowled2On() {
		if (pi4jActive) {
			yellowled2.setState(PinState.HIGH);
		}
	}

	public void yellowled2Off() {
		if (pi4jActive) {
			yellowled2.setState(PinState.LOW);
		}
	}

	public void yellowled2Blink(long delay, long duration) {
		if (pi4jActive) {
			yellowled2.blink(delay, duration);
		}
	}

	// LED GPIO pin GPIO.27
	public void greenled1On() {
		if (pi4jActive) {
			greenled1.setState(PinState.HIGH);
		}
	}

	public void greenled1Off() {
		if (pi4jActive) {
			greenled1.setState(PinState.LOW);
		}
	}

	public void greenled1Blink(long delay, long duration) {
		if (pi4jActive) {
			greenled1.blink(delay, duration);
		}
	}

	// LED GPIO pin GPIO.0
	public void greenled2On() {
		if (pi4jActive) {
			greenled2.setState(PinState.HIGH);
		}
	}

	public void greenled2Off() {
		if (pi4jActive) {
			greenled2.setState(PinState.LOW);
		}
	}

	public void greenled2Blink(long delay, long duration) {
		if (pi4jActive) {
			greenled2.blink(delay, duration);
		}
	}

	// This interface is extension of GpioPin interface with operation to read
	// digital states.
	public void gpioSwitchState() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (pi4jActive) {
					if (button1.getState().isHigh()) {
						System.out.println("button1.getState().isHigh()");
					}
					if (button2.getState().isHigh()) {
						System.out.println("button2.getState().isHigh()");
					}
					try {
						Thread.sleep(900L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	/**
	 * This code demonstrates how to perform simple blinking LED logic of a GPIO pin
	 * on the Raspberry Pi using the Pi4J library.
	 */
	public void gpioStartController() {
		// create gpio controller
		gpio = GpioFactory.getInstance();

		// provision led gpio pins as an output pins and blink
		redled1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_21);
		redled2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_22);
		yellowled1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_26);
		yellowled2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_23);
		greenled1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27);
		greenled2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00);

		// provision switch gpio pins as an input pin with its internal pull down
		// resistor enabled
		button1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_24, PinPullResistance.PULL_DOWN);
		button2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_DOWN);

		// provision buzzer gpio pin as an output pins and buzz
		buzzer1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_28);

		gpioSwitchState(); // This interface is extension of GpioPin interface with operation to read
							// digital states.
		pi4jActive = true;
	}

	public void gpioShutdown() {
		pi4jActive = false;
		// stop all GPIO activity/threads (this method will forcefully shutdown all GPIO
		// monitoring threads and scheduled tasks) Pi4J GPIO controller
		gpio.shutdown(); // Implement this method call if you wish to terminate the Pi4J GPIO controller
		// System.exit(0);
	}
}
