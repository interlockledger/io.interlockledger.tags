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

import static io.interlockledger.iltags.TestUtils.*;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Random;

import org.junit.Test;

import io.interlockledger.iltags.ILTagException;
import io.interlockledger.iltags.ilint.ILIntCodec;

public class ILBaseTagDataWriterTest {
	
	public static class TestBaseTagDataWriter extends ILBaseTagDataWriter {
		
		private ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		private boolean forceError = false;

		@Override
		protected void writeByteCore(byte v) throws ILTagException {
			if (this.forceError) {
				throw new ILTagTooMuchDataException();
			}
			this.out.write(v);
		}

		@Override
		protected void writeBytesCore(byte[] v, int off, int size) throws ILTagException {
			if (this.forceError) {
				throw new ILTagTooMuchDataException();
			}
			this.out.write(v, off, size);
		}
		
		public byte [] toByteArray() {
			return this.out.toByteArray();
		}

		public void setForceError(boolean forceError) {
			this.forceError = forceError;
		}
	}

	@Test
	public void testILBaseTagDataWriter() {
		TestBaseTagDataWriter w = new TestBaseTagDataWriter();
		
		assertEquals(0, w.getOffset());
	}

	@Test
	public void testUpdateOffset() {
		TestBaseTagDataWriter w = new TestBaseTagDataWriter();
		
		long offset = 0;
		for (int i = 0; i < 100; i++) {
			assertEquals(offset, w.getOffset());
			w.updateOffset(i);
			offset += i;
			assertEquals(offset, w.getOffset());
		}
	}

	@Test
	public void testWriteByte() throws Exception {
		TestBaseTagDataWriter w = new TestBaseTagDataWriter();
		Random r = new Random();
		
		ByteBuffer b = ByteBuffer.allocate(128);
		while (b.hasRemaining()) {
			assertEquals(b.position(), w.getOffset());
			byte v = (byte)r.nextInt();
			w.writeByte(v);
			b.put(v);
			assertEquals(b.position(), w.getOffset());
		}
		assertArrayEquals(b.array(), w.toByteArray());
	}
	
	@Test(expected = ILTagException.class)
	public void testWriteByteFail() throws Exception {
		TestBaseTagDataWriter w = new TestBaseTagDataWriter();
		w.setForceError(true);
		w.writeByte((byte)0);
	}

	@Test
	public void testWriteShort() throws Exception {
		Random r = new Random();
		TestBaseTagDataWriter w = new TestBaseTagDataWriter();
		
		ByteBuffer b = ByteBuffer.allocate(128);
		while (b.hasRemaining()) {
			assertEquals(b.position(), w.getOffset());
			short v = (short)r.nextInt();
			w.writeShort(v);
			b.putShort(v);
			assertEquals(b.position(), w.getOffset());
		}
		assertArrayEquals(b.array(), w.toByteArray());
	}
	
	@Test(expected = ILTagException.class)
	public void testWriteShortFail() throws Exception {
		TestBaseTagDataWriter w = new TestBaseTagDataWriter();
		w.setForceError(true);
		w.writeShort((short)0);
	}

	@Test
	public void testWriteInt() throws Exception {
		Random r = new Random();
		TestBaseTagDataWriter w = new TestBaseTagDataWriter();
		
		ByteBuffer b = ByteBuffer.allocate(128);
		while (b.hasRemaining()) {
			assertEquals(b.position(), w.getOffset());
			int v = r.nextInt();
			w.writeInt(v);
			b.putInt(v);
			assertEquals(b.position(), w.getOffset());
		}
		assertArrayEquals(b.array(), w.toByteArray());
	}
	
	@Test(expected = ILTagException.class)
	public void testWriteIntFail() throws Exception {
		TestBaseTagDataWriter w = new TestBaseTagDataWriter();
		w.setForceError(true);
		w.writeInt(0);
	}
	
	@Test
	public void testWriteLong() throws Exception {
		Random r = new Random();
		TestBaseTagDataWriter w = new TestBaseTagDataWriter();
		
		ByteBuffer b = ByteBuffer.allocate(128);
		while (b.hasRemaining()) {
			assertEquals(b.position(), w.getOffset());
			long v = r.nextLong();
			w.writeLong(v);
			b.putLong(v);
			assertEquals(b.position(), w.getOffset());
		}
		assertArrayEquals(b.array(), w.toByteArray());
	}
	
	@Test(expected = ILTagException.class)
	public void testWriteLongFail() throws Exception {
		TestBaseTagDataWriter w = new TestBaseTagDataWriter();
		w.setForceError(true);
		w.writeLong(0);
	}

	@Test
	public void testWriteFloat() throws Exception {
		Random r = new Random();
		TestBaseTagDataWriter w = new TestBaseTagDataWriter();
		
		ByteBuffer b = ByteBuffer.allocate(128);
		while (b.hasRemaining()) {
			assertEquals(b.position(), w.getOffset());
			float v = r.nextFloat();
			w.writeFloat(v);
			b.putFloat(v);
			assertEquals(b.position(), w.getOffset());
		}
		assertArrayEquals(b.array(), w.toByteArray());
	}
	
