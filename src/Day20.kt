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

    for (i in 0..list.size) {
        list.forEach { v ->
            v.update()
        }
        val set = list.distinctBy { it.position() }
        if (list.size != set.size) {
            // there are particles occupying the same space
            set.forEach{ particle ->
                val matches = list.filter { it.equals(particle) }
                if (matches.size > 1) {
                    list.removeAll(matches)
                }

            }
        }
    }

    println(list.size.toString())
}

class Particle(line: String) {
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

    fun update(): Int {
        velocity += acceleration;
        position += velocity
        return Math.abs(position.x) + Math.abs(position.y) + Math.abs(position.z)
    }


    fun equals(p: Particle): Boolean {
        return position == p.position
    }

    fun position(): Position {
        return position
    }
}

data class Position(var x: Int, var y: Int, var z: Int) {
    operator fun plusAssign(p: Position) {
        x += p.x
        y += p.y
        z += p.z
    }

    fun equals(p: Position): Boolean {
        return x == p.x && y == p.y && z == p.z
    }
}