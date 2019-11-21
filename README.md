# Android Ext

Android Ext is an android library which is used internally at SmartViser to share some util Kotlin 
class extensions.

## Installation

1- Add the JitPack repository to your root build.gradle (project level)
```
allprojects {
    repositories {
        ...
		maven { url 'https://www.jitpack.io' }
	}
}
```

2- Add the *android-ext* dependency with the desired version (release, commit hash or branch) to 
your module build.gradle 
```
dependencies {
    ...
    implementation 'com.github.Smartviser:android-ext:master-SNAPSHOT'
}
```
