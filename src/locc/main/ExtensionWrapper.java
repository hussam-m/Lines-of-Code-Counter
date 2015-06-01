package locc.main;

public class ExtensionWrapper {

	private String name;
	private String[] extensions;
	
	public ExtensionWrapper(String name, String[] extensions) {
		this.name = name;
		this.extensions = extensions;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public String getName() {
		return name;
	}
	
	public String[] getExtensions() {
		return extensions;
	}
	
	public String getToolTip() {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < extensions.length; i++) {
			builder.append(extensions[i]);
			if(i != extensions.length -1)
				builder.append(", ");
		}
		return builder.toString();
	}
	
	public String getCondensedExtensions() {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < extensions.length; i++) {
			if(i == extensions.length - 1)
				builder.append(extensions[i]);
			else
				builder.append(extensions[i]+",");
		}
		return builder.toString();
	}
	
	public String[] getExtensionFilterStrings() {
		String[] newString = new String[extensions.length];
		for(int i = 0; i < newString.length; i++) {
			newString[i] = extensions[i].substring(1, extensions[i].length());
		}
		return newString;
	}
	
}
