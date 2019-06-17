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

import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import org.junit.Test;

public class ILInputStreamTagDataReaderTest {
	
	
	public static class RandomSkipInputStream extends FilterInputStream {
		
		private Random random = new Random();
		
		public RandomSkipInputStream(InputStream in) {
			super(in);
		}
		
		@Override
		public long skip(long n) throws IOException {
			return super.skip(Math.abs(random.nextLong()) % (n + 1));
		}
	}
	
	private InputStream createSample(int n) {
		byte [] bin = new byte[n];
		for (int i = 0; i < n; i++) {
			bin[i] = (byte)i;
		}
		return new ByteArrayInputStream(bin);
	}

	@Test
	public void testReadByteCore() throws Exception {
		
		try (ILInputStreamTagDataReader r = new ILInputStreamTagDataReader(createSample(10))) {
			for (int i = 0; i < 10; i++) {
				assertEquals((byte)i, r.readByteCore());
			}
		}
	}
	
	@Test(expected = ILTagNotEnoughDataException.class)
	public void testReadByteCoreFail() throws Exception {
		
		try (ILInputStreamTagDataReader r = new ILInputStreamTagDataReader(createSample(0))) {
			r.readByteCore();
		}
	}

	@Test
	public void testReadBytesCore() throws Exception {

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				for (int size = 0; size < 16; size++) {
					try (ILInputStreamTagDataReader r = new ILInputStreamTagDataReader(createSample(size))) {
						
						byte [] bin = new byte[i + j + size];
						r.readBytesCore(bin, i, size);
						
						byte [] expected = new byte[i + j + size];
						for (int k = 0; k < size; k++) {
							expected[i + k] = (byte)k;
						}
						assertArrayEquals(expected, bin);
					}
				}
			}
		}
	}
	
	@Test(expected = ILTagNotEnoughDataException.class)
	public void testReadBytesCoreFail() throws Exception {
		
		try (ILInputStreamTagDataReader r = new ILInputStreamTagDataReader(createSample(0))) {
			r.readBytesCore(new byte[1], 0, 1);
		}
	}

	@Test
	public void testSkipCore() throws Exception {
		
		try (ILInputStreamTagDataReader r = new ILInputStreamTagDataReader(new RandomSkipInputStream(createSample(256)))) {
			r.skip(255);
			assertEquals((byte)0xFF, r.readByte());
		}
	}
	
	@Test(expected = ILTagNotEnoughDataException.class)
	public void testSkipCoreFail() throws Exception {
		
		try (ILInputStreamTagDataReader r = new ILInputStreamTagDataReader(new RandomSkipInputStream(createSample(256)))) {
			r.skip(257);
		}
	}

	@Test
	public void testILInputStreamTagDataReader() throws Exception {
		InputStream in = createSample(0);
		ILInputStreamTagDataReader r = new ILInputStreamTagDataReader(in);
	
		assertSame(r.in, in);
		r.close();
	}

	@Test
	public void testClose() throws Exception {
		TestInputStream in = new TestInputStream(createSample(0));
		ILInputStreamTagDataReader r = new ILInputStreamTagDataReader(in);
	
		assertFalse(in.isCloseUsed());
		r.close();
		assertTrue(in.isCloseUsed());
	}
}
