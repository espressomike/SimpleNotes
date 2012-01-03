/*
  Copyright (C) 2011  Mattias Svala

  This file is part of SimpleNotes.

  SimpleNotes is free software; you can redistribute it and/or
  modify it under the terms of the GNU General Public License
  as published by the Free Software Foundation; either version 2
  of the License, or (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.
  package com.github.simplenotes;
*/

package com.github.simplenotes.test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Collection;

import android.database.Cursor;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.github.simplenotes.Note;
import com.github.simplenotes.NotesDb;

public class NotesDbTest extends AndroidTestCase {

    private NotesDb db;

    public void setUp() {
        RenamingDelegatingContext ctx = 
            new RenamingDelegatingContext(getContext(), "dbtest_");
        db = new NotesDb(ctx);
        db.open();
        db.deleteAllNotes();
    }

    public void tearDown() {
        db.close();
    }

    public void testCanCreateNoteWithoutTagsAndReadIt() {
        String content = "foo bar baz";
        Long id = db.createNote(content, null);
        assertTrue("Note creation failed.", id != -1);
        Note note = db.getNote(id);
        assertNotNull(note);
        assertEquals(id, note.getId());
        assertEquals(content, note.getContent());
        assertEquals(0, note.getTags().size());
    }

    public void testCanCreateNoteWithTagsAndReadIt() {
        String content = "foo bar baz";
        List<String> tags = Arrays.asList(new String[] {"foo", "bar"});
        Long id = db.createNote(content, tags);
        assertTrue("Note creation failed.", id != -1);
        Note note = db.getNote(id);
        assertNotNull(note);
        assertEquals(id, note.getId());
        assertEquals(content, note.getContent());
        Collection<String> noteTags = note.getTags();
        assertEquals(tags.size(), noteTags.size());
        assertEquals(tags, noteTags);
    }

    private void assertEqualNotes(Note note0, Note note1) {
        assertEquals(note0.getContent(), note1.getContent());
        assertEquals(note0.getKey(), note1.getKey());
        assertEquals(note0.getTags(), note1.getTags());
        assertEquals(note0.getCreateDate(), note1.getCreateDate());
        assertEquals(note0.getModifyDate(), note1.getModifyDate());
        assertEquals(note0.isDeleted(), note1.isDeleted());
        assertEquals(note0.getSyncNum(), note1.getSyncNum());
        assertEquals(note0.getMinVersion(), note1.getMinVersion());
        assertEquals(note0.getShareKey(), note1.getShareKey());
        assertEquals(note0.getPublishKey(), note1.getPublishKey());

        // Check the system tags via isPinned() and isUnread() since
        // the order of the corresponding tags is undefined.
        // assertEquals(note0.isPinned(), note1.isPinned());
        // assertEquals(note0.isUnread(), note1.isUnread());
    }

    public void testCanCreateAlmostEmptyNoteFromNoteObjectAndReadIt() {
        Note note0 = new Note();
        note0.setContent("Creating note from note object.");
        Long id = db.createNote(note0);
        assertTrue("Note creation failed.", id != -1);
        assertEquals(id, note0.getId());
        Note note1 = db.getNote(id);
        assertNotNull(note1);
        assertEquals(id, note1.getId());
        assertEqualNotes(note0, note1);
    }

    public void testCanCreateFromNoteObjectAndReadIt() {
        Note note0 = new Note();
        note0.setContent("Creating note from note object.");
        note0.setKey("key");
        note0.setTags(Arrays.asList(new String[] {"foo", "bar", "baz"}));
        note0.setSystemTags(Arrays.asList(new String[] {"pinned", "unread"}));
        note0.setCreateDate(1295216117);
        note0.setModifyDate(1295216118);
        note0.setDeleted(false);
        note0.setSyncNum(10);
        note0.setMinVersion(20);
        note0.setShareKey("sharekey");
        note0.setPublishKey("publishkey");
        Long id = db.createNote(note0);
        assertTrue("Note creation failed.", id != -1);
        assertEquals(id, note0.getId());
        Note note1 = db.getNote(id);
        assertNotNull(note1);
        assertEquals(id, note1.getId());
        assertEqualNotes(note0, note1);
    }

