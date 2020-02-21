package com.swb.springcloud.userservice.Utils;

public final class ToolKit {
    public static String toHtml(String s){
        s=Replace(s,"<","&lt;");
        s=Replace(s,">","&gt;");
        s=Replace(s,"\t","    ");
        s=Replace(s,"\n","<br>");
        s=Replace(s," ","&nbsp;");
        return s;
    }

    public static String Replace(String source,String oldString,String newString){
        if(source==null) return null;
        StringBuffer output=new StringBuffer();
        int lengOfSource=source.length();
        int lengOfOld=oldString.length();
        int posStart=0; int pos;
        while((pos=source.indexOf(oldString,posStart))>=0){
            output.append(source.substring(posStart,pos));
            output.append(newString);
            posStart=pos+lengOfOld;
        }

        if(posStart<lengOfSource) output.append(source.substring(posStart));
        return output.toString();
    }
}
