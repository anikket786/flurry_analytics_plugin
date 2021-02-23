import 'dart:async';
import 'package:flutter/services.dart';
import 'constants.dart';

class FlurryAgent {
  static const MethodChannel _channel = const MethodChannel('flurry_analytics_plugin');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod(Constants.METHOD_GET_PLATFORM_VERSION);
    return version;
  }

  // Initialize Flurry Analytics (appVersion is ONLY for iOS)
  static Future<void> initialize({
    String androidApiKey = '',
    String iosApiKey = '',
    bool enableLog = true,
    String iosAppVersion,
    bool captureUncaughtExceptions,
    int continueSessionMillis,
    bool dataSaleOptOut,
    bool enableSslPinning,
    bool includeBackSessionInMetrics,
  }) async {
    Map<String, dynamic> args = {
      Constants.ANDROID_API_KEY: androidApiKey,
      Constants.IOS_API_KEY: iosApiKey,
      Constants.LOG_ENABLED: enableLog,
      Constants.IOS_APP_VERSION: iosAppVersion,
    };

    if (iosApiKey != null) args[Constants.IOS_API_KEY] = iosApiKey;
    if (iosAppVersion != null) args[Constants.IOS_APP_VERSION] = iosAppVersion;
    if (enableLog != null) args[Constants.LOG_ENABLED] = enableLog;
    if (captureUncaughtExceptions != null) args[Constants.CAPTURE_UNCAUGHT_EXCEPTIONS] = captureUncaughtExceptions;
    if (continueSessionMillis != null) args[Constants.CONTINUE_SESSION_MILLIS] = continueSessionMillis;
    if (dataSaleOptOut != null) args[Constants.IS_OPT_OUT] = dataSaleOptOut;
    if (enableSslPinning != null) args[Constants.SSL_PINNING_ENABLED] = enableSslPinning;
    if (includeBackSessionInMetrics != null) args[Constants.BACK_SESSION_IN_METRICS] = includeBackSessionInMetrics;

    await _channel.invokeMethod<void>(Constants.METHOD_INITIALIZE, args);
  }

  static Future<void> logEvent(String eventId, {Map<String, String> parameters, bool timed = false}) async {
    Map<String, dynamic> args = {
      Constants.EVENT_ID: eventId,
      Constants.TIMED_EVENT: timed,
    };

    if (parameters != null) args[Constants.PARAMETERS] = parameters;

    await _channel.invokeMethod<void>(Constants.METHOD_LOG_EVENT, args);
  }

  static Future<void> endTimedEvent(String eventId, {Map<String, String> parameters}) async {
    Map<String, dynamic> args = {
      Constants.EVENT_ID: eventId,
    };

    if (parameters != null) args[Constants.PARAMETERS] = parameters;

    await _channel.invokeMethod<void>(Constants.METHOD_END_TIMED_EVENT, args);
  }

  static Future<void> setUserId(String userId) async {
    Map<String, String> args = {
      Constants.USER_ID: userId,
    };

    await _channel.invokeMethod<void>(Constants.METHOD_SET_USER_ID, args);
  }

  static Future<void> setGender(Gender gender) async {
    Map<String, String> args = {
      Constants.GENDER: gender == Gender.MALE ? 'male' : 'female',
    };

    await _channel.invokeMethod<void>(Constants.METHOD_SET_GENDER, args);
  }

  static Future<void> setAge(int age) async {
    Map<String, int> args = {
      Constants.AGE: age,
    };

    await _channel.invokeMethod<void>(Constants.METHOD_SET_AGE, args);
  }

  static Future<void> onError(String errorId, String errorMessage, {String errorClass, Map<String, String> parameters}) async {
    Map<String, dynamic> args = {
      Constants.ERROR_ID: errorId,
      Constants.ERROR_MESSAGE: errorMessage,
    };

    if (errorClass != null) args[Constants.ERROR_CLASS] = errorClass;
    if (parameters != null) args[Constants.PARAMETERS] = parameters;

    await _channel.invokeMethod<void>(Constants.METHOD_ON_ERROR, args);
  }

  static Future<String> getReleaseVersion() async {
    String version = await _channel.invokeMethod<String>(Constants.METHOD_GET_RELEASE_VERSION);
    return version;
  }

  static Future<void> setVersionName(String versionName) async {
    Map<String, String> args = {
      Constants.VERSION_NAME: versionName,
    };

    await _channel.invokeMethod<void>(Constants.METHOD_SET_VERSION_NAME, args);
  }

  static Future<int> getAgentVersion() async {
    int version = await _channel.invokeMethod<int>(Constants.METHOD_GET_AGENT_VERSION);
    return version;
  }

  static Future<String> getSessionId() async {
    String sessionId = await _channel.invokeMethod<String>(Constants.METHOD_GET_SESSION_ID);
    return sessionId;
  }

  static Future<bool> isSessionActive() async {
    bool isActive = await _channel.invokeMethod<bool>(Constants.METHOD_IS_SESSION_ACTIVE);
    return isActive;
  }

  static Future<void> addOrigin(String originName, String originVersion, {Map<String, String> originParameters}) async {
    Map<String, dynamic> args = {
      Constants.ORIGIN_NAME: originName,
      Constants.ORIGIN_VERSION: originVersion,
    };

    if (originParameters != null) args[Constants.PARAMETERS] = originParameters;

    await _channel.invokeMethod<void>(Constants.METHOD_ADD_ORIGIN, args);
  }

  static Future<void> addSessionProperty(String name, String value) async {
    Map<String, String> args = {
      Constants.SESSION_PROPERTY: name,
      Constants.SESSION_PROPERTY_VALUE: value,
    };

    await _channel.invokeMethod<void>(Constants.METHOD_ADD_SESSION_PROPERTY, args);
  }

  static Future<void> deleteData() async {
    await _channel.invokeMethod<void>(Constants.METHOD_DELETE_DATA);
  }

  static Future<String> getInstantAppName() async {
    String instantAppName = await _channel.invokeMethod<String>(Constants.METHOD_GET_INSTANT_APP_NAME);
    return instantAppName;
  }

  static Future<void> logBreadCrumb(String crashBreadCrumb) async {
    Map<String, String> args = {
      Constants.CRASH_BREAD_CRUMB: crashBreadCrumb,
    };

    await _channel.invokeMethod<void>(Constants.METHOD_LOG_BREAD_CRUMB, args);
  }

  static Future<void> logPayment({
    String productName,
    String productId,
    int quantity,
    double price,
    String currency,
    String transactionId,
    Map<String, String> parameters,
  }) async {
    Map<String, dynamic> args = {
      Constants.PRODUCT_NAME: productName,
      Constants.PRODUCT_ID: productId,
      Constants.QUANTITY: quantity,
      Constants.PRICE: price,
      Constants.CURRENCY: currency,
      Constants.TRANSACTION_ID: transactionId,
      Constants.PARAMETERS: parameters,
    };

    await _channel.invokeMethod<void>(Constants.METHOD_LOG_PAYMENT, args);
  }

  static Future<void> setDataSaleOptOut(bool isOptOut) async {
    Map<String, bool> args = {
      Constants.IS_OPT_OUT: isOptOut,
    };

    await _channel.invokeMethod<void>(Constants.METHOD_SET_DATA_SALE_OPT_OUT, args);
  }

  static Future<void> setInstantAppName(String instantAppName) async {
    Map<String, String> args = {
      Constants.INSTANT_APP_NAME: instantAppName,
    };

    await _channel.invokeMethod<void>(Constants.METHOD_SET_INSTANT_APP_NAME, args);
  }

  static Future<void> setReportLocation(bool reportLocation) async {
    Map<String, bool> args = {
      Constants.REPORT_LOCATION: reportLocation,
    };

    await _channel.invokeMethod<void>(Constants.METHOD_SET_REPORT_LOCATION, args);
  }

  static Future<void> addSessionOrigin(String originName, String deepLink) async {
    Map<String, String> args = {
      Constants.ORIGIN_NAME: originName,
      Constants.DEEP_LINK: deepLink,
    };

    await _channel.invokeMethod<void>(Constants.METHOD_SET_SESSION_ORIGIN, args);
  }
}
