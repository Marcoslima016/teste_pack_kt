import 'dart:async';

import 'package:flutter/services.dart';

class TestePackKt {
  static const MethodChannel _channel = const MethodChannel('teste_pack_kt');

  ///[==================================  PLATFORM VERSION ==================================]

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  ///[=============================  TESTE 1 ( POSICAO HANDLE) =============================]

  static Future get handleLocationChanges async {
    const EventChannel _stream = EventChannel('locationStatusStream');

    bool _locationStatusChanged;
    if (_locationStatusChanged == null) {
      _stream.receiveBroadcastStream().listen((onData) {
        _locationStatusChanged = onData;
        print("LOCATION ACCESS IS NOW ${onData ? 'On' : 'Off'}");
        if (onData == false) {
          // Request Permission Access
        }
      });
    }
  }
}
