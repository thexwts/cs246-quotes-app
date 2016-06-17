package edu.groupawesome.quotetracker;

/**
 * Created by reed on 6/8/16.
 */

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class TagTest {

    @Test
    public void tagsList_addTag () throws Exception {

        Tag tag = new Tag("Test");

        assertTrue(Tag.addTagToTagsSet(tag));
    }

    @Test
    public void tag_changeName () throws Exception {

        Tag tag = new Tag("Test");

        assertEquals(tag.getTagName(), "Test");

        tag.changeTagName("New name");

        assertEquals(tag.getTagName(), "New name");
    }


    @Test
    public void tagsSet_addSet () throws Exception {
        // create a Set of Tags
        Set<Tag> tags = new LinkedHashSet<Tag>();
        tags.add(new Tag("Commandments"));
        tags.add(new Tag("Tithing"));
        tags.add(new Tag("Sacrifice"));
        tags.add(new Tag("Word of Wisdom"));
        tags.add(new Tag("Faith"));

        // since we don't have a clear function for the TagsSet (on purpose) we need to 'clear' it by
        // setting it to an empty Set
        Tag.setTagsSet(new LinkedHashSet<Tag>());

        // add them to the global Tags Set
        Tag.addAllTags(tags);

        Iterator<Tag> tagsIt = Tag.getTagsSet().iterator();
        for (Tag tag: tags) {
            assertEquals(tag, tagsIt.next());
        }

        // since we don't have a clear function for the TagsSet (on purpose) we need to 'clear' it by
        // setting it to an empty Set
        Tag.setTagsSet(new LinkedHashSet<Tag>());
    }

    @Test
    public void tagsSet_addArray () throws Exception {
        // create an Array of Tags
        Tag tagOne   = new Tag("Commandments");
        Tag tagTwo   = new Tag("Tithing");
        Tag tagThree = new Tag("Sacrifice");
        Tag tagFour  = new Tag("Word of Wisdom");
        Tag tagFive  = new Tag("Faith");

        Tag[] tags = { tagOne, tagTwo, tagThree, tagFour, tagFive };

        // since we don't have a clear function for the TagsSet (on purpose) we need to 'clear' it by
        // setting it to an empty Set
        Tag.setTagsSet(new LinkedHashSet<Tag>());

        // add them to the global Tags Set
        Tag.addAllTags(tags);

        Iterator<Tag> tagsIt = Tag.getTagsSet().iterator();
        for (Tag tag: tags) {
            assertEquals(tag, tagsIt.next());
        }

        // since we don't have a clear function for the TagsSet (on purpose) we need to 'clear' it by
        // setting it to an empty Set
        Tag.setTagsSet(new LinkedHashSet<Tag>());
    }

    @Test
    public void tagsSet_setKeywords_Set () throws Exception {
        // create a Set of Tags
        Set<Tag> tags = new LinkedHashSet<Tag>();
        tags.add(new Tag("Commandments"));
        tags.add(new Tag("Tithing"));
        tags.add(new Tag("Sacrifice"));
        tags.add(new Tag("Word of Wisdom"));
        tags.add(new Tag("Faith"));

        // add them to the global Tags Set
        Tag.setTagsSet(tags);

        Iterator<Tag> tagsIt = Tag.getTagsSet().iterator();
        for (Tag tag: tags) {
            assertEquals(tag, tagsIt.next());
        }

        // since we don't have a clear function for the TagsSet (on purpose) we need to 'clear' it by
        // setting it to an empty Set
        Tag.setTagsSet(new LinkedHashSet<Tag>());
    }

    @Test
    public void tagsSet_setKeywords_Array () throws Exception {
        // create an Array of Tags
        Tag[] tags = {
                new Tag("Commandments"),
                new Tag("Tithing"),
                new Tag("Sacrifice"),
                new Tag("Word of Wisdom"),
                new Tag("Faith")
        };

        // add them to the global Tags Set
        Tag.setTagsSet(tags);

        Iterator<Tag> tagsIt = Tag.getTagsSet().iterator();
        for (Tag tag: tags) {
            assertEquals(tag, tagsIt.next());
        }

        // since we don't have a clear function for the TagsSet (on purpose) we need to 'clear' it by
        // setting it to an empty Set
        Tag.setTagsSet(new LinkedHashSet<Tag>());
    }

    @Test
    public void TagsSet_return_unmodifiableSet () throws Exception {
        // create a Set of Tags
        Set<Tag> tags = new LinkedHashSet<Tag>();
        tags.add(new Tag("Commandments"));
        tags.add(new Tag("Tithing"));
        tags.add(new Tag("Sacrifice"));
        tags.add(new Tag("Word of Wisdom"));
        tags.add(new Tag("Faith"));

        // for each tag, add a couple test keywords
        int i = 0;
        for (Tag tag: tags) {
            tag.addKeyword("test" + i++);
            tag.addKeyword("test" + i++);
        }

        // set our TagsSet
        Tag.setTagsSet(tags);

        // for each Tag in the Set returned by getTagsSet
        //  we want to know if they have the same names and keywords as the Tags
        //  that we put in with setTagsSet
        //  and we want the Set to be unmodifiable, but the Tags themselves should be
        Iterator<Tag> originalTagsSetIt = tags.iterator(), returnedTagsSetIt = Tag.getTagsSet().iterator();
        while (originalTagsSetIt.hasNext() && returnedTagsSetIt.hasNext()) {
            Tag originalSetTag = originalTagsSetIt.next();
            Tag returnedSetTag = returnedTagsSetIt.next();

            // Test if the returned TagsSet really points to the same Tags
            // This test only works if LinkedHashSet is used to maintain order
            assertEquals(originalSetTag, returnedSetTag);
            // double checking by name, just because
            assertEquals(originalSetTag.getTagName(), returnedSetTag.getTagName());

            // test if the keywords are the same
            Iterator<String> originalKeywordsSetIt = originalSetTag.getKeywordsSet().iterator(),
                    returnedKeywordsSetIt = returnedSetTag.getKeywordsSet().iterator();
            while (originalKeywordsSetIt.hasNext() && returnedKeywordsSetIt.hasNext()) {
                assertEquals(originalKeywordsSetIt.next(), returnedKeywordsSetIt.next());
            }

            // make sure that the Set returned by getTagsSet can't be added to
            boolean failed = false;
            try {
                // I want this to fail
                Tag.getTagsSet().add(new Tag("Test"));
            } catch(UnsupportedOperationException ex) {
                failed = true;
            }
            assertTrue(failed);

            // make sure that the Set returned by getTagsSet can't be cleared
            failed = false;
            try {
                // I want this to fail
                Tag.getTagsSet().clear();
            } catch(UnsupportedOperationException ex) {
                failed = true;
            }
            assertTrue(failed);

            // make sure that the Set returned by getTagsSet can't have contents removed
            failed = false;
            try {
                // I want this to fail
                Tag.getTagsSet().remove(originalSetTag);
            } catch(UnsupportedOperationException ex) {
                failed = true;
            }
            assertTrue(failed);

            // make sure we still can modify the Tags we get from the Set
            returnedSetTag.addKeyword("Test");
            returnedSetTag.changeTagName("Test");
            // make sure the change reflects in the original Tag and the returned Tag
            assertEquals(originalSetTag.getTagName(), returnedSetTag.getTagName());
        }

        // since we don't have a clear function for the TagsSet (on purpose) we need to 'clear' it by
        // setting it to an empty Set
        Tag.setTagsSet(new LinkedHashSet<Tag>());
    }

    @Test
    public void keywordsSet_addKeyword () throws Exception {
        Tag tag = new Tag("Commandment");

        assertTrue(tag.addKeyword("tithing"));
        assertTrue(tag.addKeyword("honesty"));
        assertTrue(tag.addKeyword("sacrifice"));

        assertTrue(tag.hasKeyword("tithing"));
        assertTrue(tag.hasKeyword("honesty"));
        assertTrue(tag.hasKeyword("sacrifice"));
    }

    @Test
    public void keywordsSet_add_removeKeyword () throws Exception {

        Tag tag = new Tag("Commandment");

        // add some words
        assertTrue(tag.addKeyword("tithing"));
        assertTrue(tag.addKeyword("honesty"));
        assertTrue(tag.addKeyword("sacrifice"));

        // remove them
        assertTrue(tag.removeKeyword("tithing"));
        assertTrue(tag.removeKeyword("honesty"));
        assertTrue(tag.removeKeyword("sacrifice"));

        // make sure they are gone
        assertFalse(tag.hasKeyword("tithing"));
        assertFalse(tag.hasKeyword("honesty"));
        assertFalse(tag.hasKeyword("sacrifice"));
    }

    @Test
    public void keywordsSet_addSet () throws Exception {
        Tag tag = new Tag("Commandment");

        // add some words
        Set<String> keywords = new LinkedHashSet<String>();
        keywords.add("tithing");
        keywords.add("honesty");
        keywords.add("sacrifice");

        tag.addAllKeywords(keywords);

        Iterator<String> keywordsIt = tag.getKeywordsSet().iterator();
        for (String keyword: keywords) {
            assertEquals(keyword, keywordsIt.next());
        }
    }

    @Test
    public void keywordsSet_addArray () throws Exception {
        Tag tag = new Tag("Commandment");

        // add some words
        String[] keywords = { "tithing", "honesty", "sacrifice" };

        tag.addAllKeywords(keywords);

        Iterator<String> keywordsIt = tag.getKeywordsSet().iterator();
        for (String keyword: keywords) {
            assertEquals(keyword, keywordsIt.next());
        }
    }

    @Test
    public void keywordsSet_setKeywords_Set () throws Exception {
        Tag tag = new Tag("Commandment");

        // add some words
        Set<String> keywords = new LinkedHashSet<String>();
        keywords.add("tithing");
        keywords.add("honesty");
        keywords.add("sacrifice");

        tag.setKeywordsSet(keywords);

        Iterator<String> keywordsIt = tag.getKeywordsSet().iterator();
        for (String keyword: keywords) {
            assertEquals(keyword, keywordsIt.next());
        }
    }

    @Test
    public void keywordsSet_setKeywords_Array () throws Exception {
        Tag tag = new Tag("Commandment");

        // add some words
        String[] keywords = { "tithing", "honesty", "sacrifice" };

        tag.setKeywordsSet(keywords);

        Iterator<String> keywordsIt = tag.getKeywordsSet().iterator();
        for (String keyword: keywords) {
            assertEquals(keyword, keywordsIt.next());
        }
    }

    @Test
    public void keywordsSet_return_unmodifiableSet () throws Exception {
        Tag tag = new Tag("Commandment");

        // add some keywords
        Set<String> keywords = new LinkedHashSet<String>();
        keywords.add("tithing");
        keywords.add("honesty");
        keywords.add("sacrifice");

        tag.setKeywordsSet(keywords);

        // for each Keyword in the Set returned by getKeywordsSet
        //  we want to know if they are the same keywords we put in with setKeywordsSet
        //  and we want the Set to be unmodifiable

        // Test if the returned KeywordsSet really points to the same Strings
        // This test only works if LinkedHashSet is used to maintain order
        assertEquals(keywords, tag.getKeywordsSet());

        // another test to see if the keywords are the same
        Iterator<String> originalKeywordsSetIt = keywords.iterator(),
                returnedKeywordsSetIt = tag.getKeywordsSet().iterator();
        while (originalKeywordsSetIt.hasNext() && returnedKeywordsSetIt.hasNext()) {
            assertEquals(originalKeywordsSetIt.next(), returnedKeywordsSetIt.next());
        }

        // make sure that the Set returned by getTagsSet can't be added to
        boolean failed = false;
        try {
            // I want this to fail
            tag.getKeywordsSet().add("Test");
        } catch(UnsupportedOperationException ex) {
            failed = true;
        }
        assertTrue(failed);

        // make sure that the Set returned by getTagsSet can't be cleared
        failed = false;
        try {
            // I want this to fail
            tag.getKeywordsSet().clear();
        } catch(UnsupportedOperationException ex) {
            failed = true;
        }
        assertTrue(failed);

        // make sure that the Set returned by getTagsSet can't have contents removed
        failed = false;
        try {
            // I want this to fail
            tag.getKeywordsSet().remove("tithing");
        } catch(UnsupportedOperationException ex) {
            failed = true;
        }
        assertTrue(failed);
    }

}