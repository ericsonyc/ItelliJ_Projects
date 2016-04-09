out = ''
sign = '+'
out_copy = []
exponent = 0
out_integer = 0
out_float = 0


def bits_to_float(input):
    global out, sign, out_copy, exponent, out_integer, out_float
    output = list(input)
    out_copy = output[:]

    # bits
    count = 24
    for i in range(len(input) / 8 - 1):
        output.insert(count, ' ')
        count -= 8
    out = ''.join(output)

    # sign
    if output[0] == '1':
        sign = '-'

    # exponent
    exponent = int(''.join(out_copy[1:9]), 2) - 127

    # float
    fraction = out_copy[9:]
    fraction.insert(0, '1')
    fraction.insert(exponent + 1, '.')
    out_integer = int(''.join(fraction[0:exponent + 1]), 2)
    out_float = 0.0
    for i in range(len(fraction[exponent + 2:])):
        if fraction[i + exponent + 2] == '1':
            out_float += 2 ** -(i + 1)


if __name__ == '__main__':
    inputData = raw_input('bits_to_float:')
    bits_to_float(inputData)
    print('bits: ' + out)
    print('sign bit: ' + sign)
    print('exponent bits: ' + ''.join(out_copy[1:9]) + ' = ' + str(exponent))
    print('fraction bits: ' + ''.join(out_copy[9:]))
    print('as an integer: ' + str(out_integer))
    print('as a float: ' + str(out_float + out_integer))
