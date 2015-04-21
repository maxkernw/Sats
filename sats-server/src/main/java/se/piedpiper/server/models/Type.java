package se.piedpiper.server.models;


public final class Type implements Comparable<Object> {
	
	private final String name;
	private final String type;
	private final String subType;

	public Type(String name, String type, String subType) {
		this.name = name;
		this.type = type;
		this.subType = subType;
	}
	
	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getSubType() {
		return subType;
	}

	@Override
	public int compareTo(Object obj)
	{
		return this.name.compareTo( ((Type) obj).getName() );
	}

}
