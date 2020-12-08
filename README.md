# Utilities
This is a public library of personal utility classes I have created while working on other projects. A brief overview of its contents can be found below. This library can be used by anyone, as outlined in the [license](https://github.com/utk003/Utilities/blob/main/LICENSE).

## Library Contents
This library contains utility classes covering a range of different use cases.

The classes contained within this library are (in alphabetical order):
* [```ClassWrapper<K>```](https://github.com/utk003/Utilities#classwrapperk)
* [```Pair<A,B>```](https://github.com/utk003/Utilities#pairab)
* [```ThreadUtil```](https://github.com/utk003/Utilities#threadutil)
* [```Triplet<A,B,C>```](https://github.com/utk003/Utilities#tripletabc)
* [```Verifier```](https://github.com/utk003/Utilities#verifier)

Brief overviews of each class follow. Detailed descriptions can be found in the [documentation](https://utk003.github.io/documentation/libs/utilities/overview-summary.html).

### [```ClassWrapper<K>```](https://github.com/utk003/Utilities/blob/main/src/me/utk/util/data/ClassWrapper.java)
The ```ClassWrapper<K>``` class, which can be found in the ```me.utk.util.data``` package, is a wrapper class for (primarily) immutable objects (such as ```String```s) being stored as final variables. This intermediate class lets users update the value of the specific object while retaining the variable's final status.

### [```Pair<A,B>```](https://github.com/utk003/Utilities/blob/main/src/me/utk/util/data/Pair.java)
The ```Pair<A,B>``` class, which can be found in the ```me.utk.util.data``` package, provides a simple implementation for pairing objects of two (possibly different) data types. Potential applications include using this class to hold pairs of integers as coordinates.

### [```ThreadUtil```](https://github.com/utk003/Utilities/blob/main/src/me/utk/util/misc/ThreadUtil.java)
The ```ThreadUtil``` class, which can be found in the ```me.utk.util.misc``` package, is a basic thread-utility class. The class provides multiple thread-related methods and nested classes, which perform functions such as blocking threads for extended periods of time.

### [```Triplet<A,B,C>```](https://github.com/utk003/Utilities/blob/main/src/me/utk/util/data/Triplet.java)
The ```Triplet<A,B,C>``` class, which can be found in the ```me.utk.util.data``` package, provides a simple implementation for tripling objects of three (possibly different) data types. Like with pairs, potential applications include using this class to hold triplets of integers as coordinates.

### [```Verifier```](https://github.com/utk003/Utilities/blob/main/src/me/utk/util/misc/Verifier.java)
The ```Verifier``` class, which can be found in the ```me.utk.util.misc``` package, is a data-verification class. It provides a series of overloaded methods which allow users to compare two given inputs and throw an exception if they are not equal to each other. The functionality provided by this class can be thought of as a recoverable ```assert``` call.
