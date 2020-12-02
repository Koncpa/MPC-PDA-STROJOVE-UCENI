import tensorflow as tf
import tensorflow.keras as keras

class Model(keras.Model):
    # model definition
    def __init__(self):
        super(Model, self).__init__()

        self.dense = keras.layers.Dense(12, activation = tf.nn.relu)
        self.dense2 = keras.layers.Dense(1, activation = tf.nn.relu)

    # forward propagation
    def call(self, X):
        return self.dense2(self.dense(X))