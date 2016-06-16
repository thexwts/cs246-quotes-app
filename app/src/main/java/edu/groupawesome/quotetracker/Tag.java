package edu.groupawesome.quotetracker;

import java.util.*;

/**
 * Created by reed on 6/8/16.
 */
public class Tag {

    private String mTagName;
    private Set<String> mKeywordsSet = new HashSet<String>();
    private static Set<Tag> sTagSet = new HashSet<Tag>();

    // we don't want nameless Tags
    private Tag() {}

    /** Constructor: Name only, no keywords. */
    public Tag(String newName) {
        assert (newName != null);
        mTagName = newName;
    }

    /** Constructor: Name and keywords (Collection of Strings). */
    public Tag(String newName, Collection<String> newKeywords) {
        this(newName);
        assert (newKeywords != null);
        mKeywordsSet.addAll(newKeywords);
    }

    public String getTagName() {
        assert (mTagName != null);
        return mTagName;
    }

    public void changeTagName(String newTagName) {
        assert (newTagName != null);
        this.mTagName = newTagName;
    }

    public boolean addKeyword(String newKeyword) {
        assert (newKeyword != null);
        assert (mKeywordsSet != null);
        return mKeywordsSet.add(newKeyword);
    }

    public boolean addAllKeywords(String[] newKeywords) {
        assert (newKeywords != null);
        assert (mKeywordsSet != null);
        return Collections.addAll(mKeywordsSet, newKeywords);
    }

    public boolean addAllKeywords(Collection<String> newKeywords) {
        assert (newKeywords != null);
        assert (mKeywordsSet != null);
        return mKeywordsSet.addAll(newKeywords);
    }

    public boolean removeKeyword(String keyword) {
        assert (keyword != null);
        assert (mKeywordsSet != null);
        return mKeywordsSet.remove(keyword);
    }

    public boolean hasKeyword(String keyword) {
        assert (keyword != null);
        assert (mKeywordsSet != null);
        return mKeywordsSet.contains(keyword);
    }

    // TODO: test this unmodifiable Set - I don't want someone changing mKeywordsSet externally
    public Set<String> getKeywordsSet() {
        assert (mKeywordsSet != null);
        return Collections.unmodifiableSet(mKeywordsSet);
    }

    /** Clears all the keywords and add the new Collection of Keywords*/
    public void setKeywordsSet(Collection<String> keywords) {
        // we don't want any null pointer exceptions here!
        assert (keywords != null);
        assert (mKeywordsSet != null);

        // clear the current Set if it isn't empty already
        if (!sTagSet.isEmpty()) { this.mKeywordsSet.clear(); }

        // add all the keywords to the set
        this.mKeywordsSet.addAll(mKeywordsSet);
    }

    // TODO: test this unmodifiable Set - I don't want someone changing sTagsSet externally
    public static Set<Tag> getTagSet() {
        assert (sTagSet != null);
        return Collections.unmodifiableSet(sTagSet);
    }

    /** Clears the Set of Tags and adds a whole new Collection. */
    public static void setTagSet(Collection<Tag> tagSet) {
        assert (tagSet != null);
        assert (sTagSet != null);

        // clear the current Set if it isn't empty already
        if (!sTagSet.isEmpty()) { sTagSet.clear(); }

        // add all the tags to the set
        Tag.sTagSet.addAll(tagSet);
    }

    public static boolean addTagToTagsSet(Tag newTag) {
        assert (newTag != null);
        assert (sTagSet != null);
        return sTagSet.add(newTag);
    }

    public static boolean removeTagFromTagsSet(Tag tag) {
        assert (tag != null);
        assert (sTagSet != null);
        return sTagSet.remove(tag);
    }

}
