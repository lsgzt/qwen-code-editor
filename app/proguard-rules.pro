# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /sdk/tools/proguard/proguard-android.txt

# Keep Groq API models
-keep class com.pocketdev.data.remote.groq.** { *; }

# Keep Room entities
-keep class com.pocketdev.domain.model.** { *; }

# Keep Sora Editor classes
-dontwarn io.github.rosemoe.sora.**
-keep class io.github.rosemoe.sora.** { *; }

# Keep Rhino JavaScript engine
-dontwarn org.mozilla.javascript.**
-keep class org.mozilla.javascript.** { *; }

# Keep Chaquopy Python
-dontwarn com.chaquo.python.**
-keep class com.chaquo.python.** { *; }

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

# Gson
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
