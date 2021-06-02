package com.skullzbones.mcserverproxy;

import android.content.Context;
import com.skullzbones.mcserverproxy.Exceptions.MinecraftNotFoundException;
import com.skullzbones.mcserverproxy.Exceptions.NoVPNException;
import com.skullzbones.mcserverproxy.Exceptions.ServerNotSetException;

public class MBuilder {

  private final Context mContext;
  private String serverIp;
  private int serverPort;

  private boolean supressStoragePermissions = false;
  private String inGameName;
  private String gameSkinBase64;

  public MBuilder(Context context) {
    mContext = context;
  }

  public MBuilder setTargetServer(String ip, int port) {
    this.serverIp = ip;
    this.serverPort = port;
    return this;
  }

  public MBuilder setInGameName(String name) {
    this.inGameName = name;
    return this;
  }

  public MBuilder setGameSkin(String gameSkinBase64) {
    this.gameSkinBase64 = gameSkinBase64;
    return this;
  }

  public MBuilder setSuppressStoragePermissions(boolean b) {
    this.supressStoragePermissions = b;
    return this;
  }

  public MConnector build() {
    return new MConnector(this);
  }

  public void start() throws NoVPNException, ServerNotSetException, MinecraftNotFoundException {
    new MConnector(this).start();
  }

  public Context getContext() {
    return mContext;
  }

  public String getServerIp() {
    return this.serverIp;
  }

  public int getServerPort() {
    return this.serverPort;
  }

  public String getInGameName() {
    return this.inGameName;
  }

  public String getGameSkinBase64() {
    return this.gameSkinBase64;
  }

  public boolean getSuppressStoragePermissions() {
    return this.supressStoragePermissions;
  }

}