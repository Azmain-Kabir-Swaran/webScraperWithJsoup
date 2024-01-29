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

        Connection connection = Jsoup.connect(url);
        Document document = connection.get();

        Elements rows = document.getElementsByTag("tr");
        for (Element row : rows) {

            Elements td0 = row.getElementsByAttribute("Style");
            Elements td1 = row.getElementsByTag("strong");
            Elements td2 = row.getElementsByAttribute("href");
            role = td0.text().trim();
            if (role.contains("Chair")){

                name = td1.text().trim();
                email = td2.text().trim();
                Chair chair = new Chair(name, role, email);

                chairs.add(chair);
            }
        }

        return chairs;
    }

    public List<ResearchLab> findResearchLabs(String url) throws IOException {

        List<ResearchLab> researchLabs = new ArrayList<>();

        Connection connection = Jsoup.connect(url);
        Document document = connection.get();

        Elements Labs = document.getElementsByTag("a");


        for (Element Lab : Labs) {
            String labName = Lab.text().trim();
            String labLink = Lab.attr("abs:href");

            String Prefix = "https://www.ualberta.ca/computing-science/research/research-areas/";

            if (labLink.startsWith(Prefix) && !labLink.contains("index")) {

                ResearchLab researchLab = new ResearchLab(labName, labLink);

                researchLabs.add(researchLab);
            }
        }
        return researchLabs;
    }
}
