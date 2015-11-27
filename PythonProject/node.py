import matplotlib.pyplot as plt
import numpy as np

number = [x for x in range(10)]
tt = [0 for i in number]

node1 = open('D:\\picture\\node1.txt')
data1 = []
count = 0
for line in node1:
    temp = line.strip('\n').split('	')
    data1.append(float(temp[1])/(1024*1024))
    count += 1
    if count >= 10:
        break

count = 0
node2 = open('D:\\picture\\node2.txt')
data2 = []
for line in node2:
    temp = line.strip('\n').split('	')
    data2.append(float(temp[1])/(1024*1024))
    count += 1
    if count >= 10:
        break

count = 0
node3 = open('D:\\picture\\node3.txt')
data3 = []
for line in node3:
    temp = line.strip('\n').split('	')
    data3.append(float(temp[1])/(1024*1024))
    count += 1
    if count >= 10:
        break

count = 0
node4 = open('D:\\picture\\node4.txt')
data4 = []
for line in node4:
    temp = line.strip('\n').split('	')
    data4.append(float(temp[1])/(1024*1024))
    count += 1
    if count >= 10:
        break

count = 0
node5 = open('D:\\picture\\node5.txt')
data5 = []
for line in node5:
    temp = line.strip('\n').split('	')
    data5.append(float(temp[1])/(1024*1024))
    count += 1
    if count >= 10:
        break

count = 0
node6 = open('D:\\picture\\node6.txt')
data6 = []
for line in node6:
    temp = line.strip('\n').split('	')
    # print temp[1]
    data6.append(float(temp[1])/(1024*1024))
    # print data6[len(data6)-1]
    count += 1
    if count >= 10:
        break

# for i in range(len(number)):
# number[i] = string.atof(number[i])
# temp = []
# for data in number:
# temp.append(()(data))

for i in range(len(number)):
    data2[i] = data2[i] + data1[i]
for i in range(len(number)):
    data3[i] = data2[i] + data3[i]
for i in range(len(number)):
    data4[i] = data3[i] + data4[i]
for i in range(len(number)):
    data5[i] = data4[i] + data5[i]
for i in range(len(number)):
    data6[i] = data5[i] + data6[i]

print len(data2)

plt1, = plt.plot(number, data1, color='red')
plt2, = plt.plot(number, data2, color='green')
plt3, = plt.plot(number, data3, color='blue')
plt4, = plt.plot(number, data4, color='black')
plt5, = plt.plot(number, data5, color='yellow')
plt6, = plt.plot(number, data6, color='magenta')

plt.fill_between(np.array(number), np.array(tt), np.array(data1), facecolor='red')
plt.fill_between(number, data1, data2, facecolor='green')
plt.fill_between(number, data2, data3, facecolor='blue')
plt.fill_between(number, data3, data4, facecolor='black')
plt.fill_between(number, data4, data5, facecolor='yellow')
plt.fill_between(number, data5, data6, facecolor='magenta')

plt.xlabel('Time(s)')
plt.ylabel('Size(Mb)')
plt.xlim(0, 12)
plt.ylim(min(data1), max(data6))
leg = plt.legend([plt1, plt2, plt3, plt4, plt5, plt6], ['node6', 'node5', 'node4', 'node3', 'node2', 'node1'])
leg.get_frame().set_alpha(0.2)
plt.title('Bytes Out')
plt.savefig("d:\\bytesout_easy.eps")
plt.show()