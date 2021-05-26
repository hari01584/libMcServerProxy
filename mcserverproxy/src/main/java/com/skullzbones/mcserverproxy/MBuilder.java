package com.skullzbones.mcserverproxy;

import android.content.Context;
import com.skullzbones.mcserverproxy.Exceptions.MinecraftNotFoundException;
import com.skullzbones.mcserverproxy.Exceptions.NoVPNException;
import com.skullzbones.mcserverproxy.Exceptions.ServerNotSetException;

public class MBuilder {

  private final Context mContext;
  private String serverIp;
  private int serverPort;

  public MBuilder(Context context) {
    mContext = context;
  }

  public MBuilder setTargetServer(String ip, int port) {
    this.serverIp = ip;
    this.serverPort = port;
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
}