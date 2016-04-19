import matplotlib.pyplot as pl

pl.rcParams['text.usetex'] = True
pl.rcParams['mathtext.default'] = 'bf'
pl.rcParams['lines.linewidth'] = 2
pl.rcParams['font.size'] = 12


def paint(data1, data2):
    x = [i + 1 for i in range(len(data1))]
    print(x)
    plt1, = pl.plot(x, data1, color='r')
    plt2, = pl.plot(x, data1, 'o', color='black')
    plt3, = pl.plot(x, data2, color='g')
    plt4, = pl.plot(x, data2, 'o', color='black')
    pl.xlabel('Reducers', fontsize=16)
    pl.ylabel('Time(s)', fontsize=16)
    pl.xlim(0, 36)
    pl.ylim(400, 900)
    leg = pl.legend([plt1, plt3], ['old', 'new'])
    leg.get_frame()
    pl.show()


def paint2(data1, data2):
    x = [i + 1 for i in range(len(data1))]
    plt1, = pl.plot(x, data1, color='r')
    plt2, = pl.plot(x, data1, 'o', color='black')
    plt3, = pl.plot(x, data2, color='g')
    plt4, = pl.plot(x, data2, 'o', color='black')
    pl.xlabel('Reducers', fontsize=16)
    pl.ylabel('Time(s)', fontsize=16)
    pl.xlim(0, 36)
    pl.ylim(100, 950)
    leg = pl.legend([plt1, plt3], ['old', 'new'])
    leg.get_frame()
    pl.show()


def paint3(data1):
    x = [i + 1 for i in range(len(data1))]
    plt1, = pl.plot(x, data1, color='g')
    plt2, = pl.plot(x, data1, 'o', color='black')
    pl.xlabel('Reducers', fontsize=16)
    pl.ylabel('Time(s)', fontsize=16)
    pl.xlim(0, 36)
    pl.ylim(100, 330)
    leg = pl.legend([plt1], ['Kirchhoff'])
    leg.get_frame()
    pl.show()


def paint4(data1):
    x = [0]
    temp = 0.5
    for i in range(len(data1)):
        x.append(x[i] + 0.5)
    x = x[1:]
    plt1, = pl.plot(x, data1, color='g')
    plt2, = pl.plot(x, data1, 'o', color='black')
    pl.xlabel('Container Memory(G)', fontsize=16)
    pl.ylabel('Time(s)', fontsize=16)
    pl.xlim(0, 5)
    pl.ylim(100, 460)
    leg = pl.legend([plt1], ['Kirchhoff'])
    leg.get_frame()
    pl.show()


def paint5(data1):
    x = [i + 1 for i in range(len(data1))]
    plt1, = pl.plot(x, data1, color='g')
    plt2, = pl.plot(x, data1, 'o', color='black')
    pl.xlabel('RDD partitions', fontsize=16)
    pl.ylabel('Time(s)', fontsize=16)
    pl.xlim(0, 36)
    pl.ylim(50, 300)
    leg = pl.legend([plt1], ['Kirchhoff'])
    leg.get_frame()
    pl.show()


def paint6(data1):
    x = [0]
    temp = 0.5
    for i in range(len(data1)):
        x.append(x[i] + 0.5)
    x = x[1:]
    plt1, = pl.plot(x, data1, color='g')
    plt2, = pl.plot(x, data1, 'o', color='black')
    pl.xlabel('Container Memory(G)', fontsize=16)
    pl.ylabel('Time(s)', fontsize=16)
    pl.xlim(0, 5.5)
    pl.ylim(80, 210)
    leg = pl.legend([plt1], ['Kirchhoff'])
    leg.get_frame()
    pl.show()


if __name__ == '__main__':
    data1 = [779, 600, 547, 495, 486, 493, 483, 489, 465, 478, 482, 485, 508, 497, 488, 517, 540, 506, 529, 555, 559,
             550, 563, 574, 587, 554, 540, 544, 545, 557, 554, 580, 571, 578, 554]
    for i in range(len(data1)):
        data1[i] += 110
    print(len(data1))
    data2 = [787, 666, 552, 540, 536, 495, 514, 485, 499, 495, 448, 450, 512, 491, 493, 440, 473, 439, 445, 436, 519,
             471, 532, 498, 495, 527, 539, 551, 553, 523, 522, 506, 571, 551, 525]
    # paint(data1, data2)
    data3 = [926, 596, 559, 293, 296, 275, 282, 250, 244, 198, 237, 219, 208, 209, 265, 204, 218, 200, 211, 220, 201,
             232, 225, 211, 196, 189, 216, 209, 220, 219, 200, 194, 203, 227, 210]
    data4 = [729, 466, 397, 387, 378, 357, 323, 301, 374, 345, 370, 291, 273, 227, 194, 196, 193, 187, 189, 189, 199,
             174, 159, 176, 216, 181, 190, 173, 176, 198, 185, 170, 173, 172, 181]
    for i in range(len(data3) - 3):
        data3[i + 3] += 250
    # paint2(data3, data4)

    data5 = [306.2, 238.8, 205.8, 183.3, 179.8, 177.9, 169.0, 166.5, 160.3, 154.7, 153.3, 151.5, 151.8, 154.9, 151.7,
             157.3, 147.7, 147.9, 145.8, 147.8, 147.0, 147.1, 149.5, 152.4, 144.2, 146.5, 146.9, 148.1, 148.9, 154.0,
             151.9, 143.1, 147.5, 146.6, 149.8]
    data6 = [145.631, 149.215, 233.846, 232.203, 292.564, 323.220, 405.647, 384.178, 419.277]
    # paint4(data6)

    data7 = [268, 238, 183, 176, 143, 137, 138, 128, 117, 113, 114, 135, 133, 135, 117, 136, 117, 101, 117, 98, 95, 97,
             114, 135, 105, 137, 115, 116, 139, 97, 99, 118, 101, 99, 99]
    # paint5(data7)
    data8 = [204, 106, 101, 99, 96, 118, 121, 129, 116, 127]
    paint6(data8)
