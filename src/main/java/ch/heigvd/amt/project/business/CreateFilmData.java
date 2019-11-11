package ch.heigvd.amt.project.business;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreateFilmData {

    private static String[] pathsToPosters = new String[]{"the_butler.jpg", "fight_club.jpg", "joker.jpg", "scarface.jpg", "forest_gump.jpg",
            "spider_man.jpg", "star_wars.jpg", "the_lion_king.jpg", "you've_got_mail.jpg"};
    private static String[] directors = new String[]{"Brian de Palma", "Coppola", "Spielberg", "Fincher"};
    private static final Random random = new Random();

    public static void main(String[] args) {
        List<String[]> dataLines = createData();

        File csvOutputFile = new File("docker/images_docker/mysql/dump/films_generated.csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                    .map(CreateFilmData::convertToCSV)
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(CreateFilmData::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    private static String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    private static List<String[]> createData() {
        List<String[]> dataLines = new ArrayList<>();
        dataLines.add(new String[]{
                "ID", "TITLE", "RUNNING_TIME", "PATH_TO_POSTER", "DIRECTOR"
        });
        int counter = 20;

        for(int i = 0; i < 1000000; i++) {
            dataLines.add(new String[]
                    {String.valueOf(counter), "film" + random.nextInt(10000), String.valueOf(random.nextInt(200)),
                            pathsToPosters[random.nextInt(9)], directors[random.nextInt(4)] });
            counter ++;
        }
        return dataLines;
    }
}
