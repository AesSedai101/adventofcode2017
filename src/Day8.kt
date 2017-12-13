import java.io.File
import java.io.InputStream
import kotlin.math.max
import kotlin.streams.toList

/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    val inputStream: InputStream = File("input/day8.txt").inputStream()
    val list = inputStream.bufferedReader().lines().map { l -> Instruction(l) }.toList()

    val registerValues = mutableMapOf<String, Int>()
    var maxValue = 0

    list.forEach { op ->
        val conditionRegisterValue = registerValues.getOrDefault(op.condition.register, 0)
        if (op.condition.match(conditionRegisterValue)) {
            // execute op
            val register = op.register
            val value = registerValues.getOrDefault(register, 0)
            if (op.opInc) {
                val newValue = value + op.opValue
                registerValues.put(register, newValue)
                maxValue = max(maxValue, newValue)
            } else {
                val newValue = value - op.opValue
                registerValues.put(register, newValue)
                maxValue = max(maxValue, newValue)
            }
        }
    }

    val max = registerValues.maxBy { s ->
        s.value
    }
    println(max.toString())
    println(maxValue.toString())
}

class Instruction(line: String) {
    var register: String
    var opInc: Boolean
    var opValue: Int
    var condition: Condition

    init {
        val values = line.split(" ")
        this.register = values[0]
        this.opInc = values[1] == "inc"
        this.opValue = values[2].toInt()
        this.condition = Condition(values[4], values[5], values[6].toInt())
    }

}

class Condition(val register: String, val comp: String, val value: Int) {
    fun match(registerValue: Int): Boolean {
        when (comp) {
            "!=" -> return registerValue != value
            ">" -> return registerValue > value
            ">=" -> return registerValue >= value
            "<" -> return registerValue < value
            "<=" -> return registerValue <= value
            "==" -> return registerValue == value
        }
        throw UnsupportedOperationException("Unknown " + comp)
    }
}