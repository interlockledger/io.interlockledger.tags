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
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Random;

import org.junit.Test;

public class ILByteArrayTagTest {

	@Test
	public void testSerializeValue() throws Exception {
		Random random = new Random();
		
		for(int size = 0; size < 65536; size = (size << 1) + 1) {
			byte [] src = new byte[size];
			random.nextBytes(src);
			ILByteArrayTag t = new ILByteArrayTag();
			t.setValue(src);
			
			ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			try (DataOutputStream out = new DataOutputStream(bOut)){
				t.serializeValue(out);
			}
			assertArrayEquals(src, bOut.toByteArray());
		}
	}
	
	@Test(expected = ILTagException.class)
	public void testSerializeValueNotSet() throws Exception {
		ILByteArrayTag t = new ILByteArrayTag();
			
		try (DataOutputStream out = new DataOutputStream(new ByteArrayOutputStream())){
			t.serializeValue(out);
		}
	}	

	@Test
	public void testGetValueSize() {
		Random random = new Random();
		
		for(int size = 0; size < 65536; size = (size << 1) + 1) {
			byte [] src = new byte[size];
			random.nextBytes(src);
			ILByteArrayTag t = new ILByteArrayTag();
			t.setValue(src);
			assertEquals(src.length, t.getValueSize());
		}
	}

	@Test
	public void testDeserializeValue() throws Exception {
		Random random = new Random();
		
		for(int size = 0; size < 65536; size = (size << 1) + 1) {
			byte [] src = new byte[size];
			random.nextBytes(src);
			ILByteArrayTag t = new ILByteArrayTag();
			try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(src))){
				t.deserializeValue(null, src.length, in);
			}
			assertArrayEquals(src, t.getValue());
		}
	}

	@Test
	public void testILByteArrayTagLong() {
		ILByteArrayTag t = new ILByteArrayTag(0xFACADA);
		assertEquals(0xFACADA, t.getId());
	}

	@Test
	public void testILByteArrayTag() {
		ILByteArrayTag t = new ILByteArrayTag();
		assertEquals(ILTagStandardTags.TAG_BYTE_ARRAY, t.getId());
	}

	@Test
	public void testGetSetValue() {
		Random random = new Random();
		
		for(int size = 0; size < 65536; size = (size << 1) + 1) {
			byte [] src = new byte[size];
			random.nextBytes(src);
			ILByteArrayTag t = new ILByteArrayTag();
			t.setValue(src);
			assertSame(src, t.getValue());
			assertArrayEquals(src, t.getValue());
		}
	}

}