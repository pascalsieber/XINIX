package ch.zhaw.iwi.cis.pews.model.input;


public class P2PKeywordInput
{
	private String keyword;
	private String id;

	public P2PKeywordInput()
	{
		super();
	}

	public P2PKeywordInput( String keyword, String id )
	{
		super();
		this.keyword = keyword;
		this.id = id;
	}

	public String getKeyword()
	{
		return keyword;
	}

	public void setKeyword( String keyword )
	{
		this.keyword = keyword;
	}

	public String getId()
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}

}
