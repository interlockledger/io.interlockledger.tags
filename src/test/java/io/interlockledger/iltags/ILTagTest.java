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

import static io.interlockledger.iltags.TestUtils.createSampleByteArray;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;

import org.junit.Test;

import io.interlockledger.iltags.ilint.ILIntCodec;
import io.interlockledger.iltags.io.ILBaseTagDataReaderTest;
import io.interlockledger.iltags.io.ILMemoryTagDataWriter;
import io.interlockledger.iltags.io.ILTagNotEnoughDataException;

public class ILTagTest {

	@Test
	public void testEquals() {
		ILTestTag t1 = new ILTestTag(16, 16);
		ILTestTag t2 = new ILTestTag(16, 16);
		ILTestTag t3 = new ILTestTag(15, 16);
		ILTestTag t4 = new ILTestTag(16, 15);

		assertTrue(t1.equals(t1));
		assertTrue(t1.equals(t2));
		assertFalse(t1.equals(null));
		assertFalse(t1.equals(t3));
		assertFalse(t1.equals(t4));
	}

	@Test
	public void testGetId() {

		long size = 0x10;
		for (int i = 0; i < 8; i++) {
			ILTestTag t = new ILTestTag(size, size);
			assertEquals(size, t.getId());
		}
	}

	@Test
	public void testGetImplicitValueSize() {

		assertEquals(0, ILTestTag.getImplicitValueSize(0));
		assertEquals(1, ILTestTag.getImplicitValueSize(1));
		assertEquals(1, ILTestTag.getImplicitValueSize(2));
		assertEquals(1, ILTestTag.getImplicitValueSize(3));
		assertEquals(2, ILTestTag.getImplicitValueSize(4));
		assertEquals(2, ILTestTag.getImplicitValueSize(5));
		assertEquals(4, ILTestTag.getImplicitValueSize(6));
		assertEquals(4, ILTestTag.getImplicitValueSize(7));
		assertEquals(8, ILTestTag.getImplicitValueSize(8));
		assertEquals(8, ILTestTag.getImplicitValueSize(9));
		assertEquals(-1, ILTestTag.getImplicitValueSize(10));
		assertEquals(4, ILTestTag.getImplicitValueSize(11));
		assertEquals(8, ILTestTag.getImplicitValueSize(12));
		assertEquals(16, ILTestTag.getImplicitValueSize(13));
		assertEquals(-1, ILTestTag.getImplicitValueSize(14));
		assertEquals(-1, ILTestTag.getImplicitValueSize(15));

		for (int i = 16; i < 32; i++) {
			try {
				assertEquals(-1, ILTestTag.getImplicitValueSize(i));
				fail();
			} catch (IllegalArgumentException e) {
			}
		}
	}

	@Test
	public void testGetTagSize() {
		long size = 0x10;
		for (int i = 0; i < 8; i++) {
			ILTestTag t = new ILTestTag(size, size);
			assertEquals(ILIntCodec.getEncodedSize(size) + ILIntCodec.getEncodedSize(size) + size, t.getTagSize());
			size = size << 8;
		}
	}

	@Test
	public void testGetValueSize() {

		long size = 0x1;
		for (int i = 0; i < 8; i++) {
			ILTestTag t = new ILTestTag(0, size);
			assertEquals(size, t.getValueSize());
			size = size << 8;
		}
	}

	@Test
	public void testILTag() {

		long size = 0x10;
		for (int i = 0; i < 8; i++) {
			ILTestTag t = new ILTestTag(size, size);
			assertEquals(size, t.getId());
			assertEquals(size, t.getValueSize());
		}
	}

	@Test
	public void testIsImplicity() {

		for (int i = 0; i < 16; i++) {
			ILTestTag t = new ILTestTag(i, i);
			assertTrue(t.isImplicity());
		}
		for (int i = 16; i < 64; i++) {
			ILTestTag t = new ILTestTag(i, i);
			assertFalse(t.isImplicity());
		}
	}

