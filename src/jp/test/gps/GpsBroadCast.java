package jp.test.gps;

import java.util.List;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class GpsBroadCast extends Activity implements LocationListener{
	private LocationManager mLocationManager = null;
	private int count = 0;
	private String bestProvider = null;
	private int minSecond = 10000;
	private int minDistance = 10;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
               
        this.mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
               
        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        //criteria.setAltitudeRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);
        this.bestProvider = this.mLocationManager.getBestProvider(criteria, true);
        
        ((TextView) findViewById(R.id.main_tv_bestprovider)).setText("最適なロケーションプロバイダ = "
                + this.bestProvider);
        
        this.mLocationManager.requestLocationUpdates(this.bestProvider, 
        		this.minSecond, this.minDistance, this);
        Location location = this.mLocationManager.getLastKnownLocation(this.bestProvider);
        this.displayLocation(location);
    }
    
	protected void onResume() {
		super.onResume();
		this.mLocationManager.requestLocationUpdates(this.bestProvider, 
				this.minSecond, this.minDistance, this);
	}
	
	protected void onPause() {
		super.onPause();
		//this.mLocationManager.removeUpdates(this);
	}
    
    private void displayLocation(Location loc){
    	if(loc == null){
    		return; 
    	}
    	((TextView) findViewById(R.id.main_tv_latitude)).setText("latitude ="
    			+ loc.getLatitude());
    	((TextView) findViewById(R.id.main_tv_longitude)).setText("longitude ="
    			+ loc.getLongitude());
    	((TextView) findViewById(R.id.main_tv_altitude)).setText("altitude ="
    			+ loc.getAltitude());
    	((TextView) findViewById(R.id.main_tv_supprimental_info))
    		.setText("count = "+ count +"\n" +
    				"Time = " + loc.getTime() + "\n"
    			+ loc.getAltitude());
    }

	@Override
	public void onLocationChanged(Location location) {
		// TODO 自動生成されたメソッド・スタブ
		this.count++;
		this.displayLocation(location);
		Log.d("change"," "+count);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}