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
	public boolean disabled;
	public boolean administrator;
	public boolean deviceReadonly;

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

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isAdministrator() {
		return administrator;
	}

	public void setAdministrator(boolean administrator) {
		this.administrator = administrator;
	}

	public boolean isDeviceReadonly() {
		return deviceReadonly;
	}

	public void setDeviceReadonly(boolean deviceReadonly) {
		this.deviceReadonly = deviceReadonly;
	}

	public Logger getLogger() {
		return logger;
	}

}
