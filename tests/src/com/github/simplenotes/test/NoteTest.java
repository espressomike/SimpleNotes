package com.github.simplenotes.test;

import java.util.ArrayList;
import java.util.Collection;

import android.test.AndroidTestCase;

import com.github.simplenotes.Note;

public class NoteTest extends AndroidTestCase {
	
	public void testFromJsonWithKnownFields()
	{
		String json = "{ key: \"foo\", deleted: false, modifydate: 1234, createdate: 123, "
				    + "syncnum: 1, version: 1, minversion: 1, sharekey: \"bar\", "
				    + "publishkey: \"baz\", systemtags: [ \"pinned\", \"unread\" ], "
				    + "tags: [\"larry\", \"curly\", \"moe\"], content: \"foo\" }";
		
		Note n = Note.fromJSON(json);
		Collection<String> systemTags = new ArrayList<String>();
		systemTags.add("pinned");
		systemTags.add("unread");
		
		Collection<String> tags = new ArrayList<String>();
		tags.add("larry");
		tags.add("curly");
		tags.add("moe");
			
		assertEquals("foo", n.getKey());
		assertEquals(false, n.isDeleted());
		assertEquals(1234, n.getModifyDate());
		assertEquals(123, n.getCreateDate());
		assertEquals(1, n.getSyncNum());
//		assertEquals(1, n.getVersion());
		assertEquals(1, n.getMinVersion());
		assertEquals("bar", n.getShareKey());
		assertEquals("baz", n.getPublishKey());
		assertEquals(systemTags, n.getSystemTags());
		assertEquals(tags, n.getTags());
		assertEquals("foo", n.getContent());
		
	}

}
