import javafx.geometry.Pos
import java.io.File
import java.io.InputStream

/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    val inputStream: InputStream = File("input/day20.txt").inputStream()
    val list = mutableListOf<Particle>()
    inputStream.bufferedReader().lines().forEach { line ->
        list.add(Particle(line))
    }

    for(i in 0..10000) {
        var distance = Int.MAX_VALUE
        var closest : Particle? = null
        list.forEach { v ->
            val dis = v.update()
            if (dis < distance) {
                distance = dis
                closest = v
            }
        }
        closest!!.inc()
    }

    val p = list.maxBy { it.closest }
    println(list.indexOf(p))
}

class Particle(line: String) {
    var closest = 0
    private val position: Position
    private val velocity: Position
    private val acceleration: Position

    init {
        val v = line.indexOf("v=")
        val a = line.indexOf("a=")

        val ps = line.substring(0, v)
        val (px, py, pz) = ps.substring(ps.indexOf("<") + 1, ps.indexOf(">")).split(",").map { it.trim().toInt() }
        position = Position(px, py, pz)

        val vs = line.substring(v, a)
        val (vx, vy, vz) = vs.substring(vs.indexOf("<") + 1, vs.indexOf(">")).split(",").map { it.trim().toInt() }
        velocity = Position(vx, vy, vz)

        val ast = line.substring(a)
        val (ax, ay, az) = ast.substring(ast.indexOf("<") + 1, ast.indexOf(">")).split(",").map { it.trim().toInt() }
        acceleration = Position(ax, ay, az)
    }

    fun inc() {
        closest += 1
    }

    fun update(): Int {
        velocity += acceleration;
        position += velocity
        return Math.abs(position.x) + Math.abs(position.y) + Math.abs(position.z)
    }

}

data class Position(var x: Int, var y: Int, var z: Int) {
    operator fun plusAssign(p: Position) {
        x += p.x
        y += p.y
        z += p.z
    }
}