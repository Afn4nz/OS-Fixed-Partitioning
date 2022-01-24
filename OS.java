import java.io.*;
import java.util.*;

class invalidFormatException extends Exception {
   
    invalidFormatException(String s) {
        super(s);
    }
}// END user defined exception

public class OS {
    // declered partition properties:

    /* size of each partition*/
    private static int[] pSize = null;
    /* internal gragmentaion of each partition in KB (if applicable) |if not allocated=-1 otherwise it's the left KB|*/
    private static int[] pFrag = null;
    /* starting address space of each partion*/
    private static int[] pStart = null;
    /* Ending address space of each partion*/
    private static int[] pEnd = null;
    /* name of each process (intially will be null )*/
    private static String[] pName = null;
    /* status of each partition */
    private static String[] pStatus = null;

    /* declerd here to use it in all functions*/
    static Scanner read = new Scanner(System.in);
   
    public static void main(String[] args) {

        /* To get number of partitions */
        System.out.print("Please enter the number of partitions (M): ");
        int numOfPartion = 0;
        do {
            try {
                numOfPartion = read.nextInt();
                initilaz(numOfPartion);
            } catch (InputMismatchException excp) {
                System.out.println("Number of partitions should be numbers '+ve' only, Try again! >> ");
                read.next();/*just to avoid infinite loop*/
            }
        } while (numOfPartion <= 0);

        //·« ‰‰”Ï «Ìﬁ‰Ê— ﬂÌ” >>>> ⁄·Ï «Ì Ã“¡ »«·÷»ÿø
        int option = 0;//****************

        while (option != -1) {
            System.out.println("Please choose an option from the follwing menu:\n1.Request \n2.Release\n3.Status report\n4.Exit");
            option = read.nextInt();
             
           
            switch (option) {
                case 1:
                   
                    boolean flag=false;
                    String info[] = null;
                    String request = " ";
                    int size = -1;
                    int pNO = -1;
                    String pName2 = " ";
                    char strategy = ' ';
                   
                    System.out.println("Please enter the process name, the size of the process in KB , and the location strategy in the follwing format (P0 40 W)");
                    do {

                        //     flag = false;
                        try {
                         pName2=read.next().toUpperCase();
                       
                       
                            request = read.nextLine();
                              
                           
                            info = request.split(" "); // to extract information
                           
                         
                           
                           
                            size = Integer.parseInt(info[1]);
                              // System.out.println(size);
                            strategy = Character.toUpperCase(info[2].charAt(0));
                          //  System.out.println(strategy);
                            // Now check if the request in the valid format:
                           // info.length != 2 || pName2.charAt(0) != 'P' ||
                            if (info.length != 3 || pName2.charAt(0) != 'P' || (strategy != 'F' && strategy != 'W' && strategy != 'B')) {
                                //user defined Excption:
                                flag=true;
                                 //System.out.println("pName2 inside if  "+pName2);
                                size = -1;
                                pNO = -1;
                                pName2 = " ";
                                strategy = ' ';
                                throw new invalidFormatException("Invalid Format!");
                            } // end if

                            requestion(pName2, size, strategy, numOfPartion);
                           flag=false;
                        } /*catch (invalidFormatException excp) {
                            System.out.println("Wrong request format, please follow the provided format! ");
                            //flag = true; // change the flag to stop the validation loop
             
                        }//first catch
                         */ catch (Exception excp) {
                           
                            System.out.println("Something is wrong, please Try again! " + excp.getMessage());
                            //read.nextLine();/*just to avoid infinite loop*/
                            //   flag = true; // change the flag to stop the validation loop
                        }// second catch
                       
                       
                       
                    } while (flag); // end loop
                   
                    System.out.println("\nPartirion#\tStarting address\tEndingaddress\t\tPartition status\t  Partition Size\tCurrent allocated process\tInternal gragmentaion\n______________________________________________________________________________________________________________________________________________________________________");
                    for (int i = 0; i < numOfPartion; i++) {
                        System.out.println("\n" + (i + 1) + "\t\t\t" + pStart[i] + " B" + "\t\t    " + pEnd[i] + " B" + "\t\t\t" + pStatus[i] + "\t\t\t" + pSize[i] + "\t\t\t" + pName[i] + "\t\t\t    " + pFrag[i] + " KB");
                    } // since in the report was requested to print report after each request
                    break;
               
                case 2:
                   
                    System.out.println("Please enter the process name to release in the follwing format (PX -X is a number-)");
                    String processName = " ";
                    //   boolean flag2;
                   
                    do {
                        // flag2 = false;
                        try {
                            read.nextLine();
                            processName = read.nextLine().toUpperCase();
                            int pNO2 = Integer.parseInt(processName.substring(1));
                            // Now check if the process name in the valid format:
                            if (processName.charAt(0) != 'P') { //***************
                                processName = " ";
                                throw new invalidFormatException("Invalid Format!");
                            }
                            release(processName, numOfPartion);
                           
                        } // try
                        /*  catch (invalidFormatException excp) {
                   
                            System.out.println("Wrong request format, please follow the provided format! ");
                             // flag2 = true; // change the flag to stop the validation loop
                         
                        }*/ catch (Exception excp) {
                            System.out.println("Something is wrong, please Try again!");
                            read.nextLine();/*just to avoid infinite loop*/
                            //  flag2 = true; // change the flag to stop the validation loop
                           
                           
                        }// second catch
                       
                    } while (processName.equals(" "));
                    break;
               
                case 3:
                    /*Status report of each partition in the memory*/
                   
                    System.out.println("\nPartirion#\tStarting address\tEnding address\t\tPartition status\t  Partition Size\tCurrent allocated process\tInternal gragmentaion\n______________________________________________________________________________________________________________________________________________________________________");
                    for (int i = 0; i < numOfPartion; i++) {
                        System.out.println("\n" + (i + 1) + "\t\t\t" + pStart[i] + " B" + "\t\t    " + pEnd[i] + " B" + "\t\t\t" + pStatus[i] + "\t\t\t" + pSize[i] + "\t\t\t" + pName[i] + "\t\t\t    " + pFrag[i] + " KB");
                    }
                    // loadFromFile();
                    try {
                        saveToFile();
                    } catch (Exception excp) {
                        //System.out.println(excp.getMessage());
                        System.out.println("The report saved Successfully!");
                    }
                    break;
               
                case 4:
                    System.out.println("Thank you for using our program!");
                    option = -1;
                    break;

                // we need try catch to force the user enter a valid option *›‰Ê‰Â*
                default:
                    System.out.println("Invalid menue entry, Try again!");
               
            }
        }
    }

