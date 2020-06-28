import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class MyPets {

    public static void main( String[] args ) {

	// Make array list of pets:
	ArrayList<Pet> pets = new ArrayList<Pet>();
	pets.add( new Dolphin( "Delphi" ) );
	pets.add( new Puffin( "Puffy" ) );
	pets.add( new Horse( "Hudson" ) );

	// Create a random number object:
	Random rand = new Random();
	int rand_int = rand.nextInt( 3 );

	Pet currentPet = pets.get( 0 );
	ArrayList<Action> currentActions = currentPet.actions;

	//	ArrayList<Action> actions = new ArrayList<Action>();
	//	actions.add( new Eat() );
	//	actions.add( new Play() );

	String input;
	Scanner scan = new Scanner( System.in );

	System.out.println( "#######################" );
	System.out.println( "Welcome to MyPets. Your pet is a " + currentPet.species + "." );
	System.out.println( "Your pet's name is " + currentPet.name + "." );

	
	do {
	    System.out.println( "\nEnter one of the following commands:\n");

	    for ( Action action : currentActions ) {
		System.out.println( action.getCommand() + " --- " + action.getMenuMessage() );
	    }

	    System.out.println( "QUIT --- Quit the game.\n" );

	    input = scan.next().toUpperCase().trim();
	    boolean didAction = false;

	    for ( Action action : currentActions ) {
		if (input.equals( action.getCommand() ) ) {
		    System.out.println( action.doAction( currentPet ) );
		    didAction = true;
		    break;
		}
	    }
	    
	    if ( didAction == false && !input.equals("QUIT") ) {
		System.out.println( "Invalid action. Try again.\n" );
	    }
	} while (!input.equals("QUIT"));
	scan.close();
    }
}

class Pet {
    public String name;
    public String species;

    public float happiness;
    public float hunger;

    public ArrayList<Action> actions;

    public Pet( String s ) {
	this.name = s;
    }
}


class Dolphin extends Pet {

    public Dolphin( String s ) {
	super( s );
	this.species = "dolphin";
	this.happiness = 1.25f;
	this.hunger = 2.25f;

	this.actions = new ArrayList<Action>();
	this.actions.add( new Eat() );
	this.actions.add( new Play() );
	this.actions.add( new Swim() );
    }    
}

class Puffin extends Pet {

    public Puffin( String s ) {
	super( s );
	this.species = "puffin";
	this.happiness = 2.25f;
	this.hunger = 1.10f;
    }
}

    
class Horse extends Pet {

    public Horse( String s ) {
	super( s );
	this.species = "horse";
	this.happiness = 0.75f;
	this.hunger = 3.25f;
    }
}


abstract class Action {
    public abstract String getCommand();
    public abstract String getMenuMessage();
    public abstract String doAction( Pet pet );
}

class Eat extends Action {
    @Override
	public String getCommand() {
	return "EAT";
    }

    @Override
	public String getMenuMessage() {
	return "Feed your pet.";
    }

    @Override 
	public String doAction( Pet pet ) {
	return "Your pet is satisfied!" ;
    }
}

class Play extends Action {
    @Override
	public String getCommand() {
	return "PLAY";
    }

    @Override
	public String getMenuMessage() {
	return "Play with your pet" ;
    }

    @Override 
	public String doAction( Pet pet ) {
	return "Your pet enjoyed playing!" ;
    }
}
	


class Swim extends Action {
    @Override
	public String getCommand() {
	return "SWIM";
    }

    @Override
	public String getMenuMessage() {
	return "Let your pet swim!" ;
    }

    @Override 
	public String doAction( Pet pet ) {
	return "Your pet had a good swim!" ;
    }
}