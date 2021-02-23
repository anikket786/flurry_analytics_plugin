# flurry_analytics_plugin

A Flutter plugin to use Flurry Analytics. This plugin provides all the FlurryAgent methods.

## Getting Started

This is a Flutter plugin to use Flurry Analytics. It implements native calls to [Flurry Android SDK][flurry_sdk_android] and [Flurry iOS SDK][flurry_sdk_ios]. The plugin provides almost all the FlurryAgent functionality.

## Installation

Add `flurry_analytics_plugin: ^0.1.1` in your pubspec.yaml dependencies.

## How to use #

Importing the library :

``` dart
import 'package:flurry_analytics_plugin/flurry_agent.dart';
```

Add google play services library in your Android project's app level build.gradle file

```
dependencies {
    implementation "com.google.android.gms:play-services-analytics:17.0.0"
}
```

Initialization :
(Before this, you must have the Android Key and iOS Key, from Flurry dashboard)

``` dart
await FlurryAgent.initialize(androidKey: "xxx", iosKey: "xxx", enableLog: true);
```

Logging/setting UserId :

``` dart
FlurryAgent.setUserId('1234');
```

Logging event:

``` dart
FlurryAgent.logEvent('event name');

or with parameters

FlurryAgent.logEvent('event name', parameters: <String, String>{...});
```

Logging timed event:

``` dart
FlurryAgent.logEvent('event name', timed: true);

or with parameters

FlurryAgent.logEvent('event name', parameters: <String, String>{...}, timed: true);
```

Logging the end of an event (to log duration):

``` dart
FlurryAgent.endTimedEvent("event name");
```
Logging Payment

``` dart
FlurryAgent.logPayment(
    'productName',
    'productId',
    quantity,
    price,
    'currency',
    'transactionId',
    parameters,
);
```

Set Age (Note: these methods should be called before initializing FlurryAgent)

``` dart
FlurryAgent.setAge("23);
FlurryAgent.setGender(Gender.MALE);
```

And many more methods... Look at flurry sdk documentation to find out more.

[flurry_sdk_android]: https://developer.yahoo.com/flurry/docs/integrateflurry/android
[flurry_sdk_ios]: https://developer.yahoo.com/flurry/docs/integrateflurry/ios
