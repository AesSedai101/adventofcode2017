/**
 * @author elsabe.ros
 */
fun main(args: Array<String>) {
    val a = 1
    var b = 0;
    var c = 0;
    var d = 0;
    var e = 0;
    var f = 0;
    var g = 0;
    var h = 0;

    // set b 67
    b = 67
    //  set c b
    c = b
    //  jnz a 2 ->  is not zero
    //  >-- jnz 1 5 --<
    //  mul b 100
    b *= 100
    //  sub b -100000
    b -= -100000
    //  set c b
    c = b
    //  sub c -17000
    c -= -17000
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
                //   sub e -1
                e -= 1
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
            //   sub h -1
            h -= -1
        }
        //  set g b
        g = b
        //  sub g c
        g -= c
        // jnz g 2
        if (g == 0) {
            println(h.toString())
            //  jnz 1 3
            return
        }
        //  sub b -17
        b -= -17
    } while (1 != 0)//  jnz 1 -23
}
