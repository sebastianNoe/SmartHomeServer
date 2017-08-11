# SmartHomeServer
Target of this solution is to provide a RESTful Server which allows to execute commands to control controllable devices like radio sockets, shutters or other devices.
To do so currently the rcswitch-pi library on a Raspberry Pi is reused.
To define the location of that library and also to define the to be managed devices a settings.xml is expected.

## Currently supported Device Types and supported commands:
- RadioSocket
  - Toggle -> Toggles on/off of the Radio Socket

## Settings XML Format
All device types can be used here.
Device name needs to be unique.
Each device type can have its unique attributes

```XML
<smartHomeServer>
	<settings>
		<rcSwitch-piLocation></rcSwitch-piLocation>
	</settings>
	<device>
		<deviceType>RadioSocket</deviceType>
		<name>name of radio socket for identification</name>
		<systemCode>normally a sequence of 5 binary values</systemCode>
		<deviceCode>normally a number between 1 and 5</deviceCode>
	</device>
</smartHomeServer>
```
