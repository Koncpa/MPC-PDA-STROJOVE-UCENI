from json import loads
from random import randint

from action import Action
from state import Event, Info, State

actions = [
    Action('MOVE', 60),
    Action('FIRE', 1),
    Action('TURN', 45),
]

class Agent:
    def __init__(self, conn):
        self.conn = conn
        self.count = 0
        self.should_fire = []

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

        if self.count % 20 == 0:
            actions.append(Action('MOVE', 100))
        if self.count % 25 == 0:
            degrees = randint(45, 135)
            actions.append(Action('TURN', degrees))
        if self.should_fire:
            if state.timestamp < 400:
                actions.append(Action('FIRE', (500 - state.timestamp) / 100))
            else:
                actions.append(Action('FIRE', 0.5))
            actions.append(Action('TURN_GUN', 0))
            self.should_fire.pop(0)
        if self.count % 15 == 0 and not self.should_fire:
            actions.append(Action('TURN_GUN', randint(-360, 360)))

        self.count += 1

        self._send_actions(actions)

    def on_event(self, event):
        if event.event_type == 'SCANNED':
            self.should_fire.append(True)

    def _send_actions(self, actions):
        self.conn.send(f'[{", ".join([action.dump() for action in actions])}]\n'.encode('utf8'))