
__author__ = ‘peytonT’


def iscoprime(a, b):
    while a*b:
        if a > b:
            a %= b
        else:
            b %= a
    if max(a, b) == 1:
        return 1
    return 0

#----------------------------------


def findcoprime(n):
    for x in range(2, n):
        if iscoprime(n, x):
            return x
    return None

#----------------------------------


def modexp(u, m, p):

    a = 1

    while m > 0:
        if m % 2 == 0:
            u = (u * u) % p
            m /= 2
        else:
            a = (u * a) % p
            m -= 1
    return a

#----------------------------------


def modinv(a, p):

    if iscoprime(a, p):
        # using brute force
        a %= p
        for i in range(1, p):
            if a*i % p == 1:
                return i
    return None

# change your arguments here

"""

u = 273
m = 893
p = 3571
print("modexp [%s, %s, %s] = %s" % (u, m, p, modexp(u, m, p)))

#-------------------------------------------------------------

a = 2
p = 17
print("modinv [%s, %s] = %s" % (a, p, modinv(a, p)))

#-------------------------------------------------------------

n = 11004009900
print("coprime of %s: %s" % (n, findcoprime(n)))

"""