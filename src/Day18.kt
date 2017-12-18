import java.io.File
import java.io.InputStream

/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    val inputStream: InputStream = File("input/day18.txt").inputStream()
    val instructions = mutableListOf<Instr>()
    inputStream.bufferedReader().lines().forEach { line ->
        instructions.add(Instr(line))
    }

    println(runInstructions(instructions).toString())
}

fun runInstructions(instructions: List<Instr>): Long {
    val registers = mutableMapOf<String, Pair<Long, Long>>()
    var address = 0

    while(true) {
        val i = instructions[address]
        val (value, sound) = registers.getOrDefault(i.register, Pair(0L, 0L))
        when (i.instruction) {
            "snd" -> {
                registers.put(i.register, Pair(value, value))
            }
            "set" -> {
                registers.put(i.register, Pair(i.parseValue(registers), sound))
            }
            "add" -> {
                registers.put(i.register, Pair(value + i.parseValue(registers), sound))
            }
            "mul" -> {
                registers.put(i.register, Pair(value * i.parseValue(registers), sound))
            }
            "mod" -> {
                registers.put(i.register, Pair(value % i.parseValue(registers), sound))
            }
            "rcv" -> {
                if (sound != 0L) {
                    return sound
                }
            }
            "jgz" -> {
                if (value > 0) {
                    address += (i.parseValue(registers) - 1).toInt()
                }
            }
        }
        address++
    }
}

class Instr(line: String) {
    val instruction: String
    val register: String
    val numeric: Boolean
    val value: String

    init {
        val t = line.split(" ")
        instruction = t[0]
        register = t[1]
        if (t.size > 2) {
            numeric = t[2].toIntOrNull() != null
            value = t[2]
        } else {
            numeric = false
            value = ""
        }
    }

    fun parseValue(registers: Map<String, Pair<Long, Long>>): Long {
        if (numeric) {
            return value.toLong()
        }
        return registers[value]!!.first
    }
}