/*
 * Copyright 2019 InterlockLedger Network
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.interlockledger.iltags;

import static org.junit.Assert.*;

import org.junit.Test;

public class ILStandardTagsTest {

	@Test
	public void testConstants() {
		assertEquals(0, ILStandardTags.TAG_NULL.ordinal());
		assertEquals(1, ILStandardTags.TAG_BOOL.ordinal());
		assertEquals(2, ILStandardTags.TAG_INT8.ordinal());
		assertEquals(3, ILStandardTags.TAG_UINT8.ordinal());
		assertEquals(4, ILStandardTags.TAG_INT16.ordinal());
		assertEquals(5, ILStandardTags.TAG_UINT16.ordinal());
		assertEquals(6, ILStandardTags.TAG_INT32.ordinal());
		assertEquals(7, ILStandardTags.TAG_UINT32.ordinal());
		assertEquals(8, ILStandardTags.TAG_INT64.ordinal());
		assertEquals(9, ILStandardTags.TAG_UINT64.ordinal());
		assertEquals(10, ILStandardTags.TAG_ILINT64.ordinal());
		assertEquals(11, ILStandardTags.TAG_BINARY32.ordinal());
		assertEquals(12, ILStandardTags.TAG_BINARY64.ordinal());
		assertEquals(13, ILStandardTags.TAG_BINARY128.ordinal());
		assertEquals(16, ILStandardTags.TAG_BYTE_ARRAY.ordinal());
		assertEquals(17, ILStandardTags.TAG_STRING.ordinal());
		assertEquals(18, ILStandardTags.TAG_BINT.ordinal());
		assertEquals(19, ILStandardTags.TAG_BDEC.ordinal());
		assertEquals(20, ILStandardTags.TAG_ILINT64_ARRAY.ordinal());
		assertEquals(21, ILStandardTags.TAG_ILTAG_ARRAY.ordinal());
		assertEquals(22, ILStandardTags.TAG_ILTAG_SEQ.ordinal());
		assertEquals(23, ILStandardTags.TAG_RANGE.ordinal());
		assertEquals(24, ILStandardTags.TAG_VERSION.ordinal());
		assertEquals(25, ILStandardTags.TAG_OID.ordinal());
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void testConstantsDeprecated() {
		assertEquals(14, ILStandardTags.RESERVED_14.ordinal());
		assertEquals(15, ILStandardTags.RESERVED_15.ordinal());
		assertEquals(26, ILStandardTags.RESERVED_26.ordinal());
		assertEquals(27, ILStandardTags.RESERVED_27.ordinal());
		assertEquals(28, ILStandardTags.RESERVED_28.ordinal());
		assertEquals(29, ILStandardTags.RESERVED_29.ordinal());
		assertEquals(30, ILStandardTags.RESERVED_30.ordinal());
		assertEquals(31, ILStandardTags.RESERVED_31.ordinal());
	}
	
	@Test
	public void testIsStandard() {
		
		assertFalse(ILStandardTags.isStandard(-1));
		for (long id = 0; id < 32; id++) {
			assertTrue(ILStandardTags.isStandard(id));
		}
		assertFalse(ILStandardTags.isStandard(32));
	}
	
	@Test
	public void testParseId() {
		
		assertEquals(ILStandardTags.NON_STANDARD_TAG, ILStandardTags.parseId(-1));
		for (long id = 0; id < 32; id++) {
			assertEquals(ILStandardTags.values()[(int)id], ILStandardTags.parseId(id));
		}
		assertEquals(ILStandardTags.NON_STANDARD_TAG, ILStandardTags.parseId(32));
	}
}
