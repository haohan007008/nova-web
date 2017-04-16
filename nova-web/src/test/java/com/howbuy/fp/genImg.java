package com.howbuy.fp;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.junit.Test;

import com.howbuy.fp.utils.ImageHelper;

/** 
 * @author LvMeng
 * @version 2017年3月22日 上午9:25:18
 */
public class genImg {
	@SuppressWarnings("static-access")
	@Test
	public void resizeImg() throws IOException{
		 //String sourceImagePath = "C:/Users/Administrator/Pictures/clothes/16D011.png";// 读入文件  
		 //String destinationPath = "C:/Users/Administrator/Pictures/clothes/16D011_60x60.png";
		 
		 File dir = new File("C:/Users/Administrator/Pictures/clothes/");
		 
		 FileFilter filefilter = new FileFilter() {
		        public boolean accept(File file) {
		            if (file.getName().endsWith(".png")) {
		                return true;
		            }
		            return false;
		        }
		    };
		 
		 if(dir.exists()){
			 if(dir.isDirectory()){
	                //打印当前目录的路径
	                System.out.println(dir);
	                //获取该目录下的所有文件和目录组成的File数组
	                File[] files = dir.listFiles(filefilter);
	                //递归遍历每一个子文件
	                for(File file : files){
	                	System.out.println(file.getAbsolutePath());
	                	
	                	String smallImg = file.getParentFile() +"\\"+
	                			file.getName().substring(0,file.getName().length()-4)+"_60x60.png";
	                	String vmImg = file.getParentFile() +"\\"+
	                			file.getName().substring(0,file.getName().length()-4)+"_300x300.png";
	                	
	                	ImageHelper.getImageHelper().scaleImageWithParams(
	                			file.getAbsolutePath(), smallImg, 60, 60, true, "png");
	                	ImageHelper.getImageHelper().scaleImageWithParams(
	                			file.getAbsolutePath(), vmImg, 300, 300, true, "png");
	                	
	                }
	            }
		 }
		 
		 //ImageHelper.getImageHelper().scaleImageWithParams(sourceImagePath, destinationPath, 60, 60, true, "png");
		 //ImageHelper.getImageHelper().scaleImageWithParams(sourceImagePath, destinationPath, 300, 300, true, "png");
	}
}
