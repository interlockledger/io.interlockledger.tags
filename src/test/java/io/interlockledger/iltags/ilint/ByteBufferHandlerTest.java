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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.nio.ByteBuffer;

import org.junit.Test;

public class ByteBufferHandlerTest {

	@Test
	public void testGet() throws Exception {
		byte[] src = new byte[256];
		for (int i = 0; i < src.length; i++) {
			src[i] = (byte) i;
		}
		ByteBuffer in = ByteBuffer.wrap(src);

		for (int i = 0; i < src.length; i++) {
			assertEquals(i, ByteBufferHandler.INSTANCE.get(in));
		}

		try {
			ByteBufferHandler.INSTANCE.get(in);
			fail();
		} catch (ILIntException e) {
		}
	}

	@Test
	public void testInstance() {

		assertNotNull(ByteBufferHandler.INSTANCE);
	}

	@Test
	public void testWrite() throws Exception {

		ByteBuffer out = ByteBuffer.allocate(256);

		for (int i = 0; i < 256; i++) {
			ByteBufferHandler.INSTANCE.write(i, out);
		}
		assertEquals(0, out.remaining());
		out.rewind();

		for (int i = 0; i < 256; i++) {
			assertEquals(i, out.get() & 0xFF);
		}

		try {
			ByteBufferHandler.INSTANCE.write(0, out);
			fail();
		} catch (ILIntException e) {
		}
	}

}
