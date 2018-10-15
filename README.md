# BreakerBots 2018 Offseason
Offseason code for **Athena** ([FRC Power-Up 2018](https://en.wikipedia.org/wiki/FIRST_Power_Up)). Athena's code is running **Java** through **[GradleRIO](https://github.com/wpilibsuite/GradleRIO)**.

### Setup Instructions
- Download [Gradle](https://gradle.org/releases/)
- Download or Clone this Repository
- Open Eclipse and go to "File > Import"
- Under "Gradle" choose "Existing Gradle Project"
- Chose this repository as the "Project Root"
- Continue with default settings and import the project

### Building/Deploying
In the command line of this repository run `gradlew build` and then `gradlew deploy`.
You can also run `gradlew riolog` to connect to the logging from the robot.

## New Features
- Onboard trajectory generation, caching, and following
- Better Code Organization
- New Console Class (console.java) for logging organization
- Custom Command Based System
- Subsystem Manager
- New Squeezy Code with Current Detection Holding
- New Elevator Code
- New Drive Code with integration into autonomous and a bezier curve helper
- Odometry/Kinematics, robot position calculating
- Custom TCP Socket to connect to the BreakerBoard


## Subsystems
- **Squeezy Sanchez (Squeezy):** Athena's cube intake mechanism, uses a cim motor to drive arms in and out.
- **Elevator:** Athena's main elevator, a single chanel elevator driven with two cims and dinema.
- **Climber:** The combination of a hook attached to the elevator and a wench, allowing the robot to lift itself a foot off the ground from a metal bar.
- **Drive:** The main drive train of the robot (Includes Gyro, Encoder, and Motor Controllers)

## Robot
![Robot Image](/images/Athena.jpg)

## Code Style
Based roughtly off of the [Google Style Guide](http://google.github.io/styleguide/javaguide.html).

#### Naming:
- **Constants:** _ + name

