package org.example;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Extractor {
    public List<Chair> getChairs(String url) throws IOException {


        String role, name, email;
        List<Chair> chairs = new ArrayList<>();

        Connection connection = Jsoup.connect("https://www.ualberta.ca/computing-science/faculty-and-staff/index.html");
        Document document = connection.get();

        Elements Chairs = document.getElementsByTag("tr");
        for (Element Chair : Chairs) {

            Elements td0 = Chair.getElementsByAttribute("Style");
            Elements td1 = Chair.getElementsByTag("strong");
            Elements td2 = Chair.getElementsByAttribute("href");
            String Role = td0.text().trim();
            if (Role.contains("Chair")){

                String Name = td1.text().trim();
                String Email = td2.text().trim();
                Chair chair = new Chair(Name, Role, Email);

                chairs.add(chair);
            }
        }

        return chairs;
    }

    public List<ResearchLab> findResearchLabs(String url) throws IOException {

        List<ResearchLab> researchLabs = new ArrayList<>();

        Connection connection = Jsoup.connect("https://www.ualberta.ca/computing-science/research/index.html");
        Document document = connection.get();

        Elements Labs = document.getElementsByTag("a");


        for (Element Lab : Labs) {
            String LabName = Lab.text().trim();
            String LabLink = Lab.attr("abs:href");

            String Prefix = "https://www.ualberta.ca/computing-science/research/research-areas/";

            if (LabLink.startsWith(Prefix)) {

                ResearchLab researchLab = new ResearchLab(LabName, LabLink);

                researchLabs.add(researchLab);
            }
        }
        return researchLabs;
    }
}
