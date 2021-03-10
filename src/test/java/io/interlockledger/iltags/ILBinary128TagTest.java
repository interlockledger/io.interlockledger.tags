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
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import io.interlockledger.iltags.io.ILMemoryTagDataReader;
import io.interlockledger.iltags.io.ILMemoryTagDataWriter;

public class ILBinary128TagTest {

	@Test
	public void testDeserializeValueCore() throws Exception {
		Random random = new Random();

		for (int i = 0; i < 16; i++) {
			byte[] v = new byte[16];
			random.nextBytes(v);

			ILBinary128Tag t = new ILBinary128Tag();
			ILMemoryTagDataReader r = new ILMemoryTagDataReader(v);
			t.deserializeValueCore(null, r);
			assertArrayEquals(v, t.getValue());
		}
	}

	@Test(expected = ILTagException.class)
	public void testDeserializeValueCoreFail() throws Exception {
		byte[] v = new byte[15];

		ILBinary128Tag t = new ILBinary128Tag();
		ILMemoryTagDataReader r = new ILMemoryTagDataReader(v);
		t.deserializeValueCore(null, r);
	}

	@Test
	public void testEquals() {
		byte[] v0 = new byte[16];
		byte[] v1 = new byte[16];
		v1[15]++;

		ILBinary128Tag t1 = new ILBinary128Tag();
		t1.setValue(v0);
		ILBinary128Tag t2 = new ILBinary128Tag();
		t2.setValue(v0);
		ILBinary128Tag t3 = new ILBinary128Tag();
		t3.setValue(v1);
		ILBinary128Tag t4 = new ILBinary128Tag(15);
		t4.setValue(v0);

		assertTrue(t1.equals(t1));
		assertTrue(t1.equals(t2));
		assertFalse(t1.equals(null));
		assertFalse(t1.equals(t3));
		assertFalse(t1.equals(t4));
	}

	@Test
	public void testGetSetValue() {
		Random random = new Random();

		for (int i = 0; i < 16; i++) {
			byte[] v = new byte[16];
			random.nextBytes(v);

			ILBinary128Tag t = new ILBinary128Tag();
			assertArrayEquals(new byte[16], t.getValue());
			t.setValue(v);
			assertNotSame(v, t.getValue());
			assertArrayEquals(v, t.getValue());
		}
	}

	@Test
	public void testILBinary128Tag() {
		ILBinary128Tag t = new ILBinary128Tag();
		assertEquals(ILStandardTags.TAG_BINARY128.ordinal(), t.getId());
		assertArrayEquals(new byte[16], t.getValue());
	}

	@Test
	public void testILBinary128TagLong() {
		ILBinary128Tag t = new ILBinary128Tag(1234);
		assertEquals(1234, t.getId());
		assertArrayEquals(new byte[16], t.getValue());
	}

	@Test
	public void testSerializeValue() throws Exception {
		Random random = new Random();

		for (int i = 0; i < 16; i++) {
			byte[] v = new byte[16];
			random.nextBytes(v);

			ILBinary128Tag t = new ILBinary128Tag();
			t.setValue(v);
			ILMemoryTagDataWriter w = new ILMemoryTagDataWriter();
			t.serializeValue(w);
			assertArrayEquals(v, w.toByteArray());
		}
	}

}
