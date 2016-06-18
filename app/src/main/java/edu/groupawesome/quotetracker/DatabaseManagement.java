package edu.groupawesome.quotetracker;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by JL5372 on 6/8/2016.
 *
 * will add better doc later
 */
public class DatabaseManagement {

    public boolean addQuoteToDB(Quote quote) {

        //test code for stub
        return true ;
    }

    public boolean addQuoteToDB(Integer aID, Quote quote) {

        //test code for stub
        return true ;
    }

    public Quote retrieveQuoteFromDB(Integer QuoteID){


        //test code for stub
        Quote returnQuote = new Quote("Test Author", null, null, null);
        returnQuote.setID(QuoteID);
        returnQuote.setAuthor("Test Author");
        Tag tag1 = new Tag("Test Tag");
        Set<Tag> sTag1 = new HashSet<>();
        sTag1.add(tag1);
        returnQuote.setTags(sTag1);
        returnQuote.setTitle("Test Title");
        returnQuote.setQuoteText("This is the test quote");
        return returnQuote;
    }

    public List<Quote> retrieveQuotesWithTag(Tag tag){


        //test code for stub
        Quote returnQuote1 = new Quote("Test Author", null, null, null);

        returnQuote1.setID(1);
        returnQuote1.setAuthor("Test Author1");
        Tag tag1 = new Tag("Test Tag1");
        Set<Tag> sTag1 = new HashSet<>();
        sTag1.add(tag1);
        returnQuote1.setTags(sTag1);
        returnQuote1.setTitle("Test Title1");
        returnQuote1.setQuoteText("This is the test quote1");
        Quote returnQuote2 = new Quote("Test Author", null, null, null);
        returnQuote2.setID(2);
        returnQuote2.setAuthor("Test Author2");
        Tag tag2 = new Tag("Test Tag2");
        Set<Tag> sTag2 = new HashSet<>();
        sTag2.add(tag2);
        returnQuote2.setTags(sTag2);
        returnQuote2.setTitle("Test Title2");
        returnQuote2.setQuoteText("This is the test quote2");
        Quote returnQuote3 = new Quote("Test Author", null, null, null);
        returnQuote3.setID(3);
        returnQuote3.setAuthor("Test Author3");
        Tag tag3 = new Tag("Test Tag3");
        Set<Tag> sTag3 = new HashSet<>();
        sTag3.add(tag3);
        returnQuote3.setTags(sTag3);
        returnQuote3.setTitle("Test Title3");
        returnQuote3.setQuoteText("This is the test quote3");

        List<Quote> listOfQuotes = new ArrayList<>();
        listOfQuotes.add(returnQuote1);
        listOfQuotes.add(returnQuote2);
        listOfQuotes.add(returnQuote3);

        return listOfQuotes;
    }

    public List<Quote> retrieveAllQuotesFromDB(){

        //test code for stub
        Quote returnQuote = new Quote("Test Author", null, null, null);
        returnQuote.setID(1);
        returnQuote.setAuthor("Test Author1");
        Tag tag1 = new Tag("Test Tag1");
        Set<Tag> sTag1 = new HashSet<>();
        sTag1.add(tag1);
        returnQuote.setTags(sTag1);
        returnQuote.setTitle("Test Title1");
        returnQuote.setQuoteText("This is the test quote1");
        Quote returnQuote2 = new Quote("Test Author", null, null, null);
        returnQuote2.setID(2);
        returnQuote2.setAuthor("Test Author2");
        Tag tag2 = new Tag("Test Tag2");
        Set<Tag> sTag2 = new HashSet<>();
        sTag2.add(tag2);
        returnQuote2.setTags(sTag2);
        returnQuote2.setTitle("Test Title2");
        returnQuote2.setQuoteText("This is the test quote2");
        Quote returnQuote3 = new Quote("Test Author", null, null, null);
        returnQuote3.setID(3);
        returnQuote3.setAuthor("Test Author3");
        Tag tag3 = new Tag("Test Tag3");
        Set<Tag> sTag3 = new HashSet<>();
        sTag3.add(tag3);
        returnQuote3.setTags(sTag3);
        returnQuote3.setTitle("Test Title3");
        returnQuote3.setQuoteText("This is the test quote3");
        Quote returnQuote4 = new Quote("Test Author", null, null, null);
        returnQuote4.setID(4);
        returnQuote4.setAuthor("Test Author4");
        Tag tag4 = new Tag("Test Tag4");
        tag4.addKeyword("testkeyword1");
        tag4.addKeyword("testkeyword2");
        Set<Tag> sTag4 = new HashSet<>();
        sTag4.add(tag4);
        returnQuote4.setTags(sTag4);
        returnQuote4.setTitle("Test Title4");
        returnQuote4.setQuoteText("This is the test quote4");

        List<Quote> listOfQuotes = new ArrayList<>();
        listOfQuotes.add(returnQuote);
        listOfQuotes.add(returnQuote2);
        listOfQuotes.add(returnQuote3);
        listOfQuotes.add(returnQuote4);
        return listOfQuotes;
    }

    public boolean removeQuoteFromDB(Integer QuoteID){

        //test code for stub
        return true;
    }

    public boolean addTagToDB(Tag tag){

        //test code for stub
        return true;
    }

    public boolean updateTagInDB(Tag tag){

        //test code for stub
        return true;
    }

    public Tag retrieveTagFromDB(Integer tID) {

        //test code for stub
        Tag tag = new Tag("change me");
        tag.changeTagName("TestTag2");
        tag.addKeyword("TestKeyWord1");
        tag.addKeyword("TestKeyWord2");
        return tag;
    }

    public List<Tag> retrieveTagsWithKeyword(String keyWord){

        //test code for stub
        Tag tag1 = new Tag("KeywordSearchCheck1");
        tag1.addKeyword("TestKeyWord1");
        tag1.addKeyword("TestKeyWord2");
        Tag tag2 = new Tag("KeywordSearchCheck2");
        tag2.addKeyword("TestKeyWord3");
        tag2.addKeyword("TestKeyWord4");
        Tag tag3 = new Tag("KeywordSearchCheck3");
        tag3.addKeyword("TestKeyWord5");
        tag3.addKeyword("TestKeyWord6");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(tag1);
        tagList.add(tag2);
        tagList.add(tag3);
        return tagList;
    }

    public List<Tag> retrieveAllTagsFromDB(){



        //test code for stub
        Tag tag1 = new Tag("KeywordSearchCheck1");
        tag1.addKeyword("TestKeyWord1");
        tag1.addKeyword("TestKeyWord2");
        Tag tag2 = new Tag("KeywordSearchCheck2");
        tag2.addKeyword("TestKeyWord3");
        tag2.addKeyword("TestKeyWord4");
        Tag tag3 = new Tag("KeywordSearchCheck3");
        tag3.addKeyword("TestKeyWord5");
        tag3.addKeyword("TestKeyWord6");
        Tag tag4 = new Tag("KeywordSearchCheck4");
        tag4.addKeyword("TestKeyWord7");
        tag4.addKeyword("TestKeyWord8");

        List<Tag> tagList = new ArrayList<>();
        tagList.add(tag1);
        tagList.add(tag2);
        tagList.add(tag3);
        tagList.add(tag4);
        return tagList;
    }

    public boolean removeTagFromDB(Integer TagID){

        return true;
    }

}