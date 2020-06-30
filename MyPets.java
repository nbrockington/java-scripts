import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class MyPets {

    public static void main( String[] args ) {

	// Create array list of available species:
	ArrayList<String> species = new ArrayList<String>();
	species.add( "Dolphin" );
	species.add( "Puffin" );
	species.add( "Horse" );
	species.add( "Cat" );

	// Create array list of pets:
	ArrayList<Pet> pets = new ArrayList<Pet>();
	pets.add( new Dolphin( "Delphi" ) );
	pets.add( new Puffin( "Puffy" ) );
	pets.add( new Horse( "Hudson" ) );

	// Create a random number object:
	Random rand = new Random();
	int randInt = rand.nextInt( 3 );

	Pet currentPet = pets.get( randInt );
	int petID = -1;	

	String input;
	Scanner scan = new Scanner( System.in );

	System.out.println( "#######################" );
	System.out.println( "Welcome to MyPets. Your pet is a " + currentPet.getSpecies() + "." );
	System.out.println( "Your pet's name is " + currentPet.getName() + "." );

	
	do {
	    ArrayList<Action> currentActions = currentPet.getActions();

	    System.out.println( "\n################ Enter one of the following commands:\n");

	    for ( Action action : currentActions ) {
		System.out.println( action.getCommand() + " --- " + action.getMenuMessage() );
	    }

	    System.out.println( "\nSTATUS --- See the status of your pet." );
	    System.out.println( "NEW --- Create a new pet." );
	    System.out.println( "SWITCH --- Switch to another pet." );
	    System.out.println( "RELEASE --- Release current pet " + currentPet.getName() + ".\n" );
	    System.out.println( "QUIT --- Quit the game.\n" );

	    input = scan.next().toUpperCase().trim();
	    boolean didAction = false;

	    // RUNNING A SELECTED "ACTION" 
	    for ( Action action : currentActions ) {
		if ( input.equals( action.getCommand() ) ) {
		    System.out.println( action.doAction( currentPet ) );
		    didAction = true;
		    break;
		}
	    }

	    if ( didAction == false ) {		

		// GETTING PET STATUS
		if ( input.equals( "STATUS" ) ) {
		    System.out.println( "Getting pet status...\n" );
		    System.out.println( "This pet's name is " + currentPet.getName() + "." );
		    System.out.println( "It is a " + currentPet.getSpecies() + "." );
		    System.out.println( "Its hunger is " + currentPet.getHunger() + "." );
		    System.out.println( "Its happiness is " + currentPet.getHappiness() + "." );
		}		

		// CREATING A NEW PET
		else if ( input.equals( "NEW" ) ) {
		    System.out.println( "To choose your new pet's species, type the number:" );
		    System.out.println( "1 --- Random" );
		    for ( String s : species ) {
			System.out.println( ( species.indexOf( s ) + 2 ) + " --- " + s );
		    }
		    String inputNewSpecies = scan.next();
		    System.out.println( "Type the name of your new pet:" );
		    String inputNewName = scan.next();

		    if ( inputNewSpecies.equals( "1" ) ) {
			randInt = rand.nextInt( species.size() );		
			inputNewSpecies = String.valueOf( randInt + 2 );
		    }			

		    switch( Integer.parseInt( inputNewSpecies ) ) {
		    case 2:
			System.out.println( "Your new pet is a dolphin called " + inputNewName + "." );
			pets.add( new Dolphin( inputNewName ) );
			currentPet = pets.get( pets.size() - 1 );
			break;
		    case 3:
			System.out.println( "Your new pet is a puffin called " + inputNewName + "." );
			pets.add( new Puffin( inputNewName ) );
			currentPet = pets.get( pets.size() - 1 );
			break;
		    case 4:
			System.out.println( "Your new pet is a horse called " + inputNewName + "." );
			pets.add( new Horse( inputNewName ) );
			currentPet = pets.get( pets.size() - 1 );
			break;
		    case 5:
			System.out.println( "Your new pet is a cat called " + inputNewName + "." );
			pets.add( new Cat( inputNewName ) );
			currentPet = pets.get( pets.size() - 1 );
			break;
		    default:
			System.out.println( "No match for that species.");
		    }
		}

		// SWITCHING PETS
		else if ( input.equals( "SWITCH" ) ) {

		    // Check whether there is another pet available:
		    if ( pets.size() < 2 ) {
			System.out.println( "There is no pet to switch to." );
		    } 
		    else {
			System.out.println( "Type the name of the pet to switch to:" );
			
			// Cycle through names of pets in the ArrayList:
			for ( Pet pet : pets ) {
			    System.out.println( pet.getName() );
			}

			// Match input with name of pet, and update "currentPet":
			String inputSwitch = scan.next();
			for ( Pet pet : pets ) {
			    if ( inputSwitch.equals( pet.getName() ) ) {
				currentPet = pets.get( pets.indexOf( pet ) );
			    }
			}
			System.out.println( "\nYou have switched to " + currentPet.getName() + "." );

		    }
		}
		// RELEASE A PET
		else if ( input.equals( "RELEASE" ) ) {

		    if ( pets.size() > 1 ) {
			System.out.println( "\nYour pet "+ currentPet.getName() + " has been released!");

			for ( Pet pet : pets ) {
			    if ( pet.getName().equals( currentPet.getName() ) ) {
				petID =  pets.indexOf( pet ) ;
			    }
			}
			pets.remove( petID );
			currentPet = pets.get( 0 );
			System.out.println( "Your current pet's name is " + currentPet.getName() + "." );
		    }
		    else {
			System.out.println( "You cannot release your pet as you'll have no pets left!");
		    }
		}		

		// INVALID INPUT
		else if ( !input.equals( "QUIT" ) ) {
			System.out.println( "Invalid action. Try again.\n" );
		}
	    }

	} while ( !input.equals( "QUIT" ) ) ;
	scan.close() ;
    }
}

