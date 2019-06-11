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
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;

import org.junit.Test;

public class SubInputStreamTest {
	
	private static final byte [] SAMPLE_DATA;
	
	static {
		SAMPLE_DATA = new byte[256];
		for (int i = 0; i < SAMPLE_DATA.length; i++) {
			SAMPLE_DATA[i] = (byte)i;
		}
	}
	
	protected ByteArrayInputStream createInputStream() {
		return new ByteArrayInputStream(SAMPLE_DATA.clone());
	}

	@Test
	public void testRead() throws Exception {
		ByteArrayInputStream bIn = createInputStream();
		SubInputStream in = new SubInputStream(bIn, 128, false);
		
		for (int i = 0; i < 128; i++) {
			assertEquals(i, in.read());
		}
		assertEquals(-1, in.read());
		assertEquals(-1, in.read());
		assertEquals(128, bIn.read());
		in.close();
	}
	
	@Test(expected = EOFException.class)
	public void testReadFailed() throws Exception {
		SubInputStream in = new SubInputStream(createInputStream(), 257, true);
		
		for (int i = 0; i < 257; i++) {
			assertEquals(i, in.read());
		}
		in.close();
	}

	@Test
	public void testReadByteArray() throws Exception {
		byte [] expected = new byte[128];
		System.arraycopy(SAMPLE_DATA, 0, expected, 0, expected.length);
		
		for (int i = 1; i <= 129; i++) {
			ByteArrayInputStream bIn = createInputStream();
			ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			SubInputStream in = new SubInputStream(bIn, 128, false);
			byte [] buff = new byte[i];
			int r;
			do {
				r = in.read(buff);
				if (r > 0) {
					bOut.write(buff, 0, r);
				}
			} while(r >= 0);
			assertArrayEquals(expected, bOut.toByteArray());			
			in.close();
		}
	}

	@Test(expected = EOFException.class)
	public void testReadByteArrayFail() throws Exception {
	
		SubInputStream in = new SubInputStream(createInputStream(), 257, false);
		byte [] buff = new byte[16];
		int r;
		do {
			r = in.read(buff);
		} while(r >= 0);
		in.close();
	}	
	
	@Test
	public void testReadByteArrayIntInt() throws Exception {
		byte [] expected = new byte[128];
		System.arraycopy(SAMPLE_DATA, 0, expected, 0, expected.length);
		byte [] buff = new byte[129];
		
		for (int i = 1; i <= 129; i++) {
			ByteArrayInputStream bIn = createInputStream();
			ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			SubInputStream in = new SubInputStream(bIn, 128, false);
			int r;
			do {
				r = in.read(buff, 1, i);
				if (r > 0) {
					bOut.write(buff, 1, r);
				}
			} while(r >= 0);
			assertArrayEquals(expected, bOut.toByteArray());			
			in.close();
		}
	}

	@Test(expected = EOFException.class)
	public void testReadByteArrayIntIntFail() throws Exception {
		
		SubInputStream in = new SubInputStream(createInputStream(), 257, false);
		byte [] buff = new byte[16];
		int r;
		do {
			r = in.read(buff, 1, buff.length - 1);
		} while(r >= 0);
		in.close();

	}	
	
	@Test
	public void testSkip() throws Exception {
		SubInputStream in = new SubInputStream(createInputStream(), 128, true);
		
		assertEquals(128, in.skip(128));
		
		assertEquals(-1, in.read());
		assertEquals(-1, in.read());
		in.close();
	}

	@Test
	public void testAvailable() throws Exception {
		ByteArrayInputStream bIn = createInputStream();
		SubInputStream in = new SubInputStream(bIn, 128, false);
		
		for (int i = 0; i < 128; i++) {
			assertEquals(i, in.read());
			assertEquals(256 - i - 1, bIn.available());
			assertEquals(128 - i - 1, in.available());
		}
		in.close();
	}

	@Test
	public void testClose() throws IOException {
		TestInputStream tIn = new TestInputStream(createInputStream());
		SubInputStream in = new SubInputStream(tIn, 256, true);
		in.close();
		assertTrue(tIn.isCloseUsed());

		tIn = new TestInputStream(createInputStream());
		in = new SubInputStream(tIn, 256, false);
		in.close();
		assertFalse(tIn.isCloseUsed());
	}

	@Test
	public void testMark() throws IOException {
		SubInputStream in = new SubInputStream(createInputStream(), 256, true);
		in.mark(0);
		in.close();
	}

	@Test(expected = IOException.class)
	public void testReset() throws Exception {
		SubInputStream in = new SubInputStream(createInputStream(), 256, true);
		in.reset();
		in.close();
	}

	@Test
	public void testMarkSupported() throws Exception {
		SubInputStream in = new SubInputStream(createInputStream(), 256, true);
		assertFalse(in.markSupported());
		in.close();
	}

	@Test
	public void testSubInputStream() throws Exception {
		SubInputStream in = new SubInputStream(createInputStream(), 256, true);
		assertEquals(256, in.remaining());
		in.close();
	}

	@Test
	public void testRemaining() throws Exception {
		SubInputStream in = new SubInputStream(createInputStream(), 128, true);
		
		for (int i = 0; i < 128; i++) {
			assertEquals(i, in.read());
			assertEquals(128 - i - 1, in.remaining());
		}
		assertEquals(-1, in.read());
		in.close();
	}

	@Test
	public void testEnd() throws Exception {
		ByteArrayInputStream bIn = createInputStream();
		SubInputStream in = new SubInputStream(bIn, 128, true);

		in.end();
		assertEquals(-1, in.read());
		assertEquals(128, bIn.read());
		in.close();
	}
	
	@Test(expected = IOException.class)
	public void testEndFailed() throws Exception {
		ByteArrayInputStream bIn = createInputStream();
		SubInputStream in = new SubInputStream(new TestInputStream(bIn), 257, true);
		in.end();
		in.close();
	}

	@Test
	public void testIsCloseOnClose() throws Exception {
		SubInputStream in = new SubInputStream(createInputStream(), 128, true);
		assertTrue(in.isCloseOnClose());
		in.close();

		in = new SubInputStream(createInputStream(), 128, false);
		assertFalse(in.isCloseOnClose());
		in.close();
	}
}
