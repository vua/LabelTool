package com.cooooode.utils;

import com.cooooode.ui.App;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class CoreUtils {
    public static List<Integer> indexs;
    public static String header;
    public static String repeat;
    public static String footer;
    @Test
    public void test() throws IOException {
        String str="<?xml version=\"1.0\" encoding=\"UTF-8\"?><annotation>\t<folder>VOC2007</folder>\t<filename>$4</filename>\t<size>\t\t<width>$6</width>\t\t<height>$7</height>\t\t<depth>3</depth>\t</size>\t";
        String pattern ="\\$(\\d)";
        Pattern p =Pattern.compile(pattern);
        Matcher m1=p.matcher(str);
        StringBuffer sb1 = new StringBuffer();

        while (m1.find()){
            int k=Integer.valueOf(m1.group(1));
        }
    }
    public static void parseFileTool(String format) throws IOException {

        switch(format){
            case "pascal":{
                parseFile("src/resource/pascal.txt");
            }break;
            case "coco":{
                parseFile("src/resource/coco.txt");
            }break;
            case "diy":{
                parseFile("src/resource/diy.txt");
            }break;
        }
    }
    public static void writeFileTool(String method,String type) throws IOException {
        for(int i=0;i<App.final0_3.size();i++)
            writeFile(i,method,type);
    }
    public static void parseFile(String path) throws IOException {
        String content="";
        try(
            BufferedReader reader=new BufferedReader(new FileReader(new File(path)));
        ){
            String line;
            while((line=reader.readLine())!=null){
                content+=line;
            }
        }

        String pattern ="(.*)/\\*(.*)\\*/(.*)";
        Pattern p =Pattern.compile(pattern);
        Matcher m=p.matcher(content);
        header="";
        repeat="";
        footer="";
        while (m.find()){
            header=m.group(1);
            repeat=m.group(2);
            footer=m.group(3);
        }
    }
    public static void writeFile(int index,String method,String type) throws IOException {

        List<int[]> l0_3=App.final0_3.get(index);
        List<String> l4=App.final4.get(index);
        List<String> l5_8=App.final5_8.get(index);

        String pattern ="\\$(\\d)";
        Pattern p =Pattern.compile(pattern);
        Matcher m1=p.matcher(header);
        StringBuffer sb1 = new StringBuffer();
        System.out.println(header);
        while (m1.find()){
            int k=Integer.valueOf(m1.group(1));
            if(k==4)
                m1.appendReplacement(sb1, l4.get(k));
            else if(k==9) {
                m1.appendReplacement(sb1, System.getProperty("line.separator"));
            }
            else{
                m1.appendReplacement(sb1, l5_8.get(k-5));
            }
        }
        m1.appendTail(sb1);

        StringBuffer SB2=new StringBuffer();
        for(int i=0;i<l0_3.get(0).length;i++){
            Matcher m2=p.matcher(repeat);
            StringBuffer sb2 = new StringBuffer();
            while (m2.find()){
                int k=Integer.valueOf(m2.group(1));
                if(k<4)
                    m2.appendReplacement(sb2,String.valueOf(l0_3.get(k)[i]));
                else if(k==4)
                    m2.appendReplacement(sb2, l4.get(i));
                else if(k==9){
                    m2.appendReplacement(sb2, System.getProperty("line.separator"));
                }
                else{
                    m2.appendReplacement(sb2, l5_8.get(k-5));
                }
            }
            m2.appendTail(sb2);
            SB2.append(sb2);

        }

        Matcher m3=p.matcher(footer);
        StringBuffer sb3 = new StringBuffer();

        while (m3.find()){
            int k=Integer.valueOf(m3.group(1));
            if(k==4)
                m3.appendReplacement(sb3, l4.get(k));
            else if(k==9){
                m3.appendReplacement(sb3, System.getProperty("line.separator"));
            }
            else{
                m3.appendReplacement(sb3, l5_8.get(k-5));
            }
        }
        m3.appendTail(sb3);
        sb1.append(SB2).append(sb3);
        if(method.equals("alone")){
            String[] strs=l5_8.get(0).split("\\.");
            out(strs[0]+type,sb1,false);

        }else{
            out("result"+type,sb1,true);
        }

    }
    public static void out(String name,StringBuffer sb,boolean append) throws IOException {
        try(BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(new File(App.save_path+"/"+name),append)
                        , "utf-8"))
        ){
            writer.write(sb.toString());
        }

    }

}
