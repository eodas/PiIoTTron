package com.piiottron.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class User {

	private final Logger logger = LoggerFactory.getLogger(Device.class);

	public String login;
	public String name;
	public String email;
	public String phone;
	public String password;
	public boolean readonly;
	public boolean administrator;
	public boolean disabled;
	public int deviceLimit;
	public int userLimit;
	public boolean deviceReadonly;
	public boolean limitCommands;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public boolean isAdministrator() {
		return administrator;
	}

	public void setAdministrator(boolean administrator) {
		this.administrator = administrator;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public int getDeviceLimit() {
		return deviceLimit;
	}

	public void setDeviceLimit(int deviceLimit) {
		this.deviceLimit = deviceLimit;
	}

	public int getUserLimit() {
		return userLimit;
	}

	public void setUserLimit(int userLimit) {
		this.userLimit = userLimit;
	}

	public boolean isDeviceReadonly() {
		return deviceReadonly;
	}

	public void setDeviceReadonly(boolean deviceReadonly) {
		this.deviceReadonly = deviceReadonly;
	}

	public boolean isLimitCommands() {
		return limitCommands;
	}

	public void setLimitCommands(boolean limitCommands) {
		this.limitCommands = limitCommands;
	}

	public Logger getLogger() {
		return logger;
	}

}
