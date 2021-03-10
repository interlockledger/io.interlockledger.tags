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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.util.Random;

import org.junit.Test;

public class ILMemoryTagDataWriterTest {

	@Test
	public void testILMemoryTagDataWriter() {
		ILMemoryTagDataWriter w = new ILMemoryTagDataWriter();

		assertEquals(0, w.getOffset());
		assertArrayEquals(new byte[0], w.toByteArray());
	}

	@Test
	public void testToByteArray() throws Exception {
		Random r = new Random();
		ByteArrayOutputStream expected = new ByteArrayOutputStream();
		ILMemoryTagDataWriter w = new ILMemoryTagDataWriter();

		assertArrayEquals(new byte[0], w.toByteArray());
		byte[] bin = new byte[128];
		r.nextBytes(bin);
		w.writeBytes(bin);
		expected.write(bin);
		assertArrayEquals(expected.toByteArray(), w.toByteArray());
	}

	@Test
	public void testWriteByteCore() throws Exception {
		ByteArrayOutputStream expected = new ByteArrayOutputStream();

		ILMemoryTagDataWriter w = new ILMemoryTagDataWriter();
		for (int i = 0; i < 128; i++) {
			w.writeByteCore((byte) i);
			expected.write(i);
		}
		assertArrayEquals(expected.toByteArray(), w.toByteArray());
	}

	@Test
	public void testWriteBytesCore() throws Exception {
		Random r = new Random();
		ByteArrayOutputStream expected = new ByteArrayOutputStream();

		ILMemoryTagDataWriter w = new ILMemoryTagDataWriter();

		long offs = 0;
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				for (int size = 0; size < 16; size++) {
					byte[] bin = new byte[i + j + size];
					r.nextBytes(bin);
					w.writeBytes(bin, i, size);
					expected.write(bin, i, size);
					offs += size;
					assertEquals(w.getOffset(), offs);
				}
			}
		}
		assertArrayEquals(expected.toByteArray(), w.toByteArray());
	}
}
