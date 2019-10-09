../../gradlew assembleDebug
adb push build/outputs/apk/debug/home-debug.apk /storage/emulated/0/Android/data/com.github.pgycode.wanandroid/files/home.apk

# 关闭App
/Users/haha/Library/Android/sdk/platform-tools/adb shell am force-stop com.github.pgycode.wanandroid

# 启动App
/Users/haha/Library/Android/sdk/platform-tools/adb shell am start -n com.github.pgycode.wanandroid/com.github.pgycode.wanandroid.app.act.HomeActivity