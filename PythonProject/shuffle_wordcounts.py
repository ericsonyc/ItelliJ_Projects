import matplotlib.pyplot as plt
from pylab import *

plt.rcParams['text.usetex'] = True
plt.rcParams['mathtext.default'] = 'bf'
plt.rcParams['lines.linewidth'] = 2
plt.rcParams['font.size'] = 12

# offset1 = [22, 32]
# offset2 = [44, 54]
# offset3 = [62, 72]
# offset4 = [84, 92]
# offset5 = [120, 128]

str='H:\\rdma_shuffle\\data\\shuffle\\'

# read cpu
cpux = []
cpuy1 = []
cpuy2 = []
cpuy3 = []
cpuy4 = []
cpucount = 0
file = open(str+'cpu.txt', 'r')
for line in file:
    parts = line.split("\t")
    cpux.append(cpucount)
    cpuy1.append(float(parts[1]))
    cpuy2.append(float(parts[2]))
    cpuy3.append(float(parts[3]))
    cpuy4.append(float(parts[4]))
    cpucount = cpucount + 1

for i in range(len(cpux)):
    cpuy2[i] += cpuy1[i]
    cpuy3[i] += cpuy2[i]
    cpuy4[i] += cpuy3[i]

# # offset1
# for i in range(offset1[1] - offset1[0]):
#     cpuy1[i + offset1[0]] -= 10
#     cpuy2[i + offset1[0]] -= 10
#     cpuy3[i + offset1[0]] -= 10
#     cpuy4[i + offset1[0]] -= 10
# # offset2
# for i in range(offset2[1] - offset2[0]):
#     cpuy1[i + offset2[0]] -= 10
#     cpuy2[i + offset2[0]] -= 10
#     cpuy3[i + offset2[0]] -= 10
#     cpuy4[i + offset2[0]] -= 10
# # offset3
# for i in range(offset3[1] - offset3[0]):
#     cpuy1[i + offset3[0]] -= 10
#     cpuy2[i + offset3[0]] -= 10
#     cpuy3[i + offset3[0]] -= 10
#     cpuy4[i + offset3[0]] -= 10
# # offset4
# for i in range(offset4[1] - offset4[0]):
#     cpuy1[i + offset4[0]] -= 10
#     cpuy2[i + offset4[0]] -= 10
#     cpuy3[i + offset4[0]] -= 10
#     cpuy4[i + offset4[0]] -= 10
# # offset5
# for i in range(offset5[1] - offset5[0]):
#     cpuy1[i + offset5[0]] -= 10
#     cpuy2[i + offset5[0]] -= 10
#     cpuy3[i + offset5[0]] -= 10
#     cpuy4[i + offset5[0]] -= 10

# read mem
memx = []
memy1 = []
memy2 = []
memy3 = []
memy4 = []
memy5 = []
memy6 = []
memcount = 0
file = open(str+'memory.txt', 'r')
for line in file:
    parts = line.split("\t")
    memx.append(memcount)
    memy1.append(float(parts[1]) / (1024 * 1024 * 1024))
    memy2.append(float(parts[2]) / (1024 * 1024 * 1024))
    memy3.append(float(parts[3]) / (1024 * 1024 * 1024))
    memy4.append(float(parts[4]) / (1024 * 1024 * 1024))
    memy5.append(float(parts[5]) / (1024 * 1024 * 1024))
    memy6.append(float(parts[6]) / (1024 * 1024 * 1024))
    memcount = memcount + 1

for i in range(len(memx)):
    memy2[i] += memy1[i]
    memy3[i] += memy2[i]
    memy4[i] += memy3[i]
    memy5[i] += memy4[i]
    # memy6[i] += memy5[i]

# # offset1
# for i in range(offset1[1] - offset1[0]):
#     memy1[i + offset1[0]] -= 15
#     memy2[i + offset1[0]] -= 15
#     memy3[i + offset1[0]] -= 15
#     memy4[i + offset1[0]] -= 15
#     memy5[i + offset1[0]] -= 15
# # offset2
# for i in range(offset2[1] - offset2[0]):
#     memy1[i + offset2[0]] -= 15
#     memy2[i + offset2[0]] -= 15
#     memy3[i + offset2[0]] -= 15
#     memy4[i + offset2[0]] -= 15
#     memy5[i + offset2[0]] -= 15
# # offset3
# for i in range(offset3[1] - offset3[0]):
#     memy1[i + offset3[0]] -= 15
#     memy2[i + offset3[0]] -= 15
#     memy3[i + offset3[0]] -= 15
#     memy4[i + offset3[0]] -= 15
#     memy5[i + offset3[0]] -= 15
# # offset4
# for i in range(offset4[1] - offset4[0]):
#     memy1[i + offset4[0]] -= 15
#     memy2[i + offset4[0]] -= 15
#     memy3[i + offset4[0]] -= 15
#     memy4[i + offset4[0]] -= 15
#     memy5[i + offset4[0]] -= 15
# # offset5
# for i in range(offset5[1] - offset1[0]):
#     memy1[i + offset1[0]] -= 15
#     memy2[i + offset1[0]] -= 15
#     memy3[i + offset1[0]] -= 15
#     memy4[i + offset1[0]] -= 15
#     memy5[i + offset1[0]] -= 15

