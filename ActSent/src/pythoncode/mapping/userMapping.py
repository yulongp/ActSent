#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
Created on 2014年11月25日

@author: Yulong Pei
'''

fin = open('E:/Courses/11742/data/Sentiment/tweet/user_filter.txt', 'r')
usersDict = {}
index = 1
for line in fin.readlines():
    tmp = line.strip()
    if not usersDict.has_key(tmp):
        usersDict[tmp] = index
        index += 1
fin.close()

fout = open('E:/Courses/11742/data/Sentiment/mapping/user_map.txt', 'w')
for k, v in usersDict.items():
    fout.write(k + ' ' + str(v) + '\n')
fout.close()

fin = open('E:/Courses/11742/data/Sentiment/tweet/user_filter.txt', 'r')
fout = open('E:/Courses/11742/data/Sentiment/mapping/user.txt', 'w')
for line in fin.readlines():
    tmp = line.strip()
    fout.write(str(usersDict[tmp]) + '\n')
fin.close()
fout.close()