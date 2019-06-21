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

public class ILInt32TagTest {

	@Test
	public void testSerializeValue() throws Exception {
		ILInt32Tag t = new ILInt32Tag();
		ByteBuffer b = 	ByteBuffer.wrap(new byte[4]); 
		
		int v = 0x01234567;
		b.putInt(v);
		t.setValue(v);
		ILMemoryTagDataWriter w = new ILMemoryTagDataWriter();
		t.serializeValue(w);
		assertArrayEquals(b.array(), w.toByteArray());
	}

	@Test
	public void testDeserializeValueCore() throws Exception {
		ILInt32Tag t = new ILInt32Tag();
		ByteBuffer b = 	ByteBuffer.wrap(new byte[4]); 
		
		int v = (short)0x01234567;
		b.putInt(v);
		t.deserializeValueCore(null, new ILMemoryTagDataReader(b.array()));
		assertEquals(v, t.getValue());
	}

	@Test(expected = ILTagException.class)
	public void testDeserializeValueCoreFail() throws Exception {
		ILInt32Tag t = new ILInt32Tag();
		
		t.deserializeValueCore(null, new ILMemoryTagDataReader(new byte[3]));
	}

	
	@Test
	public void testILInt32TagLong() {
		ILInt32Tag t = new ILInt32Tag(123);
		
		assertEquals(123, t.getId());
		assertEquals(0, t.getValue());
	}

	@Test
	public void testILInt32Tag() {
		ILInt32Tag t = new ILInt32Tag();
		
		assertEquals(ILStandardTags.TAG_INT32, t.getId());
		assertEquals(0, t.getValue());
	}

	@Test
	public void testGetSetValue() {
		ILInt32Tag t = new ILInt32Tag();
		
		assertEquals(0, t.getValue());
		t.setValue(113);
		assertEquals(113, t.getValue());
	}

	@Test
	public void testEquals() {
		ILInt32Tag t1 = new ILInt32Tag();
		ILInt32Tag t2 = new ILInt32Tag();
		ILInt32Tag t3 = new ILInt32Tag();
		t3.setValue(1);
		ILInt32Tag t4 = new ILInt32Tag(15);
		
		assertTrue(t1.equals(t1));
		assertTrue(t1.equals(t2));
		assertFalse(t1.equals(null));
		assertFalse(t1.equals(t3));
		assertFalse(t1.equals(t4));
	}
}
