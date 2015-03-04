__author__ = 'peytonT’

import Cryptography


def rsa(p, q, m):
    print("\n****** rsa encryption ")
    n = p*q
    phi = (p-1)*(q-1)
    e = Cryptography.findcoprime(phi)
    d = Cryptography.modinv(e, phi)
    c = Cryptography.modexp(m, e, n)

    print("plaintext = ", m)
    print("cipher = ", c)
    print("coprime e = ", e)
    print("inverse d = ", d)


def elgamal(p, k, g, b, m):
    print("\n****** elgamal encryption ")
    pb = Cryptography.modexp(g,b,p)

    mask = Cryptography.modexp(pb, k, p)
    if mask == 1:
        print("BAD CHOICE of k")
        return

    cipher = (m*mask) % p
    hint = Cryptography.modexp(g, k, p)
    print("public key of B = ", pb)
    print("plaintext = ", m)
    print("cipher = ", cipher)
    print("hint = ", hint)
    print("mask = ", mask)


    q = p - 1 - b
    r = Cryptography.modexp(hint, q, p)
    d = (cipher*r) % p
    print("\nq = ", q)
    print("r = ", r)
    print("d = ", d)

# change your arguments here
rsa(7, 19, 6)
elgamal(104891, 4, 2, 225, 10346)


