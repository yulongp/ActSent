#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
Created on 2014年11月24日

@author: Yulong Pei
'''

import string
from nltk import PorterStemmer

stop = set()
fin = open('../stopwords', 'r')
for line in fin.readlines():
    stop.add(line.strip())
fin.close()

terms = set()
fin = open('E:/Courses/11742/data/Sentiment/tweet/content_pos.txt', 'r')
fout = open('E:/Courses/11742/data/Sentiment/tweet/text.txt', 'w')
for line in fin.readlines():
    tmp = line.strip().split('\t')
    cont = tmp[0]
    t = cont.split()
    for item in t:
        if item[0] == '@' or item[0] == '#':
            terms.add('t_' + item[1:].lower())
            fout.write('t_' + item[1:].lower() + ' ')
        if item[0] in string.punctuation or item.lower() in stop or item.startswith('http'):
            continue
        else:
            term = PorterStemmer().stem_word(item.lower())
            terms.add(term)
            fout.write(term + ' ')
    fout.write('\n')
fin.close()
fout.close()
print len(terms)