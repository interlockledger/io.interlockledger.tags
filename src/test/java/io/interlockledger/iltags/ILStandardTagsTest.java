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
		assertEquals(0, ILStandardTags.TAG_NULL);
		assertEquals(1, ILStandardTags.TAG_BOOL);
		assertEquals(2, ILStandardTags.TAG_INT8);
		assertEquals(3, ILStandardTags.TAG_UINT8);
		assertEquals(4, ILStandardTags.TAG_INT16);
		assertEquals(5, ILStandardTags.TAG_UINT16);
		assertEquals(6, ILStandardTags.TAG_INT32);
		assertEquals(7, ILStandardTags.TAG_UINT32);
		assertEquals(8, ILStandardTags.TAG_INT64);
		assertEquals(9, ILStandardTags.TAG_UINT64);
		assertEquals(10, ILStandardTags.TAG_ILINT64);
		assertEquals(11, ILStandardTags.TAG_BINARY32);
		assertEquals(12, ILStandardTags.TAG_BINARY64);
		assertEquals(13, ILStandardTags.TAG_BINARY128);
		assertEquals(16, ILStandardTags.TAG_BYTE_ARRAY);
		assertEquals(17, ILStandardTags.TAG_STRING);
		assertEquals(18, ILStandardTags.TAG_BINT);
		assertEquals(19, ILStandardTags.TAG_BDEC);
		assertEquals(20, ILStandardTags.TAG_ILINT64_ARRAY);
		assertEquals(21, ILStandardTags.TAG_ILTAG_ARRAY);
		assertEquals(22, ILStandardTags.TAG_ILTAG_SEQ);
		assertEquals(23, ILStandardTags.TAG_RANGE);
		assertEquals(24, ILStandardTags.TAG_VERSION);
		assertEquals(25, ILStandardTags.TAG_OID);
	}

}
