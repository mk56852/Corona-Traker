package com.coronatrakerapp.coronavirus.traker.Service;


import com.coronatrakerapp.coronavirus.traker.domain.Location;
import lombok.Getter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@Getter
public class CoronaDataService {

    private static String DataURL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private List<Location> allStats = new ArrayList<>();

    @PostConstruct
    @Scheduled(cron = "* * * 1 * *")
    public void fetchData() throws IOException {
        HttpClient client = HttpClient.newHttpClient() ;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(DataURL)).build() ;
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Location> newStats = new ArrayList<>();
        StringReader csvBodyReader = new StringReader(response.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            Location location = new Location();
            location.setState(record.get("Province/State"));
            location.setCountry(record.get("Country/Region"));
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            location.setTodayCases(latestCases - prevDayCases);
            newStats.add(location);
        }
        this.allStats = newStats;


    }
}
