
# react-native-voxeet-firebase

## Getting started

`$ npm install react-native-voxeet-firebase --save`

### Mostly automatic installation

`$ react-native link react-native-voxeet-firebase`

### Manual installation


#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNVoxeetFirebasePackage;` to the imports at the top of the file
  - Add `new RNVoxeetFirebasePackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-voxeet-firebase'
  	project(':react-native-voxeet-firebase').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-voxeet-firebase/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-voxeet-firebase')
  	```


## Usage
```javascript
import RNVoxeetFirebase from 'react-native-voxeet-firebase';

// TODO: What to do with the module?
RNVoxeetFirebase;
```
  