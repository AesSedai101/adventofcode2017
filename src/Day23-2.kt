/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    var count = 0;

    var b = 106700
    val c = 123700

    while (b != c) {
        val someFlag = determineFlag(b)
        if (someFlag) {
            count += 1
        }
        b += 17
    }
    println(count.toString())
}

private fun determineFlag(b: Int): Boolean {
    for (d in 2..b) {
        for (e in 2..b) {
            if (d * e == b) {
                return true
            }
        }
    }
    return false
}
