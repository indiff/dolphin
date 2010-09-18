package com.tan.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;


public class Compressor {
	private String path;
	
	public Compressor() {}
	public Compressor(final String path) {this.path = path;}
	
	public static void main(String[] args) throws Exception {
		Compressor compressor = new Compressor("D:\\梦幻打字王自动开始精灵.exe");
//		Compressor compressor = new Compressor("C:\\compress\\compress.txt");
		
//		compressor.compress();
		compressor.compressFile();
		Thread.sleep(1000);
		compressor.decompressFile();
//		compressor.decompress();
	}
	
	
	public void compressFile() {
		final File file = new File(path);
		if (file.exists() && file.isFile() ) {
			InputStream in = null;
			OutputStream out = null;
			byte[] buf = new byte[8192];
			int len = -1;
			int deflateLen = -1;
			try {
				in = new FileInputStream(file);
				out = new FileOutputStream(path + "compress.zip");
				while((len = in.read(buf, 0, buf.length)) != -1) {
					deflateLen = deflate(buf, 0, len);
					out.write(buf, 0, deflateLen);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (in != null)
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				if (out != null)
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
	}
	
	public void decompressFile() {
		final File file = new File(path + "compress.zip");
		if (file.exists() && file.isFile() ) {
			InputStream in = null;
			OutputStream out = null;
			byte[] buf = new byte[4190];
			byte[] inflateBuf = new byte[8197];
			int len = -1;
			int inflateLen = -1;
			try {
				in = new FileInputStream(file);
				out = new FileOutputStream(path + "decompress");
				while((len = in.read(buf, 0, buf.length)) != -1) {
					inflateLen = inflate(buf, 0, len, inflateBuf);
					out.write(inflateBuf, 0, inflateLen);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (in != null)
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				if (out != null)
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
	}
	
	public byte[] decompress(final byte[] data) {
		return decompress(data, 0, data.length);
	}
	
	public byte[] compress(final byte[] data){
		return compress(data, 0, data.length);
	}
	
	public int inflate(final byte[] data, final byte[] results) {
		return inflate(data, 0, data.length, results);
	}
	
	public int deflate(final byte[] data){
		return deflate(data, 0, data.length);
	}	
	
	public byte[] decompress(final byte[] data, final int offset, final int length) {
		Inflater  inflater = new Inflater (true);
		byte[] results = new byte[8192];
		inflater.setInput(data, offset, length);
		int len = -1;
		try {
			len = inflater.inflate(results, 0, results.length);
		} catch (DataFormatException e) {
			e.printStackTrace();
		}
		if (len == -1) return null;
		byte[] arrays = new byte[len];
		System.arraycopy(results, 0, arrays, 0, len);
		return results;
	}
	
	public byte[] compress(final byte[] data, final int offset, final int length) {
		Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION, true);
		deflater.setInput(data, offset, length);
		deflater.finish();
		int len = -1;
		len = deflater.deflate(data, offset, length);
		if (len == -1) return null;
		byte[] result = new byte[len];
		System.arraycopy(data, 0, result, 0, len);
		return result;
	}
	
	
	public int inflate(final byte[] data, final int offset, final int length, final byte[] results) {
		Inflater  inflater = new Inflater (true);
		inflater.setInput(data, offset, length);
		try {
			return inflater.inflate(results, 0, results.length);
		} catch (DataFormatException e) {
			e.printStackTrace();
		} finally {
			inflater.end();
		}
		return -1;
	}
	
	private int deflate(final byte[] data, final int offset, final int length) {
		Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION, true);
		deflater.setInput(data, offset, length);
		deflater.finish();
		deflater.end();
		return deflater.deflate(data, offset, length);
	}
	
	
//	public static void main(String ...strings) {
////		testSystemArraycopy();
//		byte[] buf = new byte[8172];
//		System.out.println(Arrays.toString(buf));
//		
//		Compressor compressor = new Compressor();
////		byte[] result = compressor.compress(buf);
//		compressor.deflate(buf);
//		
//		System.out.println(Arrays.toString(buf));
////		System.out.println(Arrays.toString(result));
//	}
	private static void testSystemArraycopy() {
		byte[] buf = new byte[10];
		buf[0] = 1;
		buf[1] = 2;
		buf[2] = 3;
		int len = 3;
		System.out.println(Arrays.toString(buf));
		byte[] arrays = new byte[len];
		System.arraycopy(buf, 0, arrays, 0, len);
		System.out.println(Arrays.toString(arrays));
	}
	
	public byte[] decompressBytes(byte[] input, int offset, int len) {
		// Decmpress the bytes.
		Inflater decompresser = new Inflater(true);
		decompresser.setInput(input, offset, len);
		byte[] buf = new byte[8192];
		int resultLength = -1;
		try {
			 resultLength = decompresser.inflate(buf);
		} catch (DataFormatException e) {
			e.printStackTrace();
		}
		decompresser.end();
		byte[] result = new byte[resultLength];
		System.arraycopy(buf, 0, result, 0, resultLength);
		return result;
	}

}
