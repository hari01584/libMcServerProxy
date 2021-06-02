package com.skullzbones.mcserverproxy;

import android.Manifest;
import android.Manifest.permission;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.VpnService;

import androidx.core.content.ContextCompat;
import com.skullzbones.mcserverproxy.Exceptions.MinecraftNotFoundException;
import com.skullzbones.mcserverproxy.Exceptions.NoVPNException;
import com.skullzbones.mcserverproxy.Exceptions.ServerNotSetException;
import com.skullzbones.mcserverproxy.Exceptions.StoragePermissionNotGiven;
import com.skullzbones.mcserverproxy.Networks._VPNReplacerService;
import java.security.Permission;

public class MConnector {

  private final MBuilder builder;

  public static MBuilder with(Context context) {
    return new MBuilder(context);
  }

  public MConnector(MBuilder mBuilder) {
    builder = mBuilder;
  }

  /**
   * Starts the vpn service after preparing and setting some parameters.
   *
   * @throws NoVPNException             Permission for vpn not given.
   * @throws ServerNotSetException      Server ip or port is invalid or empty.
   * @throws MinecraftNotFoundException No valid minecraft versions to launch is found.
   */
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

  /**
   * Sanitize parameters and prepare them before starting, throws appropriate errors if anything bad
   * is found.
   *
   * @throws ServerNotSetException Server ip or port is invalid or empty.
   * @throws NoVPNException        Permission for vpn not given.
   */
  private void sanitize() throws ServerNotSetException, NoVPNException {
    Intent prepare = VpnService.prepare(builder.getContext());
    if (prepare != null) {
      throw new NoVPNException();
    }
    if (builder.getServerIp() == null || builder.getServerIp().isEmpty()) {
      throw new ServerNotSetException();
    }
    if (builder.getInGameName() == null || builder.getInGameName().isEmpty()
        || builder.getGameSkinBase64() == null || builder.getGameSkinBase64().isEmpty()) {
      int permissionCheckWrite = ContextCompat.checkSelfPermission(builder.getContext(),
          permission.WRITE_EXTERNAL_STORAGE);
      int permissionCheckRead = ContextCompat.checkSelfPermission(builder.getContext(),
          permission.READ_EXTERNAL_STORAGE);

      if (permissionCheckWrite != PackageManager.PERMISSION_GRANTED
          || permissionCheckRead != PackageManager.PERMISSION_GRANTED) {
        if (builder.getSuppressStoragePermissions()) {
          // Nullified these two parameters, So the plugin won't set name and skin, which requires t
          // hese permission! this only in case if suppresspermission is enabled, If not then it will
          // throw runtime  exception (be sure to catch it) :D
          builder.setInGameName(null);
          builder.setGameSkin(null);
        } else {
          throw new StoragePermissionNotGiven();
        }
      }
    }
  }

}
