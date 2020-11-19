package project;


import java.awt.geom.Point2D;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;



public class Robot extends AdvancedRobot {


    public void run(){

        while(true){
            scan();  //sticky radar
            setFire(1);
            execute();
        }

    }


    //here we can collect all information, when we scan some enemy tank
    public void onScannedRobot(ScannedRobotEvent e) {
    
        
    	List<String> list = new ArrayList<>();
    	try {
			addToCsv(list);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

    }
    
    
    //method when is our tank hi
    @Override
    public void onBulletHit(robocode.BulletHitEvent event) {
        super.onBulletHit(event);

    }

    //method for adding info to CSv file
    public void addToCsv(List list) throws IOException {
    	
   

        FileWriter writer = new FileWriter("");

        List<String> test = new ArrayList<>();
        test.add("Word1");
        test.add("Word2");
        test.add("Word3");
        test.add("Word4");

        String collect = test.stream().collect(Collectors.joining(","));
        System.out.println(collect);

        writer.write(collect);
        writer.close();
	
	
	
    }
	

	
}
