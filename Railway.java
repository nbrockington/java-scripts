import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap; // Importing the HashMap class
import java.util.Arrays;

public class Railway{

    // Declaring the static variables needed to construct the HashMap
    // data structure when reading the CSV input file:
    static String stationKey;
    static String[] stationInfo = new String[3];
    static HashMap< String , String[] > stations = new HashMap< String , String[] >();

    // Constructing the train types used and assigning values:
    static TrainType highSpeedTrain = new TrainType( "High Speed" , "H" , 9f , 84, 46, 800 );
    static TrainType standardTrain = new TrainType( "Standard" , "S", 7f , 76, 38, 200 );
    static TrainType slowTrain = new TrainType( "Slow" , "L" , 4.5f , 60, 30, 100 );
    static TrainType chosenTrainType;

    // Declaring float variable for holding distance between selected stations:
    static float distanceBtwnStations;


    // MAIN method for Railway class:
    //
    public static void main( String[] args ) {

	// Read in the external file "Stations.txt" and populate the
	// HashMap accordingly:

	readExternalFile( "Stations.txt" );

	// Create default instance of Service class:
	Service myService = new Service();

	// Create a new Scanner object to read inputs/write outputs:
	String input;
	Scanner scan = new Scanner( System.in );

	System.out.println( "###############\n" );
	System.out.println( "Welcome to the Railway Service Program.\n");

	do {

	    // DISPLAY MENU
	    System.out.println( "MAIN MENU: Enter one of the following commands then press ENTER:\n" );
	    System.out.println( "\tS --- Enter station codes." );
	    System.out.println( "\tT --- Train type." );
	    System.out.println( "\tP --- Price plan and calculate profit." );
	    System.out.println( "\tC --- Clear data." );
	    System.out.println( "\tQ --- Quit." );

	    input = scan.next().toUpperCase().trim();

	    // CLEAR THE DATA
	    if ( input.equals( "C" ) ) {

		myService.reset();
	    }
	    
	    // ENTER STATION
	    else if ( input.equals( "S" ) ) {

		System.out.println( "Enter the three-letter code for the departure station:\n" );

		// Read input and check whether it is either Bath
		// (BTH) or Welwyn Garden City (WGC):
		String inputStation = scan.next();

		if ( inputStation.equals( "BTH" ) || inputStation.equals( "WGC" ) ) {

		    // If code is correct, save the station and ask for the destination:
		    myService.stationCode = inputStation;

		    System.out.println( "Enter the three-letter code for the destination station:\n" );

		    String inputDestination = scan.next();

		    if ( stations.containsKey( inputDestination ) ) {

			myService.destinationCode = inputDestination ;

			// Display full name of destination station:
			System.out.print( "Chosen destination: " );
			System.out.println( stations.get( inputDestination )[ 0 ] + "\n" );

			// Calculate distance between departure and destination stations:
			if ( inputStation.equals( "BTH" ) ) {

			    distanceBtwnStations = Float.valueOf( stations.get( inputDestination )[ 2 ] ) ;

			} else {

			    distanceBtwnStations = Float.valueOf( stations.get( inputDestination )[ 1 ] ) ;
			}
			
			System.out.println( "Distance between stations: " + distanceBtwnStations + "\n" );

		    } else {

			System.out.println( "Error: Destinatin code is not valid.\n" );
		    }

		} else {
		    // Print an error message if input Station is not BTH or WGC:
		    System.out.println( "Error: Station code must be BTH or WGC.\n" );
		}
	    }

	    // ENTER TRAIN TYPE
	    else if ( input.equals( "T" ) ) {
		
		System.out.println( "Enter the type of train to be used:\n" );
		System.out.println( "H --- High Speed Train" );
		System.out.println( "S --- Standard Train" );
		System.out.println( "L --- Slow Train\n" );

		String trainType = scan.next();

		if ( trainType.equals( "H" ) || trainType.equals( "S" ) || trainType.equals( "L" ) ) {

		    // Display data for chosen train type:
		    displayTrainInfo( trainType );

		    // Ask user for number of Standard class and First
		    // class carriages and assign the values to the
		    // myService object:
		    System.out.println( "Enter the number of Standard Class carriages: \n" );

		    myService.numStandardCars = Integer.valueOf( scan.next() );

		    System.out.println( "Enter the number of First Class carriages: \n" );

		    myService.numFirstclassCars = Integer.valueOf( scan.next() );

		    myService.trainType = chosenTrainType.name;


		    // Calculate number of standard-class seats and first-class seats:
		    myService.numStandardSeats = myService.numStandardCars * chosenTrainType.sCarNumPass ;

		    myService.numFirstclassSeats = myService.numFirstclassCars * chosenTrainType.fCarNumPass ;

		    System.out.println(" ");

		} else {
		    
		    System.out.println( "Train type is not recognised.\n" );
		}
	    }
	    
	    // PRICE PLAN AND CALCULATE PROFIT
	    else if ( input.equals( "P" ) ) {

		// Check whether departure station code has been entered:
		if ( myService.stationCode.isEmpty() ) {

		    System.out.println( "Error: Departure station code has not been entered.\n" );

		    // Check whether destination station code has been entered:
		} else if ( myService.destinationCode.isEmpty() ) {

		    System.out.println( "Error: Destination station code has not been entered.\n" );

		    // Check whether type of train has been entered:
		} else if ( myService.trainType.isEmpty() ) {

		    System.out.println( "Error: Train type has not been entered.\n" );

		    // Check whether number of First Class carriages has been entered:
		} else if ( myService.numFirstclassCars < 0 ) {

		    System.out.println( "Error: Number of First Class carriages has not been entered.\n" );

		    // Check whether the maximum distance for the type
		    // of train is greater than or equal to the
		    // distance between teh stations:
		} else if ( chosenTrainType.maxDistance < distanceBtwnStations ) {

		    System.out.println( "Error: Maximum distance for train type is too small.\n" );

		} else {

		    // Ask user to enter price of standard-class seat
		    // and first-class seat:
		    System.out.println( "Enter the price of a standard-class seat: \n" );
		    
		    myService.priceStandardSeat = Float.valueOf( scan.next() );

		    System.out.println( "Enter the price of a first-class seat: \n" );

		    myService.priceFirstclassSeat = Float.valueOf( scan.next() );


		    // Calculate travel cost per seat:
		    myService.travelCostPerSeat = ( distanceBtwnStations / 50 ) * chosenTrainType.costPer50;

		    System.out.print( "\n###\nCost, income and profit for this journey between " );
		    System.out.println( myService.stationCode + " and " + myService.destinationCode + ":\n");
		    System.out.print( "Travel cost per seat: £" );
		    System.out.printf( "%.2f" , myService.travelCostPerSeat );
		    System.out.println();

		    // Calculate total travel cost:
		    myService.travelCost = myService.travelCostPerSeat * ( myService.numStandardSeats + myService.numFirstclassSeats );

		    System.out.print( "Total travel cost: £");
		    System.out.printf( "%.2f" , myService.travelCost );
		    System.out.println();

		    // Calculate travel income:
		    myService.travelIncome = myService.numFirstclassSeats * myService.priceFirstclassSeat + myService.numStandardSeats * myService.priceStandardSeat;

		    System.out.print( "Travel income: £" );
		    System.out.printf( "%.2f" , myService.travelIncome );
		    System.out.println();

		    // Calculate travel profit:
		    myService.travelProfit = myService.travelIncome - myService.travelCost ;

		    System.out.print( "Travel profit from this service: £" );
		    System.out.printf( "%.2f" ,  myService.travelProfit );
		    System.out.println( "\n\n" );
		}
	    }


	    // INVALID INPUT
	    else if ( !input.equals( "Q" ) ) {

		System.out.println( "Invalid selection. Try again.\n" );
	    }

	} while ( !input.equals( "Q" ) );

	// QUIT THE APPLICATION
	System.out.println( "Quitting the application.\n" );
	scan.close();

    }


