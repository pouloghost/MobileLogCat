package gt.utils.log.mobilelogcat.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import gt.utils.log.mobilelogcat.R;
import gt.utils.log.mobilelogcat.component.LogActivity;
import gt.utils.log.mobilelogcat.component.LogService;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, LogService.class);
        startService(intent);

        findViewById(R.id.generate_log_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                for (int i = 0; i < 100; ++i) {
//                    if (i % 3 == 0) {
//                        Exception e = new Exception();
//                        e.printStackTrace();
//                    }
//                    if (i % 3 == 1) {
//                        Log.w("LogCatManager", "warn " + i);
//                    }
//                    if (i % 3 == 2) {
//                        Log.d("LogCatManager", "debug " + i);
//                    }
//                }
                Log.d("LogCatManager", "line 1\n line2\n line3");
            }
        });

        findViewById(R.id.show_log_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LogActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
