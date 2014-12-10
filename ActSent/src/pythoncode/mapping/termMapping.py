#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
Created on 2014年11月25日

@author: Yulong Pei
'''

termDict = {}
fin = open('E:/Courses/11742/data/Sentiment/tweet/text_filter.txt', 'r')
index = 1
for line in fin.readlines():
    tmp = line.strip().split()
    for item in tmp:
        if not termDict.has_key(item):
            termDict[item] = index
            index += 1
fin.close()

fin = open('E:/Courses/11742/data/Sentiment/tweet/text_filter.txt', 'r')
fout = open('E:/Courses/11742/data/Sentiment/mapping/tweet.txt', 'w')
for line in fin.readlines():
    tmp = line.strip().split()
    for item in tmp:
        fout.write(str(termDict[item]) + ' ')
    fout.write('\n')
fin.close()
fout.close()

fout = open('E:/Courses/11742/data/Sentiment/mapping/word_map.txt', 'w')
for k, v in termDict.items():
    fout.write(k + ' ' + str(v) + '\n')
fout.close()