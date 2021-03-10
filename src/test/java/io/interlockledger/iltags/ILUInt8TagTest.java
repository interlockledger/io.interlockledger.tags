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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ILUInt8TagTest {

	@Test
	public void testGetUnsignedValue() {
		ILUInt8Tag t = new ILUInt8Tag();

		t.setValue((byte) 0);
		assertEquals(0, t.getUnsignedValue());

		t.setValue((byte) 0x80);
		assertEquals(0x80, t.getUnsignedValue());

		t.setValue((byte) -1);
		assertEquals(0xFF, t.getUnsignedValue());
	}

	@Test
	public void testILUInt8Tag() {
		ILUInt8Tag t = new ILUInt8Tag();
		assertEquals(ILStandardTags.TAG_UINT8.ordinal(), t.getId());
	}

	@Test
	public void testILUInt8TagLong() {
		ILUInt8Tag t = new ILUInt8Tag(123);
		assertEquals(123, t.getId());
	}

}