    /*This function was created to initialize memory*/
    public static void initilaz(int size) {
        try {

            // initilaz partition properties:
            /* size of each partition*/
            pSize = new int[size];
            /* internal gragmentaion of each partition in KB (if applicable) |if not allocated=-1 otherwise it's the left KB|*/
            pFrag = new int[size];
            /* starting address space of each partion*/
            pStart = new int[size];
            /* Ending address space of each partion*/
            pEnd = new int[size];
            /* name of each process (intially will be null )*/
            pName = new String[size];
            /* status of each partition */
            pStatus = new String[size];

            /* To get the size of each partition from user*/
            for (int i = 0; i < size; i++) {
                System.out.print("Please enter the size of partition #" + (i + 1) + " in KB: ");
                pSize[i] = 0;
                do {
                    try {
                        pSize[i] = read.nextInt();

                        /* since there will be no location and deallocation (intially)*/
                        pStatus[i] = "Free";

                        /* Since the status is FREE*/
                        pFrag[i] = -1;//+++++++++++++++++++++++‰‰«œÌ „ÌÀÊœ ·Õ”«»Â«+++++++++++++++++++++++++++++++

                        /*To extract start and end addresses*/
                        if (i == 0) /* means it is the first partion so start address will be 0 (considering as we start with first partion)*/ {
                            pStart[i] = 0;
                        } else {
                            /* the start address will be equal to the end address of the previuos partition*/
                            pStart[i] = pEnd[i - 1] + 1;
                        }
                        /* size in KB so we need to convert it to B by multibly by 1024*/
                        pEnd[i] = pStart[i] + (pSize[i] * 1024) - 1;
                        pName[i] = "NULL";
                       
                    } catch (InputMismatchException excp) {
                        System.out.println("Size of partition should be numbers '+ve' only, Try again! >>");
                        read.next();/*just to avoid infinite loop*/
                    }
                } while (pSize[i] <= 0);
               
            } // END FOR LOOP  

            /*Status report of each partition in the memory*/
            // System.out.printf("%-s20,");
            System.out.println("\nPartirion#\tStarting address\tEnding address\t\tPartition status\t  Partition Size\tCurrent allocated process\tInternal gragmentaion\n______________________________________________________________________________________________________________________________________________________________________");
            for (int i = 0; i < size; i++) {
                System.out.println("\n" + (i + 1) + "\t\t\t" + pStart[i] + " B" + "\t\t    " + pEnd[i] + " B" + "\t\t\t" + pStatus[i] + "\t\t\t" + pSize[i] + "\t\t\t" + pName[i] + "\t\t\t    " + pFrag[i] + " KB");
            }
           
        } catch (Exception excp) {
            System.out.println(excp.getMessage());
        }
    }//END initilaz function

