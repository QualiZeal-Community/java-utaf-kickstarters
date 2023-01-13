package com.swaglab.util.users;

import java.util.Properties;

public class LockedOutUser extends User {
	public LockedOutUser(Properties properties) {
		super("locked_out_user", properties);
	}
}