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

import org.junit.Test;

import io.interlockledger.iltags.io.ILInputStreamTagDataReader;
import io.interlockledger.iltags.io.ILMemoryTagDataWriter;

public class ILNullTagTest {

	@Test
	public void testSerializeValue() throws Exception {
		ILNullTag t = new ILNullTag();
		ILMemoryTagDataWriter out = new ILMemoryTagDataWriter();
		
		t.serializeValue(out);
		assertArrayEquals(new byte[0], out.toByteArray());
	}

	@Test
	public void testGetValueSize() {
		ILNullTag t = new ILNullTag();
		
		assertEquals(0, t.getValueSize());
	}

	@Test
	public void testDeserializeValue() throws Exception {
		ILNullTag t = new ILNullTag();
		ByteArrayInputStream in = new ByteArrayInputStream(new byte[0]);
		
		t.deserializeValue(null, 0, new ILInputStreamTagDataReader(in));
		in.close();
	}

	@Test
	public void testILNullTag() {
		ILNullTag t = new ILNullTag();
		
		assertEquals(ILTagStandardTags.TAG_NULL, t.getId());
	}
}
