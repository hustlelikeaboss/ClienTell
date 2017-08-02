package design.hustlelikeaboss.customr.utils;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.Page;
import design.hustlelikeaboss.customr.models.Customer;
import design.hustlelikeaboss.customr.models.data.CustomerDao;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;


/**
 * Created by quanjin on 7/23/17.
 */
@Component
public class ProfileEnricher {

    @Autowired
    private CustomerDao customerDao;

//
//  helper method #1: use web scraper to Google customer facebook page
//
    public void fetchFacebookPage(Customer customer) throws UnsupportedEncodingException, MalformedURLException {
        String company = customer.getCompany();

        if (company != null) {
            // get search term
            String[] myList = company.split(" ");
            String searchTerm = "";
            for (String s : myList) {
                searchTerm += s + "+";
            }

            // create search query
            String googleSearchQuery = "https://www.google.com/search?q=" + searchTerm;

            // parse html
            Document doc = null;
            try {
                String url = googleSearchQuery;
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Element facebook = doc.select("a[href^=\"https://www.facebook.com/\"]").first();
            if (facebook != null) {
                String url = facebook.attr("href");
                customer.setFacebook(url);
                customerDao.save(customer);
            }
        }
    }

//
// helper method #2: fetch info from facebook page
//
    public void parseFacebookPage(Customer customer) {

        String facebook = customer.getFacebook();

        if (StringUtils.isNotEmpty(facebook)) {
            String substring = facebook.substring(25);
            int endOfPageId = substring.indexOf("/");
            String facebookPageId;
            if (endOfPageId != -1) { // testing for cases where the truncated string doesn't contain a "/"
                facebookPageId = substring.substring(0, endOfPageId);
            } else {
                facebookPageId = substring;
            }

            FacebookClient facebookClient = new DefaultFacebookClient("432686667131111|85SU-aeRiIiNnL9aNcLTiE8EyTE",
                    "a9310c79b05f3d87ba86809325ebf424", Version.VERSION_2_8);
            Page page = facebookClient.fetchObject(facebookPageId, Page.class,
                    Parameter.with("fields", "website, phone, location") );

            if (page != null) {

                customer.setWebsite(page.getWebsite());

                if (page.getLocation() != null) {
                    customer.setStreet(page.getLocation().getStreet());
                    customer.setCity(page.getLocation().getCity());
                    customer.setState(page.getLocation().getState());
                    customer.setZip(page.getLocation().getZip());
                }

                customer.setPhoneNumber(page.getPhone());

                customerDao.save(customer);
            }
        }
    }



}
