package com.opencart.modules;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.qualizeal.dao.CSVData;
import com.qualizeal.dao.Data;

public class DataModule implements Module {

	@Override
	public void configure(Binder binder) {
		try {
			binder.bind(Data.class).toInstance(new CSVData("data/opencart.csv"));
		} catch (Exception e) {
			throw new RuntimeException("Unable to parse the CSV file");
		}
	}
}