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

import io.interlockledger.iltags.ilint.ILIntCodec;
import io.interlockledger.iltags.io.ILMemoryTagDataReader;
import io.interlockledger.iltags.io.ILMemoryTagDataWriter;

public class ILILIntArrayTagTest {
	
	private Random random = new Random();
	
	private Long [] createSample(int size) {
		Long [] ret = new Long[size];
		for (int i = 0; i < size; i++) {
			ret[i] = random.nextLong();
		}			
		return ret;		
	}

	@Test
	public void testSerializeValue() throws Exception {
		
		for (int size = 0; size < 1024; size += 33) {
			Long [] sample = createSample(size); 
			
			ILILIntArrayTag t = new ILILIntArrayTag();
			ILMemoryTagDataWriter expected = new ILMemoryTagDataWriter();
			expected.writeILInt(size);
			for (Long l: sample) {
				t.getValue().add(l);
				expected.writeILInt(l);
			}
			
			ILMemoryTagDataWriter w = new ILMemoryTagDataWriter();
			t.serializeValue(w);
			assertArrayEquals(expected.toByteArray(), w.toByteArray());
		}
	}

	@Test
	public void testGetValueSize() {

		for (int size = 0; size < 1024; size += 33) {
			Long [] sample = createSample(size); 
			
			ILILIntArrayTag t = new ILILIntArrayTag();
			for (Long l: sample) {
				t.getValue().add(l);
			}
			
			long expected = ILIntCodec.getEncodedSize(size);
			for (Long l: sample) {
				expected += ILIntCodec.getEncodedSize(l);
			}
			assertEquals(expected, t.getValueSize());
		}
	}

	@Test
	public void testDeserializeValue() throws Exception {
	
		for (int size = 0; size < 1024; size += 33) {
			Long [] sample = createSample(size); 
			
			ILMemoryTagDataWriter src = new ILMemoryTagDataWriter();
			src.writeILInt(size);
			for (Long l: sample) {
				src.writeILInt(l);
			}

			ILILIntArrayTag t = new ILILIntArrayTag();
			t.deserializeValue(null, src.getOffset(), new ILMemoryTagDataReader(src.toByteArray()));
			assertEquals(size, t.getValue().size());
			for (int i = 0; i < size; i++) {
				assertEquals(sample[i], t.getValue().get(i));
			}			
		}
	}

	@Test
	public void testILILIntArrayTag() {
		ILILIntArrayTag t = new ILILIntArrayTag();
		
		assertEquals(ILStandardTags.TAG_ILINT64_ARRAY, t.getId());
		assertEquals(0, t.getValue().size());
	}

	@Test
	public void testILILIntArrayTagLong() {
		ILILIntArrayTag t = new ILILIntArrayTag(1234);
		
		assertEquals(1234, t.getId());
		assertEquals(0, t.getValue().size());
	}

	@Test
	public void testGetValue() {
		ILILIntArrayTag t = new ILILIntArrayTag();
		
		assertNotNull(t.getValue());
		assertEquals(0, t.getValue().size());
	}
	
	@Test
	public void testEquals() {
		ILILIntArrayTag t1 = new ILILIntArrayTag();
		t1.getValue().add(1l);
		ILILIntArrayTag t2 = new ILILIntArrayTag();
		t2.getValue().add(1l);
		ILILIntArrayTag t3 = new ILILIntArrayTag();
		t3.getValue().add(2l);
		ILILIntArrayTag t4 = new ILILIntArrayTag(15);
		t4.getValue().add(1l);
		
		assertTrue(t1.equals(t1));
		assertTrue(t1.equals(t2));
		assertFalse(t1.equals(null));
		assertFalse(t1.equals(t3));
		assertFalse(t1.equals(t4));
	}
}
