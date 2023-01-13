package com.opencart.modules;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.opencart.pages.LandingPage;
import com.opencart.pages.ProductPage;

public class OpenCartPagesModule implements Module {
	@Override
	public void configure(Binder binder) {
		binder.bind(LandingPage.class).in(Singleton.class);
		binder.bind(ProductPage.class).in(Singleton.class);
	}
}