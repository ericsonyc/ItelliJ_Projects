__author__ = 'ericson'

import matplotlib.pyplot as plt
import numpy as np

plt.rcParams['text.usetex'] = True
plt.rcParams['mathtext.default'] = 'bf'
plt.rcParams['lines.linewidth'] = 2
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

print(len(jobs[1]))
plt1, = plt.plot(x, jobs[0], color='magenta')
plt2, = plt.plot(x, jobs[1], color='yellow')
plt3, = plt.plot(x, jobs[2], color='black')
plt4, = plt.plot(x, jobs[3], color='blue')
plt5, = plt.plot(x, jobs[4], color='green')
plt6, = plt.plot(x, jobs[5], color='red')
plt7, = plt.plot(x, jobs[6], color='#213f45')
plt8, = plt.plot(x, jobs[7], color='#845354')
plt9, = plt.plot(x, jobs[8], color='#27f583')
plt10, = plt.plot(x, jobs[9], color='#23ff23')



plt.xlabel('Iteration', fontsize=16)
plt.ylabel('Value', fontsize=16)
plt.xlim(0, 100)
plt.ylim(4000, 6500)
plt.show()
