/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    var count = 0;
    var b = 106700
    val c = 123700

    while (b <= c) {
        if ((2..Math.sqrt(b.toDouble()).toInt()).any { b % it == 0 }) {
            count += 1
        }
        b += 17
    }
    println(count.toString())
}
