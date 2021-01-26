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

-keep class com.baidu.*
-keep class vi.com.*
-keep class com.baidu.vi.*


-keep class com.huawei.hianalytics.*
-keep class com.huawei.updatesdk.*
-keep class com.huawei.hms.*

-dontwarn com.baidu.**

-ignorewarnings
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable


# BaiduNavi SDK
-dontoptimize
-ignorewarnings
-keeppackagenames com.baidu.**
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,LocalVariable*Table,*Annotation*,Synthetic,EnclosingMethod

-dontwarn com.baidu.**
-dontwarn com.baidu.navisdk.**
-dontwarn com.baidu.navi.**

-keep class com.baidu.*
-keep interface com.baidu.*

-keep class vi.com.gdi.*

-dontwarn com.google.protobuf.**
-keep class com.google.protobuf.*
-keep interface com.google.protobuf.*

-dontwarn com.google.android.support.v4.**
-keep class com.google.android.support.v4.*
-keep interface com.google.android.support.v4.app.*
-keep public class * extends com.google.android.support.v4.*



