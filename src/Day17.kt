/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    val input = 304
   // val lock = mutableListOf(0)

    var index = 0
    var zeroIndex = 0
    var afterZero = 0

    for(i in 1..50000000) {
        index = (index + input) % i + 1
        //lock.add(index, i)
        if (index == zeroIndex + 1) {
            afterZero = i
        }
        if (index == zeroIndex) {
            zeroIndex += 1
        }
    }
   // println(lock.get(index + 1).toString())
    //println(lock.get(zeroIndex).toString())
   // println(lock.get(zeroIndex + 1).toString())
    println(afterZero.toString())
}