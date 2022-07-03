package com.dbs.web;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Authored by malith.hirantha@gmail.com on 3/7/2022.
 */
public class Crawler {
    public static void main(String[] args) {
        String url = "https://en.wikipedia.org/";

        crawl(1, url, new ArrayList<String>());


    }

    private static void crawl(int level, String url, ArrayList<String> visited) {
        if (level <= 5) {
            Document document = request(url, visited);
            if (document != null) {
                for (Element link : document.select("a[href]")) {
                    String next_link = link.absUrl("href");
                    if(visited.contains(next_link) == false){
                        crawl(level++, next_link, visited);
                    }
                }
            }

        }
    }

    private static Document request(String url, ArrayList<String> v) {

        try {
            Connection connection = Jsoup.connect(url);
            Document document = connection.get();
            if (connection.response().statusCode() == 200) {
                System.out.println("Link: " + url);
                System.out.println(document.title());
                v.add(url);
                return document;
            }
            return null;
        } catch (IOException ex) {
            return null;
        }
    }
}
