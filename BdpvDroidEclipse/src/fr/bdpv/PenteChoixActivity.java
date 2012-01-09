package fr.bdpv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PenteChoixActivity extends Activity {

	private Button penteChoixOuiButton;
	private Button penteChoixNonButton;

	private Context context;

	public static final void startActivity(Context context) {
		final Intent intent = new Intent(null, null, context,
				PenteChoixActivity.class);
		context.startActivity(intent);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pente_choix);
		context = this;

		penteChoixOuiButton = (Button) findViewById(R.id.PenteChoixOui);
		penteChoixOuiButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				PenteSensorActivity.startActivity(context);
			}
		});

		penteChoixNonButton = (Button) findViewById(R.id.PenteChoixNon);
		penteChoixNonButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO ajouter le code de récupération de la pente par
				// l'utilisateur
				OrientationActivity.startActivity(context);
			}
		});
	}

}
