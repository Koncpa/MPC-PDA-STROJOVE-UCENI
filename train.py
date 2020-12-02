import numpy as np
from csv import reader

from model import Model

with open('observations.csv', 'r') as file:
    reader = reader(file, delimiter = ';')
    data = np.array(list(reader))

# divide loaded data to inputs and outputs
X = data[:, :3].astype(np.float32)
y = data[:, 3:]

# prepare input data


# initialize mode
m = Model()


# train and evaluate


# save model