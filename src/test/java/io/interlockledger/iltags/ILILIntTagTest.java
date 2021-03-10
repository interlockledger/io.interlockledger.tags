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

import java.io.ByteArrayOutputStream;
import java.util.Random;

import org.junit.Test;

import io.interlockledger.iltags.ilint.ILIntCodec;
import io.interlockledger.iltags.io.ILMemoryTagDataReader;
import io.interlockledger.iltags.io.ILMemoryTagDataWriter;

public class ILILIntTagTest {

	@Test
	public void testDeserializeValue() throws Exception {
		Random random = new Random();

		for (int i = 0; i < 32; i++) {
			long v = random.nextLong();

			ByteArrayOutputStream raw = new ByteArrayOutputStream();
			ILIntCodec.encode(v, raw);
			raw.close();

			ILILIntTag t = new ILILIntTag();
			ILMemoryTagDataReader in = new ILMemoryTagDataReader(raw.toByteArray());
			t.deserializeValue(null, -1, in);
			assertEquals(v, t.getValue());
		}
	}

	@Test
	public void testEquals() {
		ILILIntTag t1 = new ILILIntTag();
		ILILIntTag t2 = new ILILIntTag();
		ILILIntTag t3 = new ILILIntTag();
		t3.setValue(1);
		ILILIntTag t4 = new ILILIntTag(15);

		assertTrue(t1.equals(t1));
		assertTrue(t1.equals(t2));
		assertFalse(t1.equals(null));
		assertFalse(t1.equals(t3));
		assertFalse(t1.equals(t4));
	}

	@Test
	public void testGetSetValue() {
		Random random = new Random();

		for (int i = 0; i < 32; i++) {
			long v = random.nextLong();
			ILILIntTag t = new ILILIntTag();
			t.setValue(v);
			assertEquals(v, t.getValue());
		}
	}

	@Test
	public void testGetValueSize() {
		Random random = new Random();

		for (int i = 0; i < 32; i++) {
			long v = random.nextLong();
			ILILIntTag t = new ILILIntTag();
			t.setValue(v);

			assertEquals(ILIntCodec.getEncodedSize(v), t.getValueSize());
		}
	}

	@Test
	public void testILILIntTag() {
		ILILIntTag t = new ILILIntTag();

		t = new ILILIntTag();
		assertEquals(ILStandardTags.TAG_ILINT64.ordinal(), t.getId());
	}

	@Test
	public void testILILIntTagLong() {
		ILILIntTag t = new ILILIntTag();

		t = new ILILIntTag(0xFACADA);
		assertEquals(0xFACADA, t.getId());
	}

	@Test
	public void testSerializeValue() throws Exception {
		Random random = new Random();

		for (int i = 0; i < 32; i++) {
			long v = random.nextLong();
			ILILIntTag t = new ILILIntTag();
			t.setValue(v);

			ILMemoryTagDataWriter actual = new ILMemoryTagDataWriter();
			t.serializeValue(actual);
			ByteArrayOutputStream expected = new ByteArrayOutputStream();
			ILIntCodec.encode(v, expected);
			expected.close();
			assertArrayEquals(expected.toByteArray(), actual.toByteArray());
		}
	}
}
