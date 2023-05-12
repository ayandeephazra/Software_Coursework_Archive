# -*- coding: utf-8 -*-
"""
Created on Tue Feb  9 22:13:42 2021

@author: Ayan Deep Hazra
"""

import numpy as np
from scipy.io import loadmat
import matplotlib.pyplot as plt 

in_data = loadmat('movie.mat')
#loadmat() loads a matlab workspace into a python dictionary, where the names of the variables are the keys 
#in the dictionary.  To see what variables are loaded, uncomment the line below: 
#print([key for key in in_data])

M = in_data['M']
print(M)
print(np.linalg.matrix_rank())