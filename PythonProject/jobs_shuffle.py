import matplotlib.pyplot as pl

pl.rcParams['text.usetex'] = True
pl.rcParams['mathtext.default'] = 'bf'
pl.rcParams['lines.linewidth'] = 2
pl.rcParams['font.size'] = 12


def paint(data1):
    x = [i + 1 for i in range(len(data1))]
    print(x)
    plt1, = pl.plot(x, data1, 'r')
    plt2, = pl.plot(x, data1, 'o', color='black')
    # plt1, = plt.plot(x, data, color='magenta')
    pl.xlabel('Reducers', fontsize=16)
    pl.ylabel('Time(s)', fontsize=16)
    pl.xlim(0, 40)
    pl.ylim(400, 800)
    leg = pl.legend([plt1], ['job6'])
    leg.get_frame()
    pl.show()


if __name__ == '__main__':
    data1 = [779, 600, 547, 495, 486, 493, 483, 489, 465, 478, 482, 485, 508, 497, 488, 517, 540, 506, 529, 555, 559,
             550, 563, 574, 587, 554, 540, 544, 545, 557, 554, 580, 571, 578, 554]
    print(len(data1))
    data2 = []
    paint(data1)
