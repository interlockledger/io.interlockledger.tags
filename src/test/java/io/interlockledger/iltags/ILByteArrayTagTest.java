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

import java.util.Random;

import org.junit.Test;

import io.interlockledger.iltags.io.ILMemoryTagDataReader;
import io.interlockledger.iltags.io.ILMemoryTagDataWriter;

public class ILByteArrayTagTest {

	@Test
	public void testSerializeValue() throws Exception {
		Random random = new Random();
		
		for(int size = 0; size < 65536; size = (size << 1) + 1) {
			byte [] src = new byte[size];
			random.nextBytes(src);
			ILByteArrayTag t = new ILByteArrayTag();
			t.setValue(src);
			
			ILMemoryTagDataWriter out = new ILMemoryTagDataWriter();
			t.serializeValue(out);
			assertArrayEquals(src, out.toByteArray());
		}
	}
	
	@Test(expected = IllegalStateException.class)
	public void testSerializeValueNotSet() throws Exception {
		ILByteArrayTag t = new ILByteArrayTag();

		ILMemoryTagDataWriter out = new ILMemoryTagDataWriter();
		t.serializeValue(out);
	}	

	@Test
	public void testGetValueSize() {
		Random random = new Random();
		
		for(int size = 0; size < 65536; size = (size << 1) + 1) {
			byte [] src = new byte[size];
			random.nextBytes(src);
			ILByteArrayTag t = new ILByteArrayTag();
			t.setValue(src);
			assertEquals(src.length, t.getValueSize());
		}
	}

	@Test
	public void testDeserializeValue() throws Exception {
		Random random = new Random();
		
		for(int size = 0; size < 65536; size = (size << 1) + 1) {
			byte [] src = new byte[size];
			random.nextBytes(src);
			ILByteArrayTag t = new ILByteArrayTag();
			ILMemoryTagDataReader in = new ILMemoryTagDataReader(src);
			t.deserializeValue(null, src.length, in);
			assertArrayEquals(src, t.getValue());
		}
	}

	@Test
	public void testILByteArrayTagLong() {
		ILByteArrayTag t = new ILByteArrayTag(0xFACADA);
		assertEquals(0xFACADA, t.getId());
	}

	@Test
	public void testILByteArrayTag() {
		ILByteArrayTag t = new ILByteArrayTag();
		assertEquals(ILStandardTags.TAG_BYTE_ARRAY.ordinal(), t.getId());
	}

	@Test
	public void testGetSetValue() {
		Random random = new Random();
		
		for(int size = 0; size < 65536; size = (size << 1) + 1) {
			byte [] src = new byte[size];
			random.nextBytes(src);
			ILByteArrayTag t = new ILByteArrayTag();
			t.setValue(src);
			assertSame(src, t.getValue());
			assertArrayEquals(src, t.getValue());
		}
	}
	@Test
	public void testEquals() {
		byte [] v0 = new byte[1];
		byte [] v1 = new byte[1];
		v1[0]++;
		
		ILByteArrayTag t1 = new ILByteArrayTag();
		t1.setValue(v0);
		ILByteArrayTag t2 = new ILByteArrayTag();
		t2.setValue(v0);
		ILByteArrayTag t3 = new ILByteArrayTag();
		t3.setValue(v1);
		ILByteArrayTag t4 = new ILByteArrayTag(15);
		t4.setValue(v0);
		
		assertTrue(t1.equals(t1));
		assertTrue(t1.equals(t2));
		assertTrue((new ILByteArrayTag()).equals(new ILByteArrayTag()));
		
		assertFalse(t1.equals(null));
		assertFalse(t1.equals(t3));
		assertFalse(t1.equals(t4));
	}
}
