package com.dev.anikumar.flurry_analytics_plugin;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.flurry.android.Constants;
import com.flurry.android.FlurryAgent;

import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** FlurryAnalyticsPlugin */
public class FlurryAnalyticsPlugin implements FlutterPlugin, MethodCallHandler {
  private final String ANDROID_API_KEY = "android_api_key";
  private final String LOG_ENABLED = "log_enabled";
  private final String EVENT_ID = "event_id";
  private final String PARAMETERS = "parameters";
  private final String TIMED_EVENT = "timed_event";
  private final String USER_ID = "user_id";
  private final String AGE = "age";
  private final String GENDER = "gender";
  private final String ERROR_ID = "error_id";
  private final String ERROR_CLASS = "error_class";
  private final String ERROR_MESSAGE = "error_message";
  private final String VERSION_NAME = "version_name";
  private final String ORIGIN_NAME = "origin_name";
  private final String ORIGIN_VERSION = "origin_version";
  private final String SESSION_PROPERTY = "session_property";
  private final String SESSION_PROPERTY_VALUE = "session_property_value";
  private final String CRASH_BREAD_CRUMB = "crash_bread_crumb";
  private final String PRODUCT_NAME = "product_name";
  private final String PRODUCT_ID = "product_id";
  private final String QUANTITY = "quantity";
  private final String PRICE = "price";
  private final String CURRENCY = "currency";
  private final String TRANSACTION_ID = "transaction_id";
  private final String IS_OPT_OUT = "is_opt_out";
  private final String INSTANT_APP_NAME = "instant_app_name";
  private final String REPORT_LOCATION = "report_location";
  private final String DEEP_LINK = "deep_link";
  private final String CONTINUE_SESSION_MILLIS = "continue_session_millis";
  private final String SSL_PINNING_ENABLED = "ssl_pinning_enabled";
  private final String CAPTURE_UNCAUGHT_EXCEPTIONS = "capture_uncaught_exceptions";
  private final String BACK_SESSION_IN_METRICS = "back_session_in_metrics";

  // Method name constants
  private final String METHOD_LOG_EVENT = "log_event";
  private final String METHOD_GET_PLATFORM_VERSION = "get_platform_version";
  private final String METHOD_INITIALIZE = "initialize";
  private final String METHOD_END_TIMED_EVENT = "end_timed_event";
  private final String METHOD_SET_USER_ID = "set_user_id";
  private final String METHOD_SET_GENDER = "set_gender";
  private final String METHOD_SET_AGE = "set_age";
  private final String METHOD_ON_ERROR = "on_error";
  private final String METHOD_GET_RELEASE_VERSION = "get_release_version";
  private final String METHOD_SET_VERSION_NAME = "set_version_name";
  private final String METHOD_GET_AGENT_VERSION = "get_agent_version";
  private final String METHOD_IS_SESSION_ACTIVE = "is_session_active";
  private final String METHOD_GET_SESSION_ID = "get_session_id";
  private final String METHOD_DELETE_DATA = "delete_data";
  private final String METHOD_ADD_ORIGIN = "add_origin";
  private final String METHOD_ADD_SESSION_PROPERTY = "add_session_property";
  private final String METHOD_GET_INSTANT_APP_NAME = "get_instant_app_name";
  private final String METHOD_LOG_BREAD_CRUMB = "log_bread_crumb";
  private final String METHOD_LOG_PAYMENT = "log_payment";
  private final String METHOD_SET_DATA_SALE_OPT_OUT = "set_data_sale_opt_out";
  private final String METHOD_SET_INSTANT_APP_NAME = "set_instant_app_name";
  private final String METHOD_SET_SESSION_ORIGIN = "set_session_origin";
  private final String METHOD_SET_REPORT_LOCATION = "set_report_location";

  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel methodChannel;
  private Context applicationContext;

