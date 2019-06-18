package io.interlockledger.iltags;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.junit.Test;

public class TestUtilsTest {

	@Test
	public void testSample() {
		ByteBuffer b = TestUtils.UTF8.encode(CharBuffer.wrap(TestUtils.SAMPLE));
		byte [] v = new byte[b.limit()];
		b.get(v);
		assertArrayEquals(TestUtils.SAMPLE_BIN, v);
	}
	
	@Test
	public void testFillSampleByteArray() {
		
		for (int size = 0; size < 16; size++) {
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 16; j++) {
					byte [] v = new byte[i + size + j];
					byte [] expected = v.clone();
					TestUtils.fillSampleByteArray(v, i, size);
					for (int k = 0; k < size; k++) {
						expected[i + k] = (byte)(k & 0xFF);
					}
					assertArrayEquals(expected, v);
				}
			}
		}	
	}
	
	@Test
	public void testCreateSampleByteArray() {
		
		for (int size = 0; size < 64; size++) {
			byte [] v = TestUtils.createSampleByteArray(size);
			byte [] expected = new byte[size];
			for (int i = 0; i < size; i++) {
				expected[i] = (byte)(i & 0xFF);
			}
			assertArrayEquals(expected, v);
		}		
	}
}