    // Defining method to display information for chosen train type:

    public static void displayTrainInfo( String t ) {

	switch( t ) {

	case "H":
	    chosenTrainType = highSpeedTrain;
	    break;
	case "S":
	    chosenTrainType = standardTrain;
	    break;
	case "L":
	    chosenTrainType = slowTrain;
	    break;
	default:
	    chosenTrainType = standardTrain;
	}	
	
	System.out.println( "\nChosen train type is " + chosenTrainType.name + " train." );
	System.out.println( "Cost per passenger per 50 miles: £" + chosenTrainType.costPer50 );
	System.out.println( "No. of passengers per carriage: " );
	System.out.println( "\tStandard Class: " + chosenTrainType.sCarNumPass );
	System.out.println( "\tFirst Class:    " + chosenTrainType.fCarNumPass );
	System.out.println( "Maximum travelling distance: " + chosenTrainType.maxDistance + "\n" );
    }

    // Defining method to read in the external file "Stations.txt":
    //
    public static void readExternalFile( String name ){


	// Input file to be parsed:
	String fileToParse = name ;
	BufferedReader fileReader = null;

	// Delimiter used in CSV file:
	final String DELIMITER = ", ";

	try{
	    String line = "";
	    
	    // Create the file reader:
	    fileReader = new BufferedReader( new FileReader( fileToParse ) );
	    
	    // Declaring the variable j which will number the station
	    // input tokens, allowing them to be saved as a key or
	    // value array:
	    int j = 0;

	    // Read the file line by line until no more lines:
	    while( ( line = fileReader.readLine() ) != null ){

		// Set j to zero when starting to read a new line from input:
		j = 0;

		// Get all tokens available in current line:
		String[] tokens = line.split( DELIMITER );

		for (String token : tokens ){

		    // Increment j to indicate next token is encountered:
		    j++;

		    if ( j == 1 ){

			// First token in a line is saved to the key
			// variable for the HashMap:
			stationKey = token ;

		    } else {

			// All other tokens are saved to the array
			// which will become the value for the
			// HashMap:
			stationInfo[ j - 2 ] = token ;

		    }
		}
		// Adding the key and value of this line to the
		// HashMap, using the method Array.copyOf() in order
		// to create a new object for the HashMap to reference
		// for each key (otherwise all would reference the
		// stationInfo variable and return whatever was in
		// that variable":

		stations.put( stationKey, Arrays.copyOf( stationInfo , 3 ) );
	    }
	    // Catching an error if cannot read the file for some reason:
	} catch (Exception e){
	    e.printStackTrace();
	}
	finally{
	    try{ 
		fileReader.close();
		// Catching an error if cannot close the file for some reason:
	    } catch (IOException e){
		e.printStackTrace();
	    }
	}
    }

}


