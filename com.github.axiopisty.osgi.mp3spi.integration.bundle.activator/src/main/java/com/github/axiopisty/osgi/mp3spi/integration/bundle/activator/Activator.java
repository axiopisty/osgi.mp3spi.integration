package com.github.axiopisty.osgi.mp3spi.integration.bundle.activator;

import com.github.axiopisty.osgi.mp3spi.integration.mp3player.api.Gui;
import javafx.application.Application;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

  @Override
  public void start(BundleContext bundleContext) throws Exception {
    System.out.println("Bundle started");
    Thread t = new Thread(() -> {
      Application.launch(Gui.class);
    });
    t.setDaemon(true);
    t.start();
  }

  @Override
  public void stop(BundleContext bundleContext) throws Exception {
    System.out.println("Bundle stopped");
  }
}
