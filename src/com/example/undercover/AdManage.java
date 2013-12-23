package com.example.undercover;

import java.util.Date;

import android.app.Activity;
import net.youmi.android.AdManager;
import net.youmi.android.smart.SmartBannerManager;

public class AdManage {
	private static int lasttime = 0;

	public static void main(String[] args) {
	}

	public static void showBanner(Activity ac) {
		if (getTime() - lasttime < 10) {
			return;
		}
		AdManager.getInstance(ac).init("fc13c104e69f1319", "bdca02f379f4f5cf",
				false);
		SmartBannerManager.init(ac);
		SmartBannerManager.show(ac);
		lasttime = getTime();
	}

	public static int getTime() {
		Date date = new Date();
		return (int) (date.getTime() / 1000);
	}
}

