package com.skullzbones.libmcproxy.sampleprojecT;

import android.Manifest;
import android.Manifest.permission;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.VpnService;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.skullzbones.mcserverproxy.Exceptions.MinecraftNotFoundException;
import com.skullzbones.mcserverproxy.Exceptions.NoVPNException;
import com.skullzbones.mcserverproxy.Exceptions.ServerNotSetException;
import com.skullzbones.mcserverproxy.Exceptions.StoragePermissionNotGiven;
import com.skullzbones.mcserverproxy.MConnector;
import com.skullzbones.mcserverproxy.MFileWriteUtils;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Permission;
import java.security.Permissions;

public class MainActivity extends AppCompatActivity {
  public static final int PICK_IMAGE = 1;
  private static final String TAG = "t/TestMainActivity";

  EditText ipedit,portedit,nameedit;
  TextView currentptext;
  Button pickimg, startsrv;

  String ip, port, name;
  String fpath;
  ActivityResultLauncher<Intent> openDirec;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    openDirec = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
          @Override
          public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == Activity.RESULT_OK){

            }
          }
        });


    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
          @Override
          public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
              Intent data = result.getData();
              Uri selectedImage = data.getData();
              fpath = selectedImage.toString();
              currentptext.setText(fpath);
            }
          }
        });

    ActivityResultLauncher<Intent> intentVpnSe = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
          @Override
          public void onActivityResult(ActivityResult result) {
          }
        });
    Intent i = VpnService.prepare(this);
    if(i!=null){
      intentVpnSe.launch(i);
    }

    ipedit = findViewById(R.id.iptext);
    portedit = findViewById(R.id.portedit);
    nameedit = findViewById(R.id.inname);
    currentptext = findViewById(R.id.curpath);
    pickimg = findViewById(R.id.pickimg);
    startsrv = findViewById(R.id.button3);

    getSharedPref();

    pickimg.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intent);
      }
    });

    startsrv.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        ip = ipedit.getText().toString();
        port = portedit.getText().toString();
        name = nameedit.getText().toString();

        storeSharedPref();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
          ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        //openDirectory(null);
        try {
          MConnector.with(MainActivity.this)
              .setTargetServer(ip, Integer.parseInt(port))
              .setInGameName(name)
              .setGameSkin(fpath)
              .start();
        } catch (NoVPNException | ServerNotSetException | MinecraftNotFoundException e) {
          e.printStackTrace();
        } catch (StoragePermissionNotGiven storagePermissionNotGiven) {
          storagePermissionNotGiven.printStackTrace();
          if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
          }
        }
      }
    });
  }

  public void openDirectory(Uri uriToLoad) {

    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
    //intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uriToLoad);
    openDirec.launch(intent);
  }




  private void storeSharedPref(){
    SharedPreferences.Editor editor = getSharedPreferences("mypref", MODE_PRIVATE).edit();
    editor.putString("ip", ip);
    editor.putString("port", port);
    editor.putString("name", name);
    editor.putString("uri",fpath);
    editor.apply();
  }

  private void getSharedPref(){
    SharedPreferences prefs = getSharedPreferences("mypref", MODE_PRIVATE);
    ip = prefs.getString("ip", "");
    port = prefs.getString("port", "19132");
    name = prefs.getString("name", "AName");
    fpath = prefs.getString("uri","");

    ipedit.setText(ip);
    portedit.setText(port);
    nameedit.setText(name);
    currentptext.setText(fpath);
  }
}