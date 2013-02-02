package com.callinize.push;

import java.util.Calendar;

import urbanairship.Device;
import urbanairship.Push;
import urbanairship.UrbanAirshipClient;

public class UrbanAirship {
	public static void main(String args[]) {
		UrbanAirshipClient client = new UrbanAirshipClient("blak3r", "c@11ini7e");
	
		/*
		Device device = new Device();
		
		device.setiOSDeviceToken("iosDeviceToken1");
		
		device.setAlias("giltUser58");
		
		device.addTag("oregon_resident");
		device.addTag("pacific_time_zone");
		device.addTag("rebecca_black_fan");
		
		client.register(device);
		*/
		Push p = new Push();
		//p.addTag("test");
		//p.addTag("rebecca_black_fan");
		p.setMessage("Whattup broseph? Holla back at your boy blizack");
		/*
		Calendar scheduleFor = Calendar.getInstance();
		scheduleFor.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		scheduleFor.set(Calendar.HOUR_OF_DAY, 8);
		
		p.addScheduleFor(scheduleFor);
		*/
		client.sendPushNotifications(p);
	}
}
