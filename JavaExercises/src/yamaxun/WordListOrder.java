package yamaxun;

// IMPORT LIBRARY PACKAGES NEEDED BY YOUR PROGRAM
// SOME CLASSES WITHIN A PACKAGE MAY BE RESTRICTED
// DEFINE ANY CLASS AND METHOD NEEDED
// CLASS BEGINS, THIS CLASS IS REQUIRED
import java.util.*;
public class WordListOrder
{
    //METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED
    public static int canArrangeWords(String arr[])
    {
        // INSERT YOUR CODE HERE
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr.length-i-1;j++){
                if(arr[j].charAt(0)>arr[j+1].charAt(0)){
                    String temp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                }
            }
        }
        for(int i=1;i<arr.length;i++){
            if(arr[i].charAt(0)!=arr[i-1].charAt(arr[i-1].length()-1)){
                return -1;
            }
        }
        return 1;
    }
// METHOD SIGNATURE ENDS

    // DO NOT IMPLEMENT THE main( ) METHOD
    public static void main(String[] args)
    {
        // PLEASE DO NOT MODIFY THIS METHOD
        String arr[]={"ghij","defg","jkl","abcd"};
        int out;
        // ASSUME INPUTS HAVE ALREADY BEEN TAKEN
        out = canArrangeWords(arr);
        System.out.println("output:"+out);
        // Print the output
    }
}
