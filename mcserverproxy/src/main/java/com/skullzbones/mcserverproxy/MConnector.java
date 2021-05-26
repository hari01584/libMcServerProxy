package com.skullzbones.mcserverproxy;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.VpnService;

import com.skullzbones.mcserverproxy.Exceptions.MinecraftNotFoundException;
import com.skullzbones.mcserverproxy.Exceptions.NoVPNException;
import com.skullzbones.mcserverproxy.Exceptions.ServerNotSetException;
import com.skullzbones.mcserverproxy.Networks._VPNReplacerService;

public class MConnector {

  private final MBuilder builder;

  public static MBuilder with(Context context) {
    return new MBuilder(context);
  }

  public MConnector(MBuilder mBuilder) {
    builder = mBuilder;
  }

  public void start() throws NoVPNException, ServerNotSetException, MinecraftNotFoundException {
    sanitize();

    _VPNReplacerService localVPNService = new _VPNReplacerService();
    Intent intentStart = new Intent(builder.getContext(), localVPNService.getClass());
    intentStart.putExtra(_VPNReplacerService.FLAG_SERVIP, builder.getServerIp());
    intentStart.putExtra(_VPNReplacerService.FLAG_SERVPORT, builder.getServerPort());
    builder.getContext().startService(intentStart);

    Intent launchIntent = builder.getContext().getPackageManager()
        .getLaunchIntentForPackage("com.mojang.minecraftpe");
    if (launchIntent != null) {
      builder.getContext().startActivity(launchIntent);
    } else {
      throw new MinecraftNotFoundException();
    }
  }

  private void sanitize() throws ServerNotSetException, NoVPNException {
    Intent prepare = VpnService.prepare(builder.getContext());
    if (prepare != null) {
      throw new NoVPNException();
    }
    if (builder.getServerIp() == null || builder.getServerIp().isEmpty()) {
      throw new ServerNotSetException();
    }
  }

}