    private static void requestion(String name, int size, char strategy, int numOfPartion) {
        switch (strategy) {
           
            case 'F':
                firstFit(size, name, numOfPartion);
                break;
           
            case 'W':
                worstFit(size, name, numOfPartion);
                break;
           
            case 'B':
                bestFit(size, name, numOfPartion);
                break;
           
            default:                
                System.out.println("Invalid menue entry, Try again!");
           
        } // end switch

        if (!isDone) {
            System.out.println("Request rejected. There is no sufficient memory to be aloocated"); ///????????????????//////////////
        } //Â‰« ÿ—Õ «·›—«ﬁ
        else {
            System.out.println("The alocation done succsuffly ");
        }
    }
   
    private static void release(String name, int numOfPartion) {
        boolean isFound = false;
       
        for (int i = 0; i < numOfPartion; i++) {
            /* afnan's logic*/
            if (pName[i].equalsIgnoreCase(name)) {
                pFrag[i] = -1;
                pName[i] = "NULL";
                pStatus[i] = "Free";
                // pSize[i]=
                //  pSize[i]=""
                isFound = true;
               
            }// end if
        }// end loop
        if (!isFound) {
            System.out.println("The process not found");
        } else {
            System.out.println("The process released");
        }
       
    }// end method

    // check it gyus!!!!!!!!!!!!******************************^^^^^^^^^^^^^
    static boolean isDone = false; //if the process allocated reassingment to true

    public static void firstFit(int size, String name, int p) {
        isDone = false;
        for (int i = 0; i < p; i++) {
            if (pStatus[i].equalsIgnoreCase("Free")) {
                if (pSize[i] >= size) {
                    pStatus[i] = "Allocated";
                    pFrag[i] = pSize[i] - size;
                    pName[i] = name;
                    isDone = true;
                    break;
                } //inner if (to not continue cycling on the other memory partions; as the intended partion was found and suits sucssesfuly.)
            } //outer if
        }// end loop
    }// end method first fit

    public static void worstFit(int size, String name, int p) {
        isDone = false;
        int max = 0;
        int intendedIndex = 0;
        for (int i = 0; i < p; i++) {
            if (pStatus[i].equalsIgnoreCase("Free")) {// to enhance the performance;firstly we'll cycle on the not allocated insted of doing the max comparsion first
                if (pSize[i] > max) {
                    max = pSize[i];
                    intendedIndex = i;
                } // inner if
            }// outer if
        } // end loop

        if (pStatus[intendedIndex].equalsIgnoreCase("Free")) {
            if (pSize[intendedIndex] >= size) {
                pStatus[intendedIndex] = "Allocated";
                pFrag[intendedIndex] = pSize[intendedIndex] - size;
                pName[intendedIndex] = name;
                isDone = true;
            } // inner if
        } // outer if
    } // end method worst fit

    public static void bestFit(int size, String name, int p) {
        isDone = false;
        int bestFitIndex = 0;
        int minDiff = (int) Double.POSITIVE_INFINITY;// Just for the first time
        int diff = 0;
       
        for (int i = 0; i < p; i++) {
            if (pStatus[i].equalsIgnoreCase("Free")) {// to enhance the performance;firstly we'll cycle on the not allocated insted of doing the max comparsion first
                if (pSize[i] >= size) {
                    diff = pSize[i] - size;
                }
               
                if (diff <= minDiff) {
                    minDiff = diff;
                    bestFitIndex = i;
                }
               
            } // outer inner if

        } // end loop

        if (pStatus[bestFitIndex].equalsIgnoreCase("Free")) {
            if (pSize[bestFitIndex] >= size) {
                pStatus[bestFitIndex] = "Allocated";
                pFrag[bestFitIndex] = pSize[bestFitIndex] - size;
                pName[bestFitIndex] = name;
                isDone = true;
            }    // end inner if
        } // end outrt if

    } // end method best

    public static void saveToFile() throws FileNotFoundException, IOException {
       
        String fileName = "Report.txt";
        FileOutputStream outF = new FileOutputStream(new File(fileName)/*,true*/);// true to write above the previous data???????                                                 ^^^^^
        ObjectOutputStream outObj = new ObjectOutputStream(outF);
        //outObj.writeInt(numOfCars);
        outObj.writeObject(pSize);
        outObj.writeObject(pFrag);
        outObj.writeObject(pStart);
        outObj.writeObject(pEnd);
        outObj.writeObject(pName);
        outObj.writeObject(pStatus);
        outObj.close();
    }
    }