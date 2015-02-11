package com.newland.nativepackage;
/**
 * @author 作�? E-mail: WangJC
 * @version 创建时间�?013-10-12 上午11:11:25 <br>
 *          类说�?统一写卡组件
 *          <p>so库文件名:OPSLibReader.so
 */
public class SimCard {

	static {
		System.loadLibrary("CMCC_SIMCard_BOSSTUN_UA100");
	}

	/**
	 * <b><i>public static native int GetOPSVersion(char[] Version)</i></b>
	 * <p>
	 * 通过该函数获取统�?��卡组件的版本信息�?br>
	 * 
	 * @param Version
	 *            函数返回，统�?��卡组件版本信息�?
	 * @return <li>0：函数执行成�?<li>�?：函数执行失�?
	 */
	public static native int GetOPSVersion(char[] Version);

	/**
	 * <b><i>public static native int GetCardSN(char[] CardSN)</i></b>
	 * <p>
	 * 该函数用于读取卡片空卡序列号，该函数支持本标准发布前和发布后的所有现场写卡系统空卡�?�?0.2.3节函数GetCardInfo虽亦能读取空卡序列号
	 * ，但只支持本标准发布后生产的空卡。因此CRM客户端可通过调用GetCardSN判断是否为本标准发布后生产的空卡�?br>
	 * 
	 * @param CardSN
	 *            空卡序列号，如卡片符合中国移动�?SIM卡远程写卡业务规范�?v1.0.0版本，则长度�?6位，如卡片符合中国移动�?
	 *            现场写卡�?��规范》，则长度为20位�?
	 * @return <li>0：函数执行成�?<li>�?：函数执行失�?
	 */
	public static native int GetCardSN(char[] CardSN);

	/**
	 * <b><i>public static native int WriteCard(char[] IssueData, char[] Result)</i></b>
	 * <p>
	 * 该函数用于实时写卡数据写入�?函数返回值为0时表示统�?��卡组件向卡片发�?写卡数据成功并得到卡片响应�?写卡是否成功须根据Result判断�?br>
	 * 
	 * @param IssueData
	 *            现场写卡系统生成的写卡下行报文，如多条报文，用�?|”分隔�?
	 * @param Result
	 *            卡片返回结果，格式参见第8.3.3.2节中�?部分“返回数据格式�?说明�?
	 *            CRM向现场写卡系统回传写卡结果时须传带MAC值的完整结果�?
	 * @return <li>0：函数执行成�?<li>�?：函数执行失�?
	 */
	public static native int WriteCard(char[] IssueData, char[] Result);
	
	/**
	 * 通过该函数连接读卡器�?
	 * @param ReaderType
	 * @param DeviceID
	 * @param Password
	 * @return
	 */
	public static native int ConfigReader(int ReaderType, char[] DeviceID, char[] Password);
	
	/**
	 * 该函数用于获取错误信息，统一写卡组件将返回最近一次函数调用的错误信息�?
	 * @param ErrorCode
	 * @param ErrorMsg
	 * @return
	 */
	public static native int GetOPSErrorMsg(int ErrorCode, char[] ErrorMsg);
	
	/**
	 * 该函数用于读取卡片信息，卡片信息包含卡片ICCID、卡片空卡序列号.
	 * @param CardInfo
	 * @return
	 */
	public static native int GetCardInfo (char[] CardInfo);
	
	//public static native int ComPortTest (String Content);
}
