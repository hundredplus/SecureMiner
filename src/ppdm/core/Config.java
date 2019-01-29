package ppdm.core;

import java.io.*;
import java.util.Properties;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Sep 8, 2008
 */
public class Config {
    public Config() {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(new File(".\\src\\ppdm\\core\\config.ini")));
            MISS_VALUE = props.getProperty("miss_value");
            VECTOR_SIZE = Integer.parseInt(props.getProperty("vector_size"));
            ATTRIBUTE = props.getProperty("attribute");
            DATA = props.getProperty("data");
            
            CONFIG_SIGNAL = props.getProperty("config_signal");
            DATA_SIGNAL = props.getProperty("data_signal");
            END_SIGNAL = props.getProperty("end_signal");
            
            DEFAULT_MIN_SUP = Double.parseDouble(props.getProperty("default_min_sup"));
            DEFAULT_MAX_SUP = Double.parseDouble(props.getProperty("default_max_sup"));
            DEFAULT_MIN_CONFIDENCE = Double.parseDouble(props.getProperty("default_min_confidence"));
            DEFAULT_NUM_RULES = Integer.parseInt(props.getProperty("default_num_rules"));
            DEFAULT_RANK_BY = Integer.parseInt(props.getProperty("default_rank_by"));
            DEFAULT_VERBOSE = Integer.parseInt(props.getProperty("default_verbose"));
            
            DEFAULT_NUM_LOOP = Integer.parseInt(props.getProperty("default_num_loop"));
            DEFAULT_NUM_CLUSTER = Integer.parseInt(props.getProperty("default_num_cluster"));
            DEFAULT_NUM_DIGIT = Integer.parseInt(props.getProperty("default_num_digit"));

            LOGO_PATH = props.getProperty("logo_path");
            
        } catch (Exception e) {
            ErrorController.show(null, e);
        }
    }
    
    public static String DELIMITER = " {},\n\t";
    public static String SPACE = " ";
    public static String ENTER = "\n";
    public static String TAB = "\t";
    public static String DOUBLE_TAB = "\t\t";
    public static String TRIPLE_TAB = "\t\t\t";
    public static String MISS_VALUE;
    public static int VECTOR_SIZE;
    public static String ATTRIBUTE;
    public static String DATA; 
    
    public static String CONFIG_SIGNAL;
    public static String DATA_SIGNAL;
    public static String END_SIGNAL;
    
    public static double DEFAULT_MIN_SUP;
    public static double DEFAULT_MAX_SUP;
    public static double DEFAULT_MIN_CONFIDENCE;
    public static int DEFAULT_NUM_RULES;
    public static int DEFAULT_RANK_BY;
    public static int DEFAULT_VERBOSE;
    
    public static int DEFAULT_NUM_LOOP;
    public static int DEFAULT_NUM_CLUSTER;
    
    public static int DEFAULT_NUM_DIGIT;

    public static String LOGO_PATH;
    
    public static void main(String[] args){
        new Config();
    }
}
