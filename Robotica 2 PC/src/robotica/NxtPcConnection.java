package robotica;

public interface NxtPcConnection
{
	public boolean sendData(String s);
	public String receiveData();
	public boolean isEmpty();
	public void start();
	public boolean isWorking();
}
