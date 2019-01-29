
package ppdm.algo.classification;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Nov 15, 2008
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import ppdm.core.Config;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Oct 1, 2008
 */
public class DataReader {

    /**
     * 
     * @param filename
     * @return the <ArrayList<String[]>> that contains the attribute names along with private data.
     */
    public static Id3Data readData(String filename) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        Id3Data id3Data = new Id3Data();
        
        //ArrayList<String[]> allAtt = new ArrayList();
        //reserve first possition for No_Of_Att
        //String[] no_of_att = new String[1];
        //al.add(no_of_att);
        
        String line = br.readLine();
        /** attribute */
        int num_of_attribute = 0;
        while (line.contains(Config.ATTRIBUTE)) {
            ArrayList<String> oneAtt = new ArrayList<String>();
            num_of_attribute++;
            StringTokenizer stoken = new StringTokenizer(line, Config.DELIMITER);
            stoken.nextToken();//omit first @attribute
            while (stoken.hasMoreTokens()){
                oneAtt.add(stoken.nextToken()); // first: att_name, then possible values
            }
            
            //convert to array and add to finall ArrayList            
            String[] str = new String[oneAtt.size()];
            for (int i=0; i<oneAtt.size();i++){
                str[i] = oneAtt.get(i);
            }
            id3Data.att.add(str);            
            
            line = br.readLine();
        }
        
        id3Data.noOfAtt = num_of_attribute;        
        
        String[] tmp;
        /** data */
        if (line.contains(Config.DATA)) {
            while (br.ready()) {
                line = br.readLine();
                tmp = new String[num_of_attribute];
                StringTokenizer stoken = new StringTokenizer(line, Config.DELIMITER);
                for (int i = 0; i < num_of_attribute; i++) {
                    tmp[i] = stoken.nextToken().trim();
                }
                id3Data.data.add(tmp);
            }
        }        
        
        br.close();
        return id3Data;
    }
    /*
    public static ArrayList<ItemSet> readNominalData(String filename) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        ArrayList<ItemSet> al = new ArrayList();
        String line = br.readLine();
        while (line.contains(Config.ATTRIBUTE)||line.contains(Config.ATTRIBUTE.toUpperCase())) {
            StringTokenizer stoken = new StringTokenizer(line, Config.DELIMITER);
            stoken.nextToken();
            al.add(new ItemSet(stoken.nextToken()));
            line = br.readLine();
        }
        if (line.contains(Config.DATA)||line.contains(Config.DATA.toUpperCase())) {
            while (br.ready()) {
                line = br.readLine();
                StringTokenizer stoken = new StringTokenizer(line, Config.DELIMITER);
                for (int i = 0; i < al.size(); i++) {
//                    al.get(i).strVals.add(stoken.nextToken());
                }
            }
        }
        br.close();
        return al;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        ArrayList<ItemSet> tl = DataReader.readBinaryData("D:\\DataReader\\Party1.txt");
        System.out.println("OK");
    }
    */
}
