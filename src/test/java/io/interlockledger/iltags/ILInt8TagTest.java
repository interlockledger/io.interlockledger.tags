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

import java.nio.ByteBuffer;

import org.junit.Test;

import io.interlockledger.iltags.io.ILMemoryTagDataReader;
import io.interlockledger.iltags.io.ILMemoryTagDataWriter;

public class ILInt8TagTest {

	@Test
	public void testSerializeValue() throws Exception {
		ILInt8Tag t = new ILInt8Tag();
		ByteBuffer b = 	ByteBuffer.wrap(new byte[1]); 
		
		byte v = (byte)0x23;
		b.put(v);
		t.setValue(v);
		ILMemoryTagDataWriter w = new ILMemoryTagDataWriter();
		t.serializeValue(w);
		assertArrayEquals(b.array(), w.toByteArray());
	}

	@Test
	public void testDeserializeValueCore() throws Exception {
		ILInt8Tag t = new ILInt8Tag();
		ByteBuffer b = 	ByteBuffer.wrap(new byte[1]); 
		
		byte v = (byte)0x23;
		b.put(v);
		t.deserializeValueCore(null, new ILMemoryTagDataReader(b.array()));
		assertEquals(v, t.getValue());
	}
	
	@Test(expected = ILTagException.class)
	public void testDeserializeValueCoreFail() throws Exception {
		ILInt8Tag t = new ILInt8Tag();
		
		t.deserializeValueCore(null, new ILMemoryTagDataReader(new byte[0]));
	}

	@Test
	public void testILInt8TagLong() {
		ILInt8Tag t = new ILInt8Tag(123);
		
		assertEquals(123, t.getId());
		assertEquals(0, t.getValue());
	}

	@Test
	public void testILInt8Tag() {
		ILInt8Tag t = new ILInt8Tag();
		
		assertEquals(ILStandardTags.TAG_INT8, t.getId());
		assertEquals(0, t.getValue());
	}

	@Test
	public void testGetSetValue() {
		ILInt8Tag t = new ILInt8Tag();
		
		assertEquals(0, t.getValue());
		t.setValue((byte)113);
		assertEquals(113, t.getValue());
	}
	
	@Test
	public void testEquals() {
		ILInt8Tag t1 = new ILInt8Tag();
		ILInt8Tag t2 = new ILInt8Tag();
		ILInt8Tag t3 = new ILInt8Tag();
		t3.setValue((byte)1);
		ILInt8Tag t4 = new ILInt8Tag(15);
		
		assertTrue(t1.equals(t1));
		assertTrue(t1.equals(t2));
		assertFalse(t1.equals(null));
		assertFalse(t1.equals(t3));
		assertFalse(t1.equals(t4));
	}
}
