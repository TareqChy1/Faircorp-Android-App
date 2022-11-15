# Faircorp Android App

- Author : [Tareq Md Rabiul Hossain Chy](https://www.linkedin.com/in/tareqmdrabiulhossainchy/)
- Android App of the 2022 Web & Mobile Programing project for [MSc CPS2 Web & Mobile programming course](https://ci.mines-stetienne.fr/cps2/syllabus/)
- Based on [Guillaume Ehret's course](https://dev-mind.fr/formations.html)

## Context

The goal of the 2022 Web & Mobile Programing project is to implement a fully functional Smart Building management system (especially building, rooms, windows and heaters controllers).\
The project is composed of a [Java SpringBoot Backend server](https://github.com/TareqChy1/Web_Programming_Project), an [Android Kotlin Application](https://github.com/TareqChy1/Faircorp-Android-App) and a [VueJS Frontend server](https://github.com/TareqChy1/Faircorp-VueJS) interacting together.


## Project setup

Using [Android Studio](https://developer.android.com/studio), anyone can build and run the app on smartphone or on a virtual device.

## The UI/UX Flaw:
### Dashboard:
<img src="Photo/Faicorp_logo.jpeg"  width="500" height="500"/>

#### Buildings --> Rooms --> Room --> Windows --> Window --> Window
##### Rooms --> Room -->Windows
##### Rooms --> Windows --> Window
#### Windows --> Window
#### Heaters --> Heater

## Libraries Used:
```text
retrofit2: to get data from RESTFUL API
```

### NOTES


- If anyone delete a room all windows and heaters inside this room will be deleted.
- If anyone delete a building all rooms inside the building will be deleted.


### Contact


- For reporting uses you can do it in the issues section of this repository.
- For contacting the developer for any other reason please email me at <tareq.chy@etu.emse.fr> or <tareqfarhadbd@gmail.com>
