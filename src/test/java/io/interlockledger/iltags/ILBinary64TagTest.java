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

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;

import org.junit.Test;

import io.interlockledger.iltags.io.ILInputStreamTagDataReader;
import io.interlockledger.iltags.io.ILMemoryTagDataWriter;

public class ILBinary64TagTest {

	@Test
	public void testSerializeValue() throws Exception {
		ByteBuffer b = ByteBuffer.allocate(8);
		
		double value = 1234.567d;
		ILBinary64Tag t = new ILBinary64Tag();
		b.putDouble(value);
		ILMemoryTagDataWriter w = new ILMemoryTagDataWriter();
		t.setValue(value);
		t.serializeValue(w);
		assertArrayEquals(b.array(), w.toByteArray());
	}

	@Test
	public void testDeserializeValueCore() throws Exception {
		ByteBuffer b = ByteBuffer.allocate(8);
		
		ILBinary64Tag t = new ILBinary64Tag();
		double value = 1234.567;
		b.putDouble(value);
		ILInputStreamTagDataReader r = new ILInputStreamTagDataReader(new ByteArrayInputStream(b.array()));
		t.deserializeValueCore(null, r);
		assertEquals(value, t.getValue(), 0.0d);
	}
	
	@Test(expected = ILTagException.class)
	public void testDeserializeValueCoreFail() throws Exception {
		ByteBuffer b = ByteBuffer.allocate(7);
		
		ILBinary64Tag t = new ILBinary64Tag();
		ILInputStreamTagDataReader r = new ILInputStreamTagDataReader(new ByteArrayInputStream(b.array()));
		t.deserializeValueCore(null, r);
	}

	@Test
	public void testILBinary64Tag() {
		ILBinary64Tag t = new ILBinary64Tag();
		
		assertEquals(ILStandardTags.TAG_BINARY64, t.getId());
		assertEquals(0.0d, t.getValue(), 0.0d);
	}

	@Test
	public void testILBinary64TagLong() {
		ILBinary64Tag t = new ILBinary64Tag(1234);
		
		assertEquals(1234, t.getId());
		assertEquals(0.0d, t.getValue(), 0.0d);
	}

	@Test
	public void testGetSetValue() {
		ILBinary64Tag t = new ILBinary64Tag();
		
		assertEquals(0.0d, t.getValue(), 0.0d);

		double value = 1234.567d;
		t.setValue(value);
		assertEquals(value, t.getValue(), 0.0d);
	}
}
