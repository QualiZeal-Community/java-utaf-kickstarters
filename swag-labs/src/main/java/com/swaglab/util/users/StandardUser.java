package com.swaglab.util.users;

import java.util.Properties;

public class StandardUser extends User {
	public StandardUser(Properties properties) {
		super("standard_user", properties);
	}
}