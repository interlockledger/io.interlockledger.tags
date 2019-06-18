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

public class ILInt16TagTest {

	@Test
	public void testSerializeValue() throws Exception {
		ILInt16Tag t = new ILInt16Tag();
		ByteBuffer b = 	ByteBuffer.wrap(new byte[2]); 
		
		short v = (short)0x0123;
		b.putShort(v);
		t.setValue(v);
		ILMemoryTagDataWriter w = new ILMemoryTagDataWriter();
		t.serializeValue(w);
		assertArrayEquals(b.array(), w.toByteArray());
	}

	@Test
	public void testDeserializeValueCore() throws Exception {
		ILInt16Tag t = new ILInt16Tag();
		ByteBuffer b = 	ByteBuffer.wrap(new byte[2]); 
		
		short v = (short)0x0123;
		b.putShort(v);
		t.deserializeValueCore(null, new ILMemoryTagDataReader(b.array()));
		assertEquals(v, t.getValue());
	}
	
	@Test(expected = ILTagException.class)
	public void testDeserializeValueCoreFail() throws Exception {
		ILInt16Tag t = new ILInt16Tag();
		
		t.deserializeValueCore(null, new ILMemoryTagDataReader(new byte[1]));
	}

	@Test
	public void testILInt16TagLong() {
		ILInt16Tag t = new ILInt16Tag(123);
		
		assertEquals(123, t.getId());
		assertEquals(0, t.getValue());
	}

	@Test
	public void testILInt16Tag() {
		ILInt16Tag t = new ILInt16Tag();
		
		assertEquals(ILStandardTags.TAG_INT16, t.getId());
		assertEquals(0, t.getValue());
	}

	@Test
	public void testGetSetValue() {
		ILInt16Tag t = new ILInt16Tag();
		
		assertEquals(0, t.getValue());
		t.setValue((short)113);
		assertEquals(113, t.getValue());
	}

}
