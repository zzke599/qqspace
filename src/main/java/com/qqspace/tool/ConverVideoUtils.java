package com.qqspace.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
* @author zzke
* @ClassName
* @Description 	音视频转格式的工具类
*/
public class ConverVideoUtils {

	private String sourceVideoPath;					//原音视频路径
	private String filerealname;							//文件名不包括后缀名
	private String filename; 								//包括后缀名
	private String videofolder; 							//转换后的音视频路径
	private final String[] targetExtension = {".mp4",".mp3"};			//视频转换为MP4,音频转换MP3
	private String videofolderPath;
	//获取ffmpeg.exe的路径
	private final String ffmpegpath = "ffmpeg.exe";
	public ConverVideoUtils(){
		
	}
	
	
	public ConverVideoUtils(String filepath)
	{
		this.sourceVideoPath = filepath;
	}
	
	public  String beginConver()
	{
		File file = new File(sourceVideoPath);
		filename = file.getName();				//获取文件名称+后缀
		filerealname = filename.substring(0, filename.lastIndexOf(".")); //获取不带后缀的文件名-后面加.toLowerCase()小写
		videofolderPath = file.getParent()+file.separator;
		
		if(!file.exists()){
			return null;
		}
		//执行转码机制
		videofolder = process();
		return  videofolder;
	}
	
	
	
	private String process() {
		//先判断视频的类型-返回状态码
		int type = checkContentType();  
		String newFile = null;
		boolean status = false;
		 //根据状态码处理
		if(type == 0){
//			System.out.println("ffmpeg可以转换,统一转为mp4文件");
			status = processVideoFormat(sourceVideoPath,targetExtension[0]);//可以指定转换为什么格式的视频
			newFile =  filerealname + targetExtension[0];
			//删除原文件
			OperationFileUtil.deleteFile(sourceVideoPath);
			
			sourceVideoPath = null;
		}else if(type == 1){
//			System.out.println("ffmpeg可以转换,统一转为mp3文件");
			status = processVideoFormatMP3(sourceVideoPath,targetExtension[1]);//可以指定转换为什么格式的音频
			newFile =  filerealname + targetExtension[1];
			//删除原文件
			OperationFileUtil.deleteFile(sourceVideoPath);
			sourceVideoPath = null;
		}else{
			newFile = filename;
		}
		return newFile;
	}


