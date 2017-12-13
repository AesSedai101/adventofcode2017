/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    val list = mutableListOf(0..255).flatten().toMutableList()
    val instructions = "206,63,255,131,65,80,238,157,254,24,133,2,16,0,1,3".toCharArray().map { s -> s.toInt() }.toIntArray() + intArrayOf(17, 31, 73, 47, 23)

    var index = 0
    var skipSize = 0

    for(z in 1..64) {
        instructions.forEach { i ->
            val end = (index + i - 1)
            swap(list, index, end)
            index = (index + i + skipSize) % list.size
            skipSize++
            println(list)
        }
    }

    // calculate dense hash
    val denseHash = mutableListOf<Int>()
    var x = 0
    while(x < list.size) {
        var hash = list[x]
        for (i in 1 until 16) {
            hash = hash xor list[x + i]
        }
        x += 16
        denseHash.add(hash)
    }
    denseHash.forEach { i ->
        print(i.toString(16))
    }
}

fun swap(list: MutableList<Int>, start: Int, end: Int): MutableList<Int> {
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