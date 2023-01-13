package com.swaglab.util.users;

import com.google.inject.Inject;
import com.qualizeal.configurations.TestConfiguration;

public class UserFactory {

	@Inject
	TestConfiguration configuration;

	public User getUserByType(SwagUserType swagUserType) {
		switch (swagUserType) {
			case PROBLEM:
				return new ProblemUser(configuration.getProperties());
			case STANDARD:
				return new StandardUser(configuration.getProperties());
			case LOCKED_OUT:
				return new LockedOutUser(configuration.getProperties());
			default:
				throw new RuntimeException("User is not defined");
		}
	}
}