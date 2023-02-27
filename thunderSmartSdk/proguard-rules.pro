# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


-keep public class com.thunderlight.thundersmartsdk.generalManager.GeneralSDKManager { public *; }
-keep public class com.thunderlight.thundersmartsdk.generalManager.PosDataCallBack { public *; }
-keep public class com.thunderlight.thundersmartsdk.generalManager.ResultCallBack { public *; }
-keep public class com.thunderlight.thundersmartsdk.generalManager.TransactionCallBack { public *; }

-keep public class com.thunderlight.thundersmartsdk.data.PosData { public *; }
-keep public class com.thunderlight.thundersmartsdk.data.TransactionData { public *; }

-keep public class com.thunderlight.thundersmartsdk.constant.HostApp { public *; }
-keep public class com.thunderlight.thundersmartsdk.constant.RequestType { public *; }
-keep public class com.thunderlight.thundersmartsdk.constant.TxnInquiryType { public *; }