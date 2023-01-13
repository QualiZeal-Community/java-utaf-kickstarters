package com.swaglab.modules;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.swaglab.pages.ProductsPage;
import com.swaglab.pages.SignInPage;

public class SwagLabPagesModule implements Module {
	@Override
	public void configure(Binder binder) {
		binder.bind(SignInPage.class).in(Singleton.class);
		binder.bind(ProductsPage.class).in(Singleton.class);
	}
}