# read io
iox = []
ioy1 = []
ioy2 = []
iocount = 0
file = open(str+'io.txt', 'r')
for line in file:
    parts = line.split("\t")
    iox.append(iocount)
    ioy1.append(float(parts[1]) / (1024 * 1024))
    ioy2.append(float(parts[2]) / (1024 * 1024))
    iocount = iocount + 1

# ax = plt.axes([2,2,2,2])
#
# ax.set_xlim(0,count)
# ax.set_ylim(0,30)
# ax.xaxis.set_major_locator(plt.MultipleLocator(1.0))
# ax.xaxis.set_minor_locator(plt.MultipleLocator(0.1))
# ax.yaxis.set_major_locator(plt.MultipleLocator(1.0))
# ax.yaxis.set_minor_locator(plt.MultipleLocator(0.1))
# ax.grid(which='major', axis='x', linewidth=0.75, linestyle='-', color='0.75')
# ax.grid(which='minor', axis='x', linewidth=0.25, linestyle='-', color='0.75')
# ax.grid(which='major', axis='y', linewidth=0.75, linestyle='-', color='0.75')
# ax.grid(which='minor', axis='y', linewidth=0.25, linestyle='-', color='0.75')
# ax.set_xticklabels([])
# ax.set_yticklabels([])

pltcpu = plt.subplot(311)
pltmem = plt.subplot(312)
pltio = plt.subplot(313)

# cpu plot
pltcpu1, = pltcpu.plot(cpux, cpuy1, color='#3333bb')
pltcpu2, = pltcpu.plot(cpux, cpuy2, color='#fff400')
pltcpu3, = pltcpu.plot(cpux, cpuy3, color='#e10000')
pltcpu4, = pltcpu.plot(cpux, cpuy4, color='#ff8a60')
pltcpu.fill_between(cpux, [0] * len(cpux), cpuy1, facecolor='#3333bb')
pltcpu.fill_between(cpux, cpuy1, cpuy2, facecolor='#fff400')
pltcpu.fill_between(cpux, cpuy2, cpuy3, facecolor='#e10000')
pltcpu.fill_between(cpux, cpuy3, cpuy4, facecolor='#ff8a60')
# pltcpu.legend([pltcpu1, pltcpu2, pltcpu3, pltcpu4], ['User', 'Nice', 'System', 'Wait'])

# mem plot
pltmem1, = pltmem.plot(memx, memy1, color='#353587')
pltmem2, = pltmem.plot(memx, memy2, color='#484861')
pltmem3, = pltmem.plot(memx, memy3, color='#33cc33')
pltmem4, = pltmem.plot(memx, memy4, color='#9aff33')
pltmem5, = pltmem.plot(memx, memy5, color='#9b00ce')
pltmem6, = pltmem.plot(memx, memy6, color='#f70000')
pltmem.fill_between(memx, [0] * len(memx), memy1, facecolor='#353587')
pltmem.fill_between(memx, memy1, memy2, facecolor='#484861')
pltmem.fill_between(memx, memy2, memy3, facecolor='#33cc33')
pltmem.fill_between(memx, memy3, memy4, facecolor='#9aff33')
pltmem.fill_between(memx, memy4, memy5, facecolor='#9b00ce')
# pltmem.fill_between(memx, memy5, memy6, facecolor='#f70000')

# io plot
pltio1, = pltio.plot(iox, ioy1, color='#33cc33')
pltio2, = pltio.plot(iox, ioy2, color='#4d6eaa')

# pltio.xlabel('Time(15s)', fontsize=16)
# pltio.ylabel('Bytes(M)', fontsize=16)
# pltio.xlim(0, count)
# pltio.ylim(0, 30)

# plt.legend([plt1, plt2, plt3, plt4, plt5, plt6], ['job6', 'job5', 'job4', 'job3', 'job2', 'job1'])  # make legend
# leg = pltio.legend([pltio1, pltio2], ['In', 'Out'])
# leg.get_frame().set_alpha(0.2)

plt.show()
