package project;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

public class Robot extends AdvancedRobot {
    private final Gson gson;
    private PrintWriter writer;

    public Robot() {
        super();

        gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }

    public void run() {
        setAdjustGunForRobotTurn(true);

        try(Socket socket = new Socket("localhost", 5000)) {
            writer = new PrintWriter(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            send(new Info());
            while (true) {
                send(new State());

                execute(gson.fromJson(reader.readLine(), Action[].class));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        send(new Event(EventType.SCANNED, new Observation(e.getBearing(), e.getHeading(), e.getDistance(), e.getVelocity(), null)));
    }

    private void send(Object object) {
        if(writer != null) {
            writer.println(gson.toJson(object));
            writer.flush();
        }
    }

    private void execute(final Action[] actions) {
        for(Action action: actions) {
            switch(action.actionType) {
                case FIRE:
                    setFire(action.value);
                    break;
                case MOVE:
                    setAhead(action.value);
                    break;
                case TURN:
                    setTurnLeft(action.value);
                    break;
                case TURN_GUN:
                    setTurnGunLeft(action.value);
                    break;
            }
        }

        execute();
    }

    class Info {
        private final MessageType messageType = MessageType.INFO;

        private double width, height;

        public Info() {
            width = getBattleFieldWidth();
            height =  getBattleFieldHeight();
        }
    }

    class State {
        private final MessageType messageType = MessageType.STATE;

        private long timestamp;

        public State() {
            timestamp = getTime();
        }
    }

    class Event {
        private final MessageType messageType = MessageType.EVENT;

        private EventType eventType;
        private Observation observation;

        public Event(EventType eventType, Observation observation) {
            this.eventType = eventType;
            this.observation = observation;
        }
    }

    enum EventType {
        SCANNED
    }

    enum MessageType {
        INFO, STATE, EVENT
    }

    class Action {
        private ActionType actionType;
        private float value;
    }

    enum ActionType {
        FIRE, MOVE, TURN, TURN_GUN
    }
}
