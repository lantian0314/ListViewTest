package com.example.cache;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FlushInputStream extends FilterInputStream {

	protected FlushInputStream(InputStream in) {
		super(in);
	}

	@Override
	public long skip(long byteCount) throws IOException {
		long totalBytesSkipped = 0L;
		while (totalBytesSkipped < byteCount) {
			long byteSkipped = in.skip(byteCount - totalBytesSkipped);
			if (byteSkipped == 0) {
				int b = read();
				if (b < 0) {
					break;
				} else {
					byteSkipped = 1;
				}
			}
			totalBytesSkipped+=byteSkipped;
		}
		return totalBytesSkipped;
	}
	
}
