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
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class OrientationActivity extends Activity {
	private Button orientationBackButton;
	private Button orientationValiderButton;
	private TextView degreView;
	private ImageView boussoleView;
	private Context context;
	private float previousDegree = 0;
	private float previousDegreeImage = 0;

	public static final void startActivity(Context context) {
		final Intent intent = new Intent(null, null, context,
				OrientationActivity.class);
		context.startActivity(intent);
	}

	// Le gestionnaire des capteurs
	private SensorManager sensorManager;
	// Notre capteur de la boussole numérique
	private Sensor sensor;

	// Notre listener sur le capteur de la boussole numérique
	private final SensorEventListener sensorListener = new SensorEventListener() {
		@Override
		public void onSensorChanged(SensorEvent event) {
			updateOrientation(event.values[SensorManager.DATA_X]);
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orientation);
		context = this;

		orientationBackButton = (Button) findViewById(R.id.OrientationBack);
		orientationBackButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// PenteChoixActivity.startActivity(context);
				updateOrientation(previousDegree - 15);
			}
		});

		orientationValiderButton = (Button) findViewById(R.id.OrientationValider);
		orientationValiderButton
				.setOnClickListener(new Button.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO lancer l'appel au serveur et/ou passer à l'écran
						// de résultat de la simulation
						updateOrientation(previousDegree + 15);

					}
				});

		boussoleView = (ImageView) findViewById(R.id.Boussole);
		boussoleView.setImageResource(R.drawable.boussole);

		degreView = (TextView) findViewById(R.id.DegreBoussole);
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

	// Mettre à jour l'orientation
	protected void updateOrientation(float newDegree) {
		float newDegreeRotationImage = 0;
		if (newDegree != previousDegree) {
			if (newDegree > previousDegree) {
				// degree augmente
				if (newDegree - previousDegree < 180) {
					// on tourne le terminal dans le sens des aiguilles d'une
					// montre
					newDegreeRotationImage = previousDegreeImage
							- (newDegree - previousDegree);
				} else {
					// on tourne le terminal dans le sens inverse des aiguilles
					// d'une montre et on passe par l'alignement au pôle nord.
					// On veut éviter que l'aiguille fasse un tour complet.
					newDegreeRotationImage = 360 - newDegree + previousDegree;
				}
			} else {
				// degree diminue
				if (previousDegree - newDegree < 180) {
					// on tourne le terminal dans le sens inverse des aiguilles
					// d'une montre.
					newDegreeRotationImage = previousDegreeImage
							+ (previousDegree - newDegree);
				} else {
					// on tourne le terminal dans le sens des aiguilles d'une
					// montre et on passe par le pôle nord.
					// on veut éviter le tour complet.
					newDegreeRotationImage = previousDegreeImage
							- (360 - previousDegree + newDegree);
				}
			}
			Log.d("boussole", "f previousDegree=" + previousDegree
					+ ", newDegree=" + newDegree + ", newDegreeRotationImage="
					+ newDegreeRotationImage);
			rotate(newDegreeRotationImage);
			previousDegree = newDegree;
			degreView.setText(String.valueOf(newDegree) + " / "
					+ String.valueOf(newDegreeRotationImage));
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

	protected void rotate(float degreeRotation) {
		Log.d("boussole", "rotation de " + previousDegreeImage + " à "
				+ degreeRotation);
		RotateAnimation boussoleRotation = new RotateAnimation(
				previousDegreeImage, degreeRotation,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		boussoleRotation.setFillAfter(true);
		boussoleRotation.setDuration(100);
		boussoleRotation.setZAdjustment(Animation.ZORDER_NORMAL);
		boussoleView.setAnimation(boussoleRotation);
		boussoleView.startAnimation(boussoleRotation);
		boussoleView.setDrawingCacheEnabled(true);
		previousDegreeImage = degreeRotation;
	}
}