//
// Defining class "Service" to hold data about the putative new train
// service:
//
class Service{

    public String stationCode;
    public String destinationCode;
    public String trainType;
    public int numStandardCars;
    public int numFirstclassCars;
    public int numStandardSeats;
    public int numFirstclassSeats;
    public float priceStandardSeat;
    public float priceFirstclassSeat;
    public float travelCostPerSeat;
    public float travelCost;
    public float travelIncome;
    public float travelProfit;

    public void reset() {
	this.stationCode = "";
	this.destinationCode = "";
	this.trainType = "";
	this.numStandardCars = -1;
	this.numFirstclassCars = -1;
	this.numStandardSeats = -1;
	this.numFirstclassSeats = -1;
	this.priceStandardSeat = -1;
	this.priceFirstclassSeat = -1;
	this.travelCostPerSeat = -1;
	this.travelCost = -1;
	this.travelIncome = -1;
	this.travelProfit = -1;

	System.out.println( "All data has been cleared.\n" );
    }

    public Service() {
	this.stationCode = "";
	this.destinationCode = "";
	this.trainType = "";
	this.numStandardCars = 100;
	this.numFirstclassCars = -1;
	this.numStandardSeats = -1;
	this.numFirstclassSeats = -1;
	this.priceStandardSeat = -1;
	this.priceFirstclassSeat = -1;
	this.travelCostPerSeat = -1;
	this.travelCost = -1;
	this.travelIncome = -1;
	this.travelProfit = -1;
    }
}

class TrainType {

    public String name;
    public String code;
    public float costPer50;
    public int sCarNumPass;
    public int fCarNumPass;
    public int maxDistance;

    public TrainType( String n , String c , float f , int x , int y, int z ) {
	this.name = n;
	this.code = c;
	this.costPer50 = f;
	this.sCarNumPass = x;
	this.fCarNumPass = y;
	this.maxDistance = z;
    }
}



    





