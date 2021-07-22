# Utilities
A library of utility classes I have created while working on other projects.

This library is for anyone who has run into similar shortcomings with other libraries and/or requires these specific functionalities. [Jump to #Licensing](#Licensing) for licensing information.

## Quick Links
* [Project Page](https://utk003.github.io/Utilities/)
* [Latest Documentation](https://utk003.github.io/Utilities/javadoc/latest.html)
* [Library Downloads](https://utk003.github.io/Utilities/releases.html)

## Library Contents
This library contains utility classes covering a wide range of use cases.

The important packages contained within this library are (in nested, alphabetical order):
* [Data](tree/main/src/main/java/io/github/utk003/util/data)
  * [Collections](tree/main/src/main/java/io/github/utk003/util/data/collection)
    * [Bijection](tree/main/src/main/java/io/github/utk003/util/data/collection/bijection)
    * [BitSet](tree/main/src/main/java/io/github/utk003/util/data/collection/bitset)
    * [MultiSet](tree/main/src/main/java/io/github/utk003/util/data/collection/multi/set)
    * [MultiMap](tree/main/src/main/java/io/github/utk003/util/data/collection/multi/map)
    * [Slice](tree/main/src/main/java/io/github/utk003/util/data/collection/slice)
  * [Immutable Tuples](tree/main/src/main/java/io/github/utk003/util/data/tuple/immutable)
  * [Mutable Tuples](tree/main/src/main/java/io/github/utk003/util/data/tuple/mutable)
  * [References](tree/main/src/main/java/io/github/utk003/util/data/reference)
  * [Singletons](tree/main/src/main/java/io/github/utk003/util/data/singleton)
* [Lambdas](tree/main/src/main/java/io/github/utk003/util/func)
* [Math](tree/main/src/main/java/io/github/utk003/util/math)
  * [Complex Numbers](tree/main/src/main/java/io/github/utk003/util/math/complex)
  * [Equation Solver](tree/main/src/main/java/io/github/utk003/util/math/solve)
  * [Interpolation](tree/main/src/main/java/io/github/utk003/util/math/interp)
  * [Noise Generation](tree/main/src/main/java/io/github/utk003/util/math/noise)
  * [Permutations](tree/main/src/main/java/io/github/utk003/util/math/perm)
  * [Unsigned Numbers](tree/main/src/main/java/io/github/utk003/util/math/unsigned)
* [Miscellaneous](tree/main/src/main/java/io/github/utk003/util/misc)
  * [Annotations](tree/main/src/main/java/io/github/utk003/util/misc/annotations)

### Data
The data package `io.github.utk003.util.data` is for data-related utilities.
The largest category within this package is for collections. These are followed by tuples, singletons, and finally references.

### Lambdas
The lambda package `io.github.utk003.util.func` is for generic lambda functions.
They can be used to represent simple functional transformations where a custom definition may be redundant or excessive.

### Math
The math package `io.github.utk003.util.math` is for alternative, advanced, or optimized mathematical constants, functions, and tools not provided by the default Java math classes.

### Miscellaneous
The miscellaneous package `io.github.utk003.util.misc` is for utilities that don't fall into any of the other categories.
The most important are the annotations and the verification class.

## Licensing
This software is publicly available under the MIT License. It can be freely used and modified in commercial and non-commercial applications.

### License Full-Text
MIT License

Copyright (c) 2020-21 Utkarsh Priyam

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
