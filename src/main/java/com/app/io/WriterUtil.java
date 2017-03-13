package com.app.io;

import com.app.instruments.Query;
import java.io.FileWriter;
import java.io.Writer;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WriterUtil {

    private static final char DEFAULT_SEPARATOR = ',';
    private ArrayList<Query> queries;
    
    public WriterUtil() { }
    
    public void setQueries(ArrayList<Query> queries) {
        this.queries = queries;
    }
    
    public void write(String input_path) {
         
        StringBuilder out = new StringBuilder(input_path);
        
        out.append("_results.csv");
        
        try (FileWriter writer = new FileWriter(out.toString())) {
            writeLine(writer, Arrays.asList("query,joules_avg,joules_std,joules_min,joules_max,watts_avg,watts_std,watts_min,watts_max,time_avg,time_std,time_min,time_max"));
            
            queries.forEach((Query query) -> {
                try {
                    writeLine(writer,Arrays.asList(query.getResults().split(",")));
                } catch (IOException ex) {
                    Logger.getLogger(WriterUtil.class.getName()).log(Level.SEVERE, ex.getMessage());
                }
            });
            
            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(WriterUtil.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
    }
    
    private void writeLine(Writer w, List<String> values) throws IOException {
        this.writeLine(w, values, DEFAULT_SEPARATOR, ' ');
    }

    private String followCVSformat(String value) {

        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;

    }

    private void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {

        boolean first = true;
        
        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first) {
                sb.append(separators);
            }
            if (customQuote == ' ') {
                sb.append(followCVSformat(value));
            } else {
                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
            }

            first = false;
        }
        sb.append("\n");
        w.append(sb.toString());

    }
}
