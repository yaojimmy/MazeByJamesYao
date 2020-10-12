/**
 * 
 */
package gui;

import generation.Order;

import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JFrame;


/**
 * This class is a wrapper class to startup the Maze game as a Java application
 * 
 * This code is refactored code from Maze.java by Paul Falstad, www.falstad.com, Copyright (C) 1998, all rights reserved
 * Paul Falstad granted permission to modify and use code for teaching purposes.
 * Refactored by Peter Kemper
 * 
 * TODO: use logger for output instead of Sys.out
 */
public class MazeApplication extends JFrame {

	// not used, just to make the compiler, static code checker happy
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public MazeApplication() {
		init("");
	}

	/**
	 * Constructor that loads a maze from a given file or uses a particular method to generate a maze
	 * @param parameter can identify a generation method (Prim, Kruskal, Eller)
     * or a filename that stores an already generated maze that is then loaded, or can be null
	 */
	public MazeApplication(String parameter) {
		init(parameter);
	}
	
	public MazeApplication(String[] parameters) {
		init(parameters);
	}

	/**
	 * Instantiates a controller with settings according to the given parameter.
	 * @param parameter can identify a generation method (Prim, Kruskal, Eller)
	 * or a filename that contains a generated maze that is then loaded,
	 * or can be null
	 * @return the newly instantiated and configured controller
	 */
	 Controller createController(String parameter) {
	    // need to instantiate a controller to return as a result in any case
	    Controller result = new Controller() ;
	    // can decide if user repeatedly plays the same mazes or 
	    // if mazes are different each and every time
	    // set to true for testing purposes
	    // set to false for playing the game
	    result.setDeterministic(false);
	    String msg = null; // message for feedback
	    // Case 1: no input
	    if (parameter == "") {
	        msg = "MazeApplication: maze will be generated with a randomized algorithm."; 
	        System.out.println(msg);
		    return result;
	    }
	    // checking case with single argument
	    // Case 2: Prim
	    else if ("Prim".equalsIgnoreCase(parameter))
	    {
	        msg = "MazeApplication: generating random maze with Prim's algorithm.";
	        result.setBuilder(Order.Builder.Prim);
	    }
	    // Case 3 a and b: Eller, Kruskal or some other generation algorithm
	    else if ("Kruskal".equalsIgnoreCase(parameter))
	    {
	    	// TODO: for P2 assignment, please add code to set the builder accordingly
	        throw new RuntimeException("Don't know anybody named Kruskal ...");
	    }
	    else if ("Eller".equalsIgnoreCase(parameter))
	    {
	    	msg = "MazeApplication: generating random maze with Eller's algorithm.";
	    	result.setBuilder(Order.Builder.Eller);
	    }
	    else if ("Wizard".equalsIgnoreCase(parameter))
	    {
	    	Robot r = new ReliableRobot();
	    	RobotDriver dr = new Wizard();
	    	dr.setRobot(r);
	    	result.setRobotAndDriver(r, dr);
	    }
	    // Case 4: a file
	    else {
	        File f = new File(parameter) ;
	        if (f.exists() && f.canRead())
	        {
	            msg = "MazeApplication: loading maze from file: " + parameter;
	            result.setFileName(parameter);
	            return result;
	        }
	        else {
	            // None of the predefined strings and not a filename either: 
	            msg = "MazeApplication: unknown parameter value: " + parameter + " ignored, operating in default mode.";
	        }
	    }
	    
	    // assuming multiple parameters, split parameter into array of strings
	    String[] parameters = parameter.split(" ");
	    UnreliableRobot r = new UnreliableRobot();
	    for (int strindex = 0; strindex < parameters.length - 1; strindex++) {
	    	switch (parameters[strindex]) {
	    	// Maze generation algorithms
	    	case "-g":
	    		// Prim case
	    		if ("Prim".equalsIgnoreCase(parameters[strindex+1])) {
	    			msg = "MazeApplication: generating random maze with Prim's algorithm.";
	    	        result.setBuilder(Order.Builder.Prim);
	    		}
	    		// Kruskal case
	    		else if ("Kruskal".equalsIgnoreCase(parameters[strindex+1]))
	    	    {
	    	        throw new RuntimeException("Don't know anybody named Kruskal ...");
	    	    }
	    		// Eller case
	    		else if ("Eller".equalsIgnoreCase(parameters[strindex+1]))
	    	    {
	    	    	msg = "MazeApplication: generating random maze with Eller's algorithm.";
	    	    	result.setBuilder(Order.Builder.Eller);
	    	    }
	    		// Case of file
	    	    else {
	    	        File f = new File(parameter);
	    	        if (f.exists() && f.canRead())
	    	        {
	    	            msg = "MazeApplication: loading maze from file: " + parameter;
	    	            result.setFileName(parameter);
	    	            return result;
	    	        }
	    	        else {
	    	            // None of the predefined strings and not a filename either: 
	    	            msg = "MazeApplication: unknown parameter value: " + parameter + " ignored, operating in default mode.";
	    	        }
	    	    }
	    		break;
	    	// Robot algorithm
	    	case "-d":
	    		// Wizard case
	    		if ("Wizard".equalsIgnoreCase(parameters[strindex+1])) {
	    			RobotDriver dr = new Wizard();
	    			dr.setRobot(r);
	    			result.setRobotAndDriver(r, dr);
	    		}
	    		// WallFollower case
	    		else if ("WallFollower".equalsIgnoreCase(parameters[strindex+1])) {
	    			RobotDriver dr = new WallFollower();
	    			dr.setRobot(r);
	    			result.setRobotAndDriver(r, dr);
	    		}
	    		//  Not valid robot alg case
	    		else {
	    			// does nothing
	    		}
	    		break;
	    	// setting unreliable sensors
	    	case "-r":
	    		// iterate through characters of following string
	    		for (int charindex = 0; charindex < parameters[strindex+1].length(); charindex++) {
	    			// if 0, set corresponding sensor to UnreliableSensor
	    			if (parameters[strindex+1].indexOf(charindex) == '0') {
	    				UnreliableSensor s = new UnreliableSensor();
	    				if (charindex == 0) {
	    					r.setFSensor(s);
	    				}
	    				if (charindex == 1) {
	    					r.setLSensor(s);
	    				}
	    				if (charindex == 2) {
	    					r.setRSensor(s);
	    				}
	    				if (charindex == 3) {
	    					r.setBSensor(s);
	    				}
	    			}
	    		}
	    		break;
	    	}
	    }
	    // controller instanted and attributes set according to given input parameter
	    // output message and return controller
	    System.out.println(msg);
	    return result;
	}
	 
