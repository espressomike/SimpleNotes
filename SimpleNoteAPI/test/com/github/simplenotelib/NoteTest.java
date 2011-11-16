package com.github.simplenotelib;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

public class NoteTest extends TestCase {
	
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
		assertEquals(1234, n.getModifydate());
		assertEquals(123, n.getCreatedate());
		assertEquals(1, n.getSyncnum());
//		assertEquals(1, n.getVersion());
		assertEquals(1, n.getMinversion());
		assertEquals("bar", n.getSharekey());
		assertEquals("baz", n.getPublishkey());
		assertEquals(systemTags, n.getSystemtags());
		assertEquals(tags, n.getTags());
		assertEquals("foo", n.getContent());
		
	}

}