	private boolean processVideoFormat(String oldfilepath, String string) {
//		System.out.println("调用了ffmpeg.exe工具");
		//先确保保存转码后的视频的文件夹存在
				File TempFile = new File(videofolderPath);
				if (TempFile.exists()) {
					if (TempFile.isDirectory()) {
							System.out.println("该文件夹存在。");
						}else {
								System.out.println("同名的文件存在，不能创建文件夹。");
							}
					}else {
						 System.out.println("文件夹不存在，创建该文件夹。");
						 TempFile.mkdir();
					}
				List<String> commend = new ArrayList<String>();
					
					commend.add(ffmpegpath);		 //ffmpeg.exe工具地址
					commend.add("-i");
					commend.add(oldfilepath);			//源视频路径			
					commend.add("-vcodec");  
			        commend.add("h263");  //
			        commend.add("-ab");		//新增4条
			        commend.add("128");      //高品质:128 低品质:64
			        commend.add("-acodec");		
			        commend.add("mp3");      //音频编码器：原libmp3lame
			        commend.add("-ac");     
			        commend.add("2");       //原1 
					commend.add("-ar");
					commend.add("22050");   //音频采样率22.05kHz
					commend.add("-r"); 
				    commend.add("29.97");  //高品质:29.97 低品质:15
					commend.add("-c:v");
					commend.add("libx264");	//视频编码器：视频是h.264编码格式
					commend.add("-strict");
					commend.add("-2");
					commend.add(videofolderPath + filerealname + targetExtension[0]);  // //转码后的路径+名称，是指定后缀的视频
					try {
						//多线程处理加快速度-解决rmvb数据丢失builder名称要相同
						ProcessBuilder builder = new ProcessBuilder();
						builder.command(commend);
						Process p = builder.start();   //多线程处理加快速度-解决数据丢失
						
						final InputStream is1 = p.getInputStream();
			            final InputStream is2 = p.getErrorStream();
			            new Thread() {    
			                public void run() {    
			                    BufferedReader br = new BufferedReader(    
			                            new InputStreamReader(is1));    
			                    try {    
			                        String lineB = null;    
			                        while ((lineB = br.readLine()) != null) {    
			                           /* if (lineB != null){   
//			                                System.out.println(lineB);    //打印mencoder转换过程代码
			                            } */
			                         }    
			                    } catch (IOException e) {    
			                        e.printStackTrace();    
			                    }    
			                }    
			            }.start();    
			            new Thread() {    
			                public void run() {    
			                    BufferedReader br2 = new BufferedReader(    
			                            new InputStreamReader(is2));    
			                    try {    
			                        String lineC = null;    
			                        while ((lineC = br2.readLine()) != null) {    
			                          /*  if (lineC != null){    
//			                                System.out.println(lineC);    //打印mencoder转换过程代码
			                            	}*/
			                            }    
			                    } catch (IOException e) {
			                        e.printStackTrace();  
			                    }    
			                }
			            }.start();	 
			          
			            p.waitFor();		//进程等待机制，必须要有，否则不生成mp4！！！
//					System.out.println("生成mp4视频为:"+videofolderPath + filerealname + targetExtension[0]);
					if(p.exitValue() == 0){
						System.out.println("正常结束进程......");
					}
					if( p.exitValue() != 0) {  
			            p.destroy();
			            System.out.println("杀死进程......");
			        }
					return true;
					} 
					catch (Exception e) {
					e.printStackTrace();
					return false;
				}
		
		
	}
	private boolean processVideoFormatMP3(String oldfilepath, String string) {
		System.out.println("调用了ffmpeg.exe工具");
		//先确保保存转码后的视频的文件夹存在
				File TempFile = new File(videofolderPath);
				if (TempFile.exists()) {
					if (TempFile.isDirectory()) {
							System.out.println("该文件夹存在。");
						}else {
								System.out.println("同名的文件存在，不能创建文件夹。");
							}
					}else {
						 System.out.println("文件夹不存在，创建该文件夹。");
						 TempFile.mkdir();
					}
				List<String> commend = new ArrayList<String>();
					
					commend.add(ffmpegpath);		 //ffmpeg.exe工具地址
					commend.add("-i");
					commend.add(oldfilepath);			//源视频路径			
					commend.add("-vcodec");  
			        commend.add("h263");  //
			        commend.add("-ab");		//新增4条
			        commend.add("128");      //高品质:128 低品质:64
			        commend.add("-acodec");		
			        commend.add("mp3");      //音频编码器：原libmp3lame
			        commend.add("-ac");     
			        commend.add("2");       //原1 
					commend.add("-ar");
					commend.add("44100");   //音频采样率44.10kHz
					commend.add("-strict");
					commend.add("-2");
					commend.add(videofolderPath + filerealname + targetExtension[1]);  // //转码后的路径+名称，是指定后缀的视频
					try {
						//多线程处理加快速度-解决rmvb数据丢失builder名称要相同
						ProcessBuilder builder = new ProcessBuilder();
						builder.command(commend);
						Process p = builder.start();   //多线程处理加快速度-解决数据丢失
						
						final InputStream is1 = p.getInputStream();
			            final InputStream is2 = p.getErrorStream();
			            new Thread() {    
			                public void run() {    
			                    BufferedReader br = new BufferedReader(    
			                            new InputStreamReader(is1));    
			                    try {    
			                        String lineB = null;    
			                        while ((lineB = br.readLine()) != null) {    
			                            /*if (lineB != null){    
			                             System.out.println(lineB);    //打印mencoder转换过程代码
			                            }*/
			                        }    
			                    } catch (IOException e) {    
			                        e.printStackTrace();    
			                    }    
			                }    
			            }.start();    
			            new Thread() {    
			                public void run() {    
			                    BufferedReader br2 = new BufferedReader(    
			                            new InputStreamReader(is2));    
			                    try {    
			                        String lineC = null;    
			                        while ((lineC = br2.readLine()) != null) {    
			                           /* if (lineC != null){    
//			                                System.out.println(lineC);    //打印mencoder转换过程代码
			                            	}*/
			                            }    
			                    } catch (IOException e) {
			                        e.printStackTrace();  
			                    }    
			                }
			            }.start();	 
			          
			            p.waitFor();		//进程等待机制，必须要有，否则不生成mp3！！！
//					System.out.println("生成mp3音频为:"+videofolderPath + filerealname + targetExtension[1]);
					if(p.exitValue() == 0){
						System.out.println("正常结束进程......");
					}
					if( p.exitValue() != 0) {  
			            p.destroy();
			            System.out.println("杀死进程......");
			        }
					return true;
					} 
					catch (Exception e) {
					e.printStackTrace();
					return false;
				}
		
		
	}

	private int checkContentType() {
		//取得视频后缀-
		String type = sourceVideoPath.substring(sourceVideoPath.lastIndexOf(".") + 1, sourceVideoPath.length()).toLowerCase();
		System.out.println("源视频类型为:"+type);
		// 如果是ffmpeg能解析的格式:(asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等)
				if (type.equals("avi")) {
					return 0;
				} else if (type.equals("mpg")) {
					return 0;
				} else if (type.equals("wmv")) {
					return 0;
				} else if (type.equals("3gp")) {
					return 0;
				} else if (type.equals("mov")) {
					return 0;
				} else if (type.equals("mp4")) {
					return -1;
				} else if (type.equals("asf")) {
					return 0;
				} else if (type.equals("asx")) {
					return 0;
				} else if (type.equals("flv")) {
					return 0;
				}else if (type.equals("mkv")) {
					return 0;
				}else if(type.equals("wav")){
					return 1;
				}else if(type.equals("wma")){
					return 1;
				}else if(type.equals("mid")){
					return 1;
				}else if(type.equals("avi")){
					return 1;
				}
		
		return -1;
	}


	public String getSourceVideoPath() {
		return sourceVideoPath;
	}
	public void setSourceVideoPath(String sourceVideoPath) {
		this.sourceVideoPath = sourceVideoPath;
	}
	public String getFilerealname() {
		return filerealname;
	}
	public void setFilerealname(String filerealname) {
		this.filerealname = filerealname;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getVideofolder() {
		return videofolder;
	}
	public void setVideofolder(String videofolder) {
		this.videofolder = videofolder;
	}
	
	

}
