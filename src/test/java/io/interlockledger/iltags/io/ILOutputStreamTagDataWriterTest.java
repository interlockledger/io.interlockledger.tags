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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import org.junit.Test;

import io.interlockledger.iltags.ILTagException;

public class ILOutputStreamTagDataWriterTest {

	@Test
	public void testWriteByteCore() throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayOutputStream expected = new ByteArrayOutputStream();
		
		try (ILOutputStreamTagDataWriter w = new ILOutputStreamTagDataWriter(out)) {
			for (int i = 0; i < 128; i++) {
				w.writeByteCore((byte)i);
				expected.write(i);
			}
		}
		assertArrayEquals(expected.toByteArray(), out.toByteArray());
	}
	
	@Test(expected = ILTagException.class)
	public void testWriteByteCoreFail() throws Exception {
		TestOutputStream out = new TestOutputStream(new ByteArrayOutputStream());
		ILOutputStreamTagDataWriter w = new ILOutputStreamTagDataWriter(out);

		out.setForError(true);
		w.writeByteCore((byte)0);
		fail();
		w.close();
	}

	@Test
	public void testWriteBytesCore() throws Exception {
		Random r = new Random();
		ByteArrayOutputStream expected = new ByteArrayOutputStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		try (ILOutputStreamTagDataWriter w = new ILOutputStreamTagDataWriter(out)) {
			
			long offs = 0;
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 16; j++) {
					for (int size = 0; size < 16; size++) {
						byte [] bin = new byte[i + j + size];
						r.nextBytes(bin);
						w.writeBytes(bin, i, size);
						expected.write(bin, i, size);
						offs += size;
						assertEquals(w.getOffset(), offs);
					}
				}
			}
		}
		assertArrayEquals(expected.toByteArray(), out.toByteArray());
	}

	@Test(expected = ILTagException.class)
	public void testWriteBytesCoreFail() throws Exception {
		TestOutputStream out = new TestOutputStream(new ByteArrayOutputStream());
		ILOutputStreamTagDataWriter w = new ILOutputStreamTagDataWriter(out);

		out.setForError(true);
		w.writeBytesCore(new byte[1], 0, 1);
		fail();
		w.close();
	}

	@Test
	public void testILOutputStreamTagDataWriter() throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		try (ILOutputStreamTagDataWriter w = new ILOutputStreamTagDataWriter(out)) {
			assertSame(out, w.out);
		}
	}

	@Test
	public void testClose() throws Exception {
		TestOutputStream out = new TestOutputStream(new ByteArrayOutputStream());
		ILOutputStreamTagDataWriter w = new ILOutputStreamTagDataWriter(out);
		
		assertFalse(out.isCloseUsed());
		w.close();
		assertTrue(out.isCloseUsed());
	}
	
	@Test(expected = IOException.class)
	public void testCloseFail() throws Exception {
		TestOutputStream out = new TestOutputStream(new ByteArrayOutputStream());
		ILOutputStreamTagDataWriter w = new ILOutputStreamTagDataWriter(out);

		out.setForError(true);
		w.close();
	}	
	
	@Test
	public void testFlush() throws Exception {
		TestOutputStream out = new TestOutputStream(new ByteArrayOutputStream());
		ILOutputStreamTagDataWriter w = new ILOutputStreamTagDataWriter(out);
		
		assertFalse(out.isFlushUsed());
		w.flush();
		assertTrue(out.isFlushUsed());
		w.close();
	}
	
	@Test(expected = IOException.class)
	public void testFlushFail() throws Exception {
		TestOutputStream out = new TestOutputStream(new ByteArrayOutputStream());
		ILOutputStreamTagDataWriter w = new ILOutputStreamTagDataWriter(out);

		out.setForError(true);
		w.flush();
		fail();
		w.close();
	}

}
