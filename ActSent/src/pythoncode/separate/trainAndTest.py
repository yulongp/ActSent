#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
Created on 2014年11月26日

@author: Yulong Pei
'''

import random

def divideRelation(infile, outfile):
    userSet = set()
    fin = open(infile, 'r')
    for line in fin.readlines():
        userSet.add(line.strip())
    fin.close()
    fin = open('E:/Courses/11742/data/Sentiment/mapping/relation.txt', 'r')
    fout = open(outfile, 'w')
    for line in fin.readlines():
        tmp = line.strip().split()
        if tmp[0] in userSet and tmp[1] in userSet:
            fout.write(line)
    fout.close()
    fin.close()

def genRandNum(a, b):
    randNum = set()
    while len(randNum) < a:
        num = random.randint(0, b)
        randNum.add(num)
    return randNum   

randNum = genRandNum(1201, 1878)
print len(randNum)
fin = open('E:/Courses/11742/data/Sentiment/mapping/tweet.txt', 'r')
content = fin.readlines()
fin.close()

fin = open('E:/Courses/11742/data/Sentiment/mapping/user.txt', 'r')
users = fin.readlines()
fin.close()


trainP = []
testP = []
fout1 = open('E:/Courses/11742/data/Sentiment/mapping/train/tweet.txt', 'w')
fout2 = open('E:/Courses/11742/data/Sentiment/mapping/test/tweet.txt', 'w')

fout3 = open('E:/Courses/11742/data/Sentiment/mapping/train/user.txt', 'w')
fout4 = open('E:/Courses/11742/data/Sentiment/mapping/test/user.txt', 'w')
for i in range(0, 1878):
    if (i+1) in randNum:
        fout1.write(content[i])
        fout3.write(users[i])
        trainP.append(users[i].strip() + ' ' + str(i+1) + ' 1\n')
    else:
        fout2.write(content[i])
        fout4.write(users[i])
        testP.append(users[i].strip() + ' ' + str(i+1) + ' 1\n')
fout1.close()
fout2.close()
fout3.close()
fout4.close()

fout1 = open('E:/Courses/11742/data/Sentiment/mapping/train/P.mm', 'w')
fout2 = open('E:/Courses/11742/data/Sentiment/mapping/test/P.mm', 'w')
fout1.write('%%MatrixMarket matrix coordinate real general\n')
fout1.write('% rows columns non-zero-values\n')
fout1.write(str(733) + ' ' + str(1878) + ' ' + str(len(trainP)) + '\n')
fout1.write('% row column value\n')
fout2.write('%%MatrixMarket matrix coordinate real general\n')
fout2.write('% rows columns non-zero-values\n')
fout2.write(str(733) + ' ' + str(1878) + ' ' + str(len(testP)) + '\n')
fout2.write('% row column value\n')
for item in trainP:
    fout1.write(item)
for item in testP:
    fout2.write(item)
fout1.close()
fout2.close()

fin = open('E:/Courses/11742/data/Sentiment/mapping/X.mm', 'r')
trainX = []
testX = []
index = 0
for line in fin.readlines():
    if index < 4:
        index += 1
    else:
        tmp = line.strip().split()
        if int(tmp[0]) in randNum:
            trainX.append(tmp[0] + ' ' + tmp[1] + ' 1\n')
        else:
            testX.append(tmp[0] + ' ' + tmp[1] + ' 1\n')
        index += 1
fin.close()

fout1 = open('E:/Courses/11742/data/Sentiment/mapping/train/X.mm', 'w')
fout2 = open('E:/Courses/11742/data/Sentiment/mapping/test/X.mm', 'w')
fout1.write('%%MatrixMarket matrix coordinate real general\n')
fout1.write('% rows columns non-zero-values\n')
fout1.write(str(1878) + ' ' + str(3289) + ' ' + str(len(trainX)) + '\n')
fout1.write('% row column value\n')
fout2.write('%%MatrixMarket matrix coordinate real general\n')
fout2.write('% rows columns non-zero-values\n')
fout2.write(str(1878) + ' ' + str(3289) + ' ' + str(len(testX)) + '\n')
fout2.write('% row column value\n')
for item in trainX:
    fout1.write(item)
for item in testX:
    fout2.write(item)
fout1.close()
fout2.close()

#divide the relations into training and test sets
divideRelation('E:/Courses/11742/data/Sentiment/mapping/train/user.txt', 
               'E:/Courses/11742/data/Sentiment/mapping/train/relation.txt')
divideRelation('E:/Courses/11742/data/Sentiment/mapping/test/user.txt', 
               'E:/Courses/11742/data/Sentiment/mapping/test/relation.txt')