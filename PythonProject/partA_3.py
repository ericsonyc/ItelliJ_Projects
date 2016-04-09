def sign_magnitude(number, bits):
    maxValue = 2 ** (bits - 1) - 1
    minValue = -2 ** (bits - 1)
    if number > maxValue or number < minValue:
        return 'None'
    output = '{:b}'.format(number)
    if number < 0:
        output = output[1:]
    output = format(output, '0>' + str(bits))
    if number < 0:
        output = '1' + output[1:]
    return output


if __name__ == '__main__':
    number = int(raw_input('Please enter the integer:'))
    bits = int(raw_input('Please enter the number of bits:'))
    output = sign_magnitude(number, bits)
    print(output)
