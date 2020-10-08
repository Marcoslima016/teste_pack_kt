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

  ///[=============================  TESTE 2 ( flutter beacon )  =============================]
  ///
  /// Event Channel used to communicate to native code ranging beacons.
  static const EventChannel _rangingChannel = EventChannel('flutter_beacon_event');

  /// This information does not change from call to call. Cache it.
  Stream _onRanging;

  Stream ranging(regions) {
    if (_onRanging == null) {
      final list = regions.map((region) => region.toJson).toList();
      _onRanging = _rangingChannel.receiveBroadcastStream(list).map((dynamic event) => RangingResult.from(event));
    }
    return _onRanging;
  }
}
