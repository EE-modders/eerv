package net.coderbot.eerv;

public class TAI 
{
	Element[] elems;
	
	public TAI()
	{
		this(0);
	}
	
	public TAI(int cap)
	{
		elems = new Element[cap];
	}
	
	public abstract static class Element{}
	
	public static class Include extends Element
	{
		String file;
		
		public String toString()
		{
			return "include "+file;
		}
	}
	
	public static class Handler extends Element
	{
		String name;
		Condition[] conditions;
		
		public String toString()
		{
			StringBuilder sb = new StringBuilder(name);
			sb.append("\n{\n");
			
			for(int j = 0;j<conditions.length;j++)
			{
				TAI.Condition c = conditions[j];
				
				if(c.remove)
				{
					sb.append("    remove ");
					sb.append(c.list[0]);
					sb.append('\n');
					continue;
				}
				
				if(c.list[0].equals("AlwaysTrue"))
				{
					sb.append("    ");
					sb.append(c.t);
					sb.append('\n');
					continue;
				}
				
				boolean flip = false;
				sb.append("    if(");
				
				if(c.t==null)
				{
					flip = true;
					sb.append('!');
				}
				
				if(c.list.length==1)
				{
					sb.append(c.list[0]);
				}
				else
				{
					for(int l = 0;l<c.list.length;l++)
					{
						sb.append(c.list[l]);
						if(l+1<c.list.length)
						{
							sb.append((c.and)?" && ":" || ");
						}
					}
				}
				
				sb.append(")\n    {\n");
				if(flip)
				{
					sb.append("        ");
					sb.append(c.f);
					sb.append("\n    }\n");
				}
				else if(c.f!=null)
				{
					sb.append("        ");
					sb.append(c.t);
					sb.append("\n    }\n");
					sb.append("    else\n");
					sb.append("    {\n");
					
					sb.append("        ");
					sb.append(c.f);
					sb.append("\n    }\n");
				}
				else
				{
					sb.append("        ");
					sb.append(c.t);
					sb.append("\n    }\n");
				}
				
				if(j+1<conditions.length)
				{
					sb.append('\n');
				}
			}
			
			sb.append("}");
			return sb.toString(); 
		}
	}
	
	public static class Condition
	{
		boolean and;
		boolean remove;
		String[] list;
		
		String t;
		String f;
	}
}
