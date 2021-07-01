package com.piiottron.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Device types allow you to store description and configuration information
 * that is common to all things associated with the same thing type. This
 * simplifies the management of things in the registry. For example, you can
 * define a LightBulb thing type. All things associated with the LightBulb thing
 * type share a set of attributes: serial number, manufacturer, and wattage.
 * 
 * When you create a thing of type LightBulb (or change the type of an existing
 * thing to LightBulb) you can specify values for each of the attributes defined
 * in the LightBulb thing type.
 */
public class Device {

	private final Logger logger = LoggerFactory.getLogger(Device.class);

	public String id;
	public String name;
	public String purpose;
	public String description;
	public String location;
	public String ipAddress;
	public String model;
	public String process;
	public String status;
	public String position;
	public String contact;
	public String category;
	public String message;
	public String lastUpdate;
	public boolean disabled;

	public Device() {
	}

	public static final String STATUS_UNKNOWN = "unknown";
	public static final String STATUS_ONLINE = "online";
	public static final String STATUS_OFFLINE = "offline";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdated(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public Logger getLogger() {
		return logger;
	}

	public void DeviceParser(String key, String value) {
		try {
			switch (key) {
			case "id":
			case "deviceid":
				setId(value);
				break;
			case "name":
				setName(value);
				break;
			case "purpose":
				setPurpose(value);
				break;
			case "description":
				setDescription(value);
				break;
			case "location":
				setLocation(value);
				break;
			case "ipAddress":
				setIpAddress(value);
				break;
			case "model":
				setModel(value);
				break;
			case "process":
				setProcess(value);
				break;
			case "status":
				setStatus(value);
				break;
			case "position":
				setPosition(value);
				break;
			case "contact":
				setContact(value);
				break;
			case "category":
				setCategory(value);
				break;
			case "message":
				setMessage(value);
				break;
			case "lastUpdate":
				setLastUpdated(parseDate(value));
				break;
			case "disabled":
				setDisabled(Boolean.parseBoolean(value));
				break;
			default:
				System.out.println("> Extended Event Token " + key + "=" + value);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	private String parseDate(String date) {
		String sdate = "";
		try {
			long ldate = Long.parseLong(date);
			sdate = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date(ldate * 1000));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return sdate;
	}

	@Override
	public String toString() {
		String s = name + " - " + id;
		if (status != null && !status.isEmpty()) {
			return s = s + " - " + status;
		}
		if (message != null && !message.isEmpty()) {
			return s = s + " - " + message;
		}
		return s;
	}
}