	 /**
	  * Instantiates Controller with settings according to given parameters.
	  */
	 Controller createController(String[] parameters) {
		// need to instantiate a controller to return as a result in any case
		Controller result = new Controller() ;
		// can decide if user repeatedly plays the same mazes or 
		// if mazes are different each and every time
		// set to true for testing purposes
		// set to false for playing the game
		result.setDeterministic(false);
		String msg = ""; // message for feedback
		UnreliableRobot r = new UnreliableRobot();
		for (int strindex = 0; strindex < parameters.length - 1; strindex++) {
		    switch (parameters[strindex]) {
		    // Maze generation algorithms
		    case "-g":
		    	// Prim case
		    	if ("Prim".equalsIgnoreCase(parameters[strindex+1])) {
		    		msg = msg + "MazeApplication: generating random maze with Prim's algorithm.\n";
		    	    result.setBuilder(Order.Builder.Prim);
		    	}
		    	// Kruskal case
		    	else if ("Kruskal".equalsIgnoreCase(parameters[strindex+1]))
		    	{
		    	    throw new RuntimeException("Don't know anybody named Kruskal ...");
		    	}
		    	// Eller case
		    	else if ("Eller".equalsIgnoreCase(parameters[strindex+1]))
		    	{
		    	    msg = msg + "MazeApplication: generating random maze with Eller's algorithm.\n";
		    	    result.setBuilder(Order.Builder.Eller);
		    	}
		    	// Case of file
		    	else {
		    	    File f = new File(parameters[strindex+1]);
		    	    if (f.exists() && f.canRead())
		    	    {
		    	        msg = msg + "MazeApplication: loading maze from file: " + parameters[strindex+1] + "\n";
		    	        result.setFileName(parameters[strindex+1]);
		    	        return result;
		    	    }
		    	    else {
		    	        // None of the predefined strings and not a filename either: 
		    	        msg = msg + "MazeApplication: unknown parameter value: " + parameters[strindex+1] + " ignored, operating in default mode.\n";
		    	    }
		    	}
		    	break;
		    // Robot algorithm
		    case "-d":
		    	// Wizard case
		    	if ("Wizard".equalsIgnoreCase(parameters[strindex+1])) {
		    		msg = msg + "Using Wizard Robot Algorithm.\n";
		    		RobotDriver dr = new Wizard();
		    		dr.setRobot(r);
		    		result.setRobotAndDriver(r, dr);
		    	}
		    	// WallFollower case
		    	else if ("WallFollower".equalsIgnoreCase(parameters[strindex+1])) {
		    		msg = msg + "Using WallFollower Robot Algorithm.\n";
		    		RobotDriver dr = new WallFollower();
		    		dr.setRobot(r);
		    		result.setRobotAndDriver(r, dr);
		    	}
		    	//  Not valid robot alg case
		    	else {
		    		// does nothing
		    	}
		    	break;
		    // setting unreliable sensors
		    case "-r":
		    	// iterate through characters of following string
		    	for (int charindex = 0; charindex < parameters[strindex+1].length(); charindex++) {
		    		// if 0, set corresponding sensor to UnreliableSensor
		    		if (parameters[strindex+1].indexOf(charindex) == '0') {
		    			UnreliableSensor s = new UnreliableSensor();
		    			if (charindex == 0) {
		    				r.setFSensor(s);
		    			}
		    			if (charindex == 1) {
		    				r.setLSensor(s);
		    			}
		    			if (charindex == 2) {
		    				r.setRSensor(s);
		    			}
		    			if (charindex == 3) {
		    				r.setBSensor(s);
		    			}
		    		}
		    	}
		    	break;
		    }
		}
		// controller instanted and attributes set according to given input parameter
		// output message and return controller
		System.out.println(msg);
		return result;
	 }

