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

import org.junit.Test;

import io.interlockledger.iltags.io.ILMemoryTagDataReader;

public class ILBooleanTagTest {

	@Test
	public void testSerializeValue() {
		ILBooleanTag t = new ILBooleanTag();
		
		assertEquals(ILStandardTags.TAG_BOOL.ordinal(), t.getId());
		assertFalse(t.getValue());
	}

	@Test
	public void testDeserializeValueCore() throws Exception {
		byte [] v = new byte[1];
		
		ILBooleanTag t = new ILBooleanTag();
		t.deserializeValueCore(null, new ILMemoryTagDataReader(v));
		assertFalse(t.getValue());
		
		v[0] = 1;
		t.deserializeValueCore(null, new ILMemoryTagDataReader(v));
		assertTrue(t.getValue());
		
		for (int i = 2; i < 256; i++) {
			try {
				v[0] = (byte)i;
				t.deserializeValueCore(null, new ILMemoryTagDataReader(v));
				fail();
			} catch (ILTagException e) {}
		}
	}
	
	@Test(expected = ILTagException.class)
	public void testDeserializeValueCoreFail() throws Exception {
	
		ILBooleanTag t = new ILBooleanTag();
		t.deserializeValueCore(null, new ILMemoryTagDataReader(new byte[0]));
	}
	
	@Test
	public void testILBooleanTag() {
		ILBooleanTag t = new ILBooleanTag();
		
		assertEquals(ILStandardTags.TAG_BOOL.ordinal(), t.getId());
		assertFalse(t.getValue());
	}

	@Test
	public void testILBooleanTagLong() {
		ILBooleanTag t = new ILBooleanTag(1234);
		
		assertEquals(1234, t.getId());
		assertFalse(t.getValue());
	}

	@Test
	public void testGetSetValue() {
		ILBooleanTag t = new ILBooleanTag();
		
		assertFalse(t.getValue());
		t.setValue(true);
		assertTrue(t.getValue());
		t.setValue(false);
		assertFalse(t.getValue());
	}
	
	@Test
	public void testEquals() {
		ILBooleanTag t1 = new ILBooleanTag();
		ILBooleanTag t2 = new ILBooleanTag();
		ILBooleanTag t3 = new ILBooleanTag();
		t3.setValue(true);
		ILBooleanTag t4 = new ILBooleanTag(15);
		
		assertTrue(t1.equals(t1));
		assertTrue(t1.equals(t2));
		assertFalse(t1.equals(null));
		assertFalse(t1.equals(t3));
		assertFalse(t1.equals(t4));
	}
}
