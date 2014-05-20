package org.kll.trackersmsserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReciever extends BroadcastReceiver {

	// Get the object of SmsManager
	final SmsManager sms = SmsManager.getDefault();

	public void onReceive(Context context, Intent intent) {

		// Retrieves a map of extended data from the intent.
		final Bundle bundle = intent.getExtras();

		try {

			if (bundle != null) {

				final Object[] pdusObj = (Object[]) bundle.get("pdus");

				for (int i = 0; i < pdusObj.length; i++) {

					SmsMessage currentMessage = SmsMessage
							.createFromPdu((byte[]) pdusObj[i]);
					String phoneNumber = currentMessage
							.getDisplayOriginatingAddress();

					String senderNum = phoneNumber;
					String message = currentMessage.getDisplayMessageBody();
					
					Log.i("SmsReceiver", "senderNum: " + senderNum
							+ "; message: " + message);
					
					
					List<String> dataFromSMS = Arrays.asList(message.split("\\s*,\\s*"));
					if (dataFromSMS.get(0).equals("trackerapp")){
						if (dataFromSMS.get(1).equals("data")){
					String id = dataFromSMS.get(2);
					String name = dataFromSMS.get(3);
					String latitude = dataFromSMS.get(4);
					String longitude = dataFromSMS.get(5);
					String accuracy = dataFromSMS.get(6);
					String timestamp = dataFromSMS.get(7);
					
					Log.i("ID", id);
					Log.i("NAME", name);
					Log.i("LATITUDE", latitude);
					Log.i("LONGITUDE", longitude);
					Log.i("ACCURACY", accuracy);
					Log.i("TIMESTAMP", timestamp);					

					sendToServer(id, name, latitude, longitude, accuracy,timestamp);
						}
					}

				} // end for loop
			} // bundle is null

		} catch (Exception e) {
			Log.e("SmsReceiver", "Exception smsReceiver" + e);

		}
	}

	public ArrayList<String> parseSMS(String message) {

		return null;

	}

	public void sendToServer(String id, String name, String latitude,
			String longitude, String accuracy, String timestamp) {
		// 1. id of the user 'id'
		// 2. name of the user 'name'
		// 3.latitude 'X'
		// 4.longitude 'Y'
		// 5.accuracy of gps 'accuracy'
		// 6.timestamp 'timestamp'

		Log.i("ID", id);
		Log.i("NAME", name);
		Log.i("LATITUDE", latitude);
		Log.i("LONGITUDE", longitude);
		Log.i("ACCURACY", accuracy);
		Log.i("TIMESTAMP", timestamp);
		new SendData().execute(id, name, latitude, longitude, accuracy,
				timestamp);

	}

}