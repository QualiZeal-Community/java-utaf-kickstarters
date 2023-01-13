package com.swaglab.modules;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.qualizeal.module.AutomationModule;

public class SwagLabAutomationModule implements Module {
	@Override
	public void configure(Binder binder) {
		binder.bind(AutomationModule.class).in(Singleton.class);
		binder.bind(DataModule.class).in(Singleton.class);
		binder.bind(SwagLabPagesModule.class).in(Singleton.class);
	}
}