def bits_to_float(input):
    sign = '+'
    output = list(input)
    out_copy = output[:]

    # bits
    count = 24
    for i in range(3):
        output.insert(count, ' ')
        count -= 8
    out = ''.join(output)

    # sign
    if output[0] == '1':
        sign = '-'

    # exponent
    split = ''.join(out_copy[1:9])
    exponent = int(split, 2) - 127

    # float
    fraction = out_copy[9:]
    fraction.insert(0, '1')
    if exponent > len(fraction):
        for i in range(exponent+1 - len(fraction)):
            fraction.append(0)
    fraction.insert(exponent + 1, '.')
    # out_integer = int(''.join(fraction[0:exponent + 1]), 2)
    out_float = 0.0
    index = fraction.index('.')
    for i in range(len(fraction)):
        if fraction[i] == '1' and i < index:
            out_float += 2 ** (index - i - 1)
        elif fraction[i]=='1':
            out_float += 2 ** (index - i)
    out_integer = int(out_float)
    print('bits: ' + out)
    print('sign bit: ' + sign)
    print('exponent bits: ' + ''.join(out_copy[1:9]) + ' = ' + str(exponent))
    print('fraction bits: ' + ''.join(out_copy[9:]))
    if sign == '-':
        print('as an integer: ' + sign + str(out_integer))
        print('as a float: ' + sign + str(out_float))
    else:
        print('as an integer: ' + str(out_integer))
        print('as a float: ' + str(out_float))


if __name__ == '__main__':
    inputData = '01111111000000000000000000000000'
    bits_to_float(inputData)
