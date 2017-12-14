/**
 * @author elsabe.ros
 */
val hex = "0123456789abcdef".toCharArray().toList()

fun main(args: Array<String>) {
    val input = "jzgqcdpd"
    var count = 0
    for (i in 0..127) {
        val inputString = input + "-" + i.toString()
        val hash = calculateHash(inputString)
        val flags = calculateFlags(hash)
        for (j in 0..127) {
            print(if (flags[j]) "#" else ".")
        }
        println()
        count += flags.count {  b -> b }
    }
    println(count.toString())
}

fun calculateFlags(hash: String): BooleanArray {
    val result = BooleanArray(128)
    var index = 0;
    while (index < 32) {
        var value = hex.indexOf(hash[index])

        if (value >= 8) {
            result[index * 4] = true
            value -= 8
        }
        if (value >= 4) {
            result[index * 4 + 1] = true
            value -= 4
        }
        if (value >= 2) {
            result[index * 4 + 2] = true
            value -= 2
        }
        result[index * 4 + 3] = value == 1
        index++
    }

    return result;
}
