import matplotlib.pyplot as plt
import numpy as np
import csv

user=[]
system=[]
wait=[]
idle=[]

with open('D:\\picture\\cluster-memory.csv') as f:
    f_csv=csv.reader(f)
    headings=next(f_csv)
    for row in f_csv:
        user.append(float(row[1])/(1024*1024*1024))
        # system.append(float(row[3]))
        # wait.append(float(row[4]))
        # idle.append(float(row[7]))

number=[x for x in range(len(user))]
print user
# print system
# print wait
# print idle
# print number

# for i in number:
#     system[i]+=user[i]
#     wait[i]+=system[i]
    # idle[i]+=wait[i]

tt=[0 for i in number]

plt1, = plt.plot(number, user, color='green')
# plt2, = plt.plot(number, system, color='green')
# plt3, = plt.plot(number, data3, color='blue')
# plt4, = plt.plot(number, wait, color='black')
# plt5, = plt.plot(number, data5, color='yellow')
# plt6, = plt.plot(number, idle, color='magenta')
#
plt.fill_between(number, tt, user, facecolor='green')
# plt.fill_between(number, user, system, facecolor='green')
# plt.fill_between(number, data2, data3, facecolor='blue')
# plt.fill_between(number, system, wait, facecolor='black')
# plt.fill_between(number, data4, data5, facecolor='yellow')
# plt.fill_between(number, wait, idle, facecolor='magenta')
#
plt.xlabel('Time(30s per interval)')
plt.ylabel('Used Memory(Gb)')
plt.xlim(min(number), max(number))
plt.ylim(0, 30)
leg = plt.legend([plt1], ['used memory'])
leg.get_frame().set_alpha(0.2)
plt.title('Cluster Memory')
plt.savefig("d:\\cluster_memory.eps")
plt.show()