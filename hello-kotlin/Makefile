SDK="${HOME}/android-sdk-linux"
BUILD_TOOLS="${SDK}/build-tools/25.0.3"
PLATFORM="${SDK}/platforms/android-25"

all: dirs package build/HelloKotlin.apk

dirs:
	mkdir -p build/gen build/obj build/apk

aapt: dirs
	"${BUILD_TOOLS}/aapt" package -f -m -J build/gen -S res \
		-M AndroidManifest.xml -I "${PLATFORM}/android.jar"

compile: aapt
	kotlinc -classpath "${PLATFORM}/android.jar" -d build/obj \
		build/gen/com/vertmoprod/hellokotlin/R.java kotlin/com/vertmoprod/hellokotlin/MainActivity.kt

dalvik: compile
	"${BUILD_TOOLS}/dx" --dex --output=build/apk/classes.dex build/obj/

package build/HelloKotlin.unsigned.apk: dalvik
	"${BUILD_TOOLS}/aapt" package -f -M AndroidManifest.xml -S res/ \
		-I "${PLATFORM}/android.jar" \
		-F build/HelloKotlin.unsigned.apk build/apk/

sign build/HelloKotlin.apk: build/HelloKotlin.unsigned.apk
	"${BUILD_TOOLS}/zipalign" -f -p 4\
		build/HelloKotlin.unsigned.apk build/HelloKotlin.aligned.apk
	"${BUILD_TOOLS}/apksigner" sign --ks "${HOME}/.debug.keystore" \
    --ks-key-alias androiddebugkey --ks-pass pass:android --key-pass pass:android \
		--out build/HelloKotlin.apk build/HelloKotlin.aligned.apk

install: build/HelloKotlin.apk
	"${SDK}/platform-tools/adb" install -r build/HelloKotlin.apk

clean:
	rm -r build/
