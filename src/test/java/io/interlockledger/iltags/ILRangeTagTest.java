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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.interlockledger.iltags.ilint.ILIntCodec;
import io.interlockledger.iltags.io.ILMemoryTagDataReader;
import io.interlockledger.iltags.io.ILMemoryTagDataWriter;

public class ILRangeTagTest {

	@Test
	public void testDeserializeValue() throws Exception {
		ILRangeTag t = new ILRangeTag();

		ILMemoryTagDataWriter raw = new ILMemoryTagDataWriter();
		raw.writeILInt(0xFACADA);
		raw.writeShort((short) 0xFADA);

		t.deserializeValue(null, raw.getOffset(), new ILMemoryTagDataReader(raw.toByteArray()));
		assertEquals(0xFACADA, t.getStart());
		assertEquals(0xFADA, t.getRange());
	}

	@Test
	public void testEquals() {
		ILRangeTag t1 = new ILRangeTag();
		ILRangeTag t2 = new ILRangeTag();
		ILRangeTag t3 = new ILRangeTag();
		t3.setStart(1);
		ILRangeTag t5 = new ILRangeTag();
		t5.setRange(1);
		ILRangeTag t4 = new ILRangeTag(15);

		assertTrue(t1.equals(t1));
		assertTrue(t1.equals(t2));
		assertFalse(t1.equals(null));
		assertFalse(t1.equals(t3));
		assertFalse(t1.equals(t4));
		assertFalse(t1.equals(t5));
	}

	@Test
	public void testGetEnd() {
		ILRangeTag t = new ILRangeTag();

		assertEquals(0, t.getEnd());
		t.setStart(12345);
		t.setRange(1234);
		assertEquals(12345 + 1234, t.getEnd());
	}

	@Test
	public void testGetSetRange() {
		ILRangeTag t = new ILRangeTag();

		assertEquals(0, t.getRange());
		t.setRange(0xFACA);
		assertEquals(0xFACA, t.getRange());
	}

	@Test
	public void testGetSetStart() {
		ILRangeTag t = new ILRangeTag();

		assertEquals(0, t.getStart());
		t.setStart(0xFACADA);
		assertEquals(0xFACADA, t.getStart());
	}

	@Test
	public void testGetValueSize() {
		ILRangeTag t = new ILRangeTag();

		assertEquals(1 + 2, t.getValueSize());

		t.setStart(0xFACADA);
		t.setRange(0xFFFF);
		assertEquals(ILIntCodec.getEncodedSize(0xFACADA) + 2, t.getValueSize());
	}

	@Test
	public void testILRangeTag() {
		ILRangeTag t = new ILRangeTag();

		assertEquals(ILStandardTags.TAG_RANGE.ordinal(), t.getId());
		assertEquals(0, t.getStart());
		assertEquals(0, t.getRange());
	}

	@Test
	public void testILRangeTagLong() {
		ILRangeTag t = new ILRangeTag(1234);

		assertEquals(1234, t.getId());
		assertEquals(0, t.getStart());
		assertEquals(0, t.getRange());
	}

	@Test
	public void testSerializeValue() throws Exception {
		ILRangeTag t = new ILRangeTag();

		ILMemoryTagDataWriter w = new ILMemoryTagDataWriter();
		t.serializeValue(w);
		assertArrayEquals(new byte[1 + 2], w.toByteArray());

		t.setStart(0xFACADA);
		t.setRange(0xFADA);
		w = new ILMemoryTagDataWriter();
		t.serializeValue(w);

		ILMemoryTagDataWriter expected = new ILMemoryTagDataWriter();
		expected.writeILInt(0xFACADA);
		expected.writeShort((short) 0xFADA);
		assertArrayEquals(expected.toByteArray(), w.toByteArray());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetRangeFailNegative() {
		ILRangeTag t = new ILRangeTag();

		t.setRange(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetRangeFailTooLarge() {
		ILRangeTag t = new ILRangeTag();

		t.setRange(0x80000);
	}

	@Test
	public void testSetValue() {
		ILRangeTag t = new ILRangeTag();

		t.setValue(1, 2);
		assertEquals(1, t.getStart());
		assertEquals(2, t.getRange());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetValueFailedLargeRange() {
		ILRangeTag t = new ILRangeTag();

		t.setValue(1, 0x10000);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetValueFailedNegativeRange() {
		ILRangeTag t = new ILRangeTag();

		t.setValue(1, -1);
	}
}
