package ch.zhaw.iwi.cis.pews.framework;

public class ShutdownThread extends Thread
{
	@Override
	public void run()
	{
		ZhawEngine.getEngine().stop();
	}
}