	/**
	 * Initializes some internals and puts the game on display.
	 * @param parameter can identify a generation method (Prim, Kruskal, Eller)
     * or a filename that contains a generated maze that is then loaded, or can be null
	 */
	private void init(String parameter) {
	    // instantiate a game controller and add it to the JFrame
	    Controller controller = createController(parameter);
		add(controller.getPanel()) ;
		// instantiate a key listener that feeds keyboard input into the controller
		// and add it to the JFrame
		KeyListener kl = new SimpleKeyListener(this, controller) ;
		addKeyListener(kl) ;
		// set the frame to a fixed size for its width and height and put it on display
		setSize(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT+22) ;
		setVisible(true) ;
		// focus should be on the JFrame of the MazeApplication and not on the maze panel
		// such that the SimpleKeyListener kl is used
		setFocusable(true) ;
		// start the game, hand over control to the game controller
		controller.start();
	}
	
	/**
	 * Initializes some internals and puts the game on display.
	 * @param parameters can identify a generation method, robot method, and set unreliable sensors
     * or a filename that contains a generated maze that is then loaded, or can be null
	 */
	private void init(String[] parameters) {
		// instantiate a game controller and add it to the JFrame
	    Controller controller = createController(parameters);
	    add(controller.getPanel()) ;
		// instantiate a key listener that feeds keyboard input into the controller
		// and add it to the JFrame
		KeyListener kl = new SimpleKeyListener(this, controller) ;
		addKeyListener(kl) ;
		// set the frame to a fixed size for its width and height and put it on display
		setSize(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT+22) ;
		setVisible(true) ;
		// focus should be on the JFrame of the MazeApplication and not on the maze panel
		// such that the SimpleKeyListener kl is used
		setFocusable(true) ;
		// start the game, hand over control to the game controller
		controller.start();
	}
	
	/**
	 * Main method to launch Maze game as a java application.
	 * The application can be operated in three ways. 
	 * 1) The intended normal operation is to provide no parameters
	 * and the maze will be generated by a randomized DFS algorithm (default). 
	 * 2) If a filename is given that contains a maze stored in xml format. 
	 * The maze will be loaded from that file. 
	 * This option is useful during development to test with a particular maze.
	 * 3) A predefined constant string is given to select a maze
	 * generation algorithm, currently supported is "Prim".
	 * @param args is optional, first string can be a fixed constant like Prim or
	 * the name of a file that stores a maze in XML format
	 */
	public static void main(String[] args) {
	    JFrame app ; 
		switch (args.length) {
		case 1 : app = new MazeApplication(args[0]);
		break ;
		case 6 : app = new MazeApplication(args);
		break ;
		case 4: app = new MazeApplication(args);
		break ;
		case 0 : 
		default : app = new MazeApplication() ;
		break ;
		}
		app.repaint() ;
	}

}
