from mpl_toolkits.mplot3d import axes3d
import matplotlib.pyplot as plt
import numpy as np

fig = plt.figure()
ax = fig.add_subplot(111, projection='3d')
X, Y, Z = axes3d.get_test_data(0.05)

print("X:" + X)
x = [0]
temp = 0.5
for i in range(8):
    x.append(x[i] + temp)
x = x[1:]
y = [i + 1 for i in range(30)]

z1 = [318.682, 236.141, 204.847, 182.572, 170.474, 166.522, 159.616, 160.905, 154.072, 162.899, 190.536, 149.556,
      148.474, 155.054, 150.709, 159.856, 154.183, 152.528, 148.514, 145.907, 142.776, 143.637, 199.238, 145.681,
      145.619, 158.328, 146.630, 144.003, 142.572, 141.477]
ax.plot_wireframe(X, Y, Z, rstride=10, cstride=10)

plt.show()
