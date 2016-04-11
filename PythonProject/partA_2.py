def add_overflow(numberA, numberB, base):
    temp = ''
    for i in range(base):
        temp += '1'
    temp = int(temp, 2)
    comA = format(numberA & temp, '0' + str(base) + 'b')
    comB = format(numberB & temp, '0' + str(base) + 'b')
    strA = str(comA)
    strB = str(comB)
    plus = 0
    count = base
    result = ''
    for i in range(base):
        a = int(strA[count - i - 1])
        b = int(strB[count - i - 1])
        temp = (plus + a + b) % 2
        plus = int((plus + a + b) / 2)
        result = str(temp) + result
    max = 2 ** (base - 1) - 1
    min = -2 ** (base - 1)
    out = numberA + numberB
    if out > max or out < min:
        print(strA + ' + ' + strB + ' = ' + result + ' overflow')
    else:
        print(strA + ' + ' + strB + ' = ' + result)


if __name__ == '__main__':
    add_overflow(1, 1, 2)
