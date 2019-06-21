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

import java.nio.ByteBuffer;

import org.junit.Test;

import io.interlockledger.iltags.ILTagException;
import io.interlockledger.iltags.ilint.ILIntCodec;

public class ILBaseTagDataReaderTest {


	public static class TestTagDataReader extends ILBaseTagDataReader { 

		@Override
		protected byte readByteCore() throws ILTagException {
			return (byte)(this.getOffset() & 0xFF);
		}

		@Override
		protected void readBytesCore(byte[] v, int off, int size) throws ILTagException {
			for (int i = 0; i < size; i++) {
				v[i + off] = (byte)i;
			}
		}

		@Override
		protected void skipCore(long n) throws ILTagException {
		}
	}
	
	public static class TestTagDataReader2 extends ILBaseTagDataReader {

		private final ByteBuffer in;
		
		public TestTagDataReader2(ByteBuffer in) {
			this.in = in;
		}
		
		@Override
		protected byte readByteCore() throws ILTagException {
			if (in.hasRemaining()) {
				return in.get();
			} else {
				throw new ILTagException();
			}
		}

		@Override
		protected void readBytesCore(byte[] v, int off, int size) throws ILTagException {
			if (size <= in.remaining()) {
				in.get(v, off, size);
			} else {
				throw new ILTagException();
			}			
		}

		@Override
		protected void skipCore(long n) throws ILTagException {
			if (n <= in.remaining()) {
				in.position((int)(in.position() + n));
			} else {
				throw new ILTagException();
			}			
		}		
	}
	
	@Test
	public void testILBaseTagDataReader() {
		TestTagDataReader r = new TestTagDataReader();
		
		assertEquals(0, r.getOffset());
		assertFalse(r.isLimited());
		assertEquals(Long.MAX_VALUE, r.getRemaining());
	}

	@Test
	public void testUpdateOffset() throws ILTagException {
		TestTagDataReader r = new TestTagDataReader();

		long expected = 0;
		for (int i = 0; i < 64; i++) {
			r.updateOffset(i);
			expected += i;
			assertEquals(expected, r.getOffset());
		}
		
		// Fail due to the limit
		r = new TestTagDataReader();
		r.pushLimit(0);
		r.updateOffset(0);
		for (int i = 1; i < 64; i++) {
			try {
				r.updateOffset(i);
				fail(); 
			} catch (ILTagNotEnoughDataException e){
			}
		}		
	}
	
	@Test(expected = ILTagNotEnoughDataException.class)
	public void testUpdateOffsetFail() throws ILTagException {
		TestTagDataReader r = new TestTagDataReader();
		
		r.pushLimit(10);
		r.updateOffset(11);
	}

	@Test
	public void testReadByte() throws Exception {
		TestTagDataReader r = new TestTagDataReader();

		for (int i = 0; i < 16; i++) {
			assertEquals(i + 1, r.readByte() & 0xFF);
		}
	}
	
	@Test(expected = ILTagNotEnoughDataException.class)
	public void testReadByteFail() throws Exception {
		TestTagDataReader r = new TestTagDataReader();

		r.pushLimit(0);
		r.readByte();
	}

	@Test
	public void testReadByteCore() throws Exception {
		TestTagDataReader r = new TestTagDataReader();

		for (int i = 0; i < 16; i++) {
			assertEquals(i, r.readByteCore() & 0xFF);
			r.updateOffset(1);
		}
	}

	@Test
	public void testReadShort() throws Exception {
		TestTagDataReader r = new TestTagDataReader();

		assertEquals(0x0001, r.readShort());
		r.pushLimit(2);
		assertEquals(0x0001, r.readShort());
	}
	
	@Test(expected = ILTagNotEnoughDataException.class)
	public void testReadShortFail() throws Exception {
		TestTagDataReader r = new TestTagDataReader();

		r.pushLimit(1);
		r.readShort();
	}	

	@Test
	public void testReadInt() throws Exception {
		TestTagDataReader r = new TestTagDataReader();

		assertEquals(0x00010203, r.readInt());
		r.pushLimit(4);
		assertEquals(0x00010203, r.readInt());
	}
	
