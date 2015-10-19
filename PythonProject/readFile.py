__author__ = 'ericson'

file=open('./hello.txt','r')
alltext=file.read()
file.close()
file2=open("./data","w+")
count=1
while count<=500000:
    file2.write(alltext)
    count=count+1

file2.close()
