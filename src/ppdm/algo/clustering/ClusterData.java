
package ppdm.algo.clustering;

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
public class ClusterData {

    /**
     * 
     * @param filename
     * @return ArrayList<String[]>: the first element is the list of attribute name followed by the data.
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static ArrayList<String[]> readData(String filename) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        ArrayList<String[]> al = new ArrayList();
        
        ArrayList<String> att = new ArrayList<String>();
        String line = br.readLine();
        /** attribute */
        int num_of_attribute = 0;
        while (line.contains(Config.ATTRIBUTE)) {
            num_of_attribute++;
            StringTokenizer stoken = new StringTokenizer(line, Config.DELIMITER);
            stoken.nextToken();//omit first
            att.add(stoken.nextToken());
            line = br.readLine();
        }
        String[] str = new String[num_of_attribute];
        for (int i=0; i<num_of_attribute;i++){
            str[i] = att.get(i);
        }
        al.add(str);
        
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
                al.add(tmp);
            }
        }
        br.close();
        return al;
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
        ArrayList<ItemSet> tl = ClusterData.readBinaryData("D:\\ClusterData\\Party1.txt");
        System.out.println("OK");
    }
    */
}
