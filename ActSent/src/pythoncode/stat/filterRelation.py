#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
Created on 2014年11月25日

@author: Yulong Pei
'''

fin = open('E:/Courses/11742/data/Sentiment/tweet/user_filter.txt', 'r')
users = set()
for line in fin.readlines():
    users.add(line.strip())
fin.close()

print len(users)

fin = open('E:/Courses/11742/data/Sentiment/relation.txt', 'r')
fout = open('E:/Courses/11742/data/Sentiment/tweet/relation_filter.txt', 'w')
for line in fin.readlines():
    tmp = line.strip().split()
    if tmp[0] in users and tmp[1] in users:
        fout.write(line)
fin.close()
fout.close()