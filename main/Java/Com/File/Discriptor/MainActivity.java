package com.File.Discriptor;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.constraintlayout.helper.widget.MotionEffect;
import androidx.core.text.HtmlCompat;
import com.File.Discriptor.CustomEditText;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
	public CustomEditText text;
	public TextView textShow;
	public String max = "";
	public WebView v;
	public String path = "";
	public HashMap<String, Integer> map;
	public boolean enter = false;
	public Editable eb;
	public ScaleGestureDetector detector;
	public float size = 0;
	public String NameOfFile = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		text = findViewById(R.id.Edit);

		Intent getFromBack = getIntent();
		NameOfFile = (String) getFromBack.getExtras().get("Name");

		path = Environment.getExternalStorageDirectory() + "/HTML/";
		File f = new File(path);
		try {
			f.mkdirs();
		} catch (Exception e) {

		}

		if (text.getText().toString().isEmpty()) {
			load();
		}
	
		size = text.getTextSize();
		eb = text.getEditableText();
		
		map = new HashMap<>();
		map.put("function", Color.YELLOW);
		map.put("onclick", Color.BLUE);
		map.put("<script>", Color.GREEN);
		map.put("</script>", Color.GREEN);
		map.put("<style>", Color.BLUE);
		map.put("</style>", Color.BLUE);
		map.put("document", Color.MAGENTA);
		map.put("if", Color.RED);
		map.put("else if", Color.LTGRAY);
		map.put("var", Color.BLUE);
		map.put("<html>", Color.argb(200, 200, 200, 200));
		map.put("</html>", Color.argb(200, 200, 200, 200));
		map.put("<!DOCTYPE html>", Color.argb(200, 50, 50, 0));
		map.put("prompt", Color.LTGRAY);
		map.put("alert", Color.RED);
		map.put("addEventListener", Color.parseColor("#FFBF00"));
		map.put("console.log", Color.parseColor("#FFAF00"));
		map.put("px", Color.parseColor("#ADD8E6"));

		set(text.getEditableText());

		text.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
					//	TextView textviewmessage = (TextView) findViewById(R.id.textViewmessage);
					//	textviewmessage.setText("You hit 'Enter' key");
					set(text.getEditableText());

					return true;
				}

				return false;

			}
		});

		text.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable editable) {

				eb = editable;

			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu arg0) {
		MenuInflater i = getMenuInflater();
		i.inflate(R.menu.menu, arg0);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem arg0) {
		switch (arg0.getItemId()) {
		case R.id.item3:

			Intent i = new Intent(MainActivity.this, ConsolePreview.class);
			i.putExtra("Meta", NameOfFile);
			startActivity(i);

			break;
		case R.id.item:
			set(eb);
			new assist().execute("");
			break;

		case R.id.add:
			size += 1;
			text.setTextSize(size);
			break;

		case R.id.sub:
			size -= 1;
			text.setTextSize(size);
			break;
		}

		return super.onOptionsItemSelected(arg0);
	}

	public class assist extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String[] arg0) {

			try {
				FileOutputStream fos = new FileOutputStream(NameOfFile);
				fos.write(text.getText().toString().getBytes());
				fos.flush();
				fos.close();
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void arg0) {
			super.onPostExecute(arg0);
		}

	}

	@Override
	protected void onDestroy() {
		new assist().execute("");
		super.onDestroy();
	}

	void load() {
		String data = "";
		try {
			BufferedInputStream bff = new BufferedInputStream(new FileInputStream(NameOfFile));
			int r = bff.read();
			while (r != -1) {
				data += (char) r;
				r = bff.read();
			}
			text.setText(data);

		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
		}
	}

	void set(Editable editable) {
		String string = editable.toString();
		String[] split = string.split("\\s");

		int startIndex = 0;
		for (int i = 0; i < split.length; i++) {
			String s = split[i];
			if (map.containsKey(s)) {
				int index = string.indexOf(s, startIndex);
				int color = map.get(s);
				editable.setSpan(new ForegroundColorSpan(color), index, index + s.length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

				startIndex = index + s.length();
			}

		}
	}

}