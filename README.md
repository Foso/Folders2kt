<h1 align="center">Folders2kt 📁</h1>
An Interpreter/Transpiler of the Folders esoteric programming language, a language with no code and just folders, written in Kotlin


[![justforfunnoreally.dev badge](https://img.shields.io/badge/justforfunnoreally-dev-9ff)](https://justforfunnoreally.dev)
[![jCenter](https://img.shields.io/badge/Apache-2.0-green.svg
)](https://github.com/Foso/Folders2kt/blob/master/LICENSE)
  <a href="https://twitter.com/intent/tweet?text=Hey, check out Folders2kt https://github.com/Foso/Folders2kt via @jklingenberg_ 
"><img src="https://img.shields.io/twitter/url/https/github.com/angular-medellin/meetup.svg?style=social" alt="Tweet"></a>

### Show some :heart: and star the repo to support the project

[![GitHub stars](https://img.shields.io/github/stars/Foso/Folders2kt.svg?style=social&label=Star)](https://github.com/Foso/Folders2kt) [![GitHub forks](https://img.shields.io/github/forks/Foso/Folders2kt.svg?style=social&label=Fork)](https://github.com/Foso/Folders2kt/fork) [![GitHub watchers](https://img.shields.io/github/watchers/Foso/Folders2kt.svg?style=social&label=Watch)](https://github.com/Foso/Folders2kt) [![Twitter Follow](https://img.shields.io/twitter/follow/jklingenberg_.svg?style=social)](https://twitter.com/jklingenberg_)

## Introduction


"Folders is a language where the program is encoded into a directory structure. All files within are ignored, as are the names of the folders. Commands and expressions are encoded by the pattern of folders within folders. "
Folders was created by Daniel Temkin https://danieltemkin.com/Esolangs/Folders/. If you want to learn more about the language, check https://www.youtube.com/watch?v=Igh-vBI2LXc

This is what a "Hello World"-program looks like:
<p align="center">
  <img src ="https://raw.githubusercontent.com/Foso/Folders2kt/master/docs/helloworld.png" height=500 />
</p>

Folders2kt can execute this programs on the JVM or print you Kotlin source code for it. 

### Run it
To execute a Folders program you need to add the directory path of the program with the "-d" parameter

<kbd> java -jar Folders2kt-1.0-SNAPSHOT-all.jar -d /Users/jklingenberg/Code/2021/Folders2kt/sample_programs/99Bottles </kbd>

<p align="center">
  <img src ="https://raw.githubusercontent.com/Foso/Folders2kt/master/docs/execute.png" height=50 />
</p>

### Print Source
To execute a Folders program you need the directory path of the program with the "-d" parameter and the "-source" parameter

<kbd> java -jar Folders2kt-1.0-SNAPSHOT-all.jar -d /Users/jklingenberg/Code/2021/Folders2kt/sample_programs/99Bottles -source </kbd>

<p align="center">
  <img src ="https://raw.githubusercontent.com/Foso/Folders2kt/master/docs/source.png" height=200 />
</p>

# 🏠 Architecture

## 🛠️ Built With
### Kotlin
[![jCenter](https://img.shields.io/badge/Kotlin-2.0.0-green.svg
)]()



### Project Structure
* <kbd>src/main</kbd> - Source code of the interpreter
* <kbd>sample_programs</kbd> - Example programs I got from https://github.com/SinaKhalili/Folders.py
You need to extract the zips before you can run them.

## ✍️ Feedback

Feel free to send feedback on [Twitter](https://twitter.com/jklingenberg_) or [file an issue](https://github.com/foso/Folders2kt/issues/new). Feature requests are always welcome. 


## 📜 License

This project is licensed under the Apache License, Version 2.0 - see the [LICENSE.md](https://github.com/Foso/Folders2kt/blob/master/LICENSE) file for details

### Find this project useful ? :heart:
* Support it by clicking the :star: button on the upper right of this page. :v:

License
-------

    Copyright 2021 Jens Klingenberg

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
