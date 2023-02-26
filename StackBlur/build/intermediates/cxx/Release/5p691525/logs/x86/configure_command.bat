@echo off
"D:\\Android\\Sdk\\ndk\\23.1.7779620\\ndk-build.cmd" ^
  "NDK_PROJECT_PATH=null" ^
  "APP_BUILD_SCRIPT=C:\\Users\\m.moradkia\\AndroidStudioProjects\\NewLandSDK\\StackBlur\\jni\\Android.mk" ^
  "NDK_APPLICATION_MK=C:\\Users\\m.moradkia\\AndroidStudioProjects\\NewLandSDK\\StackBlur\\jni\\Application.mk" ^
  "APP_ABI=x86" ^
  "NDK_ALL_ABIS=x86" ^
  "NDK_DEBUG=0" ^
  "APP_PLATFORM=android-21" ^
  "NDK_OUT=C:\\Users\\m.moradkia\\AndroidStudioProjects\\NewLandSDK\\StackBlur\\build\\intermediates\\cxx\\Release\\5p691525/obj" ^
  "NDK_LIBS_OUT=C:\\Users\\m.moradkia\\AndroidStudioProjects\\NewLandSDK\\StackBlur\\build\\intermediates\\cxx\\Release\\5p691525/lib" ^
  "APP_SHORT_COMMANDS=false" ^
  "LOCAL_SHORT_COMMANDS=false" ^
  -B ^
  -n
