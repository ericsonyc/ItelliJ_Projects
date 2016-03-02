__author__ = 'ericson'

import matplotlib.pyplot as plt
import numpy as np

plt.rcParams['text.usetex'] = True
plt.rcParams['mathtext.default'] = 'bf'
# plt.rcParams['lines.linewidth'] = 2
plt.rcParams['font.size'] = 12
time = []  # time
x = [i for i in range(100)]  # x axis
jobs = [[] for i in range(10)]
files = ['file1', 'file2', 'file3', 'file4', 'file5', 'file6', 'file7', 'file8', 'file9', 'file10']

for i in range(len(jobs)):
    file = open('D:\\Projects\\ItelliJ_Projects\\Artificial_Fish\\src\\fish\\' + files[i])
    for line in file.readlines():
        line = line.strip('\n')
        jobs[i].append(float(line))

for i in range(len(jobs)):
    for j in range(len(x) - len(jobs[i])):
        jobs[i].append(max(jobs[i]))

print(len(jobs))
print(len(jobs[0]))
# for i in range(len(jobs)):
#     for j in range(len(jobs[i])):
#         print(str(jobs[i][j])+" ")
#     print("\n")

# file = open('F:\\test\\capacity')
# for line in file:
#     line_split = line.strip('\n').split(',')
#     time.append(long(line_split[0]))
#     x.append(0)
#     size = len(line_split) - 1
#     for i in range(size):
#         items = line_split[i + 1].split(':')
#         if int(items[0]) == 1:
#             job1.append(float(items[1]))
#         if int(items[0]) == 2:
#             job2.append(float(items[1]))
#         if int(items[0]) == 3:
#             job3.append(float(items[1]))
#         if int(items[0]) == 4:
#             job4.append(float(items[1]))
#         if int(items[0]) == 5:
#             job5.append(float(items[1]))
#         if int(items[0]) == 6:
#             job6.append(float(items[1]))
#     while len(job1) < len(time):
#         job1.append(0)
#     while len(job2) < len(time):
#         job2.append(0)
#     while len(job3) < len(time):
#         job3.append(0)
#     while len(job4) < len(time):
#         job4.append(0)
#     while len(job5) < len(time):
#         job5.append(0)
#     while len(job6) < len(time):
#         job6.append(0)

# realtime = []
# temp = time[0]
# for item in time:
#     realtime.append(float(item - temp) / 1000000000)
#
# for i in range(len(job1)):
#     job2[i] += job1[i]
# for i in range(len(job2)):
#     job3[i] += job2[i]
# for i in range(len(job3)):
#     job4[i] += job3[i]
# for i in range(len(job4)):
#     job5[i] += job4[i]
# for i in range(len(job5)):
#     job6[i] += job5[i]

# print time
# print realtime
# print job1
# print sum(job6)/len(job6)
# x = np.array(x)
print(len(jobs[1]))
plt1, = plt.plot(x, jobs[0], color='magenta', linewidth='3')
plt2, = plt.plot(x, jobs[1], color='yellow', linewidth='3')
plt3, = plt.plot(x, jobs[2], color='black', linewidth='3')
plt4, = plt.plot(x, jobs[3], color='blue', linewidth='3')
plt5, = plt.plot(x, jobs[4], color='green', linewidth='3')
plt6, = plt.plot(x, jobs[5], color='red', linewidth='3')
plt7, = plt.plot(x, jobs[6], color='#213f45', linewidth='3')
plt8, = plt.plot(x, jobs[7], color='#845354', linewidth='3')
plt9, = plt.plot(x, jobs[8], color='#27f583', linewidth='3')
plt10, = plt.plot(x, jobs[9], color='#23ff23', linewidth='3')
# plt.fill_between(realtime, x, job1, facecolor='red')
# plt.fill_between(realtime, job1, job2, facecolor='green')
# plt.fill_between(realtime, job2, job3, facecolor='blue')
# plt.fill_between(realtime, job3, job4, facecolor='black')
# plt.fill_between(realtime, job4, job5, facecolor='yellow')
# plt.fill_between(realtime, job5, job6, facecolor='magenta')
# # plt.fill_between(x,y,t,facecolor="b")


plt.xlabel('Iteration', fontsize=16)
plt.ylabel('Value', fontsize=16)
plt.xlim(0, 100)
plt.ylim(4000, 6500)

# plt.legend([plt1, plt2, plt3, plt4, plt5, plt6], ['job6', 'job5', 'job4', 'job3', 'job2', 'job1'])  # make legend


# leg=plt.legend([plt1,plt2,plt3,plt4,plt5,plt6],['job6','job5','job4','job3','job2','job1'])
# leg.get_frame().set_alpha(0.2)
# plt.savefig('d:\\simulation.eps')
plt.show()
