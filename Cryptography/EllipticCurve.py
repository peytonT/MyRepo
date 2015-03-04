__author__ = 'peytonT’

import Cryptography


# used for both negative and positive numbers
def mod(a, p):

    if a < 0:
        temp = abs(a) % p
        return p - temp
    return a % p


def find_points():
    nopoints = 0

    if (p % 4) == 3:
        for x in range(0, p-1):
            ypow2 = (pow(x, 3) + a*x + b) % p
            r = Cryptography.modexp(ypow2, (p+1)/4, p)
            r1 = p - r
            if r*r % p == ypow2:
                print("(%s, %s)" % (x, r))
                nopoints += 1

            if r1*r1 % p == ypow2:
                print("(%s, %s)" % (x, r1))
                nopoints += 1
        print("\n Number of points = Elliptic's order = ", nopoints)

    else:
        print("\nDon't know how to compute root!!!")


def add_points(x1, y1, x2, y2):
    try:
        alpha = mod(y1-y2, p) * (Cryptography.modinv(mod(x1-x2, p), p)) % p
        x = mod(alpha*alpha - x1 - x2, p)
        y = mod(alpha*(x1 - x) - y1, p)

      #  print("[%s, %s] + [%s, %s] = [%s, %s]" % (x1, y1, x2, y2, x, y))
        return x, y

    except TypeError:
        print("\n Cannot add")
        return 0


def calculate_2p(xp, yp):
    alpha = ((3*xp*xp + a) % p) * (Cryptography.modinv(2*yp, p)) % p

    x2p = mod(alpha * alpha - 2*xp, p)
    y2p = mod(alpha * (xp - x2p) - yp, p)

   # print("2[%s, %s] = [%s, %s]" % (xp, yp, x2p, y2p))
    return x2p, y2p


def calculate_n_point(n, xp, yp):

    result = calculate_2p(xp, yp)

    temp1, temp2 = result

    if n == 2:
        print("\n %s*(%s, %s) = (%s, %s)" % (n, xp, yp, temp1, temp2))
        return result

    for i in range(3, n+1):
        result = add_points(temp1, temp2, xp, yp)
        if result == 0:
            #return the rank of this point
            return -1, i

        temp1, temp2 = result

    print("\n %s*(%s, %s) = (%s, %s)" % (n, xp, yp, temp1, temp2))
    return result


def find_point_order(x, y):

    # a point always has order of at least 2
    i = 2

    while True:
        result = calculate_n_point(i, x, y)
        if result[0] == -1:
            print("\n order: ", result[1])
            break
        i += 1




# change your arguments here:

a = 7
b = 15
p = 3571

find_points()

#add_points(9, 16, 9, 1)
#calculate_n_point(135, 6, 62)
#find_point_order(6, 62)
