package com.saad.chatserver.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.saad.chatserver.model.User;
import com.saad.chatserver.ref;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class CsvFiles {

    static final String USERS_FILE = "src/main/resources/db/users.csv";

    private static final String USERS_HEADER = "username,password,noc";

    private static final String CONVERSATION_HEADER = "username,content,date";

    private static final String STATS_HEADER = "word,occurrences";

    public static void prepareUsersFile() {
        prepareAFile(USERS_FILE, USERS_HEADER);
    }

    public static String prepareConversationFile(User user) {
        final String conversationFolder = String.format(ref.ConversationPath, user.getUsername(), LocalDateTime.now().toString().replaceAll(":", "-"));
        System.out.println(conversationFolder);
        File file = new File(conversationFolder);
        file.mkdirs();
        prepareAFile(conversationFolder.concat("conversation.csv"), CONVERSATION_HEADER);
        return conversationFolder;
    }

    private static void prepareAFile(String fileName, String fileHeader) {
        File f = new File(fileName);
        try {
            if (!f.exists()) {
                if (f.createNewFile()) {
                    writeToFileAsNew(f, fileHeader);
                }
            } else {
                Scanner myReader = new Scanner(f);
                String headerLine;
                if (myReader.hasNextLine()) {
                    headerLine = myReader.nextLine();
                    if (!headerLine.equals(fileHeader)) {
                        writeToFileAsNew(f, fileHeader);
                    }
                } else {
                    writeToFileAsNew(f, fileHeader);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToFileAsNew(File file, String header) throws IOException {
        FileWriter myWriter = new FileWriter(file);
        myWriter.write(header);
        myWriter.append("\n");
        myWriter.close();
    }

    public static void dumpStatsFile(String conversationFolder, String conversationFile) throws IOException, CsvException {
        // Create an object of filereader class
        // with CSV file as a parameter.
        FileReader filereader = new FileReader(conversationFolder.concat(conversationFile));

        // create csvReader object
        // and skip first Line
        CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
        List<String[]> allData = csvReader.readAll();
        filereader.close();

        Map<String, Integer> wordMap = new HashMap<>();

        for (String[] row : allData) {
            final String[] messageWords = row[1].split(" ");
            for (String w : messageWords) {
                if (wordMap.containsKey(w)) {
                    //Existing Word
                    wordMap.put(w, wordMap.get(w) + 1);
                } else {
                    //New Word
                    wordMap.put(w, 1);
                }
            }
        }

        String statsFile = conversationFolder.concat("stats.csv");
        writeToFileAsNew(new File(statsFile), STATS_HEADER);

        //SORT THE MAP
        Map<String, Integer> sortedByCount = wordMap.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        //DUMP SORTED STATS INTO FILE
        try {
            FileWriter fileWriter = new FileWriter(statsFile, true);

            Iterator it = sortedByCount.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                fileWriter.write(pair.getKey() + "," + pair.getValue());
                fileWriter.append("\n");
                it.remove(); // avoids a ConcurrentModificationException
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
