package com.swaglab.modules;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.qualizeal.dao.CSVData;
import com.qualizeal.dao.Data;
import com.swaglab.util.users.User;
import com.swaglab.util.users.UserFactory;

public class DataModule implements Module {

	@Override
	public void configure(Binder binder) {
		try {
			binder.bind(UserFactory.class).in(Singleton.class);
			binder.bind(Data.class).toInstance(new CSVData("data/swag.csv"));
		} catch (Exception e) {
			throw new RuntimeException("Unable to parse the CSV file");
		}
	}
}