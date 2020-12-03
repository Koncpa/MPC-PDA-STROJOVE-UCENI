from json import loads
from random import randint

from action import Action
from state import Event, Info, State
from model import Model

class Agent:
    # agent initialization, model loading
    def __init__(self, conn):
        self.conn = conn
        self.model = Model()

    # game loop
    def run(self):
        while True:
            try:
                data = self.conn.recv(1024)
            except ConnectionResetError:
                break

            if not data:
                break
                
            messages = data.split(b'\n')

            for message in messages:
                if not message:
                    continue

                message = loads(message)

                if message['message_type'] == 'INFO':
                    self.on_start(Info(message))
                elif message['message_type'] == 'STATE':
                    self.on_turn(State(message))
                elif message['message_type'] == 'EVENT':
                    self.on_event(Event(message))
                else:
                    print(f'Received unsupported data: {data}')

        self.conn.close()

    def on_start(self, info):
        print(f'Info: {info}')

    def on_turn(self, state):
        actions = []

        self._send_actions(actions)

    def on_event(self, event):
        pass
        # if event.event_type == 'SCANNED':

    def _send_actions(self, actions):
        self.conn.send(f'[{", ".join([action.dump() for action in actions])}]\n'.encode('utf8'))