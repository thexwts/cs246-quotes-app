package edu.groupawesome.quotetracker;

/**
 * Created by reed on 6/8/16.
 */

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TagTest {

    @Test
    public void tagsList_addTag () throws Exception {

        Tag tag = new Tag("Test");

        assertTrue(Tag.addTagToTagsList(tag));
    }

    @Test
    public void tag_changeName () throws Exception {

        Tag tag = new Tag("Test");

        assert(tag.getTagName() == "Test");

        tag.changeTagName("New name");

        assert(tag.getTagName() == "New name");
    }

    private void is(String s) {
    }

    @Test
    public void setAndGetTagsList () throws Exception {
        List<Tag> tags = new ArrayList<Tag>();
        tags.add(new Tag("Commandments"));
        tags.add(new Tag("Tithing"));
        tags.add(new Tag("Sacrifice"));
        tags.add(new Tag("Word of Wisdom"));
        tags.add(new Tag("Faith"));

        Tag.setTagList(tags);

        assertSame(Tag.getTagList(), tags);

    }

    @Test
    public void keywordsList_addKeyword () throws Exception {

        Tag tag = new Tag("Commandment");

        assertTrue(tag.addKeyword("tithing"));
        assertTrue(tag.addKeyword("honesty"));
        assertTrue(tag.addKeyword("sacrifice"));

        assertTrue(tag.hasKeyword("tithing"));
        assertTrue(tag.hasKeyword("honesty"));
        assertTrue(tag.hasKeyword("sacrifice"));
    }


    @Test
    public void keywordsList_add_removeKeyword () throws Exception {

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

}
