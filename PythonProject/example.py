__author__ = 'ericson'

import matplotlib.pyplot as pl

x1 = [1, 2, 3, 4, 5]  # Make x, y arrays for each graph
y1 = [2.706, 1.254, 0.805, 0.634, 0.523]

# x2 = [1, 2, 3, 4, 5, 6, 7]
y2 = [1.276, 0.647, 0.474, 0.342, 0.213]
# use pylab to plot x and y
pl.plot(x1, y1, 'r', label='Hadoop')
pl.plot(x1, y2, 'g', label="Spark")
pl.legend()

pl.title('Hadoop vs Spark')  # give plot a title
pl.xlabel('Slaves')  # make axis labels
pl.ylabel('Time(h)')

pl.xlim(0, 6)  # set axis limits
pl.ylim(0, 3)

pl.show()  # show the plot on the screen

# x = [1, 2, 3, 4, 5, 6, 7]
# print x[0:3]