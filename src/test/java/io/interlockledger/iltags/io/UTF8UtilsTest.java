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
package io.interlockledger.iltags.io;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;

import org.junit.Test;

import static io.interlockledger.iltags.io.BaseDataTest.*;

public class UTF8UtilsTest {

	@Test
	public void testUTF8() {
		assertEquals("UTF-8", UTF8Utils.UTF8.name());
	}
	
	@Test
	public void testNewDecoder() {
		CharsetDecoder d = UTF8Utils.newDecoder();
		
		assertEquals(UTF8Utils.UTF8.name(), d.charset().name());
		assertEquals(CodingErrorAction.REPORT, d.malformedInputAction());
		assertEquals(CodingErrorAction.REPORT, d.unmappableCharacterAction());
	}

	@Test
	public void testNewEncoder() {
		CharsetEncoder e = UTF8Utils.newEncoder();
		
		assertEquals(UTF8Utils.UTF8.name(), e.charset().name());
		assertEquals(CodingErrorAction.REPORT, e.malformedInputAction());
		assertEquals(CodingErrorAction.REPORT, e.unmappableCharacterAction());
	}

	@Test
	public void testGetEncodedSize() throws Exception {

		for (int size = 0; size < 256; size++) {
			String s = genRandomString(size);
			ByteBuffer b = UTF8Utils.newEncoder().encode(CharBuffer.wrap(s));
			assertEquals(b.limit(), UTF8Utils.getEncodedSize(s));			
		}
	}
}
