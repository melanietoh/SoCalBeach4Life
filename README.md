# SoCalBeach4Life - CSCI310 Project 2
By [Melanie Toh](https://github.com/melanietoh) (melaniex@usc.edu), [Catriona Jung](https://github.com/cjung109) (cyjung@usc.edu), and [Eric Cheng](https://github.com/Eric-Chng) (echeng68@usc.edu)

## Improved Capabilities 

(since 2.4)   

We added 3 major capability improvements.  
- Our first additional capability is augmenting trips to allow users to select restaurants as part of their trip.   
- Our second additional capability is allowing users to share their trips with friends so they can coordinate trips.   
- Our second additional capability is adjusting our stored routes for trips to support multi-stop trips.   

## Installation

Note: If at any point the system shows a popup saying allow `qemu-services-386` to receive incoming connections. Allow it.

### Update SDK   
On the top bar for Android Studio, click Tools -> SDK Manager.  
Click the SDK Tools Tab, click the checkmark (so it is checked) beside *Android Emulator* and *Google Play Services*. Apply changes and accept download popups.  

### Firebase Google Services     
On the top left of Android Studio, click the button that says Android and select the project option. Open the SoCalBeach4Life folder and find the app folder. Download and move the [google-services.json](https://drive.google.com/file/d/1-rwRC8U6uoXm3KaWGPVmR4YUpU1E8YfD/view?usp=sharing) file into that folder. 

### Maps API Key      
While in the Project view of the file explorer on android studio, find the *local.properties* file. Click it and append the following `MAPS_API_KEY=AIzaSyCEnsK36FKyv44d_stqm4i0jwwBAIPS8zg`    
Switch back to the Android view in file explorer (top right button). Inside the manifests folder, open `AndroidManifest.xml`       
On line 30, change the value to `MAPS_API_KEY`     
On line 38, change the value to `true`      

### Updating Google Play Services for Map Kit         
In Android Studio, in the top tab click Run -> Edit Configurations.  
Under Launch Options section, change Launch to `Launch Specified Activity`     
Underneath for the Activity field, click the 3 dots and select `BeachMapsActivity` as the launch activity.    

Run the app. When the app opens, it will display a screen with an update button (Google Maps API will not run without updated Google Play services). Click the update button.  
It will prompt you to login to gmail. Login with an @gmail domain email. After logging in, disable backup to google drive and accept google terms of services.   
It will now take you to the Google Play Store where it will show a Google Play Services App. Download it and wait for it to finish installing before moving onto the next step.

### Running the App      
In Android Studio, in the top tab click Run -> Edit Configurations.   
Under Launch Options section, change Launch to `Default Activity`   
You can now build and run the app and it will let you explore **SoCalBeach4Life!**


## Credits

All icons created by Freepik - Flaticon
