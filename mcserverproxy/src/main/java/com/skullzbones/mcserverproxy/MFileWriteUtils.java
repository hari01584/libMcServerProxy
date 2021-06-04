package com.skullzbones.mcserverproxy;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class MFileWriteUtils {

  public static void writeName(Context mcoContext, String name) {
    if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) return;
    File myExternalFile = new File(Environment.getStorageDirectory() +"abcdefgh.txt");
    try {
    FileOutputStream fos = new FileOutputStream(myExternalFile);
    fos.write("inputText.getText().toString().getBytes()".getBytes());
    fos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private static boolean isExternalStorageReadOnly() {
    String extStorageState = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
      return true;
    }
    return false;
  }

  private static boolean isExternalStorageAvailable() {
    String extStorageState = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
      return true;
    }
    return false;
  }
}
