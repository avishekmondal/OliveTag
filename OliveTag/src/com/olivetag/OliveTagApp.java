package com.olivetag;

import android.app.Application;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.utils.Logger;

public class OliveTagApp extends Application {

	private static String APP_NAMESPACE = "com.olivetag";

	public static int count = 0;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		// ACRA.init(this);

		Logger.DEBUG_WITH_STACKTRACE = true;

		Permission[] permissions = new Permission[] { Permission.EMAIL,
				Permission.PUBLIC_PROFILE };

		SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
				.setAppId(getString(R.string.facebook_app_id))
				.setNamespace(APP_NAMESPACE).setPermissions(permissions)
				.build();

		SimpleFacebook.setConfiguration(configuration);

		// UNIVERSAL IMAGE LOADER SETUP
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheInMemory(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.displayer(new FadeInBitmapDisplayer(300)).build();

		@SuppressWarnings("deprecation")
		ImageLoaderConfiguration config2 = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.defaultDisplayImageOptions(defaultOptions)
				.memoryCache(new WeakMemoryCache())
				.discCacheSize(100 * 1024 * 1024).build();

		ImageLoader.getInstance().init(config2);
		// END - UNIVERSAL IMAGE LOADER SETUP
	}

}