	@Test(expected = ILTagException.class)
	public void testWriteFloatFail() throws Exception {
		TestBaseTagDataWriter w = new TestBaseTagDataWriter();
		w.setForceError(true);
		w.writeFloat(0);
	}

	@Test
	public void testWriteDouble() throws Exception {
		Random r = new Random();
		TestBaseTagDataWriter w = new TestBaseTagDataWriter();
		
		ByteBuffer b = ByteBuffer.allocate(128);
		while (b.hasRemaining()) {
			assertEquals(b.position(), w.getOffset());
			double v = r.nextDouble();
			w.writeDouble(v);
			b.putDouble(v);
			assertEquals(b.position(), w.getOffset());
		}
		assertArrayEquals(b.array(), w.toByteArray());
	}
	
	@Test(expected = ILTagException.class)
	public void testWriteDoubleFail() throws Exception {
		TestBaseTagDataWriter w = new TestBaseTagDataWriter();
		w.setForceError(true);
		w.writeDouble(0);
	}
	
	@Test
	public void testWriteBytesByteArray() throws Exception {
		Random r = new Random();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		TestBaseTagDataWriter w = new TestBaseTagDataWriter();
		
		long offs = 0;
		for (int i = 0; i < 16; i++) {
			for (int size = 0; size < 16; size++) {
				byte [] bin = new byte[size];
				r.nextBytes(bin);
				w.writeBytes(bin);
				out.write(bin);
				offs += size;
				assertEquals(w.getOffset(), offs);
			}
		}
		assertArrayEquals(out.toByteArray(), w.toByteArray());
	}
	
	@Test(expected = ILTagException.class)
	public void testWriteBytesByteArrayFail() throws Exception {
		TestBaseTagDataWriter w = new TestBaseTagDataWriter();
		w.setForceError(true);
		w.writeBytes(new byte[1]);
	}

	@Test
	public void testWriteBytesByteArrayIntInt() throws Exception {
		Random r = new Random();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		TestBaseTagDataWriter w = new TestBaseTagDataWriter();
		
		long offs = 0;
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				for (int size = 0; size < 16; size++) {
					byte [] bin = new byte[i + j + size];
					r.nextBytes(bin);
					w.writeBytes(bin, i, size);
					out.write(bin, i, size);
					offs += size;
					assertEquals(w.getOffset(), offs);
				}
			}
		}
		assertArrayEquals(out.toByteArray(), w.toByteArray());
	}

	@Test(expected = ILTagException.class)
	public void testWriteBytesByteArrayIntIntFail() throws Exception {
		TestBaseTagDataWriter w = new TestBaseTagDataWriter();
		w.setForceError(true);
		w.writeBytes(new byte[1], 0, 1);
	}
	
	@Test
	public void testWriteILInt() throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		TestBaseTagDataWriter w = new TestBaseTagDataWriter();
		
		long offs = 0;
		long value = 0x08;
		for (int i = 0; i < 16; i++, value = (value << 4) + 1) {
			int size = ILIntCodec.getEncodedSize(value);
			w.writeILInt(value);
			ILIntCodec.encode(value, out);
			offs += size;
			assertEquals(w.getOffset(), offs);
		}
		assertArrayEquals(out.toByteArray(), w.toByteArray());
	}
	
	@Test(expected = ILTagException.class)
	public void testWriteILIntFail() throws Exception {
		TestBaseTagDataWriter w = new TestBaseTagDataWriter();
		w.setForceError(true);
		w.writeILInt(0);
	}

	@Test
	public void testGetOffset() throws Exception {
		Random r = new Random();
		TestBaseTagDataWriter w = new TestBaseTagDataWriter();
		
		long offs = 0;
		for (int i = 0; i < 16; i++) {
			int size = r.nextInt(128);
			byte [] bin = new byte[size];
			w.writeBytes(bin);
			offs += size;
			assertEquals(offs, w.getOffset());
		}
	}

	@Test
	public void testWriteString() throws Exception {

		for (int size = 0; size < 1024; size += 33) {
			TestBaseTagDataWriter w = new TestBaseTagDataWriter();

			String s = genRandomString(size);
			w.writeString(s);
			ByteBuffer out = UTF8.encode(s);
			byte [] expected = new byte[out.limit()];
			out.get(expected);
			assertArrayEquals(expected, w.toByteArray());
		}
	}
	
	@Test
	public void testWriteStringSample() throws Exception {
		TestBaseTagDataWriter w = new TestBaseTagDataWriter();

		w.writeString(SAMPLE);
		assertArrayEquals(SAMPLE_BIN, w.toByteArray());
	}
}
