package io.interlockledger.iltags;

import static org.junit.Assert.*;

import org.junit.Test;

public class ILTagStandardTagsTest {

	@Test
	public void testConstants() {
		assertEquals(0, ILTagStandardTags.TAG_NULL);
		assertEquals(1, ILTagStandardTags.TAG_BOOL);
		assertEquals(2, ILTagStandardTags.TAG_INT8);
		assertEquals(3, ILTagStandardTags.TAG_UINT8);
		assertEquals(4, ILTagStandardTags.TAG_INT16);
		assertEquals(5, ILTagStandardTags.TAG_UINT16);
		assertEquals(6, ILTagStandardTags.TAG_INT32);
		assertEquals(7, ILTagStandardTags.TAG_UINT32);
		assertEquals(8, ILTagStandardTags.TAG_INT64);
		assertEquals(9, ILTagStandardTags.TAG_UINT64);
		assertEquals(10, ILTagStandardTags.TAG_ILINT64);
		assertEquals(11, ILTagStandardTags.TAG_BINARY32);
		assertEquals(12, ILTagStandardTags.TAG_BINARY64);
		assertEquals(13, ILTagStandardTags.TAG_BINARY128);
		assertEquals(16, ILTagStandardTags.TAG_BYTE_ARRAY);
		assertEquals(17, ILTagStandardTags.TAG_STRING);
		assertEquals(18, ILTagStandardTags.TAG_BINT);
		assertEquals(19, ILTagStandardTags.TAG_BDEC);
		assertEquals(20, ILTagStandardTags.TAG_ILINT64_ARRAY);
		assertEquals(21, ILTagStandardTags.TAG_ILTAG_ARRAY);
		assertEquals(22, ILTagStandardTags.TAG_ILTAG_SEQ);
		assertEquals(23, ILTagStandardTags.TAG_RANGE);
		assertEquals(24, ILTagStandardTags.TAG_VERSION);
		assertEquals(25, ILTagStandardTags.TAG_OID);
	}

}