	@Test
	public void testIsImplicityLong() {
		for (int i = 0; i < 16; i++) {
			assertTrue(ILTestTag.isImplicity(i));
		}
		for (int i = 16; i < 64; i++) {
			assertFalse(ILTestTag.isImplicity(i));
		}
	}

	@Test
	public void testIsStandard() {

		for (int i = 0; i < 32; i++) {
			ILTestTag t = new ILTestTag(i, i);
			assertTrue(t.isStandard());
		}
		for (int i = 32; i < 64; i++) {
			ILTestTag t = new ILTestTag(i, i);
			assertFalse(t.isStandard());
		}
	}

	@Test
	public void testIsStandardLong() {

		for (int i = 0; i < 32; i++) {
			assertTrue(ILTestTag.isStandard(i));
		}
		for (int i = 32; i < 64; i++) {
			assertFalse(ILTestTag.isStandard(i));
		}
	}

	@Test
	public void testReadRawBytes() throws Exception {

		for (int size = 0; size < 1024; size += 33) {
			ILTestTag t = new ILTestTag(16, size);
			ILBaseTagDataReaderTest.TestTagDataReader r = new ILBaseTagDataReaderTest.TestTagDataReader();
			r.pushLimit(size);
			byte[] v = t.readRawBytes(size, r);
			r.popLimit(true);
			byte[] expected = createSampleByteArray(size);
			assertArrayEquals(expected, v);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testReadRawBytesFailNegativeSize() throws Exception {
		ILTestTag t = new ILTestTag(16, 16);

		ILBaseTagDataReaderTest.TestTagDataReader r = new ILBaseTagDataReaderTest.TestTagDataReader();
		t.readRawBytes(-1, r);
	}

	@Test(expected = ILTagNotEnoughDataException.class)
	public void testReadRawBytesFailNoData() throws Exception {
		ILTestTag t = new ILTestTag(16, 16);

		ILBaseTagDataReaderTest.TestTagDataReader r = new ILBaseTagDataReaderTest.TestTagDataReader();
		r.pushLimit(15);
		t.readRawBytes(16, r);
	}

	@Test(expected = ILTagException.class)
	public void testReadRawBytesFailTooLarge() throws Exception {
		ILTestTag t = new ILTestTag(16, 16);

		ILBaseTagDataReaderTest.TestTagDataReader r = new ILBaseTagDataReaderTest.TestTagDataReader();
		t.readRawBytes(((long) Integer.MAX_VALUE) + 1, r);
	}

	@Test
	public void testSerialize() throws Exception {

		for (int size = 0; size < 512; size += 128) {
			ILTestTag t = new ILTestTag(16, size);
			ILMemoryTagDataWriter actual = new ILMemoryTagDataWriter();

			t.serialize(actual);

			ByteArrayOutputStream expected = new ByteArrayOutputStream();
			expected.write(16);
			ILIntCodec.encode(size, expected);
			for (int i = 0; i < size; i++) {
				expected.write(i & 0xFF);
			}
			assertArrayEquals(expected.toByteArray(), actual.toByteArray());
		}
	}

	@Test
	public void testSerializeFakeStandard1() throws Exception {
		ILTestTag t = new ILTestTag(0, 0);
		ILMemoryTagDataWriter actual = new ILMemoryTagDataWriter();
		t.serialize(actual);

		ByteArrayOutputStream expected = new ByteArrayOutputStream();
		expected.write(0);

		assertArrayEquals(expected.toByteArray(), actual.toByteArray());
	}

	@Test
	public void testSerializeFakeStandard2() throws Exception {
		ILTestTag t = new ILTestTag(1, 1);
		ILMemoryTagDataWriter actual = new ILMemoryTagDataWriter();

		t.serialize(actual);

		ByteArrayOutputStream expected = new ByteArrayOutputStream();
		expected.write(1);
		expected.write(0);

		assertArrayEquals(expected.toByteArray(), actual.toByteArray());
	}
}
