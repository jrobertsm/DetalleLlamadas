package com.jrsm.android.detallellamadas;

import android.database.Cursor;
import android.provider.CallLog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView tv1;
    String detalleLlamadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView) findViewById(R.id.tv1);
        detalleLlamadas = this.getCallDetails();
        tv1.setText(detalleLlamadas);
    }

    private String getCallDetails() {

        StringBuffer sb = new StringBuffer();
        Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null,
                null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        sb.append("DETALLE DE LLAMADAS");
        while (managedCursor.moveToNext()) {
            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = managedCursor.getString(duration);
            String dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "SALIDA";
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    dir = "ENTRADA";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "PERDIDA";
                    break;
            }
            sb.append("\nTeléfono:--- " + phNumber + " \nTipo de llamada:--- "
                    + dir + " \nFecha:--- " + callDayTime
                    + " \nDuración:--- " + callDuration + "segundos");
            sb.append("\n-----------------------------------------");
        }
        managedCursor.close();
        return sb.toString();

    }
}
