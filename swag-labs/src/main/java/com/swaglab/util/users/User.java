package com.swaglab.util.users;

import com.swaglab.util.UserInfo;

import java.util.Properties;

public class User implements UserInfo {

	private final String userType;
	private final Properties properties;

	public User(String userType, Properties properties) {
		this.userType = userType;
		this.properties = properties;
	}

	@Override
	public String getUserName() {
		return properties.getProperty(userType + ".username");
	}

	@Override
	public String getPassword() {
		return properties.getProperty(userType + ".password");
	}
}