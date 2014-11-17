package cn.centurywar.undercover;

import java.util.Date;

import android.app.Activity;

public class AdManage {
	private static int lasttime = 0;
	public static boolean showad=true;

	public static void main(String[] args) {
	}

	public static void showBanner(Activity ac) {
		if (showad == false) {
			return;
		}
		if (getTime() - lasttime < 10) {
			return;
		}
//		TODO::YOUMI
//		AdManager.getInstance(ac).init("fc13c104e69f1319", "bdca02f379f4f5cf",
//				false);
//		SmartBannerManager.init(ac);
//		SmartBannerManager.show(ac);
//		lasttime = getTime();
	}

	public static int getTime() {
		Date date = new Date();
		return (int) (date.getTime() / 1000);
	}
}

