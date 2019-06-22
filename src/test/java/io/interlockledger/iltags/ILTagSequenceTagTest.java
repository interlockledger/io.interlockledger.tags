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

import java.util.ArrayList;

import org.junit.Test;

import io.interlockledger.iltags.io.ILMemoryTagDataReader;
import io.interlockledger.iltags.io.ILMemoryTagDataWriter;

public class ILTagSequenceTagTest {

	@Test
	public void testSerializeValueEmpty() throws Exception {
		ILTagSequenceTag t = new ILTagSequenceTag();

		ILMemoryTagDataWriter w = new ILMemoryTagDataWriter(); 
		t.serializeValue(w);
		assertArrayEquals(new byte[0], w.toByteArray());		
	}
	
	@Test
	public void testSerializeValue() throws Exception {
		ILTagSequenceTag t = new ILTagSequenceTag();

		ILMemoryTagDataWriter w = new ILMemoryTagDataWriter(); 
		t.getValue().add(new ILNullTag());
		t.getValue().add(null);
		t.getValue().add(new ILBinary128Tag());
		t.getValue().add(new ILInt8Tag());
		t.serializeValue(w);

		ILMemoryTagDataWriter expected = new ILMemoryTagDataWriter(); 
		ILNullTag.NULL.serialize(expected);
		ILNullTag.NULL.serialize(expected);
		(new ILBinary128Tag()).serialize(expected);
		(new ILInt8Tag()).serialize(expected);
		assertArrayEquals(expected.toByteArray(), w.toByteArray());		
	}

	@Test
	public void testGetValueSize() {
		ILTagSequenceTag t = new ILTagSequenceTag();
		
		assertEquals(0, t.getValueSize());
		
		t.getValue().add(new ILNullTag());
		t.getValue().add(null);
		t.getValue().add(new ILBinary128Tag());
		t.getValue().add(new ILInt8Tag());
		
		long size = 0;
		for (ILTag tag: t.getValue()) {
			if (tag != null) {
				size += tag.getTagSize();
			} else {
				size += ILNullTag.NULL.getTagSize();
			}
		}
		assertEquals(size, t.getValueSize());
	}
	
	@Test
	public void testDeserializeValueEmpty() throws Exception {
		ILTagSequenceTag t = new ILTagSequenceTag();
		
		t.deserializeValue(new ILTagFactory(), 0, new ILMemoryTagDataReader(new byte[0]));
		assertEquals(0, t.getValue().size());
	}

	@Test
	public void testDeserializeValue() throws Exception {
		ArrayList<ILTag> a = new ArrayList<ILTag>();
		a.add(new ILNullTag());
		a.add(new ILNullTag());
		a.add(new ILBinary128Tag());
		a.add(new ILInt8Tag());
		
		ILMemoryTagDataWriter serialized = new ILMemoryTagDataWriter();
		for (ILTag tag: a) {
			tag.serialize(serialized);
		}
		ILTagSequenceTag t = new ILTagSequenceTag();
		
		t.deserializeValue(new ILTagFactory(), serialized.getOffset(), new ILMemoryTagDataReader(serialized.toByteArray()));
		
		assertEquals(4, t.getValue().size());
		assertTrue(ILTagSequenceTag.equals(a, t.getValue()));
	}
	
	@Test(expected = ILTagException.class)
	public void testDeserializeValueFailed() throws Exception {
		ILTagSequenceTag t = new ILTagSequenceTag();

		byte [] b = new byte[2];
		b[0] = 1;
		t.deserializeValue(new ILTagFactory(), 1, new ILMemoryTagDataReader(b));
	}

	@Test
	public void testILTagSequenceTag() {
		ILTagSequenceTag t = new ILTagSequenceTag();

		assertEquals(ILStandardTags.TAG_ILTAG_SEQ, t.getId());
		assertEquals(0, t.getValue().size());
	}

	@Test
	public void testILTagSequenceTagLong() {
		ILTagSequenceTag t = new ILTagSequenceTag(123);

		assertEquals(123, t.getId());
		assertEquals(0, t.getValue().size());
	}

	@Test
	public void testGetValue() {
		ILTagSequenceTag t = new ILTagSequenceTag(123);

		assertEquals(0, t.getValue().size());
		assertTrue(t.getValue() instanceof ArrayList<?>);
	}

	@Test
	public void testSetValue() {
		ILTagSequenceTag t = new ILTagSequenceTag(123);
		ArrayList<ILTag> a = new ArrayList<ILTag>();

		a.add(new ILNullTag());
		a.add(null);
		a.add(new ILInt8Tag());
		assertEquals(0, t.getValue().size());

		t.setValue(a);
		assertEquals(a.size(), t.getValue().size());
		assertTrue(ILTagSequenceTag.equals(a, t.getValue()));
		
		t.setValue(a);
		assertEquals(a.size(), t.getValue().size());
		assertTrue(ILTagSequenceTag.equals(a, t.getValue()));		
	}

	@Test
	public void testEqualsListOfILTagListOfILTag() {
		ArrayList<ILTag> a = new ArrayList<ILTag>();
		ArrayList<ILTag> b = new ArrayList<ILTag>();
		ArrayList<ILTag> c = new ArrayList<ILTag>();
		
		assertTrue(ILTagSequenceTag.equals(null, null));
		assertTrue(ILTagSequenceTag.equals(a, a));
		assertTrue(ILTagSequenceTag.equals(a, b));
		assertTrue(ILTagSequenceTag.equals(b, a));
		
		a.add(new ILNullTag());
		a.add(null);
		a.add(new ILInt8Tag());
		b.add(new ILNullTag());
		b.add(null);
		b.add(new ILInt8Tag());
		assertTrue(ILTagSequenceTag.equals(a, b));
		assertTrue(ILTagSequenceTag.equals(b, a));
		
		b.set(1, new ILNullTag());
		assertTrue(ILTagSequenceTag.equals(a, b));
		assertTrue(ILTagSequenceTag.equals(b, a));
		
		b.add(new ILNullTag());
		assertFalse(ILTagSequenceTag.equals(a, b));
		assertFalse(ILTagSequenceTag.equals(b, a));

		c.add(new ILNullTag());
		c.add(null);
		c.add(new ILInt8Tag());
		((ILInt8Tag)c.get(2)).setValue((byte)1);
		assertFalse(ILTagSequenceTag.equals(a, c));
		assertFalse(ILTagSequenceTag.equals(c, a));
		
		assertFalse(ILTagSequenceTag.equals(a, null));
		assertFalse(ILTagSequenceTag.equals(null, b));
	}
}
