package edu.groupawesome.quotetracker;

import java.util.*;

/**
 * Created by reed on 6/8/16.
 */
public class Tag {

    private String mTagName;
    private Set<String> mKeywords = new HashSet<String>();
    private static List<Tag> sTagList = new ArrayList<Tag>();

    // we don't want nameless Tags
    private Tag() {}


    public Tag(String name) {
        assert (name != null);
        mTagName = name;
    }

    public Tag(String name, List<String> keywords) {
        this(name);
        assert (keywords != null);
        mKeywords.addAll(keywords);
    }


    public String getTagName() {
        return mTagName;
    }

    public void changeTagName(String mTagName) {
        assert (mTagName != null);
        this.mTagName = mTagName;
    }

//    public List<String> getKeywords() {
//        return mKeywords;
//    }
//
//    public void setmKeywords(List<String> mKeywords) {
//        assert (mKeywords != null);
//        this.mKeywords = mKeywords;
//    }

    public boolean addKeyword(String keyword) {
        return mKeywords.add(keyword);
    }

    public boolean addAllKeywords(String[] keywords) {
        return Collections.addAll(mKeywords, keywords);
    }

    public boolean addAllKeywords(Set<String> keywords) {
        return mKeywords.addAll(keywords);
    }

    public boolean removeKeyword(String keyword) {
        return mKeywords.remove(keyword);
    }

    public boolean hasKeyword(String keyword) {
        return mKeywords.contains(keyword);
    }

    public static List<Tag> getTagList() {
        return sTagList;
    }

    public static void setTagList(List<Tag> sTagList) {
        Tag.sTagList = sTagList;
    }

    public static boolean addTagToTagsList(Tag newTag) {
        return sTagList.add(newTag);
    }

}
