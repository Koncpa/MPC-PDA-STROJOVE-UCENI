from models import SVMModel, KNNModel
from util import read_data

data = read_data('training_data/sitting_duck.csv')

# divide loaded data to inputs and outputs
X = data[:, :3]
y = data[:, -1]

model = SVMModel()

model.fit(X, y)

model.save('test_svm.pkl')


model = KNNModel()

model.fit(X, y)

model.save('test_knn.pkl')