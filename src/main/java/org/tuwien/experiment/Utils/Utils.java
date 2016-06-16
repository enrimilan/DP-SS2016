package org.tuwien.experiment.utils;


import org.apache.commons.lang.math.NumberUtils;
import org.tuwien.experiment.entity.Earthquake;
import org.tuwien.experiment.entity.Volcano;

import java.io.*;
import java.util.ArrayList;

public class Utils {

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
            System.out.println("File " + filename + " doesn't exist");
        } catch (IOException e) {
            System.out.println("An error occurred");
        }

        return volcanoes;
    }

    public static ArrayList<Earthquake> parseEarthquakes(String filename) {
        ArrayList<Earthquake> earthquakes = new ArrayList<Earthquake>();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF8"))) {
            StringBuilder sb = new StringBuilder();
            br.readLine();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                String earthquakeData[] = line.split("\\s+");
                int size = earthquakeData.length;
                Double latitude = Double.parseDouble(earthquakeData[size-3]);
                Double longitude = Double.parseDouble(earthquakeData[size-2]);
                Double depth = Double.parseDouble(earthquakeData[size-1]);
                earthquakes.add(new Earthquake(latitude, longitude, depth));
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File " + filename + " doesn't exist");
        } catch (IOException e) {
            System.out.println("An error occurred");
        }

        return earthquakes;
    }

    public static double calculateDistanceBetween(double lat1, double lng1, double lat2, double lng2) {
        //thx to stackoverflow http://stackoverflow.com/questions/837872/calculate-distance-in-meters-when-you-know-longitude-and-latitude-in-java
        double earthRadius = 6371000;
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;

        return dist;
    }
}