    public void testCanCreateFromNoteObjectAndUpdateItAndReadIt() {
        Note note0 = new Note();
        note0.setContent("Creating note from note object.");
        note0.setKey("key");
        note0.setTags(Arrays.asList(new String[] {"foo", "bar", "baz"}));
        note0.setSystemTags(Arrays.asList(new String[] {"pinned", "unread"}));
        note0.setCreateDate(1295216117);
        note0.setModifyDate(1295216118);
        note0.setDeleted(false);
        note0.setSyncNum(10);
        note0.setMinVersion(20);
        note0.setShareKey("sharekey");
        note0.setPublishKey("publishkey");
        long id = db.createNote(note0);
        assertTrue("Note creation failed.", id != -1);

        note0.setTags(Arrays.asList(new String[] {"x", "y", "z"}));
        note0.setContent("new content");
        db.updateNote(note0);

        Note note1 = db.getNote(note0.getId());
        assertNotNull(note1);
        assertEquals(note0.getId(), note1.getId());
        assertEqualNotes(note0, note1);
    }

    public void testReadAllNotesWithGetAllNotes() {
        Note note0 = new Note();
        note0.setContent("Creating note0 from note object.");
        note0.setKey("key");
        note0.setTags(Arrays.asList(new String[] {"foo", "bar", "baz"}));
        note0.setSystemTags(Arrays.asList(new String[] {"unread"}));
        note0.setCreateDate(1295216117);
        note0.setModifyDate(1295216118);
        note0.setDeleted(false);
        note0.setSyncNum(10);
        note0.setMinVersion(15);
        note0.setShareKey("sharekey");
        note0.setPublishKey("publishkey");
        Long id0 = db.createNote(note0);
        assertTrue("Note creation failed.", id0 != -1);

        Note note1 = new Note();
        note1.setContent("Creating note1 from note object.");
        note1.setKey("key2");
        note1.setTags(Arrays.asList(new String[] {"apa", "bepa", "cepa"}));
        note1.setSystemTags(Arrays.asList(new String[] {"pinned"}));
        note1.setCreateDate(1295216117);
        note1.setModifyDate(1295216118);
        note1.setDeleted(false);
        note1.setSyncNum(10);
        note1.setMinVersion(15);
        note1.setShareKey("sharekey2");
        note1.setPublishKey("publishkey2");
        Long id1 = db.createNote(note1);
        assertTrue("Note creation failed.", id1 != -1);

        Cursor notes = db.getAllNotes();
        notes.moveToFirst();
        assertTrue(notes.isFirst());
        Note note = NotesDb.noteFrom(notes);
        assertEquals(id1, note.getId());
        assertEqualNotes(note1, note);
        assertFalse(notes.isAfterLast());
        note = NotesDb.noteFrom(notes);
        assertEquals(id0, note.getId());
        assertEqualNotes(note0, note);
        assertTrue(notes.isAfterLast());
    }

    public void testDeletingAllNotesWorks() {
        db.createNote("hello", Arrays.asList(new String[] {"foo", "bar"}));
        db.createNote("bye", Arrays.asList(new String[] {"apa"}));
        db.deleteAllNotes();
        Cursor notes = db.getAllNotes();
        assertEquals(0, notes.getCount());
    }

    public void testDeletingOneNoteWorks() {
        long id = db.createNote("hello",
                                Arrays.asList(new String[] {"foo", "bar"}));
        db.createNote("bye", Arrays.asList(new String[] {"apa"}));
        db.deleteNote(id);
        Cursor notes = db.getAllNotes();
        assertEquals(1, notes.getCount());
    }

    public void testNotesAreCountedCorrectly() {
        db.createNote("hello",
                      Arrays.asList(new String[] {"foo", "bar"}));
        db.createNote("bye", Arrays.asList(new String[] {"apa"}));
        assertEquals(2, db.countAllNotes());
    }
}
