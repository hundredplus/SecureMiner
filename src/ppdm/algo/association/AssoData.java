package ppdm.algo.association;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.StringTokenizer;
import ppdm.core.Config;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Oct 1, 2008
 */
public class AssoData {

    /**
     * 
     * @param filename
     * @return the <ArrayList<ItemSet>> that contains the attribute names along with private data.
     */
    public static ArrayList<ItemSet> readNumericData(String filename) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        ArrayList<ItemSet> al = new ArrayList();
        String line = br.readLine();
        /** attribute */
        while (line.contains(Config.ATTRIBUTE)) {
            StringTokenizer stoken = new StringTokenizer(line, Config.DELIMITER);
            stoken.nextToken();
            al.add(new ItemSet(stoken.nextToken()));
            line = br.readLine();
        }
        /** data */
        if (line.contains(Config.DATA)) {
            while (br.ready()) {
                line = br.readLine();
                StringTokenizer stoken = new StringTokenizer(line, Config.DELIMITER);
                for (int i = 0; i < al.size(); i++) {
                    al.get(i).values.add(new BigInteger(stoken.nextToken()));
                }
            }
        }

        br.close();
        return al;
    }
    
    public static ArrayList<ItemSet> readNominalData(String filename) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        ArrayList<ItemSet> al = new ArrayList();
        String line = br.readLine();
        /** attribute */
        while (line.contains(Config.ATTRIBUTE)||line.contains(Config.ATTRIBUTE.toUpperCase())) {
            StringTokenizer stoken = new StringTokenizer(line, Config.DELIMITER);
            stoken.nextToken();
            al.add(new ItemSet(stoken.nextToken()));
            line = br.readLine();
        }
        /** data */
        if (line.contains(Config.DATA)||line.contains(Config.DATA.toUpperCase())) {
            while (br.ready()) {
                line = br.readLine();
                StringTokenizer stoken = new StringTokenizer(line, Config.DELIMITER);
                for (int i = 0; i < al.size(); i++) {
                    al.get(i).strVals.add(stoken.nextToken());
                }
            }
        }
        br.close();
        return al;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        ArrayList<ItemSet> tl = AssoData.readNumericData("D:\\Data\\Party1.txt");
        System.out.println("OK");
    }
}
