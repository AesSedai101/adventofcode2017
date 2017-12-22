import java.io.File
import java.io.InputStream

/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    val inputStream: InputStream = File("input/day21.txt").inputStream()
    val mapping = mutableMapOf<String, String>()
    inputStream.bufferedReader().lines().forEach { line ->
        val (k, v) = line.split("=>").map { it.trim() }
        mapping.put(k, v)
        var key = k
        for (i in 0..2) {
            key = rotate(key)
            mapping.put(key, v)
            mapping.put(flip(key), v)
        }
    }

    var board = stringToBoard(".#./..#/###")
    repeat(18, {
        if (board.size % 2 == 0) {
            board = expandBoard(2, mapping, board)
        } else {
            board = expandBoard(3, mapping, board)
        }
    })
    println(board.sumBy { c -> c.count { it == '#' } }.toString())
}

fun expandBoard(divisor: Int, mappings: Map<String, String>, board: Array<CharArray>): Array<CharArray> {
    val boxes = board.size / divisor
    val newSize = (divisor + 1) * boxes
    val expanded = Array(newSize, { CharArray(newSize) })

    for (row in 0 until boxes) {
        for (col in 0 until boxes) {
            // process the block starting at [topRow][leftCol]
            val topRow = row * divisor
            val leftCol = col * divisor
            var block = ""
            for (i in topRow until topRow + divisor) {
                for (j in leftCol until leftCol + divisor) {
                    block += board[i][j]
                }
                block += "/"
            }
            // find the "to" mapping and write to expanded array
            val to = mappings[block.substring(0, block.length - 1)]!!
            val toBoard = stringToBoard(to)
            for (i in 0 until toBoard.size) {
                for (j in 0 until toBoard.size) {
                    expanded[row * (divisor + 1) + i][col * (divisor + 1) + j] = toBoard[i][j]
                }
            }
        }
    }
    return expanded
}

fun printBoard(board: String) {
    val b = stringToBoard(board)
    b.forEach { r ->
        r.forEach { print(it) }
        println()
    }
    println()
}

fun rotate(value: String): String {
    val board = stringToBoard(value)
    val rotated = Array(board.size, { CharArray(board.size) })

    for (row in 0 until board.size) {
        for (col in board.size - 1 downTo 0) {
            rotated[row][col] = board[col][board.size - 1 - row]
        }
    }
    return boardToString(rotated)
}

fun flip(value: String): String {
    val board = stringToBoard(value)
    val flipped = Array(board.size, { CharArray(board.size) })

    for (row in 0 until board.size) {
        for (col in 0 until board.size) {
            flipped[row][col] = board[row][board.size - col - 1]
        }
    }

    return boardToString(flipped)
}

private fun boardToString(rotated: Array<CharArray>): String {
    var result = ""
    for (i in rotated.indices) {
        rotated[i].forEach { result += it }
        result += "/"
    }
    return result.substring(0, result.length - 1)
}

fun stringToBoard(s: String): Array<CharArray> {
    val rows = s.split("/")
    val board = Array(rows.size, { CharArray(rows.size) })
    for (i in rows.indices) {
        board[i] = rows[i].toCharArray()
    }
    return board
}
