package ttr.model.player;

import java.util.ArrayList;
import java.util.HashMap;

import ttr.model.destinationCards.Destination;
import ttr.model.destinationCards.DestinationTicket;
import ttr.model.destinationCards.Route;
import ttr.model.destinationCards.Routes;
import ttr.model.trainCards.TrainCard;
import ttr.model.trainCards.TrainCardColor;

public class MyPlayer extends Player{
    private ArrayList<Route> currPath = null;
    private int state;
    private DestinationTicket currTicket;
    public MyPlayer(String name) {
		super(name);
		currPath = new ArrayList<Route>();
		currTicket =  new DestinationTicket(null,null);
	}
	public MyPlayer(){
		super("bjk3yf Player");
		currPath = new ArrayList<Route>();
		currTicket = new DestinationTicket(null,null);
	}

	//FIX RAINBOW LOGIC???
	
//	public DestinationTicket findNewTicket() {
//		ArrayList<DestinationTicket> x = super.getDestinationTickets();
//		for(DestinationTicket n : x) {
//			if(Routes.getInstance().hasCompletedRoute(this, n.getFrom(),n.getTo())){
//				continue; 
//			}
//		}
//	}
    @Override
	public void makeMove(){
        ArrayList<DestinationTicket> x = super.getDestinationTickets();
        System.out.println(x.size());
        DestinationTicket tempT = null;
//        if(currPath.size()==0) {
//        	System.out.println("ITS NULL");
//        }
//        else {
//        	for(int i = 0; i < currPath.size(); i++) {
//        		System.out.print(currPath.get(i).getDest1());
//        		System.out.print(currPath.get(i).getDest2());
//        		System.out.println(" ");
//        	}
//        }
        if(x.size()==0) {
        	// out of ticket state
        	//if(this.getNumTrainPieces() < ) change state when reached a certain milestone
        	super.drawDestinationTickets();
        	currPath.clear(); //clearing the route;
        	
        }
        else {
//	        for(int i = 0; i < x.size(); i++) {
//	        	System.out.println(x.get(i).getFrom());
//	        	if(Routes.getInstance().hasCompletedRoute(this, x.get(i).getFrom(),x.get(i).getTo())) {
//	        		continue;
//	        	}
//	        	else {
//	        		tempT = x.get(i);
//	        		break;
//	        	}
//	        }
        	tempT = x.get(0);
        	
        	System.out.println(x.get(0).getFrom());

	    	if(currPath.size()==0) { // initial state
	    		System.out.println("NEW P{ATH");
	    		currPath = findPath(tempT.getFrom(), tempT.getTo());
	    		state = determineState();
	    	}
	    	else if(!(tempT.equals(currTicket))) { // when ticket changes
        		currPath = findPath(tempT.getFrom(), tempT.getTo());
        	}
	    	else { // checks if everything is the same
	    		for(Route r : currPath) {
	    			if(r.getOwner()==null) {
	    				continue;
	    			}
	    			else if(r.getOwner().getName().equals(super.getName())) { //if nothing changes
	    				continue;
	    			}
	    			else {
	    				currPath = findPath(tempT.getFrom(),tempT.getTo()); //if path is now blocked, find a new path. 
	    				System.out.println("diffpath");
	    				state = determineState(); //update state when route is changed
	    				break;
	    			}
	    		}
	    	}
	    	currTicket = tempT;

    	//state = determineState();

    	
//    	if(state == 4) {
//    		super.drawDestinationTickets();
//    		currPath = null; //reset calculating the new path
//    	}
    	if(state == 3) {
    		Route claim = claimable();
    		if(claim!=null) {
    			if(claim.getColor()==TrainCardColor.rainbow) {
    				TrainCardColor c = getMinColor(claim.getCost()-getNumTrainCardsByColor(TrainCardColor.rainbow));
    				if(c != null)
    					super.claimRoute(claim, c);
    				System.out.println("CLAIMABLE");
    			}
    			else {
    				super.claimRoute(claim, claim.getColor());
    				System.out.println("CLAIMABLE2");
    			}
    		}
    	}
    	else if(state == 2) {
    		TrainCardColor c = ColorWorkingOn();
    		if(c != null) {
    			ArrayList<TrainCard> faceup = getFaceUpCards();
    			for(int i = 0; i < faceup.size(); i++) {
    				if(faceup.get(i).getColor() == c || faceup.get(i).getColor() == TrainCardColor.rainbow) {
    					drawTrainCard(i+1);
    					break;
    				}
    			}
    		}
//    		else {
//    		drawTrainCard(0);
//    		}
    		drawTrainCard(0);

    	}
    	else {
    		System.out.println("END");
    		
    	}
        
    	state = determineState();         //System.out.println(Routes.getInstance().shortestPathcost(x.get(0).getTo(), x.get(0).getFrom()));
//        ArrayList<Route> rL = findPath( x.get(0).getFrom(), x.get(0).getTo());
//        for(Route ro : currPath) {
//        	System.out.println(ro.getDest1());
//        	System.out.println(ro.getDest2());
//        	//System.out.println(ro.getColor());
//        	System.out.println(" ");
//        }
        }
    }
    public TrainCardColor getMinColor(int x) {
    	TrainCardColor[] tempM = {TrainCardColor.black,TrainCardColor.red,TrainCardColor.blue,TrainCardColor.green,TrainCardColor.orange,TrainCardColor.purple,TrainCardColor.white,TrainCardColor.yellow};
    	int min = x;
    	int index = -1;
    	int ph = getNumTrainCardsByColor(TrainCardColor.black);
    	if(min <=ph) {
    		min = ph;
    		index = 0;
    	}
    	ph = getNumTrainCardsByColor(TrainCardColor.red);
    	if(min <=ph) {
    		min = ph;
    		index = 1;
    	}
    	ph = getNumTrainCardsByColor(TrainCardColor.blue);
    	if(min <=ph) {
    		min = ph;
    		index = 2;
    	}
    	ph = getNumTrainCardsByColor(TrainCardColor.green);
    	if(min <=ph) {
    		min = ph;
    		index = 3;
    	}
    	ph = getNumTrainCardsByColor(TrainCardColor.orange);
    	if(min <=ph) {
    		min = ph;
    		index = 4;
    	}
    	ph = getNumTrainCardsByColor(TrainCardColor.purple);
    	if(min <=ph) {
    		min = ph;
    		index = 5;
    	}
    	ph = getNumTrainCardsByColor(TrainCardColor.white);
    	if(min <=ph) {
    		min = ph;
    		index = 6;
    	}
    	ph = getNumTrainCardsByColor(TrainCardColor.yellow);

    	if(min <=ph) {
    		min = ph;
    		index = 7;
    	}
    	if(index >=0) {
    		return tempM[index];
    	}
    	return null;
    }
    //I believe there are 4 states: beginning, outOfCards, EnoughCards, and getMoreTickets
    public TrainCardColor ColorWorkingOn() {
    	TrainCardColor min = null;
    	int count = 100000;
    	for(Route r : currPath) {
    		if(r.getOwner()==null) {
    			if(getNumTrainCardsByColor(r.getColor()) < count) {
    				count = getNumTrainCardsByColor(r.getColor());
    				min = r.getColor();
    			}
    		}
    	}
    	return min;
    }
    
