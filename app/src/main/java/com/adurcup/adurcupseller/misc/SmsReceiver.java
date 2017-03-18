package com.adurcup.adurcupseller.misc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.adurcup.adurcupseller.R;

/**
 * Created by kshivang on 20/12/16.
 *
 */

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = SmsReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                Object[] pdusObj = (Object[])bundle.get("pdus");
                if (pdusObj != null) {
                    for (Object aPdusObj : pdusObj) {
                        SmsMessage currentMessage;
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                            currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);
                        } else {
                            String format = bundle.getString("format");
                            currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj, format);
                        }

                        String senderAddress = currentMessage.getDisplayOriginatingAddress();
                        String message = currentMessage.getDisplayMessageBody();
                        if (!senderAddress.toLowerCase().contains("adurcp")) {
                            return;
                        }
                        String otp = getOTP(message);
                        if (otp != null && otp.length() > 0) {
                            Intent newIntent = new Intent(TAG)
                                    .putExtra(context.getString(R.string.key_otp), otp);
                            context.sendBroadcast(newIntent);
                        } else {
                            Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getOTP(String message) {
        return message.replaceAll("[^0-9]", "");
    }
}
