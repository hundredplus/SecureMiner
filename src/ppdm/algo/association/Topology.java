package ppdm.algo.association;

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
 * @date Oct 7, 2008
 */
public class Topology {

    public static ArrayList<PartyID> readTopology(String filename) throws FileNotFoundException, IOException {
        ArrayList<PartyID> al = new ArrayList<PartyID>();
        PartyID pid = new PartyID();

        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        int num_party = 0;
        line = br.readLine();
        while (line.startsWith("#")) {
            line = br.readLine();
        }
        if (line.startsWith("@NumParty")) {
            num_party = Integer.parseInt(br.readLine().trim());
        }

        if (br.readLine().startsWith("@party") || br.readLine().startsWith("@Party")) {
            for (int i = 0; i < num_party; i++) {
                pid = new PartyID();
                StringTokenizer token = new StringTokenizer(br.readLine().trim(), Config.DELIMITER);
                token.nextToken(); //omit the ID
                pid.ID = i;
                pid.IP = token.nextToken().trim();//IP
                pid.port = Integer.parseInt(token.nextToken().trim());//port
                int num_att = Integer.parseInt(token.nextToken().trim());
                for (int j = 0; j < num_att; j++) {
                    token = new StringTokenizer(br.readLine().trim(), Config.DELIMITER);
                    token.nextToken();//omit the @attribute
                    pid.attribute.add(token.nextToken().trim());
                }
                al.add(pid);
            }
        }
        br.close();
        return al;
    }
}
