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

import static io.interlockledger.iltags.TestUtils.createSampleByteArray;
import static io.interlockledger.iltags.TestUtils.fillSampleByteArray;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.nio.ByteBuffer;

import org.junit.Test;

public class ILMemoryTagDataReaderTest {

	@Test
	public void testILMemoryTagDataReaderByteArray() throws Exception {

		for (int size = 0; size < 256; size++) {
			ILMemoryTagDataReader r = new ILMemoryTagDataReader(createSampleByteArray(size));
			byte[] v = new byte[size];
			r.readBytesCore(v, 0, v.length);
			assertArrayEquals(createSampleByteArray(size), v);
			try {
				r.readByteCore();
				fail();
			} catch (ILTagNotEnoughDataException e) {
			}
		}
	}

	@Test
	public void testILMemoryTagDataReaderByteArrayIntInt() throws Exception {

		for (int size = 0; size < 16; size++) {
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 16; j++) {
					byte[] src = new byte[i + size + j];
					fillSampleByteArray(src, i, size);
					ILMemoryTagDataReader r = new ILMemoryTagDataReader(src, i, size);
					byte[] v = new byte[size];
					r.readBytesCore(v, 0, v.length);
					assertArrayEquals(createSampleByteArray(size), v);
					try {
						r.readByteCore();
						fail();
					} catch (ILTagNotEnoughDataException e) {
					}
				}
			}
		}
	}

	@Test
	public void testILMemoryTagDataReaderByteBuffer() throws Exception {

		for (int size = 0; size < 16; size++) {
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 16; j++) {
					byte[] src = new byte[i + size + j];
					ByteBuffer b = ByteBuffer.wrap(src, i, size);
					ILMemoryTagDataReader r = new ILMemoryTagDataReader(b);
					fillSampleByteArray(src, i, size); // Ensure the same buffer
					byte[] v = new byte[size];
					r.readBytesCore(v, 0, v.length);
					assertArrayEquals(createSampleByteArray(size), v);
					assertEquals(i, b.position());
					try {
						r.readByteCore();
						fail();
					} catch (ILTagNotEnoughDataException e) {
					}
				}
			}
		}
	}

	@Test
	public void testILMemoryTagDataReaderByteBufferDirect() throws Exception {

		ByteBuffer b = ByteBuffer.allocateDirect(32);
		ILMemoryTagDataReader r = new ILMemoryTagDataReader(b);

		// Change the value on b
		for (int i = 0; i < 32; i++) {
			b.put((byte) i);
		}

		byte[] v = new byte[32];
		r.readBytesCore(v, 0, v.length);
		assertArrayEquals(createSampleByteArray(32), v);
	}

	@Test
	public void testReadByteCore() throws Exception {

		for (int size = 0; size < 256; size++) {
			ILMemoryTagDataReader r = new ILMemoryTagDataReader(createSampleByteArray(size));
			for (int i = 0; i < size; i++) {
				assertEquals((byte) i, r.readByteCore());
			}
			try {
				r.readByteCore();
				fail();
			} catch (ILTagNotEnoughDataException e) {
			}
		}
	}

	@Test
	public void testReadBytesCore() throws Exception {

		for (int size = 0; size < 16; size++) {
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 16; j++) {
					ILMemoryTagDataReader r = new ILMemoryTagDataReader(createSampleByteArray(size));

					byte[] bin = new byte[i + size + j];
					r.readBytesCore(bin, i, size);
					byte[] expected = new byte[i + size + j];
					fillSampleByteArray(expected, i, size);
					assertArrayEquals(expected, bin);
					try {
						r.readBytesCore(bin, i, j + 1);
						fail();
					} catch (ILTagNotEnoughDataException e) {
					}
				}
			}
		}
	}

	@Test
	public void testSkipCore() throws Exception {

		for (int size = 0; size < 256; size++) {
			for (int i = 0; i <= size; i++) {
				ILMemoryTagDataReader r = new ILMemoryTagDataReader(createSampleByteArray(size));
				r.skipCore(i);
				if (i < size) {
					assertEquals((byte) i, r.readByteCore());
				} else {
					try {
						r.readByteCore();
						fail();
					} catch (ILTagNotEnoughDataException e) {
					}
				}
			}
		}
	}

	@Test(expected = ILTagNotEnoughDataException.class)
	public void testSkipCoreFail() throws Exception {
		ILMemoryTagDataReader r = new ILMemoryTagDataReader(createSampleByteArray(16));
		r.skip(17);
	}
}
