def add_overflow(numberA, numberB, base):
    output = numberA + numberB
    return output


if __name__ == '__main__':
    numberA = int(raw_input('Please enter the first integer:'))
    numberB = int(raw_input('Please enter the second integer:'))
    base = int(raw_input('Please enter the base:'))
    maxValue = 2 ** (base - 1) - 1
    minValue = -2 ** (base - 1)
    output = add_overflow(numberA, numberB, base)
    a = bin(numberA)
    b = bin(numberB)
    a = a[a.index('b') + 1:]
    b = b[b.index('b') + 1:]
    if numberA < 0:
        a = format(a, '1>' + str(base))
    else:
        a = format(a, '0>' + str(base))
    if numberB < 0:
        b = format(b, '1>' + str(base))
    else:
        b = format(b, '0>' + str(base))
    outStr = a + ' + ' + b + ' = '
    c = bin(output)
    c = c[c.index('b') + 1:]
    if output < 0:
        c = format(c, '1>' + str(base))
    else:
        c = format(c, '0>' + str(base))
    if ((output > maxValue) or (output < minValue)):
        print(outStr + c + ' overflow')
    else:
        print(outStr + c)
