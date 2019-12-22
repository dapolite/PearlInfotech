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
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-optimizationpasses 5
-allowaccessmodification
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgent
-keep public class * extends android.preference.Preference
-keep public class Admin.Student.** { *; }
-keep public class Admin.Faculty.** { *; }
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







# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
