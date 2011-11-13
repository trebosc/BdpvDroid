package fr.bdpv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends Activity {
	Button backButton;
	TextView textAbout;
	Context context;

	public static final void startActivity(Context context) {
		final Intent intent = new Intent(null, null, context,
				AboutActivity.class);
		context.startActivity(intent);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		context = this;

		backButton = (Button) findViewById(R.id.AboutBack);
		backButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				mainActivity.startActivity(context);
			}
		});

		textAbout = (TextView) findViewById(R.id.TextAbout);
		textAbout.setText(getString(R.string.CommentAbout));// Html.fromHtml(getString(R.string.CommentAbout)));
		textAbout.setAutoLinkMask(Linkify.ALL);
		textAbout.setLinksClickable(true);
	}
}
