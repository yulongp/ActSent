#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
Created on 2014年11月23日

@author: Yulong Pei
'''

fin = open('E:/Courses/11742/data/Sentiment/tweet_content.txt', 'r')
fout = open('E:/Courses/11742/data/Sentiment/tweet/content.txt', 'w')
fout1 = open('E:/Courses/11742/data/Sentiment/tweet/user.txt', 'w')
index = 0
numLine = 30
cont = 0
for line in fin.readlines():
    if index%5 == 3:
        fout.write(line)
        if numLine > len(line.strip().split()):
            numLine = len(line.strip().split())
            cont = index
    elif index%5 == 2:
        tmp = line.split(' -+- ')
        fout1.write(tmp[1] + '\n')
    index += 1
fin.close()
fout.close()
fout1.close()
print numLine
print cont