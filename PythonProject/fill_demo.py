__author__ = 'ericson'

import numpy as np
import matplotlib.pyplot as plt

# x=np.linspace(0,1)
# y=np.sin(4*np.pi*x)*np.exp(-5*x)
# plt.fill(x,y,'r')
# plt.grid(True)
# plt.show()

def scatter(mat, col1, col2, lables):
    fig = plt.figure()
    ax = fig.add_subplot(111)
    ax.scatter(mat[:, col1], mat[:, col2], c=15.0 * np.array(lables), s=15.0 * np.array(lables))
    plt.show()

def plot_pic():
    x=range(6)
    y=[i**3+i*10+7 for i in x]
    plt.plot(x,y)
    plt.show()

def multiplot_pic():
    x=np.arange(1,10)
    plt.plot(x,x*3,x,x**2,x,x*30)
    plt.show()

plt.grid(True)
plt.axis()
plt.ylabel("Y")
plt.xlabel("X")
plt.title('Simple plot')
plt.plot(x,x*3)