SDK="${HOME}/android-sdk-linux"
BUILD_TOOLS="${SDK}/build-tools/25.0.3"
PLATFORM="${SDK}/platforms/android-25"

all: dirs package build/HelloJava.apk

dirs:
	mkdir -p build/gen build/obj build/apk

aapt: dirs
	"${BUILD_TOOLS}/aapt" package -f -m -J build/gen -S res \
		-M AndroidManifest.xml -I "${PLATFORM}/android.jar"

compile: aapt
	javac -source 1.7 -target 1.7 -bootclasspath "${JAVA_HOME}/jre/lib/rt.jar" \
		-classpath "${PLATFORM}/android.jar" -d build/obj \
		build/gen/com/vertmoprod/hellojava/R.java java/com/vertmoprod/hellojava/MainActivity.java

dalvik: compile
	"${BUILD_TOOLS}/dx" --dex --output=build/apk/classes.dex build/obj/

package build/HelloJava.unsigned.apk: dalvik
	"${BUILD_TOOLS}/aapt" package -f -M AndroidManifest.xml -S res/ \
		-I "${PLATFORM}/android.jar" \
		-F build/HelloJava.unsigned.apk build/apk/

sign build/HelloJava.apk: build/HelloJava.unsigned.apk
	"${BUILD_TOOLS}/zipalign" -f -p 4\
		build/HelloJava.unsigned.apk build/HelloJava.aligned.apk
	"${BUILD_TOOLS}/apksigner" sign --ks "${HOME}/.debug.keystore" \
    --ks-key-alias androiddebugkey --ks-pass pass:android --key-pass pass:android \
		--out build/HelloJava.apk build/HelloJava.aligned.apk

install: build/HelloJava.apk
	"${SDK}/platform-tools/adb" install -r build/HelloJava.apk

clean:
	rm -r build/
