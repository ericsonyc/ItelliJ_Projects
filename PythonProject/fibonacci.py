def fib(n):
    if n < 3:
        return 1
    else:
        output=fib(n-2)+fib(n-1)
        print(str(output)+' ')
        return output


if __name__ == "__main__":
    print(str(fib(23)))
    # a = 1
    # b = 1
    # c = 0
    # for i in range(23):
    #     print(a)
    #     c = a + b
    #     a = b
    #     b = c
