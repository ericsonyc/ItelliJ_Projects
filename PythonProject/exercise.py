__author__ = 'ericson'

# object-oriented plot
# from matplotlib.figure import Figure
# from matplotlib.backends.backend_agg import FigureCanvasAgg as FigureCanvas
#
# fig = Figure()
# canvas = FigureCanvas(fig)
#
# # first axes
# ax1 = fig.add_axes([0.1, 0.1, 0.8, 0.8])
# line, = ax1.plot([0, 1], [0, 1])
# ax1.set_title("ax1")
#
# # second axes
# ax2 = fig.add_axes([0.8, 0.1, 0.8, 0.8])
# # sca = ax2.scatter([1, 3, 5], [2, 1, 2])
# # ax2.set_title("ax2")
# # plt.show(canvas)
# canvas.print_figure(r'H:\AdaptiveSchedulerLatex\bare_adv\figures\demo.eps')


# object-oriented plot

from matplotlib.figure import Figure
from matplotlib.backends.backend_agg import FigureCanvasAgg as FigureCanvas

fig = Figure()
canvas = FigureCanvas(fig)
ax = fig.add_axes([0.1, 0.1, 0.8, 0.8])

# from matplotlib.path import Path
# import matplotlib.patches as patches
#
# verts = [
# (0., 0.),
# (0., 1.),
# (0.5, 1.5),
# (1., 1.),
# (1., 0.),
# (0., 0.),
# ]
#
# codes = [Path.MOVETO,
# Path.LINETO,
# Path.LINETO,
#          Path.LINETO,
#          Path.LINETO,
#          Path.CLOSEPOLY,
# ]
#
# path = Path(verts, codes)
#
# patch = patches.PathPatch(path, facecolor='coral')
# ax.add_patch(patch)
# ax.set_xlim(-0.5, 2)
# ax.set_ylim(-0.5, 2)
#
# canvas.print_figure(r'H:\AdaptiveSchedulerLatex\bare_adv\figures\demo.eps')

import matplotlib.pyplot as plt
import numpy as np

plt.rcParams['text.usetex'] = True
plt.rcParams['mathtext.default'] = 'bf'

n_groups = 4
means_makespan = (1285.234, 1397.568, 1442.126, 936.271)
means_mem = (0.72925,0.7245,0.7187,0.932)
means_cpu = (0.38,0.37,0.365,0.5034)
means_makespan_new = (447.917, 457.535, 464.479, 332.475)
means_mem_new = (0.815957021489, 0.758236, 0.743570498, 0.95234)
means_cpu_new = (0.51824087956, 0.52345, 0.514556724267, 0.742395)
index = np.arange(n_groups)
bar_width = 0.2
opacity = 0.8
# x = np.arange(0., np.e, 0.01)
# y1 = np.exp(-x)
# y2 = np.log(x)
fig = plt.figure()
ax1 = fig.add_subplot(111)
# ax1.bar(x, y1, 'r', label="right");
ax1.bar(index + bar_width, means_makespan, bar_width, alpha=opacity, color='g', label='Makespan')
ax1.legend(loc=2).get_frame().set_alpha(0.5)
ax1.set_ybound(0, 1750)
ax1.set_ylabel('Time(s)')
ax1.set_xlabel('Schedulers')
ax2 = ax1.twinx()  # this is the important function
# ax2.plot(x, y2, 'g', label="left")
ax2.bar(index + bar_width * 2, means_mem, bar_width, alpha=opacity, color='r', label='Memory-Usage')
ax2.bar(index + bar_width * 3, means_cpu, bar_width, alpha=opacity, color='b', label="CPU-Usage")
hl = ax2.legend(loc=1, bbox_to_anchor=(0.85, 1)).get_frame().set_alpha(0.5)
ax2.set_ybound(0, 1)
ax2.set_ylabel('Percentage(\%)')
plt.xticks(index + bar_width * 2.5, ('FIFO', 'Fair', 'Capacity', 'HYAS'))
plt.show()