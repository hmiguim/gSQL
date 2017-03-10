package com.app.gsql;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;

public class Parser {

    public static ArrayList<Query> decompose(String path) throws IOException {

        ArrayList<Query> parsed = new ArrayList<>();

        List<String> lines = Files.readAllLines(Paths.get(path));

        Query query;
        Pattern p;
        Matcher m;

        p = Pattern.compile("([A-Za-z0-9\\'\\s\\,\\=\\>\\<\\.\\(\\)\\*\\_\\-]+)(\\|)(\\[)([A-Za-z\\s\\;\\_\\=]*)(\\])");

        for (String s : lines) {

            if (!s.startsWith("#")) {

                m = p.matcher(s);

                if (m.matches()) {
                    if (m.group(4).length() > 0) {
                        ArrayList<String> opts = new ArrayList<>(Arrays.asList(m.group(4).split(";")));
                        query = new Query(m.group(1), opts);
                    } else {
                        query = new Query(m.group(1), new ArrayList<>());
                    }
                    parsed.add(query);
                }
            }
        }

        return parsed;
    }
}
