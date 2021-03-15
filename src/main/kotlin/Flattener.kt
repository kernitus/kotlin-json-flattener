package main.kotlin

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser

fun main() {
    // Grab and parse input JSON from stdin
    val inputJson = Parser.default().parse(System.`in`) as JsonObject
    val flattenedJson = JsonObject()
    inputJson.flatten(flattenedJson)
    // Pretty print the flattened JSON
    println(flattenedJson.toJsonString(true))
}

// Extension function: takes a JsonObject and flattens all values
private fun JsonObject.flatten(output: JsonObject, currentKey: String = "") {
    for ((key, value) in this.entries) { // Look through all entries on top level
        val path = if (currentKey.isEmpty()) key else "$currentKey.$key" // Merge with subkey as required
        if (value is JsonObject) { // If deep entry, recursively flatten the value
            value.flatten(output, path)
        } else {
            output[path] = value // Store value to output with flattened key
        }
    }
}