class Pet {
    private String name;
    private String species;

    private float happiness;
    private float hunger;

    protected ArrayList<Action> actions;

    public Pet( String s ) {
	this.name = s;
    }

    public ArrayList<Action> getActions() {
	return this.actions;
    }

    public String getName() {
	return this.name;
    }

    public String getSpecies() {
	return this.species;
    }

    public void setSpecies( String s ) {
	this.species = s;
    }

    public float getHappiness() {
	return this.happiness;
    }

    public void setHappiness( float x ) {
	this.happiness = x;
    }

    public float getHunger() {
	return this.hunger;
    }

    public void setHunger( float y ) {
	this.hunger = y;
    }
}

class Dolphin extends Pet {

    public Dolphin( String s ) {
	super( s );
	this.setSpecies( "dolphin" );
	this.setHappiness( 1.25f );
	this.setHunger( 2.25f );

	this.actions = new ArrayList<Action>();
	this.actions.add( new Eat() );
	this.actions.add( new Play() );
	this.actions.add( new Swim() );
    }    
}

class Cat extends Pet {

    public Cat( String s ) {
	super( s );
	this.setSpecies( "cat" );
	this.setHappiness( 2.0f );
	this.setHunger( 1.5f );
	
	this.actions = new ArrayList<Action>();
	this.actions.add( new Eat() );
	this.actions.add( new Play() );
	this.actions.add( new Sleep() );
    }
}

class Puffin extends Pet {

    public Puffin( String s ) {
	super( s );
	this.setSpecies( "puffin" );
	this.setHappiness( 2.25f );
	this.setHunger( 1.10f );

	this.actions = new ArrayList<Action>();
	this.actions.add( new Eat() );
	this.actions.add( new Play() );
	this.actions.add( new Fly() );
    }
}
    
class Horse extends Pet {

    public Horse( String s ) {
	super( s );
	this.setSpecies( "horse" );
	this.setHappiness( 0.75f );
	this.setHunger( 3.25f );

	this.actions = new ArrayList<Action>();
	this.actions.add( new Eat() );
	this.actions.add( new Play() );
	this.actions.add( new Jump() );
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
	return "EAT" ;
    }

    @Override
	public String getMenuMessage() {
	return "Feed your pet." ;
    }

    @Override 
	public String doAction( Pet pet ) {
	pet.setHunger( pet.getHunger() - 0.5f );

	if ( pet.getHunger() < 0.5 ) {
	    pet.setHappiness( pet.getHappiness() + 0.25f );
	    return "Your pet is satisfied!" ;
	}
	else {
	    return "Your pet is still hungry!";
	}
    }
}

class Sleep extends Action {
    @Override
	public String getCommand() {
	return "SLEEP" ;
    }

    @Override
	public String getMenuMessage() {
	return "Let your pet sleep!";
    }

    @Override 
	public String doAction( Pet pet ) {
	pet.setHunger( pet.getHunger() + 0.25f );
	pet.setHappiness( pet.getHappiness() - 0.75f );

	if ( pet.getHappiness() < 1.0f ) {
	    return "Your pet would like to play!";
	}
	else {
	    return "Your pet could sleep some more!";
	}
    }
}

class Play extends Action {
    @Override
	public String getCommand() {
	return "PLAY" ;
    }

    @Override
	public String getMenuMessage() {
	return "Play with your pet" ;
    }

    @Override 
	public String doAction( Pet pet ) {
	pet.setHunger( pet.getHunger() + 0.25f );
	pet.setHappiness( pet.getHappiness() + 0.3f );

	if ( pet.getHappiness() < 2.0 ) {
	    return "Your pet wants more fun!";
	}
	else {
	    return "Your pet enjoyed playing!" ;
	}
    }
}
	

class Swim extends Action {
    @Override
	public String getCommand() {
	return "SWIM" ;
    }

    @Override
	public String getMenuMessage() {
	return "Let your pet swim!" ;
    }

    @Override 
	public String doAction( Pet pet ) {
	pet.setHunger( pet.getHunger() + 0.5f );
	pet.setHappiness( pet.getHappiness() - 0.5f );

	if (pet.getHunger() > 0.5f && pet.getHappiness() < 2.0 ) {
	    return "Your pet is exhausted and hungry!";
	}
	else {
	    return "Your pet had a good swim!" ;
	}
    }
}

class Fly extends Action {
    @Override
	public String getCommand() {
	return "FLY" ;
    }

    @Override
	public String getMenuMessage() {
	return "Let your pet fly!" ;
    }

    @Override
	public String doAction( Pet pet ) {
	pet.setHunger( pet.getHunger() + 0.5f );
	pet.setHappiness( pet.getHappiness() - 0.5f );

	if (pet.getHunger() > 0.5f && pet.getHappiness() < 2.0 ) {
	    return "Your pet is exhausted and hungry!";
	}
	else {
	    return "Your pet had a good flight!" ;
	}
    }
}

class Jump extends Action {
    @Override 
	public String getCommand() {
	return "JUMP" ;
    }

    @Override
	public String getMenuMessage() {
	return "Let your pet jump!" ;
    }

    @Override 
	public String doAction( Pet pet ) {
	pet.setHunger( pet.getHunger() + 1.25f );
	pet.setHappiness( pet.getHappiness() - 0.5f );

	if (pet.getHunger() > 0.5f && pet.getHappiness() < 2.0 ) {
	    return "Your pet is exhausted and hungry!";
	}
	else {
	    return "Your pet jumped very high!" ;
	}
    }
}
