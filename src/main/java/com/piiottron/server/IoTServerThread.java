package com.piiottron.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.piiottron.bpmrules.jBPMRules;
import com.piiottron.database.DataManager;
import com.piiottron.model.Event;

public class IoTServerThread extends Thread {
	jBPMRules jbpmRules;

	PrintStream out;
	String response = "";
	IoTServer ws = null;
	Socket socket = null;
	DataManager dataManager = null;

	private final Logger logger = LoggerFactory.getLogger(IoTServerThread.class);

	public IoTServerThread(Socket socket, IoTServer ws, jBPMRules jbpmRules, DataManager dataManager) {
		this.ws = ws;
		this.socket = socket;
		this.jbpmRules = jbpmRules;
		this.dataManager = dataManager;
		setName("IoT Server Thread");

		start();
	}

	@Override
	public void run() {
		try {
			String ipAddress = socket.getInetAddress().getHostAddress();
			System.out.println("> TRACE IoT device connected from IP " + ipAddress);

			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintStream(socket.getOutputStream());
			String request = "";
			while (true) {
				request = in.readLine();
				System.out.println("> TRACE " + request);

				boolean moreLines = true;
				while (moreLines) {
					String reqtmp = in.readLine();

					if ((reqtmp.equals(null)) || (reqtmp.equals(""))) {
						break;
					}
				}

				Event event = new Event();
				String[] req = Pattern.compile(" ").split(request);

				if (req[0].equals("GET") || req[0].equals("POST")) {

					String arg = req[1].substring(req[1].indexOf('?') + 1);
					String[] tokens = arg.split("&");

					for (String token : tokens) {
						try {
							String key = token.substring(0, token.indexOf('='));
							String value = token.substring(token.indexOf('=') + 1);
							event.add(key, value);

						} catch (IndexOutOfBoundsException e) {
							System.err.println("Error: Unexpected exception caught: " + e.getMessage());
							e.printStackTrace();
						}
					}
					
					if (dataManager.selectDevice(event.getId())) {
						String deviceProcess = dataManager.selectDeviceProcess(event.getId());
						if ((deviceProcess != null) && (!deviceProcess.isEmpty())) {
							event.setProcess(deviceProcess);
						}

						dataManager.updateDevice(event.getId(), ipAddress, event.getDeviceTime()); 
					} else {
						System.err.println("Error: Unknown Device id" + event.getId() + "  IP Address " + ipAddress);
					}
					
					dataManager.insterEvent(event);

					// Set response jBPM Global Variable List
					// kcontext.getKnowledgeRuntime().setGlobal("response", "");
					response = jbpmRules.receive(event);
					if ((response != null) && (response.length() > 0)) {
						out.println(response);
						System.out.println("> TRACE RSP " + response);
					}
					sendHttpTextResp(200, response);
				}
			}

		} catch (Exception localException3) {
			ws.decConnection();
		}
	}

	private void sendHttpTextResp(int status, String response) throws IOException {
		String headerText = "OK";

		switch (status) {
		case 200:
			headerText = "OK";
			break;
		case 404:
			headerText = "File not found";
			break;
		case 401:
			headerText = "Unauthorized";
			break;
		default:
			headerText = "OK";
		}

		out.println("HTTP/1.1 " + status + " " + headerText);
		out.println("Content-Length: 0");
		
		if(response == null || response.length() == 0) {
			// No trailer
		} else {
			out.println(response); 
		}
		out.println(""); // do not forget this one

		out.flush();
		out.close();
		socket.close();
	}
}
