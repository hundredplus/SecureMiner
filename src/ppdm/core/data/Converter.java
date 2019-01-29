package ppdm.core.data;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;
import ppdm.core.Config;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Sep 30, 2008
 */
public class Converter {
    /*
     * This class converts a nominal data set to binary dataset. 
     * And save it to a file.
     */

    public static void main(String[] args) throws Exception {
        Converter.nominal2binary("D:\\Data\\outtest2.txt", "D:\\Data\\outouttest2.txt");
    }

    public static void nominal2binary(String inputFileName, String outputFileName) throws Exception {
        //input scanner
        Scanner scan = new Scanner(new File(inputFileName));
        //output writer
        PrintWriter outFile = new PrintWriter(outputFileName);

        //hold line by line input
        StringTokenizer tokenizer;
        String temp, att_name = "";
        List attributeName, possibleValue;

        attributeName = new List();
        possibleValue = new List();

        tokenizer = new StringTokenizer(scan.nextLine());
        temp = tokenizer.nextToken();
        //if temp != @attribute
        if (!temp.equalsIgnoreCase(Config.ATTRIBUTE)) {
            //error data format
            System.out.println("Error data format - No attribute information");
            return;
        }

        //iterate through all attribute
        while (temp.equalsIgnoreCase(Config.ATTRIBUTE)) {

            //grab attribute name
            if (!tokenizer.hasMoreTokens()) {
                System.out.println("Error data format - No attribute name information");
                return;
            } else {
                att_name = tokenizer.nextToken();
            }

            if (tokenizer.hasMoreTokens()) {

                //grab possible value of att
                temp = tokenizer.nextToken("}");
                temp = temp.trim();
                temp = temp.substring(1, temp.length());

                StringTokenizer tokenizer2 = new StringTokenizer(temp, Config.DELIMITER);
                //number of possible attribute value

                possibleValue.insertLast("" + tokenizer2.countTokens());
                //read att value one by one
                while (tokenizer2.hasMoreTokens()) {
                    temp = tokenizer2.nextToken();
                    temp = temp.trim();
                    outFile.println("@attribute " + att_name + "=" + temp + " {0,1}");
                    attributeName.insertLast(temp);
                }

                outFile.flush();
            } else {
                //no attribute type
                System.err.println("Error data format - No attribute type information");
            }

            tokenizer = new StringTokenizer(scan.nextLine());
            temp = tokenizer.nextToken();
        }

        if (temp.equalsIgnoreCase(Config.DATA)) {
            outFile.println("@data");
            String outTemp;
            while (scan.hasNext()) {
                outTemp = "";
                tokenizer = new StringTokenizer(scan.nextLine(), Config.DELIMITER);
                int curIndex = 0;
                //iterate through all attribute
                for (int i = 0; i < possibleValue.length(); i++) {
                    //attribute value
                    temp = tokenizer.nextToken();
                    for (int j = 0; j < Integer.parseInt((String) possibleValue.getObject(i + 1)); j++) {
                        if (temp.equalsIgnoreCase((String) attributeName.getObject(curIndex + j + 1))) {
                            outTemp += "1,";
                        } else {
                            outTemp += "0,";
                        }
                    }
                    curIndex += Integer.parseInt((String) possibleValue.getObject(i + 1));
                }
                outTemp = outTemp.substring(0, outTemp.length() - 1);
                outFile.println(outTemp);
                outFile.flush();
            }
        } else {
            System.err.println("Error data format - No data information");
        }
        outFile.close();
        scan.close();
    }
    /*
    public static void nominal2binary(String inputFileName, String outputFileName) {
    try{
    BufferedReader  br = new BufferedReader(new FileReader(inputFileName));
    FileWriter      fw = new FileWriter(outputFileName, false);
    String line;
    while (true){
    line = br.readLine();
    if (line.startsWith(COMMENT)) continue;
    if (line.trim() == null || line.trim() == "") continue;
    if (line.startsWith(ATTRIBUTE)){
    StringTokenizer stoken = new StringTokenizer(line, DELIM2);
    stoken.nextToken(); //omit the first
    String attName = stoken.nextToken();
    
    }
    }
    
    
    }catch (Exception e){
    
    }
    
    
    }
    
    public static void nominal2numeric(String inputFileName, String outputFileName) {
    try {
    //input scanner
    Scanner scan = new Scanner(new File(inputFileName));
    //output writer
    PrintWriter outFile = new PrintWriter(outputFileName);
    
    //hold line by line input
    StringTokenizer tokenizer;
    String temp;
    //temporary var hold a string to be writen as output
    String outTemp;
    //list all possible value of each attribute
    //used to convert string data to integer
    //will be null if corresponding att is already a numeric
    List attValueList;
    
    tokenizer = new StringTokenizer(scan.nextLine());
    temp = tokenizer.nextToken();
    //if temp != @attribute
    if (!temp.equalsIgnoreCase(ATTRIBUTE)) {
    //error data format
    System.out.println("Error data format - No attribute information");
    }
    
    attValueList = new List();
    
    //iterate through all attribute
    while (temp.equalsIgnoreCase(ATTRIBUTE)) {
    outTemp = temp + " ";
    
    //grab attribute name
    if (tokenizer.hasMoreTokens()) {
    outTemp += tokenizer.nextToken();
    } else {
    //no attribute name
    System.out.println("Error data format - No attribute name information");
    }
    
    if (tokenizer.hasMoreTokens()) {
    //read attribute tpye
    temp = tokenizer.nextToken();
    
    //if att is not numeric (string or numeric_discrete)
    if (temp.equalsIgnoreCase(NUMERIC_CONTINUOUS_SYMBOL) || temp.equalsIgnoreCase(NUMERIC_DISCRETE_SYMBOL)) {
    outTemp += " " + temp + " ";
    outTemp += tokenizer.nextToken() + " ";
    attValueList.insertLast(null);
    } //if att type is numeric
    else {
    
    //treat att as numeric_discrete 
    outTemp += " numeric_discrete {";
    
    //grab possible value of att
    temp = tokenizer.nextToken("}");
    temp = temp.trim();
    temp = temp.substring(1, temp.length());
    
    StringTokenizer tokenizer2 = new StringTokenizer(temp, ",");
    //number of possible attribute value
    int count = 0;
    //list to hold all possible attribute value
    List tempList = new List();
    
    //read att value one by one
    while (tokenizer2.hasMoreTokens()) {
    count++;
    tempList.insertLast(tokenizer2.nextToken());
    }
    
    //write possible att value as integer
    for (int i = 1; i < count; i++) {
    outTemp += i + ",";
    }
    outTemp += count + "}";
    
    attValueList.insertLast(tempList);
    
    }
    
    outFile.println(outTemp);
    outFile.flush();
    } else {
    //no attribute type
    System.out.println("Error data format - No attribute type information");
    }
    
    tokenizer = new StringTokenizer(scan.nextLine());
    temp = tokenizer.nextToken();
    }
    
    //if temp equals @data
    if (temp.equalsIgnoreCase(DATA)) {
    outFile.println("@data");
    while (scan.hasNext()) {
    outTemp = "";
    tokenizer = new StringTokenizer(scan.nextLine(), ",");
    
    //iterate through all attribute
    for (int i = 0; i < attValueList.length(); i++) {
    //attribute value
    temp = tokenizer.nextToken();
    
    //if corresponding att is numeric
    if (attValueList.getObject(i + 1) == null) {
    outTemp += temp + ",";
    } else {
    //get list of all possible value
    List tempList = (List) attValueList.getObject(i + 1);
    
    //iterate through all possible value to look for the appropriate index
    for (int j = 1; j <= tempList.length(); j++) {
    if (temp.equalsIgnoreCase(((String) tempList.getObject(j)).trim())) {
    outTemp += j + ",";
    break;
    }
    }
    }
    }
    outTemp = outTemp.substring(0, outTemp.length() - 1);
    outFile.println(outTemp);
    outFile.flush();
    }
    } else {
    System.out.println("Error data format - No data information");
    }
    } catch (IOException e) {
    System.out.println(e.getMessage());
    }
    }
     */
}
