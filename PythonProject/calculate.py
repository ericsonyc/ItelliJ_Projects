__author__ = 'ericson'

import string

file=open('D:\\totalresource')
count=0
mem=0
cpu=0
for line in file:
    str_split=line.split(',')
    count=count+1
    mem=mem+string.atof(str_split[1])
    cpu=cpu+string.atof(str_split[2])

print "mem:"+str(mem/count)+",cpu:"+str(cpu/count)