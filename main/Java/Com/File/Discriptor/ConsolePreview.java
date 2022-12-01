package com.File.Discriptor;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ConsolePreview extends AppCompatActivity {
private WebView show;
private String Meta="";
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.console);
	Intent data = getIntent();
	Meta=(String) data.getExtras().get("Meta");
		show= findViewById(R.id.HTML);
		 show.setWebViewClient(new WebViewClient());
	      show.getSettings().setJavaScriptEnabled(true);
		  show.getSettings().setDomStorageEnabled(true);
		   show.getSettings().setBuiltInZoomControls(true);
		   show.getSettings().setAllowFileAccess(true);
	        show.getSettings().setAllowUniversalAccessFromFileURLs(true);
			show.getSettings().setAllowFileAccessFromFileURLs(true);
	   	 show.getSettings().setAllowContentAccess(true);
		
		    show.setWebChromeClient(new WebChromeClient(){
		 	 
			@Override
			public boolean onConsoleMessage(ConsoleMessage consoleMessage) { 
			   Toast.makeText(ConsolePreview.this,consoleMessage.message(),Toast.LENGTH_SHORT);
			  AlertDialog d= new AlertDialog.Builder(ConsolePreview.this).create();
			   d.setTitle("Console.log was called");
			   d.setMessage(consoleMessage.message());
			   d.show();
			  	return true;
			}

		 });
		 
		if(!Meta.isEmpty()) {
    //	String decoded=Base64.encodeToString(Meta.getBytes(),Base64.NO_PADDING);
		   // show.loadData(decoded,"text/html","base64");
		 String path= Environment.getExternalStorageDirectory()+"/HTML/MFile.html";
		   show.loadUrl("file:///"+Meta);
		}
	}
	@Override
	public void onBackPressed() {
	  if(show.canGoBack()){
		  show.goBack(); 
	  }else{
		super.onBackPressed();
		}
	}
}