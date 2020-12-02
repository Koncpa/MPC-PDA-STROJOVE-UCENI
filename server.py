from socket import socket, AF_INET, SOCK_STREAM
from agent import Agent

def server_program(host = 'localhost', port = 5000):
    print(f'Starting Robocode Server {host}:{port}')

    server_socket = socket(AF_INET, SOCK_STREAM)
    server_socket.bind((host, port))
    
    while True:
        server_socket.listen(1)
        conn, address = server_socket.accept()
        print(f'Connection from: {str(address)}')
        
        Agent(conn).run()


if __name__ == '__main__':
    server_program()