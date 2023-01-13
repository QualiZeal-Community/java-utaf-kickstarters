package com.opencart.modules;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.opencart.components.ProductThumbnail;
import com.opencart.components.SearchField;

public class OpenCartComponentsModule implements Module {
	@Override
	public void configure(Binder binder) {
		binder.bind(SearchField.class).in(Singleton.class);
		binder.bind(ProductThumbnail.class).in(Singleton.class);
	}
}