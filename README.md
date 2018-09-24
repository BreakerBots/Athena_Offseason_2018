# BreakerBots 2018 Offseason
Offseason Code for Power-up-2018. Implements all new code in practice for 2019.

## Running
1) Download or Clone This Repository
2) Open Eclipse
3) Press "File" then "Import"
4) Under "General" choose "Existing Projects Into Workspace"
5) Choose the location of This Repository, and then "Finish"
6) Install the [Pathfinder Libraries](https://github.com/JacisNonsense/Pathfinder)
7) Run "WPILib Java Deploy"


## Features
- Onboard trajectory generation, caching, and following
- Code Organization
- New Console Class (console.java) for logging organization
- Custom Command Based System
- Subsystem Manager
- New Squeezy Code with Current Detection Holding
- New Elevator Code
- New Drive Code with integration into autonomous
- Odometry, robot position calculating


## Robot
![Robot Image](/images/Athena.jpg)


# Breakerbots Style Guide

## Naming Scheme
| Object | Naming Scheme |
| ------------- | ------------- |
| Regular Classes | UpperCamelCase |
| Static Classes | lowerCamelCase or UpperCamelCase |
| Sub-Classes | either UpperCamelCase or lowerCamelCase |
| Methods | lowerCamelCase |
| Regular Variables | either lowerCamelCase or UpperCamelCase |
| Constants | _ + name (lowerCamelCase) |

## Object Organization
| Object | Organization |
| -- | -- |
| Single Instance Objects | Singleton or Static |
| Constant Classes | Use sub-classes for org. |
| Complex Systems/Objects | Include a Dictionary of Some Sort |

#### Singleton Example
```
private static Squeezy _inst = null; 
public static Squeezy getInstance() { if (_inst == null) _inst = new Squeezy(); return _inst; }
```

#### Static Sub-Classes Example
```
public class Constants {
	...
	
	//Drive
	public static final class Drive {
		...
	}
	
	//Squeezy
	public static final class Squeezy {
		...
	}
}
```

#### Dictionary Example
```
Squeezy Vocab:
 	 - Word: Description/Definition
 		- Subword [MODIFIER] (ABBRIVIATION): Description/Definition <Relative Reference>
 
 	 - Fold: Squeezy Moving/Folding/Lifting Up and Down
 		- Up: Squeezy is folded up closer to the elevator (facing up)
 		- Down: Squeezy is folded down closer to the group (facing forward)
 		- Either (ethr): Squeezy is either folder <Up> or <Down> 
 
 	 - Wheels: The Green Wheels on Squeezy
 		- In [Speed]: The Wheels are spinning in an intake motion at <Speed> (in toward the robot)
 		- Out [Speed]: The Wheels are spinning in an eject motion at <Speed> (out from the robot)
 		- Idle: The Wheels are not being spun by the motor
 
 	 - Arms: Squeezy Arms and the Motor/Belt moving them
 		- Out: The Arms are the point farthest from eachother (fully open)
 		- In: The Arms are at the point closest to eachother (full in)
 		- Holding (hld or hold): The Arms are at or moving toward ~13 inches from eachother and pushing against the cube (enought to hold the cube)
 		- Idle (idl or idle): The Arms can be in any position and the motor is idle
 		- Idle Hold (i-hld or ihld): The Arms are ~13 inches from eachother (touching the cube), but not forcing/hold the cube
 		- Idle Out (i-out or iout): The arms are <Out> and <Idle>
 		- Idle In (i-in or iin): The arms are <In> and <Idle>
 		- Moving Out [Speed] (m-out): The arms are moving toward the <Out> position at <Speed>
 		- Moving In [Speed] (m-in): The arms are moving toward the <In> position at <Speed>
		...
```

## Devices, Controls and Constants
All devices on the robot, controls and constants are organized into static files (Devices.java, Constants.java, HMI.java). Each of these files have organization sub-classes (ex. Devices.Squeezy). To use a device, constant, or control either call them directly (ex. Device.Squeezy._armsInSpeed) or create a list of references at the beginning of the file.

**Example:**
```
...
//Devices (d + DEVICE)
private static TalonSRX dArms       = Devices.Squeezy.squeeze;
private static TalonSRX dLWheel     = Devices.Squeezy.leftSpin;
private static TalonSRX dRWheel     = Devices.Squeezy.rightSpin;
private static DoubleSolenoid dFold = Devices.Squeezy.fold;
...
```
