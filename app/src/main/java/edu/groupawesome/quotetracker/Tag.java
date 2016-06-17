package edu.groupawesome.quotetracker;

import java.util.*;

/**
 * Created by reed on 6/8/16.
 */
class Tag {

    private String mTagName;
    private Set<String> mKeywordsSet = new LinkedHashSet<String>();
    private static Set<Tag> sTagsSet = new LinkedHashSet<Tag>();

    // we don't want nameless Tags
    private Tag() {}

    /** Constructor: Name only, no keywords. */
    Tag(String newName) {
        assert(newName != null);
        mTagName = newName;
    }

    /** Constructor: Name and keywords (Collection of Strings). */
    Tag(String newName, Collection<String> newKeywords) {
        this(newName);
        assert(newKeywords != null);
        mKeywordsSet.addAll(newKeywords);
    }

    String getTagName() {
        assert(mTagName != null);
        return mTagName;
    }

    void changeTagName(String newTagName) {
        assert(newTagName != null);
        mTagName = newTagName;
    }

    boolean addKeyword(String newKeyword) {
        assert(newKeyword != null);
        assert(mKeywordsSet != null);
        return mKeywordsSet.add(newKeyword);
    }

    boolean addAllKeywords(String[] newKeywords) {
        assert(newKeywords != null);
        assert(mKeywordsSet != null);
        return Collections.addAll(mKeywordsSet, newKeywords);
    }

    boolean addAllKeywords(Collection<String> newKeywords) {
        assert(newKeywords != null);
        assert(mKeywordsSet != null);
        return mKeywordsSet.addAll(newKeywords);
    }

    boolean removeKeyword(String keyword) {
        assert(keyword != null);
        assert(mKeywordsSet != null);
        return mKeywordsSet.remove(keyword);
    }

    boolean hasKeyword(String keyword) {
        assert(keyword != null);
        assert(mKeywordsSet != null);
        return mKeywordsSet.contains(keyword);
    }

    /** Returns an unmodifiable copy of this tag's KeywordsSet. The Set can't be modified. Use this tag's object methods to modify the KeywordsSet. */
    Set<String> getKeywordsSet() {
        assert(mKeywordsSet != null);
        return Collections.unmodifiableSet(mKeywordsSet);
    }

    /** Clears all the keywords and add the new Collection of Keywords. */
    void setKeywordsSet(Collection<String> newKeywords) {
        // we don't want any null pointer exceptions here!
        assert(newKeywords != null);
        assert(mKeywordsSet != null);

        // clear the current Set if it isn't empty already
        if (!mKeywordsSet.isEmpty()) { mKeywordsSet.clear(); }

        // add all the keywords to the set
        mKeywordsSet.addAll(newKeywords);
    }

    /** Clears all the keywords and add the new Collection of Keywords. */
    void setKeywordsSet(String[] newKeywords) {
        // we don't want any null pointer exceptions here!
        assert(newKeywords != null);
        assert(mKeywordsSet != null);

        // clear the current Set if it isn't empty already
        if (!mKeywordsSet.isEmpty()) { mKeywordsSet.clear(); }

        // add all the keywords to the set
        Collections.addAll(mKeywordsSet, newKeywords);
    }

    //
    // Static methods for working on the TagsSet
    //

    /** Returns an unmodifiable copy of the TagsSet. The Set can't be modified but the Tags within it can be. Use Tag's static methods to modify the global TagsSet. */
    static Set<Tag> getTagsSet() {
        assert(sTagsSet != null);
        return Collections.unmodifiableSet(sTagsSet);
    }

    /** Clears the Set of Tags and adds a whole new Collection. */
    static void setTagsSet(Collection<Tag> newTagSet) {
        assert(newTagSet != null);
        assert(sTagsSet != null);

        // clear the current Set if it isn't empty already
        if (!sTagsSet.isEmpty()) { sTagsSet.clear(); }

        // add all the tags to the set
        sTagsSet.addAll(newTagSet);
    }

    /** Clears the Set of Tags and adds a whole new Collection. */
    static void setTagsSet(Tag[] newTagSet) {
        assert(newTagSet != null);
        assert(sTagsSet != null);

        // clear the current Set if it isn't empty already
        if (!sTagsSet.isEmpty()) { sTagsSet.clear(); }

        // add all the tags to the set
        Collections.addAll(sTagsSet, newTagSet);
    }

    static boolean addTagToTagsSet(Tag newTag) {
        assert(newTag != null);
        assert(sTagsSet != null);
        return sTagsSet.add(newTag);
    }

    static boolean addAllTags(Tag[] newTags) {
        assert(newTags != null);
        assert(sTagsSet != null);
        return Collections.addAll(sTagsSet, newTags);
    }

    static boolean addAllTags(Collection<Tag> newTags) {
        assert(newTags != null);
        assert(sTagsSet != null);
        return sTagsSet.addAll(newTags);
    }

    static boolean removeTagFromTagsSet(Tag tag) {
        assert(tag != null);
        assert(sTagsSet != null);
        return sTagsSet.remove(tag);
    }

}