	@Test(expected = ILTagNotEnoughDataException.class)
	public void testReadIntFail() throws Exception {
		TestTagDataReader r = new TestTagDataReader();

		r.pushLimit(3);
		r.readInt();
	}	

	@Test
	public void testReadLong() throws Exception {
		TestTagDataReader r = new TestTagDataReader();

		assertEquals(0x0001020304050607l, r.readLong());
		r.pushLimit(8);
		assertEquals(0x0001020304050607l, r.readLong());
	}
	
	@Test(expected = ILTagNotEnoughDataException.class)
	public void testReadLongFail() throws Exception {
		TestTagDataReader r = new TestTagDataReader();

		r.pushLimit(7);
		r.readLong();
	}	

	@Test
	public void testReadFloat() throws Exception {
		TestTagDataReader r = new TestTagDataReader();

		assertEquals(Float.intBitsToFloat(0x00010203), r.readFloat(), 0.0f);
		r.pushLimit(4);
		assertEquals(Float.intBitsToFloat(0x00010203), r.readFloat(), 0.0f);
	}
	
	@Test(expected = ILTagNotEnoughDataException.class)
	public void testReadFloatFail() throws Exception {
		TestTagDataReader r = new TestTagDataReader();

		r.pushLimit(3);
		r.readFloat();
	}

	@Test
	public void testReadDouble() throws Exception {
		TestTagDataReader r = new TestTagDataReader();

		assertEquals(Double.longBitsToDouble(0x0001020304050607l), r.readDouble(), 0.0f);
		r.pushLimit(8);
		assertEquals(Double.longBitsToDouble(0x0001020304050607l), r.readDouble(), 0.0f);
	}
	
	@Test(expected = ILTagNotEnoughDataException.class)
	public void testReadDoubleFail() throws Exception {
		TestTagDataReader r = new TestTagDataReader();

		r.pushLimit(7);
		r.readDouble();
	}
	
	@Test
	public void testReadBytesByteArray() throws Exception {
		
		for (int size = 0; size < 256; size++) {
			TestTagDataReader r = new TestTagDataReader();
			byte [] bin = new byte[size];
			r.readBytes(bin);
			byte [] expected = new byte[size];
			for (int i = 0; i < size; i++) {
				expected[i] = (byte)i;
			}
		}
		
		for (int size = 0; size < 256; size++) {
			TestTagDataReader r = new TestTagDataReader();
			byte [] bin = new byte[size];
			r.pushLimit(size);
			r.readBytes(bin);
			byte [] expected = new byte[size];
			for (int i = 0; i < size; i++) {
				expected[i] = (byte)i;
			}
		}
	}

	@Test(expected = ILTagNotEnoughDataException.class)
	public void testReadBytesByteArrayFail() throws Exception {
		TestTagDataReader r = new TestTagDataReader();

		r.pushLimit(9);
		r.readBytes(new byte[10]);
	}
	
