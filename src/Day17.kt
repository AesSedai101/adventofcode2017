/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    val input = 304
    val lock = mutableListOf(0)
    var index = 0

    for(i in 1..2017) {
        index = (index + input) % lock.size + 1
        lock.add(index, i)
    }
    println(lock.get(index + 1).toString())
}