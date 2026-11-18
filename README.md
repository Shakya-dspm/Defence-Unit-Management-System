# Defence System Project

A Java Swing desktop application that simulates a central defence control system communicating with three defence units: **Helicopter, Tank, and Submarine**.

The central controller provides unit monitoring, broadcast and private messaging, area-status updates, and operation authorization based on the selected position and control-strength level. Each defence unit has its own interface and manages resources such as ammunition and energy. The submarine also monitors oxygen.

## Features

### Central control system
- Opens and manages the Helicopter, Tank, and Submarine unit windows.
- Sends messages to all units or privately to a selected unit.
- Displays combined ammunition and soldier counts.
- Calculates an overall energy status (`HIGH`, `MID`, or `LOW`) from the three units' energy levels.
- Sets the area status for all units.
- Uses a control-strength slider to determine which operations are enabled for positioned units.

### Defence units
- **Helicopter:** shooting, missile, and laser operations.
- **Tank:** shooting, radar, missile, and rotating-shooting operations.
- **Submarine:** shooting, sonar, Trident missile, and Tomahawk missile operations.
- Each unit has its own ammunition and energy display.
- Units can send messages to the central controller.

### Ammunition and shooting
- A normal shoot-button click consumes one round.
- Holding the shoot button activates continuous shooting after a short hold delay.
- Continuous shooting consumes ammunition repeatedly and stops when the button is released or ammunition/energy is exhausted.

### Energy management
- Operations consume energy; the amount depends on the operation and unit.
- Energy also decreases gradually over time.
- Operations check whether energy is available before performing their resource-consuming action.
- Communication with the controller is handled separately from the unit's operation controls.

### Submarine oxygen management
- The submarine has a separate oxygen-level indicator.
- Submarine operations consume oxygen.
- An operation warns and is blocked when the oxygen level is already below 70%.
- A timer gradually recovers oxygen when it is below 90%; otherwise, the displayed level fluctuates between 90% and 100%.

## Technologies

- **Language:** Java
- **GUI:** Java Swing
- **IDE:** Apache NetBeans
- **Build tool:** Maven
- **Programming approach:** Object-Oriented Programming (OOP)

## OOP concepts used

- **Encapsulation:** Unit state and interface components are maintained in class fields, with many state fields declared `private`.
- **Abstraction:** The `SignalObsever` interface defines common operations that each defence-unit window must provide.
- **Polymorphism:** `Helicopter`, `Tank`, and `Submarine` implement `SignalObsever`, allowing the controller to work with a common set of unit behaviours.

The three defence-unit classes also extend `javax.swing.JFrame` to create their desktop windows. They do not inherit from one another.

## Project structure

```text
DefenceSystemProject/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── com/mycompany/defencesystemproject/
                ├── DefenceSystemProject.java
                ├── MainController.java
                ├── SignalObsever.java
                ├── Helicopter.java
                ├── Tank.java
                ├── Submarine.java
                ├── StyleButton.java
                ├── StyleCheckBox.java
                ├── StyleComboBox.java
                ├── StyleSlider.java
                ├── StyleSpinner.java
                ├── StyleTextArea.java
                └── StyleTextField.java
```

## Run the project

### Using NetBeans

1. Extract or clone this repository.
2. Open Apache NetBeans.
3. Select **File → Open Project**.
4. Select the `DefenceSystemProject` folder (the folder containing `pom.xml`).
5. Allow NetBeans to load the Maven project and its dependencies.
6. Run the project. The main entry point is `DefenceSystemProject.java`.

### Using Maven from a terminal

From the project directory, run:

```bash
mvn clean package
```

Then run the application using NetBeans, or use the Maven execution configuration available in your IDE.

## Notes

- This is an educational simulation built with a desktop graphical interface. It does not connect to real defence equipment or external communication networks.
- The operation thresholds and resource-consumption values are implemented in the Java source files and can be adjusted there.

## Author

**Shakya**

GitHub: [@Shakya-dspm](https://github.com/Shakya-dspm)
