package cn.centurywar.util;

public class MathUtil {

	private MathUtil(){}
	private static MathUtil instance	= new MathUtil();
	public static MathUtil getInstance() {
		return instance;
	}
	private int num;
	private boolean flag;
	private String[] random = {"1","2","3","4","5","6",};
	private int random_num	= 0;
	
	/**
	 * @param max	最大值
	 * @param taget	目标值
	 * @return		数组长度为taget的数组，其中的值的范围为0-max，且各值互不相同
	 */
	public int[] check(int max, int taget){
		int[] array = new int[taget];
		//i为数组寻找初选数字，len为数组中的已经选好的数字，
		for(int i=0,len=0;len<taget;i++){
			flag	= false;
			num	= (int)Math.floor(Math.random()*max);
			if(i>0){
				for(int j=len-1;j>=0;j--){
					if(array[j]==num){
						flag	= true;
						break;
					}
				}
				if(!flag){
					array[len] = num;
					len++;
				}
			}else{
				array[i] = num; 
				len++;
			}
		}
		return array;
	}
	
	/**
	 * 获得一个随机数，begin 要比 end大 ，否则返回0
	 * @param begin 	随机区间的开始数字
	 * @param end		随机区间的结束数字
	 * @return
	 */
	public int getRondom(int begin, int end){
		if(end>begin){
			return begin+(int)Math.floor((Math.random()*(end-begin)));
		}else{
			return 0;
		}
	}
	
	public String getRandomNum(){
		int i	= random_num++%6;
		return random[i];
	}
}
