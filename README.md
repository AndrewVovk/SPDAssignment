#### The test assignment done for SPD-Ukraine Inc. 
Android app using [imgur.com apis](https://apidocs.imgur.com/?version=latest) to create an endless timeline (infinite scroll) of public images. 

![ezgif com-resize](https://user-images.githubusercontent.com/13447866/58929517-0448c980-8760-11e9-96b2-b9a96ae582e1.gif)

Based on:
- Kotlin
- Android Jetpack's ViewModel & LiveData
- LruCache
- DiskLruCache
- Retrofit 2
- Dagger 2



### Setting up environment and assembling APK

1. [Download](https://developer.android.com/studio/) , [install](https://developer.android.com/studio/install) and launch the latest version of Android Studio. 
2. Import this GitHub project into Android Studio: File -> New -> Project from Version Control -> Git. 

![import](https://user-images.githubusercontent.com/13447866/58370938-12534a80-7f15-11e9-9d60-20866831bdd4.jpg)

Then enter the URL of this project and hit "Clone".

![clone](https://user-images.githubusercontent.com/13447866/58930712-4b858900-8765-11e9-9c7e-7c100dcd42db.jpg)

3. To build and run the app, select Run > Run in the menu bar. 

![run](https://user-images.githubusercontent.com/13447866/58371143-77a83b00-7f17-11e9-8569-4f094617909b.jpg)

If it's the first time running the app, Android Studio asks you to select a deployment target. Select a device to install and run the app.

![select](https://user-images.githubusercontent.com/13447866/58371301-efc33080-7f18-11e9-9aab-3cb6763ac73b.jpg)

If the dialog says, "No USB devices or running emulators detected," then you need to [set up and connect your device](https://developer.android.com/studio/run/device.html) or launch an emulator by clicking a device listed under Available Virtual Devices. If there are no virtual devices listed, click Create New Virtual Device and follow the Virtual Device Configuration wizard ([instruction](https://developer.android.com/studio/run/managing-avds.html)).

4. To build an APK, select Build Bundle(s) / APK(s) > Build APK(s) in Android Studio menu.

![build](https://user-images.githubusercontent.com/13447866/58371369-5d6f5c80-7f19-11e9-9415-4b957dcff1c6.jpg)
