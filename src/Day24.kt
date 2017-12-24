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

    var max = Int.MIN_VALUE
    connectors.filter { it -> it.A == 0 }.forEach { c ->
        val v = buildBridge(c.selectA(), connectors.filter { it != c }, "")
        if (v > max) {
            max = v
        }
    }
    println(max.toString())
}

fun buildBridge(c: Connector, connectors: List<Connector>, prep: String): Int {
    //println(prep + "- " + c.A + "/" + c.B + " [" + c.SCORE + "]")
    // find all matches
    val matches = connectors.filter { c.notSelected() == it.A || c.notSelected() == it.B}.sortedByDescending { c.SCORE }
    if (matches.isEmpty()) {
        return c.SCORE
    }
    // and try them all
    var max = Int.MIN_VALUE
    matches.forEach { m ->
        val v = buildBridge(m.select(c.notSelected()), connectors.filter { it != m }, prep + " ")
        if (v > max) {
            max = v
        }
    }
    return c.SCORE + max
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