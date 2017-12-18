import java.io.File
import java.io.InputStream
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.concurrent.thread

/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    val inputStream: InputStream = File("input/day18.txt").inputStream()
    val instructions = mutableListOf<Instr>()
    inputStream.bufferedReader().lines().forEach { line ->
        instructions.add(Instr(line))
    }

    val p1 = Program18(0)
    val p2 = Program18(1)

    thread {
        p1.runInstructions(p2, instructions)
        println("0: " + p1.queueCount.toString())
        p2.stop()
    }
    thread {
        p2.runInstructions(p1, instructions)
        println("1: " + p2.queueCount.toString())
        p1.stop()
    }
}

class Program18(val p: Long) {
    val queue: Queue<Long> = ConcurrentLinkedQueue<Long>()
    var queueCount = 0;
    private var waiting : AtomicBoolean = AtomicBoolean(false)
    private var stopVal : AtomicBoolean = AtomicBoolean(false)

    fun runInstructions(other: Program18, instructions: List<Instr>): Long {
        val registers = mutableMapOf<String, Long>()
        registers.put("p", p)

        var address = 0

        while (true) {
            if (stopVal.get()){
                return -1L
            }
            val i = instructions[address]
            val value = registers.getOrDefault(i.register, 0L)
            when (i.instruction) {
                "snd" -> {
                    queue.add(value)
                    queueCount++
                }
                "set" -> {
                    registers.put(i.register, i.parseValue(registers))
                }
                "add" -> {
                    registers.put(i.register, value + i.parseValue(registers))
                }
                "mul" -> {
                    registers.put(i.register, value * i.parseValue(registers))
                }
                "mod" -> {
                    registers.put(i.register, value % i.parseValue(registers))
                }
                "rcv" -> {
                    waiting.set(true)
                    val v = other.dequeueValue(this)
                    waiting.set(false)
                    if (v == null) {
                        return -1L
                    }
                    registers.put(i.register, v)
                }
                "jgz" -> {
                    val number = i.register.toIntOrNull()
                    if (number != null) {
                        if (number > 0) {
                            address += (i.parseValue(registers) - 1).toInt()
                        }
                    } else {
                        if (value > 0) {
                            address += (i.parseValue(registers) - 1).toInt()
                        }
                    }
                }
            }
            address++
        }
    }

    private fun dequeueValue(caller: Program18): Long? {
        while (queue.isEmpty()) {
            if (caller.check(waiting.get())) {
                // both are waiting
                return null
            }
        }
        return queue.poll()
    }

    private fun check(w: Boolean) : Boolean{
        return waiting.get() && w && queue.isEmpty()
    }

    fun stop() {
        this.stopVal.set(true);
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

    fun parseValue(registers: Map<String, Long>): Long {
        if (numeric) {
            return value.toLong()
        }
        return registers[value]!!
    }
}