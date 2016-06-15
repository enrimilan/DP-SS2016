package org.tuwien.experiment;

import org.apache.commons.lang.math.NumberUtils;
import org.tuwien.experiment.entity.Volcano;

import java.io.*;
import java.util.ArrayList;

public class App {

    public static void main(String[] args) {
        if(args.length != 1) {
            throw new IllegalArgumentException("USAGE: experiment VOLCANOES_FILE");
        }
        ArrayList<Volcano> volcanoes = parseVolcanoes(args[0]);
    }

    public static ArrayList<Volcano> parseVolcanoes(String filename) {
        ArrayList<Volcano> volcanoes = new ArrayList<Volcano>();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF8"))) {
            StringBuilder sb = new StringBuilder();
            br.readLine();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                String volcanoData[] = line.split("\\s+");
                String name = "";
                Double latitude, longitude;
                int i;
                for(i=0; i<volcanoData.length; i++) {
                    if(NumberUtils.isNumber(volcanoData[i])){
                        i++;
                        break;
                    }
                    else if(!volcanoData[i].trim().isEmpty()){
                        name = name + volcanoData[i] + " ";
                    }
                }
                latitude = Double.parseDouble(volcanoData[i++]);
                longitude = Double.parseDouble(volcanoData[i]);
                volcanoes.add(new Volcano(name, latitude, longitude));
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return volcanoes;
    }
}