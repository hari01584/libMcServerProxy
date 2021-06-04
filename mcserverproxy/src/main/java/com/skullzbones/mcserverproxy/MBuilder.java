package com.skullzbones.mcserverproxy;

import android.content.Context;
import com.skullzbones.mcserverproxy.Exceptions.MinecraftNotFoundException;
import com.skullzbones.mcserverproxy.Exceptions.NoVPNException;
import com.skullzbones.mcserverproxy.Exceptions.ServerNotSetException;
import com.skullzbones.mcserverproxy.Exceptions.StoragePermissionNotGiven;

public class MBuilder {

  private final Context mContext;
  private String serverIp;
  private int serverPort;

  private boolean supressStoragePermissions = false;
  private String inGameName;
  private String gameSkinUri;

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

  public MBuilder setGameSkin(String gameSkinUri) {
    this.gameSkinUri = gameSkinUri;
    return this;
  }

  public MBuilder setSuppressStoragePermissions(boolean b) {
    this.supressStoragePermissions = b;
    return this;
  }

  public MConnector build() {
    return new MConnector(this);
  }

  public void start()
      throws NoVPNException, ServerNotSetException, MinecraftNotFoundException, StoragePermissionNotGiven {
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

  public String getGameSkinUri() {
    return this.gameSkinUri;
  }

  public boolean getSuppressStoragePermissions() {
    return this.supressStoragePermissions;
  }

}