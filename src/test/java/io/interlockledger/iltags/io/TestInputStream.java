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

/**
 * Test InputStream.
 */
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

class TestInputStream extends FilterInputStream {

	private boolean closeUsed;

	public TestInputStream(InputStream in) {
		super(in);
	}

	@Override
	public void close() throws IOException {
		this.closeUsed = true;
		super.close();
	}

	public boolean isCloseUsed() {
		return closeUsed;
	}

	/**
	 * This implementation never skips.
	 */
	@Override
	public long skip(long n) throws IOException {
		return 0;
	}
}
