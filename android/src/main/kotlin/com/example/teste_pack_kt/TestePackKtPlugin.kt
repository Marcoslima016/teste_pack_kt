package com.example.teste_pack_kt

import android.content.Context
import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.EventChannel.EventSink
import io.flutter.plugin.common.EventChannel.StreamHandler
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle


/** TestePackKtPlugin */
public class TestePackKtPlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "teste_pack_kt")
    channel.setMethodCallHandler(this);
  }

  // This static function is optional and equivalent to onAttachedToEngine. It supports the old
  // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
  // plugin registration via this function while apps migrate to use the new Android APIs
  // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
  //
  // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
  // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
  // depending on the user's project. onAttachedToEngine or registerWith must both be defined
  // in the same class.
  companion object {
    @JvmStatic
    fun registerWith(registrar: Registrar) {
      val channel = MethodChannel(registrar.messenger(), "teste_pack_kt")
      channel.setMethodCallHandler(TestePackKtPlugin())
      val eventChannel = EventChannel(registrar.messenger(), "locationStatusStream")



      eventChannel.setStreamHandler(
              object: StreamHandler {
                override fun onListen(p0: Any?, p1: EventSink) {
                  val listener = object : LocationListener {
                    override fun onLocationChanged(location: android.location.Location) {
                    }

                    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

                    }

                    override fun onProviderEnabled(provider: String) {
                      p1.success(true)
                    }

                    override fun onProviderDisabled(provider: String) {
                      p1.success(false)
                    }
                  }

                  val locationManager = registrar.activeContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
                  locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                          2000,
                          10f, listener)

                }

                override fun onCancel(p0: Any) {
                }
              }
      )


    }
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}