	@Test
	public void testReadBytesByteArrayIntInt() throws Exception {
		TestTagDataReader r = new TestTagDataReader();
		
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				for (int size = 0; size < 16; size++) {
					byte [] bin = new byte[i + j + size];
					r.readBytes(bin, i, size);
					byte [] expected = new byte[i + j + size];
					for (int k = 0; k < size; k++) {
						expected[i + k] = (byte)k;
					}
					assertArrayEquals(expected, bin);
				}
			}
		}
		
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				for (int size = 0; size < 16; size++) {
					byte [] bin = new byte[i + j + size];
					r.pushLimit(size);
					r.readBytes(bin, i, size);
					r.popLimit(true);
					byte [] expected = new byte[i + j + size];
					for (int k = 0; k < size; k++) {
						expected[i + k] = (byte)k;
					}
					assertArrayEquals(expected, bin);
				}
			}
		}
	}
	
	@Test(expected = ILTagNotEnoughDataException.class)
	public void testReadBytesByteArrayIntIntFail() throws Exception {
		TestTagDataReader r = new TestTagDataReader();
		
		r.pushLimit(9);
		r.readBytes(new byte[10]);
	}

	@Test
	public void testReadBytesCore() throws Exception {
		TestTagDataReader r = new TestTagDataReader();
		
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				for (int size = 0; size < 16; size++) {
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

	@Test(expected = ILTagNotEnoughDataException.class)
	public void testReadILInt() throws Exception {
		TestTagDataReader r;

		r = new TestTagDataReader();
		r.skip(254);
		r.pushLimit(8);		
		r.readILInt();
	}
	
	@Test
	public void testReadILIntFail() throws Exception {
		TestTagDataReader r;

		r = new TestTagDataReader();
		r.skip(254);
		assertEquals(0x01020304050607l + ILIntCodec.ILINT_BASE, r.readILInt());
		
		r = new TestTagDataReader();
		r.skip(254);
		r.pushLimit(9);		
		assertEquals(0x01020304050607l + ILIntCodec.ILINT_BASE, r.readILInt());
	}

	@Test
	public void testSkip() throws Exception {
		TestTagDataReader r = new TestTagDataReader();
		
		long expected = 0;
		for (int i = 0; i < 256; i++) {
			expected += i;
			r.skip(i);
			assertEquals(expected, r.getOffset());
		}
	}
	
	@Test(expected = ILTagNotEnoughDataException.class)
	public void testSkipFail() throws Exception {
		TestTagDataReader r = new TestTagDataReader();
		
		r.pushLimit(0);
		r.skip(1);
	}

	@Test
	public void testPushLimit() {
		TestTagDataReader r = new TestTagDataReader();
		
		assertEquals(Long.MAX_VALUE, r.getRemaining());
		r.pushLimit(10);
		assertEquals(10, r.getRemaining());

		r.pushLimit(10);
		assertEquals(10, r.getRemaining());
		
		r.pushLimit(9);
		assertEquals(9, r.getRemaining());
		
		r.pushLimit(0);
		assertEquals(0, r.getRemaining());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPushLimitFailNegative() {
		TestTagDataReader r = new TestTagDataReader();
		
		r.pushLimit(-1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPushLimitFailSmall() {
		TestTagDataReader r = new TestTagDataReader();
		
		r.pushLimit(1);
		r.pushLimit(2);
	}
	

	@Test
	public void testPopLimit() throws Exception {
		TestTagDataReader r = new TestTagDataReader();
		
		assertEquals(Long.MAX_VALUE, r.getRemaining());
		r.pushLimit(10);
		r.pushLimit(10);
		r.pushLimit(9);
		r.pushLimit(0);
		
		assertEquals(0, r.getRemaining());
		r.popLimit(false);
		r.readByte();
		assertEquals(8, r.getRemaining());
		r.popLimit(false);
		assertEquals(9, r.getRemaining());
		r.popLimit(false);
		assertEquals(9, r.getRemaining());
		r.skip(9);
		r.popLimit(true);
		assertEquals(Long.MAX_VALUE - 10, r.getRemaining());
	}

	@Test(expected = IllegalStateException.class)
	public void testPopLimitFailNoLimit() throws Exception {
		TestTagDataReader r = new TestTagDataReader();

		r.popLimit(false);
	}
	
	@Test(expected = ILTagTooMuchDataException.class)
	public void testPopLimitRemaining() throws Exception {
		TestTagDataReader r = new TestTagDataReader();

		r.pushLimit(1);
		r.popLimit(true);
	}
	
	@Test
	public void testGetRemaining() throws Exception {
		TestTagDataReader r;
		long expected;
		
		r = new TestTagDataReader();
		expected = Long.MAX_VALUE;
		for (int i = 0; i < 256; i++) {
			expected -= i;
			r.skip(i);
			assertEquals(expected, r.getRemaining());
		}
		
		r = new TestTagDataReader();
		r.pushLimit(10);
		for (int i = 0; i < 10; i++) {
			r.readByte();
			assertEquals(10 - r.getOffset(), r.getRemaining());
		}
		r.popLimit(true);
		for (int i = 0; i < 10; i++) {
			r.readByte();
			assertEquals(Long.MAX_VALUE - r.getOffset(), r.getRemaining());
		}
	}

	@Test
	public void testIsLimited() throws Exception {
		TestTagDataReader r = new TestTagDataReader();

		assertFalse(r.isLimited());
		r.pushLimit(10);
		assertTrue(r.isLimited());
		r.popLimit(false);
		assertFalse(r.isLimited());
	}

	@Test
	public void testGetOffset() throws Exception {
		TestTagDataReader r;
		long expected;
		
		r = new TestTagDataReader();
		expected = 0;
		for (int i = 0; i < 256; i++) {
			assertEquals(expected, r.getOffset());
			expected += i;
			r.skip(i);
			assertEquals(expected, r.getOffset());
		}
		
		r = new TestTagDataReader();
		r.pushLimit(10);
		for (int i = 0; i < 10; i++) {
			assertEquals(i, r.getOffset());
			r.readByte();
			assertEquals(i + 1, r.getOffset());
		}		
	}
	
	@Test
	public void testReadStringLong() throws Exception {
		
		for (int size = 0; size < 256; size += 33) {
			String s = genRandomString(size);
			byte [] sBin = stringToUTF8(s);
			ByteBuffer b = ByteBuffer.allocate(sBin.length + 1);
			b.put(sBin);
			b.rewind();
			TestTagDataReader2 r = new TestTagDataReader2(b);
			assertEquals(s, r.readString(sBin.length));
		}
	}
	
	@Test
	public void testReadStringLongSample() throws Exception {
		TestTagDataReader2 r = new TestTagDataReader2(ByteBuffer.wrap(SAMPLE_BIN));
		
		assertEquals(SAMPLE, r.readString(SAMPLE_BIN.length));				
	}
	
	@Test(expected = ILTagException.class)
	public void testReadStringLongFail() throws Exception {
		TestTagDataReader2 r = new TestTagDataReader2(ByteBuffer.wrap(SAMPLE_BIN));
		
		r.readString(SAMPLE_BIN.length +  1);				
	}
	
	@Test
	public void testReadStringLongAppendable() throws Exception {
		
		for (int size = 0; size < 256; size += 33) {
			String s = genRandomString(size);
			byte [] sBin = stringToUTF8(s);
			ByteBuffer b = ByteBuffer.allocate(sBin.length + 1);
			b.put(sBin);
			b.rewind();
			TestTagDataReader2 r = new TestTagDataReader2(b);
			StringBuffer sb = new StringBuffer();
			assertEquals(s.length(), r.readString(sBin.length, sb));
			assertEquals(s, sb.toString());
		}
	}
	
	@Test(expected = ILTagException.class)
	public void testReadStringLongAppendableFail1() throws Exception {
		
		ByteBuffer src = ByteBuffer.allocate(1);
		src.put((byte)0x80);
		
		src.rewind();
		TestTagDataReader2 r = new TestTagDataReader2(src);
		StringBuffer sb = new StringBuffer();
		r.readString(1, sb);
	}
	
	@Test(expected = ILTagException.class)
	public void testReadStringLongAppendableFail2() throws Exception {
		
		ByteBuffer src = ByteBuffer.allocate(2);
		src.put((byte)0b11000000);
		src.rewind();		
		TestTagDataReader2 r = new TestTagDataReader2(src);
		StringBuffer sb = new StringBuffer();
		r.readString(2, sb);
	}
	
	@Test(expected = ILTagException.class)
	public void testReadStringLongAppendableFail3() throws Exception {
		
		ByteBuffer src = ByteBuffer.allocate(3);
		src.put((byte)0b11100000);
		src.put((byte)0b10000000);
		src.rewind();		
		TestTagDataReader2 r = new TestTagDataReader2(src);
		StringBuffer sb = new StringBuffer();
		r.readString(3, sb);
	}
	
	
	@Test(expected = ILTagException.class)
	public void testReadStringLongAppendableFail4() throws Exception {
		
		ByteBuffer src = ByteBuffer.allocate(4);
		src.put((byte)0b11110000);
		src.put((byte)0b10000000);
		src.put((byte)0b10000000);
		src.rewind();		
		TestTagDataReader2 r = new TestTagDataReader2(src);
		StringBuffer sb = new StringBuffer();
		r.readString(4, sb);
	}
}
