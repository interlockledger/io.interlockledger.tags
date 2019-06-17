package io.interlockledger.iltags.io;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.junit.Test;

public class BaseDataTestTest {

	@Test
	public void testSample() {
		
		
		ByteBuffer b = BaseDataTest.UTF8.encode(CharBuffer.wrap(BaseDataTest.SAMPLE));
		byte [] v = new byte[b.limit()];
		b.get(v);
		assertArrayEquals(BaseDataTest.SAMPLE_BIN, v);
	}

}
