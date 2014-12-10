#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
Created on 2014年11月25日

@author: Yulong Pei
'''

def genPMatrix(x):
    users = []
    fin = open('E:/Courses/11742/data/Sentiment/mapping/train/user.txt', 'r')
    for line in fin.readlines():
        users.append(int(line.strip()))
    fin.close()
    num = len(users)
    
    fout = open('E:/Courses/11742/data/Sentiment/mapping/train/P.mm', 'w')
    fout.write('%%MatrixMarket matrix coordinate real general\n')
    fout.write('% rows columns non-zero-values\n')
    fout.write(str(x) + ' ' + str(num) + ' ' + str(num) + '\n')
    fout.write('% row column value\n')
    for i in range(0, num):
        fout.write(str(users[i]) + ' ' + str(i+1) + ' 1\n')
    fout.close()
 
def genFMatrix(x):
    users = {}
    fin = open('E:/Courses/11742/data/Sentiment/mapping/user_map.txt', 'r')
    for line in fin.readlines():
        tmp = line.strip().split()
        users[tmp[0]] = tmp[1]
    fin.close()
    
    num = 0
    relation = []
    fin = open('E:/Courses/11742/data/Sentiment/tweet/relation_filter.txt', 'r')
    #fout = open('E:/Courses/11742/data/Sentiment/mapping/relation.txt', 'w')
    for line in fin.readlines():
        tmp = line.strip().split()
        #fout.write(users[tmp[0]] + ' ' + users[tmp[1]] + '\n')
        relation.append(users[tmp[0]] + ' ' + users[tmp[1]])
        num += 1
    fin.close()
    #fout.close()
    
    print num
    fout = open('E:/Courses/11742/data/Sentiment/mapping/F.mm', 'w')
    fout.write('%%MatrixMarket matrix coordinate real general\n')
    fout.write('% rows columns non-zero-values\n')
    fout.write(str(x) + ' ' + str(x) + ' ' + str(num) + '\n')
    fout.write('% row column value\n')
    for item in relation:
        fout.write(item + ' 1\n')
    fout.close()
    
    
def genXMatrix():
    fin = open('E:/Courses/11742/data/Sentiment/mapping/train/tweet.txt', 'r')
    index = 1
    cont = []
    for line in fin.readlines():
        ts = set()
        for item in line.strip().split():
            ts.add(item)
        for item in ts:
            cont.append(str(index) + ' ' + item)
        index += 1
    fin.close()
    
    fout = open('E:/Courses/11742/data/Sentiment/mapping/train/X.mm', 'w')
    fout.write('%%MatrixMarket matrix coordinate real general\n')
    fout.write('% rows columns non-zero-values\n')
    fout.write(str(1878) + ' ' + str(3289) + ' ' + str(len(cont)) + '\n')
    fout.write('% row column value\n')
    for item in cont:
        fout.write(item + '\n')
    fout.close()


#genPMatrix(733)
#genFMatrix(733)
genXMatrix()