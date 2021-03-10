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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.junit.Test;

import io.interlockledger.iltags.io.ILMemoryTagDataReader;
import io.interlockledger.iltags.io.ILMemoryTagDataWriter;
import io.interlockledger.iltags.io.UTF8Utils;

public class ILStringTagTest {

	@Test
	public void testDeserializeValue() throws Exception {
		ILStringTag t = new ILStringTag();

		for (int size = 0; size < 1024; size += 33) {
			String s = TestUtils.genRandomString(size, true);
			ByteBuffer exp = UTF8Utils.newEncoder().encode(CharBuffer.wrap(s));
			byte[] bexp = new byte[exp.remaining()];
			exp.get(bexp);
			t.deserializeValue(null, bexp.length, new ILMemoryTagDataReader(bexp));
			assertEquals(s, t.getValue());
		}
	}

	@Test
	public void testEquals() {
		ILStringTag t1 = new ILStringTag();
		t1.setValue(" ");
		ILStringTag t2 = new ILStringTag();
		t2.setValue(" ");
		ILStringTag t3 = new ILStringTag();
		t3.setValue("1");
		ILStringTag t4 = new ILStringTag(15);
		t4.setValue(" ");
		ILStringTag t5 = new ILStringTag();

		assertTrue(t1.equals(t1));
		assertTrue(t1.equals(t2));
		assertTrue((t1).equals(t2));
		assertTrue((new ILStringTag()).equals(new ILStringTag()));

		assertFalse(t1.equals(null));
		assertFalse(t1.equals(t3));
		assertFalse(t1.equals(t4));
		assertFalse(t1.equals(t5));

		assertFalse(t1.equals(t4));

	}

	@Test
	public void testGetSetValue() {
		ILStringTag t = new ILStringTag(123);

		assertNull(t.getValue());
		t.setValue("value");
		assertEquals("value", t.getValue());
	}

	@Test
	public void testGetValueSize() {
		ILStringTag t = new ILStringTag();

		for (int size = 0; size < 1024; size += 33) {
			String s = TestUtils.genRandomString(size, true);
			t.setValue(s);
			assertEquals(UTF8Utils.getEncodedSize(s), t.getValueSize());
		}
	}

	@Test
	public void testILStringTag() {
		ILStringTag t = new ILStringTag();

		assertEquals(ILStandardTags.TAG_STRING.ordinal(), t.getId());
		assertNull(t.getValue());
	}

	@Test
	public void testILStringTagLong() {
		ILStringTag t = new ILStringTag(123);

		assertEquals(123, t.getId());
		assertNull(t.getValue());
	}

	@Test
	public void testSerializeValue() throws Exception {
		ILStringTag t = new ILStringTag();

		for (int size = 0; size < 1024; size += 33) {
			String s = TestUtils.genRandomString(size, true);
			t.setValue(s);

			ILMemoryTagDataWriter w = new ILMemoryTagDataWriter();
			t.serializeValue(w);

			ByteBuffer exp = UTF8Utils.newEncoder().encode(CharBuffer.wrap(s));
			byte[] bexp = new byte[exp.remaining()];
			exp.get(bexp);
			assertArrayEquals(bexp, w.toByteArray());
		}
	}
}
