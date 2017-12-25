import java.io.File
import java.io.InputStream

/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    val inputStream: InputStream = File("input/day24.txt").inputStream()
    val connectors = mutableListOf<Connector>()
    inputStream.bufferedReader().lines().forEach { line ->
        connectors.add(Connector(line))
    }

    var maxLength = Int.MIN_VALUE
    var maxScore = Int.MIN_VALUE
    connectors.filter { it -> it.A == 0 }.forEach { c ->
        val (score, length) = buildBridge(c.selectA(), connectors.filter { it != c }, "")
        if (length > maxLength) {
            maxScore = score
            maxLength = length
        } else if (length == maxLength && score > maxScore) {
            maxScore = score
        }
    }
    println(maxScore.toString())
}

fun buildBridge(c: Connector, connectors: List<Connector>, prep: String): Pair<Int, Int> {
    //println(prep + "- " + c.A + "/" + c.B + " [" + c.SCORE + "]")
    // find all matches
    val matches = connectors.filter { c.notSelected() == it.A || c.notSelected() == it.B }.sortedByDescending { c.SCORE }
    if (matches.isEmpty()) {
        return Pair(c.SCORE, 1)
    }
    // and try them all
    var maxLenght = Int.MIN_VALUE
    var maxScore = 0
    matches.forEach { m ->
        val (score, length) = buildBridge(m.select(c.notSelected()), connectors.filter { it != m }, prep + " ")
        if (length > maxLenght) {
            maxLenght = length
            maxScore = score
        } else if (length == maxLenght && score > maxScore) {
            maxScore = score
        }
    }
    return Pair(c.SCORE + maxScore, maxLenght + 1)
}

class Connector(line: String) {
    val A: Int
    val B: Int
    val SCORE: Int
    private var selected = 0;

    init {
        val sp = line.split("/")
        this.A = sp[0].toInt()
        this.B = sp[1].toInt()
        this.SCORE = A + B
    }

    fun selectA(): Connector {
        selected = A
        return this
    }

    fun selectB(): Connector {
        selected = B
        return this
    }

    fun notSelected(): Int {
        if (selected == A) {
            return B
        }
        if (selected == B) {
            return A
        }
        throw UnsupportedOperationException("Neither A nor B selected")
    }

    fun select(v: Int): Connector {
        if (A == v) {
            selectA()
            return this
        }
        if (B == v) {
            selectB()
            return this
        }
        throw UnsupportedOperationException("Neither A nor B selected")
    }
}