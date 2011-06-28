package com.voddler.android.tools.memorysucker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MemorySuckerActivity extends Activity {

	private static final String LARGE_STRING = "afkasjfalsjfiowqf0afj09209fj1092j0912fj0912fj1209jfj1029fj0291jf1290f1029jf0912jf0912jf09j2109fj2109jf0129jf0912j0912jf091jf920j1209fj1092j0192jf091j2f091jf09j1209fj1209fj0192jf0912jf091j2f09j2109fj1209fj1092jf0912jf091j2f091j09fj1209fj1092jf2091jf0912f091209fj0912jf091j2f09j12f09j12f09j1209fj1092fj0921jf0912jf091j2fj129f";
	
	private TextView mStatusText;
	private Button mActionButton;
	
	private Runtime mRuntime = Runtime.getRuntime();
	
	private SuckAsyncTask mSuckTask = new SuckAsyncTask();
	
	private boolean mIsSucking = false;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mStatusText = (TextView) findViewById(R.id.status);
        mActionButton = (Button) findViewById(R.id.button);
        
        mActionButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toggleSucking();
			}
		});
    }
    
    protected void toggleSucking() {
    	if (!mIsSucking) {
    		mIsSucking = true;
    		mSuckTask.execute();
        	updateStatus();
    	} else {
    		mSuckTask.cancel(true);
    	}
    }
    
    private void suckingCompleted() {
    	mIsSucking = false;
    	updateStatus();
    }
    
    private void updateStatus() {
    	if (!mIsSucking) {
    		mActionButton.setText("Start sucking");
    	} else {
    		mActionButton.setText("Stop sucking");
    	}
    }

	private class SuckAsyncTask extends AsyncTask<Void, Long, Void> {

		boolean isRunning = false;
		
    	List<String> sucking = new ArrayList<String>();
    	
		@Override
		protected Void doInBackground(Void... params) {
			long i = 0;
			while (!isCancelled()) {
				i++;
				String s = LARGE_STRING + (new Random()).nextInt();
				sucking.add(s);
				this.publishProgress(i);
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Long... values) {
			setStatus(values[0]);
		}

		

		@Override
		protected void onCancelled(Void result) {
			suckingCompleted();
		}

		@Override
		protected void onPostExecute(Void result) {
			suckingCompleted();
		}
		
    	
    }

	public void setStatus(Long i) {
		mStatusText.setText(String.format(
				"%sb (%s)",
				mRuntime.freeMemory(),
				i
		));
	}
}