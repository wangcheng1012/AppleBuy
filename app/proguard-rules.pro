# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\work\android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#1、-injars //表示要进行混淆的class文件或jar、war等,可用文件目录表示，例如：
#2、-injar ../jar(**.class) 或者-injar ../jar/in.jar
#3、-outjars 表示要生成的jar包，后跟jar包名字，如：-outjars ../out.jar
#4、-libraryjars 后面跟要编译in.jar的其它类包，如果是多个，用多行列出，如：
#5、-libraryjars d:/1/2/1.jar
#6、-libraryjars d:/1/2/2.jar
#7、-libraryjars d:/1/2/3.jar
#8、-libraryjars d:/1/2/4.jar
#9、-keep 后跟项目的入口类，如：-keep public class gps.lip.LIPService{ public static void main(java.lang.String[])}
#-keep 后还可以跟在项目中没有用到的类或方法，但在配置文件中有用到，如果不用该参数保留出来，在做优化时，就会直接的删除掉了，项目运行时会报找不到类的错误。
#-printusage该参数是把优化时移除的类及方法记录下来，后跟一个文件。如：-printusage ./jar/deadCode.txt
#其余的常用的参数，如：
#10、-target 1.6 //指定版本号
#11、-forceprocessing //强制执行，即使过期
#12、-allowaccessmodification //指定，当执行修改方法或属性的modifer范围
#13、-printmapping  //指定混淆后，类或方法生成的map,后跟指定的路径及文件名 *.map
#14、-overloadaggressively //
#15、-repackageclasses //把执行后的类重新放在某一个目录下，后跟一个目录名
#16、-dontpreverify //不用预先检查
#17、-verbose //不用输出详细的过程
#18、-dontwarn//不用输出警告
#19、-dontnote//不用输出通知

#把优化时移除的类及方法记录下来
-printusage ./jar/deadCode.txt
#SweetAlert
-keep class cn.pedant.SweetAlert.Rotate3dAnimation {
    public <init>(...);
}
#-keep class com.dw.applebuy.ui.loginreg.LoginActivity{*;}
#-keep class com.rxmvp.api.GsonConverter.**{*;}

# keep annotated by NotProguard
-keep @cn.trinea.android.common.annotation.NotProguard class * {*;}
-keep class * {
@cn.trinea.android.common.annotation.NotProguard <fields>;
}
-keepclassmembers class * {
@cn.trinea.android.common.annotation.NotProguard <methods>;
}
#Serializable接口的不混淆
-keepnames class * implements java.io.Serializable
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {

static final long serialVersionUID;

private static final java.io.ObjectStreamField[] serialPersistentFields;

!static !transient <fields>;

private void writeObject(java.io.ObjectOutputStream);

private void readObject(java.io.ObjectInputStream);

java.lang.Object writeReplace();

java.lang.Object readResolve();

}

-keepnames class com.AnywayAds.Mini$* {
    public <fields>;
    public <methods>;
}

#---------- 三方包的 -------


# 百度
-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-dontwarn com.baidu.**

# gson工具不需要混淆
-dontwarn com.google.gson**
-keep class com.google.gson.**{*;}

# Gson
#-keep class com.google.gson.stream.** { *; }
#-keepattributes EnclosingMethod
#-keep class org.xz_sale.entity.**{*;}

# rxjava
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
-dontnote rx.internal.util.PlatformDependent

# OkHttp3
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okio.**
# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
#-keepattributes Signature-keepattributes Exceptions

# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

#支付宝
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class android.net.SSLCertificateSocketFactory{*;}
-dontwarn android.net.SSLCertificateSocketFactory
#微信
-keep class com.tencent.mm.opensdk.** {
   *;
}
-keep class com.tencent.wxop.** {
   *;
}
-keep class com.tencent.mm.sdk.** {
   *;
}
#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#极光
-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }
