package com.app.io;

import com.app.adapters.Config;
import com.app.instruments.Query;
import java.io.FileReader;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ParserUtil {

    public static Config readDatabaseConfiguration(String path) {
        
        Config conf = null;
        
        try {
            JSONParser parser = new JSONParser();
            
            JSONObject dbConfig = (JSONObject) parser.parse(new FileReader(path));
            String host = (String) dbConfig.get("host");
            long port = (Long) dbConfig.get("port");
            String db = (String) dbConfig.get("db");
            String username = (String) dbConfig.get("username");
            String password = (String) dbConfig.get("password");
            String dbms = (String) dbConfig.get("dbms");
                    
            conf = new Config(host,port,db,username,password,dbms);
            
        } catch (IOException | ParseException ex) {
            Logger.getLogger(ParserUtil.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
        return conf;
        
    }
    
    public static ArrayList<Query> decompose(String path) {
        try {
            
            ArrayList<Query> queries = new ArrayList<>();
            
            List<String> lines = Files.readAllLines(Paths.get(path));
            
            Query query;
            Pattern p;
            Matcher m;
            
            p = Pattern.compile("(Q[0-9]+)\\s*\\|\\s*([A-Za-z0-9\\'\\:\\@\\s\\,\\]\\[\\/\\+\\=\\>\\<\\.\\(\\)\\*\\_\\-\\;]+)(\\s*\\|\\s*\\[([A-Za-z\\=\\;\\_\\s]+)\\])?");
            
            for (String str : lines) {
                
                if (!str.startsWith("--")) {
                    m = p.matcher(str);
                    
                    if (m.matches()) {
                        if (m.group(4) != null) {
                            ArrayList<String> options = new ArrayList<>(Arrays.asList(m.group(4).split(";")));
                            query = new Query(m.group(1), m.group(2), options);
                        } else {
                            query = new Query(m.group(1), m.group(2), new ArrayList<>());
                        }
                        
                        queries.add(query);
                    }
                }
            }
            return queries;
        } catch (IOException ex) {
            Logger.getLogger(ParserUtil.class.getName()).log(Level.SEVERE, ex.getMessage());
            return new ArrayList<>();
        }
    }
}
