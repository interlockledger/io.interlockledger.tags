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

public class ILUInt16TagTest {

	@Test
	public void testILUInt16Tag() {
		ILUInt16Tag t = new ILUInt16Tag();
		assertEquals(ILStandardTags.TAG_UINT16, t.getId());
	}

	@Test
	public void testILUInt16TagLong() {
		ILUInt16Tag t = new ILUInt16Tag(123);
		assertEquals(123, t.getId());
	}

	@Test
	public void testGetUnsignedValue() {
		ILUInt16Tag t = new ILUInt16Tag();
		
		t.setValue((short)0);
		assertEquals(0, t.getUnsignedValue());
		
		t.setValue((short)0x8000);
		assertEquals(0x8000, t.getUnsignedValue());
		
		t.setValue((short)-1);
		assertEquals(0xFFFF, t.getUnsignedValue());

	}

}
