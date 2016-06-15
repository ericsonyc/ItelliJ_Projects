__author__ = 'ericson'

import matplotlib.pyplot as plt

plt.rcParams['text.usetex'] = True
plt.rcParams['mathtext.default'] = 'bf'
# plt.rcParams['lines.linewidth'] = 2
plt.rcParams['font.size'] = 12
time = []  # time
x = []  # x axis
job1 = []  # job1
job2 = []  # job2
job3 = []  # job3
job4 = []  # job4
job5 = []  # job5
job6 = []  # job6
cpujob1 = []
cpujob2 = []
cpujob3 = []
cpujob4 = []
cpujob5 = []
cpujob6 = []
file = open('H:\\data\\fairjob')
tempfifo = 2951462412395399
tempjob1 = 2
tempjob2 = 2
tempjob3 = 2
tempjob4 = 2
tempjob5 = 2
tempjob6 = 2

tempcpu1 = 0
tempcpu2 = 0
tempcpu3 = 0
tempcpu4 = 0
tempcpu5 = 0
tempcpu6 = 0

for line in file:
    line_split = line.strip('\n').split(',')
    temp = long(line_split[0])
    time.append((temp + tempfifo) / 2)
    time.append(temp)
    tempfifo = temp
    x.append(0)
    x.append(0)
    size = len(line_split) - 1
    for i in range(size):
        items = line_split[i + 1].split(':')
        if int(items[0]) == 1:
            tmp = float(items[1])
            job1.append((tmp + tempjob1) / 2)
            job1.append(tmp)
            tempjob1 = tmp
            tmp1 = tmp / 2.0 * 3.0
            cpujob1.append((tmp1 + tempcpu1) / 2)
            cpujob1.append(tmp1)
            tempcpu1 = tmp1
        if int(items[0]) == 2:
            tmp = float(items[1])
            job2.append((tmp + tempjob2) / 2)
            job2.append(tmp)
            tempjob2 = tmp
            tmp2 = tmp / 2.0 * 4
            cpujob2.append((tmp2 + tempcpu2) / 2)
            cpujob2.append(tmp2)
            tempcpu2 = tmp2
        if int(items[0]) == 3:
            tmp = float(items[1])
            job3.append((tmp + tempjob3) / 2)
            job3.append(tmp)
            tempjob3 = tmp
            tmp3 = tmp / 3.0 * 1
            cpujob3.append((tmp3 + tempcpu3) / 2)
            cpujob3.append(tmp3)
            tempcpu3 = tmp3
        if int(items[0]) == 4:
            tmp = float(items[1])
            job4.append((tmp + tempjob4) / 2)
            job4.append(tmp)
            tempjob4 = tmp
            tmp4 = tmp / 4.0 * 2
            cpujob4.append((tmp4 + tempcpu4) / 2)
            cpujob4.append(tmp4)
            tempcpu4 = tmp4
        if int(items[0]) == 5:
            tmp = float(items[1])
            job5.append((tmp + tempjob5) / 2)
            job5.append(tmp)
            tempjob5 = tmp
            tmp5 = tmp
            cpujob5.append((tmp5 + tempjob5) / 2)
            cpujob5.append(tmp)
            tempcpu5 = tmp5
        if int(items[0]) == 6:
            tmp = float(items[1])
            job6.append((tmp + tempjob6) / 2)
            job6.append(tmp)
            tempjob6 = tmp
            tmp6 = tmp / 2.0
            cpujob6.append((tmp6 + tempcpu6) / 2)
            cpujob6.append(tmp / 2.0)
            tempcpu6 = tmp6
    while len(job1) < len(time):
        job1.append(0)
        cpujob1.append(0)
    while len(job2) < len(time):
        job2.append(0)
        cpujob2.append(0)
    while len(job3) < len(time):
        job3.append(0)
        cpujob3.append(0)
    while len(job4) < len(time):
        job4.append(0)
        cpujob4.append(0)
    while len(job5) < len(time):
        job5.append(0)
        cpujob5.append(0)
    while len(job6) < len(time):
        job6.append(0)
        cpujob6.append(0)

realtime = []
temp = time[0]
for item in time:
    realtime.append(float(item - temp) / 1000000000)

newrealtime = [0]
length = max(realtime) - min(realtime)
iterval = 547.917 / len(realtime)
data = 0
for i in range(len(realtime) - 1):
    data += iterval
    newrealtime.append(data)

print(len(newrealtime))
print(max(newrealtime))
print(len(cpujob1))
print(len(cpujob2))
print(len(cpujob3))
print(len(cpujob4))
print(len(cpujob5))
print(len(cpujob6))

for i in range(len(job1)):
    job2[i] += job1[i]
    cpujob2[i]+=cpujob1[i]
for i in range(len(job2)):
    job3[i] += job2[i]
    cpujob3[i]+=cpujob2[i]
for i in range(len(job3)):
    job4[i] += job3[i]
    cpujob4[i]+=cpujob3[i]
for i in range(len(job4)):
    job5[i] += job4[i]
    cpujob5[i]+=cpujob4[i]
for i in range(len(job5)):
    job6[i] += job5[i]
    cpujob6[i]+=cpujob5[i]

print time
print realtime
print job1
print sum(job6) / len(job6)

plt1, = plt.plot(newrealtime, cpujob6, color='magenta')
plt2, = plt.plot(newrealtime, cpujob5, color='yellow')
plt3, = plt.plot(newrealtime, cpujob4, color='black')
plt4, = plt.plot(newrealtime, cpujob3, color='blue')
plt5, = plt.plot(newrealtime, cpujob2, color='green')
plt6, = plt.plot(newrealtime, cpujob1, color='red')

plt.fill_between(newrealtime, x, cpujob1, facecolor='red')
plt.fill_between(newrealtime, cpujob1, cpujob2, facecolor='green')
plt.fill_between(newrealtime, cpujob2, cpujob3, facecolor='blue')
plt.fill_between(newrealtime, cpujob3, cpujob4, facecolor='black')
plt.fill_between(newrealtime, cpujob4, cpujob5, facecolor='yellow')
plt.fill_between(newrealtime, cpujob5, cpujob6, facecolor='magenta')
# # plt.fill_between(x,y,t,facecolor="b")
plt.xlabel('Time(s)', fontsize=16)
plt.ylabel('CPU Threads', fontsize=16)
plt.xlim(0, 650)
plt.ylim(0, 144)

# plt.legend([plt1, plt2, plt3, plt4, plt5, plt6], ['job6', 'job5', 'job4', 'job3', 'job2', 'job1'])  # make legend
leg = plt.legend([plt1, plt2, plt3, plt4, plt5, plt6], ['job6', 'job5', 'job4', 'job3', 'job2', 'job1'])
leg.get_frame().set_alpha(0.2)
plt.savefig('D:\\faircpu.eps')
plt.show()
