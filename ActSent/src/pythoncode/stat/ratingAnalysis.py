#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
Created on 2014年11月24日

@author: Yulong Pei
'''

import string

def detectSentiment(rating):
    r3 = 0
    r4 = 0
    if rating.has_key('3'):
        r3 = rating['3']
    
    if rating.has_key('4'):
        r4 = rating['4']
    
    if rating.has_key('1'):
        if rating.has_key('2'):
            if rating['1'] > rating['2']:
                if rating['1'] >= r3 and rating['1'] >= r4:
                    return 1
                else:
                    return 0
            elif rating['1'] < rating['2']:
                if rating['2'] >= r3 and rating['2'] >= r4:
                    return 2
                else:
                    return 0
            else:
                return 0
        else:
            if rating['1'] >= r3 and rating['1'] >= r4:
                return 1
            else:
                return 0
    else:
        if rating.has_key('2'):
            if rating['2'] >= r3 and rating['2'] >= r4:
                return 2
            else:
                return 0
        else:
            return 0

fin = open('E:/Courses/11742/data/Sentiment/rating_filter.txt', 'r')
sent = []
fSet = set()
index = 0
for line in fin.readlines():
    rating = {}
    tmp = line.strip().split('\t')
    #print len(tmp)
    for item in tmp:
        if not item[0] in string.ascii_letters:
            if rating.has_key(item):
                rating[item] += 1
            else:
                rating[item] = 1
    s = detectSentiment(rating)
    #print s
    sent.append(s)
    if s == 0:
        fSet.add(index)
    index += 1
fin.close()

print len(fSet)

fin = open('E:/Courses/11742/data/Sentiment/tweet/text.txt', 'r')
fout = open('E:/Courses/11742/data/Sentiment/tweet/text_filter.txt', 'w')
index = 0
terms = set()
for line in fin.readlines():
    if not index in fSet:
        fout.write(line)
        tmp = line.strip().split()
        for item in tmp:
            terms.add(item)
    index +=1 
fout.close()
fin.close()

print len(terms)

fin = open('E:/Courses/11742/data/Sentiment/tweet/user.txt', 'r')
fout = open('E:/Courses/11742/data/Sentiment/tweet/user_filter.txt', 'w')
index = 0
terms = set()
for line in fin.readlines():
    if not index in fSet:
        fout.write(line)
        tmp = line.strip().split()
        for item in tmp:
            terms.add(item)
    index +=1 
fout.close()
fin.close()

fout = open('E:/Courses/11742/data/Sentiment/mapping/label.txt', 'w')
for item in sent:
    if not item == 0:
        fout.write(str(item) + '\n')
fout.close()