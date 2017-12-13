/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    val list = mutableListOf(0..255).flatten().toMutableList()
    val instructions = "206,63,255,131,65,80,238,157,254,24,133,2,16,0,1,3".split(",").map { s -> s.trim().toInt() }
    var index = 0
    var skipSize = 0

    instructions.forEach { i ->
        val end = (index + i - 1)
        swap(list, index, end)
        index = (index + i + skipSize) % list.size
        skipSize++
        println(list)
    }

    println((list[0] * list[1]).toString())
}

fun swap(list : MutableList<Int>, start: Int, end: Int) : MutableList<Int>{
    val swapList = mutableListOf<Int>()
    for (i in start..end) {
        swapList.add(list[i % list.size])
    }
    var index = start
    for (i in swapList.size - 1 downTo 0) {
        list[index % list.size] = swapList[i]
        index++
    }
    return list
}