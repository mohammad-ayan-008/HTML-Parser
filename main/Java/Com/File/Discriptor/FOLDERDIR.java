package com.File.Discriptor;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class FOLDERDIR extends AppCompatActivity {
	private ListView v;
	private Adapter ap;
	public String Path,sp= "";
	public ArrayList<String> f= new ArrayList();
    public  ArrayList<File> ls= new ArrayList();
     public String Name="";
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.folders);
		v = findViewById(R.id.LView);
		Path = sp= Environment.getExternalStorageDirectory() + "/HTML";
      try{
	   ls= getFolders(Path);
	 }catch(Exception e){
		 
	 }
	 
//	   ap= new ArrayAdapter(this,android.R.layout.simple_list_item_1,f);
        ap= new Adapter(this,f);
	    v.setAdapter(ap);
		v.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			
			
			@Override
	    	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	            
		    File f=  ls.get(arg2);
		     if(f.isDirectory()){
				try{
		    	ls.clear();
		     	sp+="/"+f.getName();
				 int l=  sp.lastIndexOf("/");
				  // Name+="/"+sp.substring(l,sp.length());
				    ls=getFolders(sp);
				
					ap.notifyDataSetChanged();
			
				  // Toast.makeText(getApplicationContext(),sp,Toast.LENGTH_SHORT).show();
				  }catch(Exception e){
					  
				  }
				 
				  
		   	 }else{
				if(f.getName().endsWith(".html")){
					Intent i= new Intent(FOLDERDIR.this,MainActivity.class);			
				
					
					 sp+="/" + f.getName();
				//	 Toast.makeText(getApplicationContext(),sp,Toast.LENGTH_SHORT).show();
			
					  i.putExtra("Name",sp);
				
					  sp=sp.substring(0,sp.lastIndexOf("/"));
					  
					    startActivity(i);
					  }
				}
		  	}
	 		
		});
	}

	ArrayList<File> getFolders(String P) throws Exception {
		File fs = new File(P);
	      f.clear();
		ArrayList<File> files = new ArrayList<>();
     
		File[] l = fs.listFiles();
	
		for (File fil : l) {
			files.add(fil);
			f.add(fil.getName());
		}

		return  files;
	}
  @Override
  public void onBackPressed() {
	  if(!sp.equals(Path)){
		
		  sp=sp.substring(0,sp.lastIndexOf("/"));
		  
			//  Toast.makeText(getApplicationContext(),sp+" Name "+Name,Toast.LENGTH_SHORT).show();
		try{
		 ls = getFolders(sp);
		 ap.notifyDataSetChanged();
		  }catch(Exception e){
			  
		  }
	  }else{
	  super.onBackPressed();
	  }
  }

  public class Adapter extends ArrayAdapter<String>{
	  Context c;
	  ArrayList<String> item;
	  public Adapter(Context ct,ArrayList<String> list){
		 super(ct,R.layout.listlayout,list);
		   this.c=ct;
		    this.item=list;
			
	  }
	    
	    
	  
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
	  LayoutInflater inf =(LayoutInflater) getSystemService(c.LAYOUT_INFLATER_SERVICE);
	     View v= inf.inflate(R.layout.listlayout,parent,false);
	   
	   
	   TextView lst= v.findViewById(R.id.TextShow);
	    lst.setText(item.get(position));
		
		return v;
	}
	

  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu arg0) {
	  MenuInflater inf= getMenuInflater();
	   inf.inflate(R.menu.menu2,arg0);
	  return true;
  }
  @Override
  public boolean onOptionsItemSelected(MenuItem arg0) {
	    switch (arg0.getItemId()){
			
		 case R.id.Files:
		   Do_Acess();
		   break;	
		}
	  
	  return super.onOptionsItemSelected(arg0);
  }
  
  void Do_Acess(){
	  final Dialog d= new Dialog(this);
	  d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	   d.setContentView(R.layout.dialoglayout);
	 final Button btn= d.findViewById(R.id.Done);
	  final  EditText nameF= d.findViewById(R.id.Filename);
		 d.show();
	    btn.setOnClickListener(new View.OnClickListener(){
			
			
			@Override
			public void onClick(View arg0) {
		
			try{
		FileOutputStream fos = new FileOutputStream(sp+"/"+nameF.getText().toString()+".html");
		 fos.write("<!Doctype html>".getBytes());
		   fos.flush();
			 ls= getFolders(sp);
			 ap.notifyDataSetChanged();
			  }catch(Exception e){
				  
			  }
			
			   d.dismiss();
			   	
			}

		});
	   
  }
}