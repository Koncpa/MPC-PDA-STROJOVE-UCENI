from socket import socket, AF_INET, SOCK_STREAM
from agent import Agent
from models import SVMModel, KNNModel

def server_program(agent, host = 'localhost', port = 5000):
    print(f'Starting Robocode Server {host}:{port}')

    server_socket = socket(AF_INET, SOCK_STREAM)
    server_socket.bind((host, port))
    
    while True:
        server_socket.listen(1)
        conn, address = server_socket.accept()
        print(f'Connection from: {str(address)}')
        
        agent.run(conn)

if __name__ == '__main__':
    # agent = Agent(SVMModel('test_svm.pkl'))
    agent = Agent(KNNModel('test_knn.pkl'))

    # from tensorflow.keras.models import load_model
    # agent = Agent(load_model('model/sitting_duck.h5'))

    server_program(agent)