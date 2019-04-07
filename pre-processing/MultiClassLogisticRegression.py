

import pandas as pd
import numpy as np
from sklearn import linear_model
import math

digits = pd.read_csv('falldeteciton.csv')
print(digits)
print((digits.target))

#Basic model
from sklearn.linear_model import LogisticRegression
model = LogisticRegression()

#Split the data into train and test
from sklearn.model_selection import train_test_split
X_train, X_test, y_train, y_test = train_test_split(digits[['TIME',	'SL','EEG','BP','HR','CIRCLUATION']],digits.target, test_size=0.2)

model.fit(X_train, y_train)

print('score')
print(model.score(X_test, y_test))

#model.predict(digits[['TIME','SL','EEG','BP','HR','CIRCLUATION']][0:5])
model.predict(digits[['TIME','SL','EEG','BP','HR','CIRCLUATION']])

y_predicted = model.predict(X_test)

from sklearn.metrics import confusion_matrix
cm = confusion_matrix(y_test, y_predicted)
print("cm")
print(cm)



