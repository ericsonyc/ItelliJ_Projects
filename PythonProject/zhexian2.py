__author__ = 'ericson'

import matplotlib.pyplot as plt

plt.rcParams['text.usetex'] = True
plt.rcParams['mathtext.default'] = 'bf'
time = []  # time
x = []  # x axis
job1 = []  # job1
job2 = []  # job2
job3 = []  # job3
job4 = []  # job4
job5 = []  # job5
job6 = []  # job6
file = open('D:\jobresource')
for line in file:
    line_split = line.strip('\n').split(',')
    time.append(long(line_split[0]))
    x.append(0)
    size = len(line_split) - 1
    for i in range(size):
        items = line_split[i + 1].split(':')
        if int(items[0]) == 1:
            job1.append(float(items[1]))
        if int(items[0]) == 2:
            job2.append(float(items[1]))
        if int(items[0]) == 3:
            job3.append(float(items[1]))
        if int(items[0]) == 4:
            job4.append(float(items[1]))
        if int(items[0]) == 5:
            job5.append(float(items[1]))
        if int(items[0]) == 6:
            job6.append(float(items[1]))
    # while len(job1) < len(time):
    #     job1.append(0)
    # while len(job2) < len(time):
    #     job2.append(0)
    # while len(job3) < len(time):
    #     job3.append(0)
    # while len(job4) < len(time):
    #     job4.append(0)
    # while len(job5) < len(time):
    #     job5.append(0)
    # while len(job6) < len(time):
    #     job6.append(0)

realtime = []
temp = time[0]
for item in time:
    realtime.append(float(item - temp) / 1000000000)

for i in range(min(len(job1),len(job2))):
    job2[i] += job1[i]
for i in range(min(len(job2),len(job3))):
    job3[i] += job2[i]
for i in range(min(len(job3),len(job4))):
    job4[i] += job3[i]
for i in range(min(len(job4),len(job5))):
    job5[i] += job4[i]
for i in range(min(len(job5),len(job6))):
    job6[i] += job5[i]

print time
print realtime
print job1
print job6

plt.plot(realtime[0:len(job1)], job1, color='red')
plt.plot(realtime[0:len(job2)], job2, color='green')
plt.plot(realtime[0:len(job3)], job3, color='blue')
plt.plot(realtime[0:len(job4)], job4, color='green')
plt.plot(realtime[0:len(job5)], job5, color='yellow')
plt.plot(realtime[0:len(job6)], job6, color='magenta')
plt.fill_between(realtime[0:len(job1)], x, job1, facecolor='red')
plt.fill_between(realtime[0:min(len(job1),len(job2))], job1, job2, facecolor='green')
plt.fill_between(realtime[0:min(len(job2),len(job3))], job2, job3, facecolor='blue')
plt.fill_between(realtime[0:min(len(job3),len(job4))], job3, job4, facecolor='black')
plt.fill_between(realtime[0:min(len(job4),len(job5))], job4, job5, facecolor='yellow')
plt.fill_between(realtime[0:min(len(job5),len(job6))], job5, job6, facecolor='magenta')
# # plt.fill_between(x,y,t,facecolor="b")
plt.xlabel('Time(s)')
plt.ylabel('Memory(G)')
plt.xlim(0, 500)
plt.ylim(0, 62)
plt.show()