  @SuppressWarnings("deprecation")
  public static void registerWith(io.flutter.plugin.common.PluginRegistry.Registrar registrar) {
    final FlurryAnalyticsPlugin instance = new FlurryAnalyticsPlugin();
    instance.onAttachedToEngine(registrar.context(), registrar.messenger());
  }

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    onAttachedToEngine(flutterPluginBinding.getApplicationContext(), flutterPluginBinding.getBinaryMessenger());
  }

  private void onAttachedToEngine(Context applicationContext, BinaryMessenger messenger) {
    this.applicationContext = applicationContext;
    methodChannel = new MethodChannel(messenger, "flurry_analytics_plugin");
    methodChannel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals(METHOD_GET_PLATFORM_VERSION)) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if (call.method.equals(METHOD_INITIALIZE)) {
      initialize(call, result);
    } else if (call.method.equals(METHOD_LOG_EVENT)) {
      logEvent(call, result);
    } else if (call.method.equals(METHOD_END_TIMED_EVENT)) {
      endTimedEvent(call, result);
    } else if(call.method.equals(METHOD_SET_USER_ID)) {
      setUserId(call, result);
    } else if (call.method.equals(METHOD_SET_GENDER)) {
      setGender(call, result);
    } else if (call.method.equals(METHOD_SET_AGE)) {
      setAge(call, result);
    } else if (call.method.equals(METHOD_ON_ERROR)) {
      onError(call, result);
    } else if (call.method.equals(METHOD_GET_RELEASE_VERSION)) {
      getReleaseVersion(result);
    } else if (call.method.equals(METHOD_SET_VERSION_NAME)) {
      setVersionName(call, result);
    } else if (call.method.equals(METHOD_GET_AGENT_VERSION)) {
      getAgentVersion(result);
    } else if (call.method.equals(METHOD_IS_SESSION_ACTIVE)) {
      isSessionActive(result);
    } else if (call.method.equals(METHOD_GET_SESSION_ID)) {
      getSessionId(result);
    } else if (call.method.equals(METHOD_DELETE_DATA)) {
      deleteData(result);
    } else if (call.method.equals(METHOD_ADD_ORIGIN)) {
      addOrigin(call, result);
    } else if (call.method.equals(METHOD_ADD_SESSION_PROPERTY)) {
      addSessionProperty(call, result);
    }  else if (call.method.equals(METHOD_GET_INSTANT_APP_NAME)) {
      getInstantAppName(result);
    } else if (call.method.equals(METHOD_LOG_BREAD_CRUMB)) {
      logBreadCrumb(call, result);
    } else if (call.method.equals(METHOD_LOG_PAYMENT)) {
      logPayment(call, result);
    } else if (call.method.equals(METHOD_SET_DATA_SALE_OPT_OUT)) {
      setDataSaleOptOut(call, result);
    } else if (call.method.equals(METHOD_SET_INSTANT_APP_NAME)) {
      seInstantAppName(call, result);
    } else if (call.method.equals(METHOD_SET_REPORT_LOCATION)) {
      setReportLocation(call, result);
    } else if (call.method.equals(METHOD_SET_SESSION_ORIGIN)) {
      setSessionOrigin(call, result);
    } else {
      result.notImplemented();
    }
  }

  private void initialize(final MethodCall call, final Result result) {
    String apiKey = call.argument(ANDROID_API_KEY);

   FlurryAgent.Builder agentBuilder = new FlurryAgent.Builder()
            .withLogLevel(Log.DEBUG);

    if (call.hasArgument(LOG_ENABLED)) {
      boolean var = call.argument(LOG_ENABLED);
      agentBuilder.withLogEnabled(var);
    }

    if (call.hasArgument(CAPTURE_UNCAUGHT_EXCEPTIONS)) {
      boolean var = call.argument(CAPTURE_UNCAUGHT_EXCEPTIONS);
      agentBuilder.withCaptureUncaughtExceptions(var);
    }

    if (call.hasArgument(CONTINUE_SESSION_MILLIS)) {
      int var = call.argument(CONTINUE_SESSION_MILLIS);
      agentBuilder.withContinueSessionMillis(var);
    }

    if (call.hasArgument(IS_OPT_OUT)) {
      boolean var = call.argument(IS_OPT_OUT);
      agentBuilder.withDataSaleOptOut(var);
    }

    if (call.hasArgument(SSL_PINNING_ENABLED)) {
      boolean var = call.argument(SSL_PINNING_ENABLED);
      agentBuilder.withSslPinningEnabled(var);
    }

    if (call.hasArgument(BACK_SESSION_IN_METRICS)) {
      boolean var = call.argument(BACK_SESSION_IN_METRICS);
      agentBuilder.withIncludeBackgroundSessionsInMetrics(var);
    }

    agentBuilder.build(applicationContext, apiKey);
    result.success(null);
  }

  private void logEvent(final MethodCall call, final Result result) {
    String eventId = call.argument(EVENT_ID);
    boolean timedEvent = call.argument(TIMED_EVENT);

    if (call.hasArgument(PARAMETERS)) {
      Map<String, String> parameters = call.argument(PARAMETERS);
      FlurryAgent.logEvent(eventId, parameters, timedEvent);
    } else {
      FlurryAgent.logEvent(eventId, timedEvent);
    }

    result.success(null);
  }

  private void endTimedEvent(final MethodCall call, final Result result) {
    String eventId = call.argument(EVENT_ID);

    if (call.hasArgument(PARAMETERS)) {
      Map<String, String> parameters = call.argument(PARAMETERS);
      FlurryAgent.endTimedEvent(eventId, parameters);
    } else {
      FlurryAgent.endTimedEvent(eventId);
    }

    result.success(null);
  }

  private void setUserId(final MethodCall call, final Result result) {
    String userId = call.argument(USER_ID);
    FlurryAgent.setUserId(userId);
    result.success(null);
  }

  private void setAge(final MethodCall call, final Result result) {
    int age = call.argument(AGE);
    FlurryAgent.setAge(age);
    result.success(null);
  }

  private void setGender(final MethodCall call, final Result result) {
    String gender = call.argument(GENDER);
    FlurryAgent.setGender(gender.equals("male") ? Constants.MALE : Constants.FEMALE);
    result.success(null);
  }

  private void onError(final MethodCall call, final Result result) {
    String errorId = call.argument(ERROR_ID);
    String errorMessage = call.argument(ERROR_MESSAGE);

    if (call.hasArgument(ERROR_CLASS)) {
      String errorClass = call.argument(ERROR_CLASS);
      if (call.hasArgument(PARAMETERS)) {
        Map<String, String> parameters = call.argument(PARAMETERS);
        FlurryAgent.onError(errorId, errorMessage, errorClass, parameters);
      } else {
        FlurryAgent.onError(errorId, errorMessage, errorClass);
      }
    }

    result.success(null);
  }

  private void getReleaseVersion(final Result result) {
    String version = FlurryAgent.getReleaseVersion();
    result.success(version);
  }

  private void setVersionName(final MethodCall call, final Result result) {
    String versionName = call.argument(VERSION_NAME);
    FlurryAgent.setVersionName(versionName);
    result.success(null);
  }

  private void getAgentVersion(final Result result) {
    int version = FlurryAgent.getAgentVersion();
    result.success(version);
  }

  private void isSessionActive(final Result result) {
    boolean isActive = FlurryAgent.isSessionActive();
    result.success(isActive);
  }

  private void getSessionId(final Result result) {
    String sessionId = FlurryAgent.getSessionId();
    result.success(sessionId);
  }

  private void deleteData(final Result result) {
    FlurryAgent.deleteData();
    result.success(null);
  }

  private void addOrigin(final MethodCall call, final Result result) {
    String originName = call.argument(ORIGIN_NAME);
    String originVersion = call.argument(ORIGIN_VERSION);

    if (call.hasArgument(METHOD_ADD_ORIGIN)) {
      Map<String, String> parameters = call.argument(PARAMETERS);
      FlurryAgent.addOrigin(originName, originVersion, parameters);
    } else {
      FlurryAgent.addOrigin(originName, originVersion);
    }

    result.success(null);
  }

  private void addSessionProperty(final MethodCall call, final Result result) {
    String name = call.argument(SESSION_PROPERTY);
    String value = call.argument(SESSION_PROPERTY_VALUE);
    FlurryAgent.addSessionProperty(name, value);
    result.success(null);
  }

  private void getInstantAppName(final Result result) {
    String instantAppName = FlurryAgent.getInstantAppName();
    result.success(instantAppName);
  }

  private void logBreadCrumb(final MethodCall call, final Result result) {
    String crashBreadCrumb = call.argument(CRASH_BREAD_CRUMB);
    FlurryAgent.logBreadcrumb(crashBreadCrumb);
    result.success(null);
  }

  private void logPayment(final MethodCall call, final Result result) {
    String productName = call.argument(PRODUCT_NAME);
    String productId = call.argument(PRODUCT_ID);
    int quantity = call.argument(QUANTITY);
    double price = call.argument(PRICE);
    String currency = call.argument(CURRENCY);
    String transactionId = call.argument(TRANSACTION_ID);
    Map<String, String> parameters = call.argument(PARAMETERS);

    FlurryAgent.logPayment(productName, productId, quantity, price, currency, transactionId, parameters);
    result.success(null);
  }

  private void setDataSaleOptOut(final MethodCall call, final Result result) {
    boolean isOptOut = call.argument(IS_OPT_OUT);
    FlurryAgent.setDataSaleOptOut(isOptOut);
    result.success(null);
  }

  private void seInstantAppName(final MethodCall call, final Result result) {
    String instantAppName = call.argument(INSTANT_APP_NAME);
    FlurryAgent.setInstantAppName(instantAppName);
    result.success(null);
  }

  private void setReportLocation(final MethodCall call, final Result result) {
    boolean reportLocation = call.argument(REPORT_LOCATION);
    FlurryAgent.setReportLocation(reportLocation);
    result.success(null);
  }

  private void setSessionOrigin(final MethodCall call, final Result result) {
    String originName = call.argument(ORIGIN_NAME);
    String deepLink = call.argument(DEEP_LINK);
    FlurryAgent.setSessionOrigin(originName, deepLink);
    result.success(null);
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    applicationContext = null;
    methodChannel.setMethodCallHandler(null);
    methodChannel = null;
  }
}
