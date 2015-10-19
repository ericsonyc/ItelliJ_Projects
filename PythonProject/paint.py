__author__ = 'ericson'

import numpy as np
import matplotlib.pyplot as plt
# mu,sigma=100,15
# x=mu+sigma*np.random.randn(10000)
# n,bins,patches=plt.hist(x,50,normed=1,facecolor='g',alpha=0.75)
# plt.xlabel("Smarts")
# plt.ylabel("Probability")
# plt.title("Histogram of IQ")
# plt.text(60,.025,r'$\mu=100,\ \sigma=15$')
# plt.axis([40,160,0,0.03])
# plt.grid(True)
# plt.show()

# X=np.linspace(-np.pi,np.pi,256,endpoint=True)
# C,S=np.cos(X),np.sin(X)
# plt.plot(X,C)
# plt.plot(X,S)
# plt.grid(True)
# plt.show()
# plt.savefig("D:\cos.eps")

# x,y=np.mgrid[-2:2:20j,-2:2:20j]
# z=x*np.exp(-x**2-y**2)
#
# ax=plt.subplot(111,projection='3d')
# ax.plot_surface(x,y,z,rstride=2,cstride=1,cmap=plt.cm.coolwarm,alpha=0.8)
# ax.set_xlabel('x')
# ax.set_ylabel('y')
# ax.set_zlabel('z')
#
# plt.show()

plt.rcParams['text.usetex'] = True
plt.rcParams['mathtext.default'] = 'bf'
n_groups = 4
means_makespan = (20, 35, 30, 35)
means_mem = (25, 32, 34, 20)
means_cpu = (10, 20, 30, 40)
fig = plt.figure()
ax1 = fig.add_subplot(111)
ax1.legend(loc=1)
ax1.set_ylabel('Time')
plt.ylim(0, 50)

# fig, ax = plt.subplots()
index = np.arange(n_groups)
bar_width = 0.2
opacity = 0.8
# plt.xlabel('Schedulers')
# plt.ylabel('Time')
ax2 = ax1.twinx()
ax2.legend(loc=2)
ax2.set_ylabel('Percentage')
ax2.set_xlabel('Schedulers')
ax2.bar(index + bar_width, means_makespan, bar_width, alpha=opacity, color='g', label='Makespan')
ax2.bar(index + bar_width * 2, means_mem, bar_width, alpha=opacity, color='r', label='Memory-Usage')
ax2.bar(index + bar_width * 3, means_cpu, bar_width, alpha=opacity, color='b', label="CPU-Usage")
plt.title('Comparasion between Schedulers')
plt.xticks(index + bar_width, ('FIFO', 'Fair', 'Capacity', 'HYAS'))
plt.ylim(0, 1)
# plt.legend()
# plt.tight_layout()
plt.show()