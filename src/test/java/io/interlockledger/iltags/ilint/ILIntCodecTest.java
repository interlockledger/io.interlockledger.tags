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
package io.interlockledger.iltags.ilint;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;

import org.junit.Test;

public class ILIntCodecTest {

	@Test
	public void testGetEncodedSize() throws Exception {
		
		assertEquals(1, ILIntCodec.getEncodedSize(0));
		assertEquals(1, ILIntCodec.getEncodedSize(247));

		assertEquals(2, ILIntCodec.getEncodedSize(248));
		assertEquals(2, ILIntCodec.getEncodedSize(0xFF + 248));

		assertEquals(3, ILIntCodec.getEncodedSize(0xFF + 248 + 1));
		assertEquals(3, ILIntCodec.getEncodedSize(0xFFFF + 248));

		assertEquals(4, ILIntCodec.getEncodedSize(0xFFFF + 248 + 1));
		assertEquals(4, ILIntCodec.getEncodedSize(0xFFFFFF + 248));

		assertEquals(5, ILIntCodec.getEncodedSize(0xFFFFFF + 248 + 1));
		assertEquals(5, ILIntCodec.getEncodedSize(0xFFFFFFFFl + 248));

		assertEquals(6, ILIntCodec.getEncodedSize(0xFFFFFFFFl + 248 + 1));
		assertEquals(6, ILIntCodec.getEncodedSize(0xFFFFFFFFFFl + 248));

		assertEquals(7, ILIntCodec.getEncodedSize(0xFFFFFFFFFFl + 248 + 1));
		assertEquals(7, ILIntCodec.getEncodedSize(0xFFFFFFFFFFFFl + 248));

		assertEquals(8, ILIntCodec.getEncodedSize(0xFFFFFFFFFFFFl + 248 + 1));
		assertEquals(8, ILIntCodec.getEncodedSize(0xFFFFFFFFFFFFFFl + 248));

		assertEquals(9, ILIntCodec.getEncodedSize(0xFFFFFFFFFFFFFFl + 248 + 1));
		assertEquals(9, ILIntCodec.getEncodedSize(0xFFFFFFFFFFFFFFFFl));
	}

	@Test
	public void testDecodeSingleByte() throws Exception {
		ByteBuffer buff = ByteBuffer.allocate(1);

		for (int i = 0; i < 248; i++) {
			buff.rewind();
			buff.put((byte)i);
			buff.rewind();
			assertEquals(i, ILIntCodec.decode(buff) );
		}

		for (int i = 248; i < 256; i++) {
			buff.rewind();
			buff.put((byte)i);
			buff.rewind();
			try {
				ILIntCodec.decode(buff);
				fail();
			} catch (ILIntException e) {}
		}
	}

	@Test
	public void testEncodeSingleByte() throws Exception {
		
		for (long v = 0; v < 248; v++) {
			ByteBuffer buff = ByteBuffer.allocate(1);
			assertEquals(1, buff.remaining());
			assertEquals(1, ILIntCodec.encode(v, buff));
			assertEquals(0, buff.remaining());
			buff.rewind();
			assertEquals(v, buff.get() & 0xFFl);			
		}
	}

	@Test
	public void testEncodeMultibyte() throws Exception {

		// Lower bound
		ByteBuffer buff = ByteBuffer.allocate(2);
		assertEquals(2, buff.remaining());
		assertEquals(2, ILIntCodec.encode(248, buff));
		assertEquals(0, buff.remaining());
		buff.rewind();
		assertEquals(248, buff.get() & 0xFF);			
		assertEquals(0, buff.get() & 0xFF);			

		// Lower bound
		long addition = 0x100;
		for (int size = 3; size < 10; size++) {
			long v = 248 + addition;
			buff = ByteBuffer.allocate(size);
			assertEquals(size, ILIntCodec.encode(v, buff));
			assertEquals(0, buff.remaining());
			buff.rewind();

			assertEquals(248 + (size - 2), buff.get() & 0xFF);	
			assertEquals(0x01, buff.get() & 0xFF);
			for (int i = 3; i < size + 1; i++) {
				assertEquals(0x0, buff.get() & 0xFF);		
			}
			addition = addition << 8;
		}
		
		// Upper bound
		addition = 0xFFFF;
		for (int size = 3; size < 9; size++) {
			long v = 248 + addition;
			buff = ByteBuffer.allocate(size);
			assertEquals(size, ILIntCodec.encode(v, buff));
			assertEquals(0, buff.remaining());
			buff.rewind();
			assertEquals(248 + (size - 2), buff.get() & 0xFF);	
			for (int i = 2; i < size + 1; i++) {
				assertEquals(0xFF, buff.get() & 0xFF);		
			}
			addition = (addition << 8) | 0xFF;
		}		

		// Max
		buff = ByteBuffer.allocate(9);
		assertEquals(9, buff.remaining());
		assertEquals(9, ILIntCodec.encode(0xFFFFFFFFFFFFFFFFl, buff));
		assertEquals(0, buff.remaining());
		buff.rewind();
		assertEquals(0xFF, buff.get() & 0xFF);			
		assertEquals(0xFF, buff.get() & 0xFF);			
		assertEquals(0xFF, buff.get() & 0xFF);			
		assertEquals(0xFF, buff.get() & 0xFF);			
		assertEquals(0xFF, buff.get() & 0xFF);			
		assertEquals(0xFF, buff.get() & 0xFF);			
		assertEquals(0xFF, buff.get() & 0xFF);			
		assertEquals(0xFF, buff.get() & 0xFF);			
		assertEquals(0x07, buff.get() & 0xFF);			
	}
}
