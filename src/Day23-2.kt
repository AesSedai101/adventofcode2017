/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    var d = 0;
    var e = 0;
    var f = 0;
    var g = 0;
    var h = 0;

    var b = 106700
    val c = 123700

    do {
        //  set f 1
        f = 1
        //  set d 2
        d = 2
        do {
            //  set e 2
            e = 2
            do {
                //  set g d
                g = d
                //  mul g e
                g *= e
                //  sub g b
                g -= b
                //  jnz g 2
                if (g == 0) {
                    //  set f 0
                    f = 0
                }
                e += 1
                //   set g e
                g = e
                //  sub g b
                g -= b
                //   jnz g -8
            } while (g != 0)
            //  sub d -1
            d -= -1
            //  set g d
            g = d
            //  sub g b
            g -= b
            //  jnz g -13
        } while (g != 0)
        //  jnz f 2
        if (f == 0) {
            h += 1
        }
        //  set g b
        g = b
        //  sub g c
        g -= c
        // jnz g 2
        if (g == 0) {
            println(h.toString())
            return
        }
        b += 17
    } while (true)
}