    public int determineState() {
//    	if(super.getDestinationTickets().size()==0) { //checks if destination tickets had ran out
//    		//super.drawDestinationTickets();
//    		return 4;
//    	}
//    	for(Route r : currPath) {
//    		if(r.getOwner()==null) {
//    			TrainCardColor tCC = r.getColor();
    	//System.out.println(claimable());
    			if(claimable()!=null) {
    				return 3;		//able to claim a route
    			}
//    		}
//    	}
    	return 2; //unable to claim route
    }
    public Route claimable() {
    	for(Route r : currPath) {
    		if(r.getOwner()==null) {
    			TrainCardColor tCC = r.getColor();
    			if(tCC==TrainCardColor.rainbow) {
    				if(getMinColor(r.getCost()-getNumTrainCardsByColor(TrainCardColor.rainbow))!=null) {
    					return r;
    				}
    			}
    			else {
    				if(r.getCost()<=getNumTrainCardsByColor(tCC) + getNumTrainCardsByColor(TrainCardColor.rainbow)) {
        				return r;		//able to claim a route
        			}
    				else {
    					//System.out.println("ESF");
    				}
    			}
    		}
    	}
    	return null;
    }
    // Finds shortest path and returns list, implemented from shortestPathcost() function.
    public ArrayList<Route> findPath(Destination from, Destination to){ 
    	ArrayList<Route> path = new ArrayList<Route>();
    	if(from == to) return path;
    	HashMap<Destination, Integer> openList = new HashMap<Destination, Integer>();
		HashMap<Destination, Integer> closedList = new HashMap<Destination, Integer>();
		HashMap<Destination, Destination> relationships = new HashMap<Destination, Destination>();
		openList.put(from, 0);
		while(openList.size() > 0){
			/* Pop something off the open list, if destination then return true */
			Destination next = null;
			int minCost = 9999;
			for(Destination key : openList.keySet()){
				if(openList.get(key) < minCost){
					next = key;
					minCost = openList.get(key);
				}
			}
			/* Take it off the open list and put on the closed list */
			openList.remove(next);
			closedList.put(next, minCost);
			/* If this is the destination, then return!!!! */
			if(next == to) break;
			/* Get all the neighbors of the next city that aren't on open or closed lists already */
			for(Destination neighbor : Routes.getInstance().getNeighbors(next)){
				if(closedList.containsKey(neighbor)) continue;
				/* get route between next and neighbor and see if better than neighbor's value */
				ArrayList<Route> routesToNeighbor = Routes.getInstance().getRoutes(next, neighbor);
				for(Route routeToNeighbor : routesToNeighbor){
					int newCost = closedList.get(next) + routeToNeighbor.getCost();
					boolean mine = true;
					if(routeToNeighbor.getOwner() != null) { //If no one owns, free game
						mine = routeToNeighbor.getOwner().getName().equals(super.getName());
						//if player owns, it will be true
					}
					if(openList.containsKey(neighbor)){	
						if(newCost < openList.get(neighbor) && mine){ //only put if better 
							openList.put(neighbor, newCost);
							relationships.put(neighbor,next);
						}
					}
					else if((!openList.containsKey(neighbor)) && mine){ //only put if valid
						openList.put(neighbor, newCost);
						relationships.put(neighbor,next);
					}
				}
			}//end of this will have relation to the to destination
		}
		Destination d = to;
		while(d != from) {
			Destination d2 = relationships.get(d);
			ArrayList<Route> tR = Routes.getInstance().getRoutes(d, d2);
			path.add(tR.get(0));
			d = d2;
		}
//		ArrayList<Route> fin = new ArrayList<Route>();
//		for(int i = path.size()-1; i >= 0; i--) {
//			fin.add(path.get(i));
//		}
    	return path;
    }
}
