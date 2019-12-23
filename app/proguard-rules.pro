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
-keep class androidx.core.app.CoreComponentFactory { *; }
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-keepattributes Exceptions,InnerClasses,Signature
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgent
-keep public class * extends android.preference.Preference
-keep public class Admin.Student.** { *; }
-keep public class com.example.pearlinfotech.Admin.Faculty.** { *; }
-keep public class Admin.Course.** { *; }
-keep class com.example.pearlinfotech.Admin.** { *; }
-keep class com.example.pearlinfotech.About.** { *; }
-keep class com.example.pearlinfotech.Attendance.** { *; }
-keep class com.example.pearlinfotech.Dashbard.** { *; }
-keep class com.example.pearlinfotech.ExamDetail.** { *; }
-keep class com.example.pearlinfotech.Fees.** { *; }
-keep class com.example.pearlinfotech.Login.** { *; }
-keep class com.example.pearlinfotech.Message.** { *; }
-keep class com.example.pearlinfotech.Performance.** { *; }
-keep class com.example.pearlinfotech.TimeTable.** { *; }
# Preserve the special static methods that are required in all enumeration classes.
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keep class * implements android.os.Serializable {
  public static final android.os.Serializable$Creator *;
}
-keepclassmembers class **.R$* {
    public static <fields>;
}
# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
