package fr.bdpv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class mainActivity extends Activity {

	private Button estimateButton;
	private Button nearInstallationButton;
	private Button aboutButton;
	private Context context;

	public static final void startActivity(Context context) {
		final Intent intent = new Intent(null, null, context,
				mainActivity.class);
		context.startActivity(intent);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		context = this;

		estimateButton = (Button) findViewById(R.id.estimate_production);
		estimateButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				PenteChoixActivity.startActivity(context);
			}
		});

		aboutButton = (Button) findViewById(R.id.about);
		aboutButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				AboutActivity.startActivity(context);
			}
		});
	}
}