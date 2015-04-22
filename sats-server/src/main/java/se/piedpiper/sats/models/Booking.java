package se.piedpiper.sats.models;


public final class Booking implements Comparable<Object>
{

	private final String status;
	private final se.piedpiper.sats.models.Class aClass;
	private final String center;
	private final String id;
	private final int positionInQueue;

	public Booking(String status, se.piedpiper.sats.models.Class aClass, String center, String id,
			int positionInQueue)
	{
		this.status = status;
		this.aClass = aClass;
		this.center = center;
		this.id = id;
		this.positionInQueue = positionInQueue;
	}

	public String getStatus()
	{
		return status;
	}

	public se.piedpiper.sats.models.Class getAClass()
	{
		return aClass;
	}

	public String getCenter()
	{
		return center;
	}

	public String getId()
	{
		return id;
	}

	public int getPositionInQueue()
	{
		return positionInQueue;
	}
	
	@Override
	public int compareTo(Object obj)
	{
		return this.id.compareTo( ((Booking) obj).getId() );
	}
}
