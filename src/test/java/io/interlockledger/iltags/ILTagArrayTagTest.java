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

import io.interlockledger.iltags.ilint.ILIntCodec;
import io.interlockledger.iltags.io.ILMemoryTagDataReader;
import io.interlockledger.iltags.io.ILMemoryTagDataWriter;

public class ILTagArrayTagTest {

	@Test
	public void testSerializeValueEmpty() throws Exception {
		ILTagArrayTag t = new ILTagArrayTag();

		ILMemoryTagDataWriter w = new ILMemoryTagDataWriter(); 
		t.serializeValue(w);
		assertArrayEquals(new byte[1], w.toByteArray());	
	}
	
	@Test
	public void testSerializeValue() throws Exception {
		ILTagArrayTag t = new ILTagArrayTag();

		ILMemoryTagDataWriter w = new ILMemoryTagDataWriter(); 
		t.getValue().add(new ILNullTag());
		t.getValue().add(null);
		t.getValue().add(new ILBinary128Tag());
		t.getValue().add(new ILInt8Tag());
		t.serializeValue(w);

		ILMemoryTagDataWriter expected = new ILMemoryTagDataWriter();
		expected.writeILInt(4);
		ILNullTag.NULL.serialize(expected);
		ILNullTag.NULL.serialize(expected);
		(new ILBinary128Tag()).serialize(expected);
		(new ILInt8Tag()).serialize(expected);
		assertArrayEquals(expected.toByteArray(), w.toByteArray());			
	}

	@Test
	public void testGetValueSize() {
		ILTagArrayTag t = new ILTagArrayTag();
		
		assertEquals(1, t.getValueSize());
		
		t.getValue().add(new ILNullTag());
		t.getValue().add(null);
		t.getValue().add(new ILBinary128Tag());
		t.getValue().add(new ILInt8Tag());
		
		long size = ILIntCodec.getEncodedSize(t.getValue().size());
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
		ILTagArrayTag t = new ILTagArrayTag();
		
		t.deserializeValue(new ILTagFactory(), 1, new ILMemoryTagDataReader(new byte[1]));
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
		serialized.writeILInt(4);
		for (ILTag tag: a) {
			tag.serialize(serialized);
		}

		ILTagArrayTag t = new ILTagArrayTag();
		t.deserializeValue(new ILTagFactory(), serialized.getOffset(), new ILMemoryTagDataReader(serialized.toByteArray()));
		assertEquals(4, t.getValue().size());
		assertTrue(ILTagSequenceTag.equals(a, t.getValue()));
	}
	
	@Test(expected = ILTagException.class)
	public void testDeserializeValueFailedEmpty() throws Exception {

		ILTagArrayTag t = new ILTagArrayTag();
		t.deserializeValue(new ILTagFactory(), 0, new ILMemoryTagDataReader(new byte[0]));
	}

	@Test(expected = ILTagException.class)
	public void testDeserializeValueFailedWrongSize() throws Exception {
		ArrayList<ILTag> a = new ArrayList<ILTag>();
		a.add(new ILNullTag());
		a.add(new ILNullTag());
		a.add(new ILBinary128Tag());
		a.add(new ILInt8Tag());
		
		ILMemoryTagDataWriter serialized = new ILMemoryTagDataWriter();
		serialized.writeILInt(4);
		for (ILTag tag: a) {
			tag.serialize(serialized);
		}
		serialized.writeByte((byte)0);

		ILTagArrayTag t = new ILTagArrayTag();
		t.deserializeValue(new ILTagFactory(), serialized.getOffset(), new ILMemoryTagDataReader(serialized.toByteArray()));
	}

	@Test
	public void testILTagArrayTag() {
		ILTagArrayTag t = new ILTagArrayTag();

		assertEquals(ILStandardTags.TAG_ILTAG_ARRAY.ordinal(), t.getId());
		assertEquals(0, t.getValue().size());
	}

	@Test
	public void testILTagArrayTagLong() {
		ILTagArrayTag t = new ILTagArrayTag(123);

		assertEquals(123, t.getId());
		assertEquals(0, t.getValue().size());
	}
}
