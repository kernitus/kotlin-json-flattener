# kotlin-json-flattener
This repository implements a simple JSON flattener in Kotlin.
An input JSON is fed through stdin and a flattened version, where there are only top-level keys, is sent to stdout.
I used Kotlin due to its conciseness, its type nullability system (to avoid logical errors), and the availability of great libraries such as [Klaxon](https://github.com/cbeust/klaxon/) and [Kotest](https://kotest.io/).

## Building and Running
The repository provides a Gradle script which contains the required dependencies and setup for running tests.
By running `gradle build` the script will automatically create a "fat jar", which includes all dependencies necessary to run.
The jar will by default be found at `build/libs/kotlin-json-flattener-fat.jar`.
Thus, to run the compiled program we can execute `java -jar build/libs/kotlin-json-flattener-fat.jar`.
However, this will not do anything because it is not given any input.
We must use Unix pipes as so: `cat example.json | java -jar build/libs/kotlin-json-flattener-fat.jar`, and the flattened JSON will be output to the terminal.

## Methodology
The program works by directly parsing the input from stdin using the Klaxon library, turning it into a JsonObject.
It then calls the `flatten` function, which is an extension function of JsonObject.
This function will loop through all the top-level key-value pairs, and for each, will either recursively call `flatten` again (if the value is a JsonObject), or store the value in the output JsonObject.
The path including the subkeys is generated each iteration by appending the current key to the key passed in as a function argument. If the argument is empty, it is a top-level key, and so nothing is prepended.
This avoids having leading full stops in the output JSON where there shouldn't be.

This approach will have a worst-case runtime of O(N), where N is the total amount of keys in the JSON file.
It will also have a worst-case auxiliary space requirement of O(D) stack frames, where D is the maximum depth of the JSON file.

## Tests
The repository also includes some Unit tests, implemented using Kotest. The tests can be run using the `gradle test` command.
The following tests are included and all pass successfully:
* Test for an empty JSON
* Test for the provided sample JSON
* Test for an already-flattened JSON
* Test for a JSON with duplicate keys
* Test for a deep JSON (with nested objects)
* Test for a very deep JSON (with nested objects)

## Time taken
The total time taken to implement this project is about 2 hours 30 minutes.
This includes the initial setup time of making the repository, the directory structure, and a working Gradle file, at about 30 minutes.
Next was the implementation of the Flattener, which was in the order of 45 minutes, including finding a suitable JSON library and learning how to use it.
Subsequent was the writing of tests, which involved setting up Kotest correctly and coming up with suitable tests, also about 45 minutes.
Finally came the write-up, which took about 30 minutes, including figuring out how to create a fat Jar so that the program can be run independently of an IDE.
