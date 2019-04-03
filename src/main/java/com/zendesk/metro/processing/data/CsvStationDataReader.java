package com.zendesk.metro.processing.data;

import com.zendesk.metro.processing.contracts.IStationDataReader;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.in;

/** Class reads csv files StationMap.csv from class path */
public class CsvStationDataReader implements IStationDataReader {
    private static final String DEFAULT_SEPARATOR = ",";

    @Override
    public Iterable<InfoTuple> readData() throws IOException, ParseException {
        List<InfoTuple> infoTuples = new ArrayList<>();

        String filePath = "StationMap.csv";
        InputStream is = new ClassPathResource(filePath).getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        // Skipping first line
        String line = br.readLine();
        String[] parts;
        while (line != null) {
            line = br.readLine();
            if (line == null) {
                break;
            }
            parts = line.split(DEFAULT_SEPARATOR);
            assert parts.length == 3;
            Pattern p = Pattern.compile("([A-Z]+)([0-9]+)");
            Matcher m = p.matcher(parts[0].trim().toUpperCase());
            if (!m.find()) {
                // handle bad string
            }
            String code = m.group(1);
            int num = Integer.parseInt(m.group(2));
            infoTuples.add(
                    new InfoTuple(code, num, parts[1].trim(), getCalendarDate(parts[2].trim())));
        }
        in.close();
        return infoTuples;
    }

    private Calendar getCalendarDate(String dateString) throws ParseException {
        String pattern = "dd MMMM yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = simpleDateFormat.parse(dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}
