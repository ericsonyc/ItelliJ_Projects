def sign_magnitude(number, bits):
    maxValue = 2 ** (bits - 1) - 1
    minValue = -2 ** (bits - 1)
    if number > maxValue or number <= minValue:
        return 'None'
    output = '{:b}'.format(number)
    if number < 0:
        output = output[1:]
    output = format(output, '0>' + str(bits))
    if number < 0:
        output = '1' + output[1:]
    return output


if __name__ == '__main__':
    number = -16
    bits = 5
    output = sign_magnitude(number, bits)
    print(output)
