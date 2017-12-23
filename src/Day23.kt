import java.io.File
import java.io.InputStream

/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    val inputStream: InputStream = File("input/day23.txt").inputStream()
    val instructions = mutableListOf<Instr>()
    inputStream.bufferedReader().lines().forEach { line ->
        instructions.add(Instr(line))
    }

    val registers = mutableMapOf<String, Long>()
    registers.put("a", 0)
    var index = 0
    var mulCount = 0


    while (true) {
        val i = instructions.getOrNull(index)
        if (i == null) {
            println(mulCount.toString())
            return
        }
        val value = registers.getOrDefault(i.register, 0L)
        when (i.instruction) {
            "set" -> {
                registers.put(i.register, i.parseValue(registers))
            }
            "sub" -> {
                registers.put(i.register, value - i.parseValue(registers))
            }
            "mul" -> {
                mulCount++
                registers.put(i.register, value * i.parseValue(registers))
            }
            "jnz" -> {
                var number = i.register.toIntOrNull()
                if (number == null) number = value.toInt()
                if (number != 0) {
                    index += (i.parseValue(registers) - 1).toInt()
                }
            }
        }
        index++
    }
}
