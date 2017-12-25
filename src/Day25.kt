import java.io.File
import java.io.InputStream
import kotlin.streams.toList

/**
 * @author elsabe.ros
 */
val R = 1
val L = -1

fun main(args: Array<String>) {
    val inputStream: InputStream = File("input/day25.txt").inputStream()

    val lines = inputStream.bufferedReader().lines().filter { !it.isEmpty() }.toList()

    val startState = lines[0]!!.replace("Begin in state", "").trim().substring(0, 1)
    val diagnosticCount = lines[1]!!.replace("Perform a diagnostic checksum after ", "").replace(" steps.", "").trim().toLong()

    val states = mutableListOf<State>()
    var index = 2
    while (index < lines.size) {
        val name = lines[index].replace("In state ", "").replace(":", "").trim()
        index += 2

        val zValue = lines[index].replace("- Write the value ", "").trim().substring(0, 1).toInt()
        val zDirection = lines[index + 1].replace("- Move one slot to the ", "").trim()
        val zState = lines[index + 2].replace("- Continue with state ", "").trim().substring(0, 1)
        index += 4
        val oValue = lines[index].replace("- Write the value ", "").trim().substring(0, 1).toInt()
        val oDirection = lines[index + 1].replace("- Move one slot to the ", "").trim()
        val oState = lines[index + 2].replace("- Continue with state ", "").trim().substring(0, 1)
        index += 3
        states.add(State(name, zValue, zDirection, zState, oValue, oDirection, oState))
    }

    println(runTuring(startState, diagnosticCount, states))
}

fun runTuring(startState: String, diagnosticCount: Long, states: List<State>): String {
    var state = states.find { it.name.equals(startState) }!!
    var counter = 0L
    var index = 0

    val tape = mutableMapOf<Int, Int>()
    while (counter < diagnosticCount) {
        val thisValue = tape.getOrDefault(index, 0)
        val change = if (thisValue == 0) state.zero else state.one

        tape.put(index, change.newValue)
        index += change.direction
        state = states.find { it.name.equals(change.newState) }!!

        counter++
    }

    return tape.count { it.value == 1}.toString()
}

class State(name: String, zValue: Int, zDirection: String, zState: String, oValue: Int, oDirection: String, oState: String) {
    val name: String
    val zero: Change
    val one: Change

    init {
        this.name = name
        this.zero = Change(zValue, zDirection, zState)
        this.one = Change(oValue, oDirection, oState)
    }
}

class Change(zValue: Int, zDirection: String, zState: String) {
    val newValue: Int
    val newState: String
    val direction: Int

    init {
        this.newValue = zValue;
        this.newState = zState
        this.direction = if ("right.".equals(zDirection)) R else L
    }
}