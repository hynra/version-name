# version-name
Simple library to get current app version name from Play Strore

### Installation
 in root project: 
 ```
 allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
 ```
 
 in app:
 ```
 dependencies {
	        implementation 'com.github.hynra:version-name:v0.1'
	}
 ```
 
 ### Usage
 
 `VersionName.get(packagename, Listener)`
 
 Litener return two parameters:
 
 `versionName`: version name of app
 
 `isWithVaries`: app come with varies apk.
 
 * Limitation: always check `isWithVaries`, if true then versionName return `Varies with device`, not result that you expected.
 
 * example
 ```java
 VersionName.get("app.pptik.itb.semut", new VersionName.Listener() {
            @Override
            public void onVersionLoaded(String versionName, boolean isWithVaries) {
                TextView t = findViewById(R.id.text);
                if(isWithVaries){
                // 0.4.8
                    t.setText("Version not found, app varies");
                }else t.setText(versionName);
            }
        });
 ```
