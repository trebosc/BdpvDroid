package fr.bdpv;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PenteSensorActivity extends Activity {

	private Button penteOkButton;
	private TextView degreView;
	private float previousDegree = 0;

	private Context context;

	// Le gestionnaire des capteurs
	private SensorManager sensorManager;
	// Notre capteur de la boussole numérique
	private Sensor sensor;

	public static final void startActivity(Context context) {
		final Intent intent = new Intent(null, null, context,
				PenteSensorActivity.class);
		context.startActivity(intent);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pente_sensor);
		context = this;

		penteOkButton = (Button) findViewById(R.id.PenteSensorOk);
		penteOkButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Ajouter la sauvegarde du degre de la pente
				OrientationActivity.startActivity(context);
			}
		});

		degreView = (TextView) findViewById(R.id.DegrePente);

		// Récupération du gestionnaire de capteurs
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		// Demander au gestionnaire de capteur de nous retourner les capteurs de
		// type boussole
		List<Sensor> sensors = sensorManager
				.getSensorList(Sensor.TYPE_ORIENTATION);
		// s'il y a plusieurs capteurs de ce type on garde uniquement le premier
		if (sensors.size() > 0) {
			sensor = sensors.get(0);
		}
	}

	// Notre listener sur le capteur de la boussole numérique
	private final SensorEventListener sensorListener = new SensorEventListener() {
		@Override
		public void onSensorChanged(SensorEvent event) {
			updateOrientation(event.values[SensorManager.DATA_Y]);
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};

	// Mettre à jour l'orientation
	protected void updateOrientation(float newDegree) {
		if (newDegree != previousDegree) {
			Log.d("pente", "previousDegree=" + previousDegree + ", newDegree="
					+ newDegree);
			previousDegree = newDegree;
			degreView.setText(String.valueOf(newDegree));
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Lier les évènements de la boussole numérique au listener
		sensorManager.registerListener(sensorListener, sensor,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onStop() {
		super.onStop();
		// Retirer le lien entre le listener et les évènements de la boussole
		// numérique
		sensorManager.unregisterListener(sensorListener);
	}

}