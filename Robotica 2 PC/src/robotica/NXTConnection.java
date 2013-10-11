package robotica;

import java.io.InputStream;
import java.io.OutputStream;

import lejos.pc.comm.NXTInfo;

public class NXTConnection
{
	private NXTInfo nxtInfo;
	private OutputStream dataOut = null;
	private InputStream dataIn = null;

	public NXTConnection(NXTInfo info, OutputStream out, InputStream in)
	{
		nxtInfo = info;
		dataOut = out;
		dataIn = in;
	}

	public NXTInfo getNXTInfo()
	{
		return nxtInfo;
	}

	public String getDeviceAdress()
	{
		return nxtInfo.deviceAddress;
	}
}
