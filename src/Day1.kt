/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    val input = "";
    val length = input.length - 1
    var sum = 0;
    for (i in input.indices) {
        val value = input[i]
        if (i == length) {
            if (value == input[0]) {
                sum += value.toString().toInt();
            }
        } else {
            if (value == input[i + 1]) {
                sum += value.toString().toInt();
            }
        }
    }
    print(sum)
}