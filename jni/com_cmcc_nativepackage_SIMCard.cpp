#include <termios.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <pthread.h>

#include <jni.h>
#include <stddef.h>
#include <assert.h>
#include <string.h>
#include <stdio.h>
#include "simcard_jni.h"

bool command_block = false;

int fd;
COMMUNICATION_RESP_PACK ComRespPack;
COMMUNICATION_DATA_PACK ComDataPack;
//实际端口
char SimCardWriterDev[] = "/dev/ttySC0";
//本地测试端口
//char SimCardWriterDev[] = "/dev/ttyS2";
JavaVM* g_jvm = NULL;

//获取统一写卡组件的版本信息
JNIEXPORT jint JNICALL Java_com_bsdtec_nativepackage_SimCardJni_GetOPSVersion(
		JNIEnv *env, jobject obj, jcharArray version) {
	char char_version[] = "BosstunSimCardWriter_V1.0";
	int ver_len = strlen(char_version);
	jchar* buf = (jchar*) malloc(ver_len * sizeof(jchar) + 1);
	for (int i = 0; i < ver_len; i++) {
		buf[i] = char_version[i];
	}
	env->SetCharArrayRegion(version, 0, ver_len, reinterpret_cast<jchar*>(buf));
	free(buf);
	return SUCCESS_CODE;
}

//读取卡片空卡序列号
JNIEXPORT jint JNICALL Java_com_bsdtec_nativepackage_SimCardJni_GetCardSN(
		JNIEnv *env, jobject obj, jcharArray CardSN) {
	if(!set_block_command()){
		return COMMAND_BLOCK;
	}
	int ret;
	ret = initSerialPort(SimCardWriterDev, 115200, O_NONBLOCK);
	if (ret < 0) {
		ALOGE("Initialize serial port fail!");
		return release_block_command(FAIL);
	}
	char sendbuf[] = "GetCardSN"; //待组合的命令
	char retbuf[512]; //串口回传数据

	COMMUNICATION_RESP_PACK rcv_pack;

	COMMUNICATION_DATA_PACK data_pack = constructControlDataPack(CMD_GetCardSN,
			0, NULL);

	tcflush(fd, TCIOFLUSH);
	sleep(2);
	write(fd, &data_pack, data_pack.bLength + 2);

	ALOGD(
			"GetCardSN send Command: bCommandId=%d, bLength=%d, dataBuffer=%s", data_pack.bCommandId, data_pack.bLength, data_pack.dataBuffer);
	//读取数据的长度
	int nread = 0;

	uint8_t* prcv_pack = (uint8_t*)&rcv_pack;

	nread = NoneBlockRead(fd, prcv_pack, sizeof(rcv_pack));

	ALOGD("GetCardSN total read count:%d", nread);

	ALOGD("GetCardSN read com content: bResult=%d, bLength=%d", rcv_pack.bResult, rcv_pack.bLength);
	ALOGD("GetCardSN dataBuffer Content:");
	for(int i=0; i < nread-2; i++){
		ALOGD("%02X",rcv_pack.dataBuffer[i]);
	}

	ALOGD("GetCardSN dataBuffer End");

	if(nread < rcv_pack.bLength+2){
		ALOGD("GetCardSN data length error,read_total=%d, data_len=%d", nread, rcv_pack.bLength+2);
		return release_block_command(DATA_LEN_ERROR);
	}

	//固定20个字节
	jchar ret_buf[20];

	uint8_t* hex = (uint8_t*) rcv_pack.dataBuffer;

	int returnCode = rcv_pack.bLength==0?DATA_LEN_ZERO:rcv_pack.bResult;
	int dataLen = rcv_pack.bLength;
	ALOGD("GetCardSN returnCode:%d, dataLen:%d", returnCode, dataLen);

	if(dataLen > 0)
	{
		hexToAsc(ret_buf, hex, MIN(dataLen, 10));

		//若不满20字节，强制用F补齐
		for (int k = dataLen*2; k < 20; k++) {
			ret_buf[k] = 'F';
		}
		char char_CardInfo[20];
		for(int m=0; m<20; m++){
			char_CardInfo[m] = (char)ret_buf[m];
		}
		ALOGD("GetCardSN CardSN:%s", char_CardInfo);

		env->SetCharArrayRegion(CardSN, 0, 20,
				reinterpret_cast<jchar*>(ret_buf));
	}
	return release_block_command(returnCode);

}

//读取卡片信息，卡片信息包含卡片ICCID、卡片空卡序列号
JNIEXPORT jint JNICALL Java_com_bsdtec_nativepackage_SimCardJni_GetCardInfo(
		JNIEnv * env, jobject obj, jcharArray CardInfo) {
	if(!set_block_command()){
		return COMMAND_BLOCK;
	}
	int ret;
	ret = initSerialPort(SimCardWriterDev, 115200, O_NONBLOCK);
	if (ret < 0) {
		ALOGE("Initialize serial port fail!");
		return FAIL;
	}
	char retbuf[512]; //串口回传数据
	char commandName[] = "GetCardInfo"; //待组合的命令

	COMMUNICATION_RESP_PACK rcv_pack;

	COMMUNICATION_DATA_PACK data_pack = constructControlDataPack(
			CMD_GetCardInfo, 0, NULL);
	tcflush(fd, TCIOFLUSH);
	write(fd, &data_pack, data_pack.bLength + 2);

	ALOGD(
			"send Command %s: bCommandId=%d, bLength=%d, dataBuffer=%s", commandName, data_pack.bCommandId, data_pack.bLength, data_pack.dataBuffer);
	//读取数据的长度
	int nread = 0;

	uint8_t* prcv_pack = (uint8_t*)&rcv_pack;

	nread = NoneBlockRead(fd, prcv_pack, sizeof(rcv_pack));

	ALOGD("GetCardInfo total read count:%d", nread);

	ALOGD("GetCardInfo read com content: bResult=%d, bLength=%d", rcv_pack.bResult, rcv_pack.bLength);

	ALOGD("GetCardInfo dataBuffer Content:");
	for(int i=0; i < nread-2; i++){
		ALOGD("%02X",rcv_pack.dataBuffer[i]);
	}
	ALOGD("GetCardInfo dataBuffer End");

	if(nread < rcv_pack.bLength+2){
		ALOGD("GetCardInfo data length error,read_total=%d, data_len=%d", nread, rcv_pack.bLength+2);
		return release_block_command(DATA_LEN_ERROR);
	}

	uint8_t* hex = (uint8_t*) rcv_pack.dataBuffer;
	int returnCode = rcv_pack.bLength==0?DATA_LEN_ZERO:rcv_pack.bResult;
	int dataLen = rcv_pack.bLength;
	ALOGD("GetCardInfo returnCode:%d, dataLen:%d", returnCode, dataLen);
	if(dataLen > 0)
	{
		jchar ret_buf[100];
		hexToAsc(ret_buf, hex, dataLen);

		char char_CardInfo[100];
		for(int m=0; m<dataLen*2; m++){
			char_CardInfo[m] = (char)ret_buf[m];
		}
		char_CardInfo[dataLen*2] = '\0';

		ALOGD("GetCardInfo CardInfo:%s", char_CardInfo);

		env->SetCharArrayRegion(CardInfo, 0, dataLen*2,
				reinterpret_cast<jchar*>(ret_buf));
	}
	return release_block_command(returnCode);
}



//获取错误信息
JNIEXPORT jint JNICALL Java_com_bsdtec_nativepackage_SimCardJni_GetOPSErrorMsg(
		JNIEnv *env, jobject obj, int ErrorCode, jcharArray ErrorMsg) {
	char erro[2][20] = { "NoError", "Error Info" };
	char* erromsg = erro[ErrorCode % 2];
	int erro_len = strlen(erromsg);
	jchar* buf = (jchar*) malloc(erro_len * sizeof(jchar) + 1);
	for (int i = 0; i < erro_len; i++) {
		buf[i] = erromsg[i];
	}
	env->SetCharArrayRegion(ErrorMsg, 0, erro_len,
			reinterpret_cast<jchar*>(buf));
	free(buf);

	return SUCCESS_CODE;
}

//获取读卡器信息
JNIEXPORT jint JNICALL Java_com_bsdtec_nativepackage_SimCardJni_ConfigReader(
		JNIEnv *env, jobject obj, int ReaderType, jcharArray DeviceID,
		jcharArray Password) {
	ALOGD("ConfigReader in Navtive method");
	return SUCCESS_CODE;
}

//测试串口打开（打印机），与数据发送
JNIEXPORT jint JNICALL Java_com_bsdtec_nativepackage_SimCardJni_ComPortTest(
		JNIEnv *env, jobject obj, jstring content) {
	int ret;
	pthread_t pt;

	ret = initSerialPort(SimCardWriterDev, 115200, O_NONBLOCK);
	if (ret < 0) {
		ALOGE("Initialize serial port fail!");
		return FAIL;
	}
//开启监听线程
//    env->GetJavaVM(&g_jvm);
//    pthread_create(&pt, NULL, &StatusThread, (void*)0);

	char *printContent = (char *) env->GetStringUTFChars(content, NULL);
	int length = (int) env->GetStringUTFLength(content);

	ALOGD("String Content: %s, %d", printContent, length);

	if (fd > 0) {
		write(fd, printContent, length);
		print_newline();
	} else {
		ALOGE("printer device not open!");
		return FIND_PRINTER_FAIL;
	}

	return SUCCESS;
}

static JNINativeMethod gMethods[] = { { "GetOPSVersion", "([C)I",
		(void*) Java_com_bsdtec_nativepackage_SimCardJni_GetOPSVersion }, {
		"GetOPSErrorMsg", "(I[C)I",
		(void*) Java_com_bsdtec_nativepackage_SimCardJni_GetOPSErrorMsg }, {
		"GetCardSN", "([C)I",
		(void*) Java_com_bsdtec_nativepackage_SimCardJni_GetCardSN }, {
		"GetCardInfo", "([C)I",
		(void*) Java_com_bsdtec_nativepackage_SimCardJni_GetCardInfo }, {
		"WriteCard", "([C[C)I",
		(void*) Java_com_bsdtec_nativepackage_SimCardJni_WriteCard }, {
		"ConfigReader", "(I[C[C)I",
		(void*) Java_com_bsdtec_nativepackage_SimCardJni_ConfigReader },
//		{"ComPortTest", "(Ljava/lang/String;)I",(void*) Java_com_bsdtec_nativepackage_SimCardJni_ComPortTest },
};
static int registerNativeMethods(JNIEnv* env, const char* className,
		JNINativeMethod* gMethods, int numMethods) {
	jclass clazz;
	clazz = env->FindClass(className);
	if (clazz == NULL) {
		return JNI_FALSE;
	}
	if (env->RegisterNatives(clazz, gMethods, numMethods) < 0) {
		return JNI_FALSE;
	}

	return JNI_TRUE;
}


static int registerNatives(JNIEnv* env) {
	if (!registerNativeMethods(env, JNIREG_CLASS, gMethods,
			sizeof(gMethods) / sizeof(gMethods[0])))
		return JNI_FALSE;

	return JNI_TRUE;
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void* reserved) {
	JNIEnv* env = NULL;
	jint result = -1;

	if (vm->GetEnv((void**) &env, JNI_VERSION_1_4) != JNI_OK) {
		return -1;
	}
	assert(env != NULL);

	if (!registerNatives(env)) {
		return -1;
	}
	/* success -- return valid version number */
	result = JNI_VERSION_1_4;

	return result;
}


//实时写卡数据写入
JNIEXPORT jint JNICALL Java_com_bsdtec_nativepackage_SimCardJni_WriteCard(
		JNIEnv * env, jobject obj, jcharArray IssueData, jcharArray Result) {
	if(!set_block_command()){
		return COMMAND_BLOCK;
	}
	jchar* char_array = env->GetCharArrayElements(IssueData, NULL);
	size_t data_len = charTohex(char_array[0]) * 16 + charTohex(char_array[1]) + 1;
	data_len *= 2;
	jboolean* buf = (jboolean *) calloc(data_len+1, sizeof(jboolean));
	jboolean *pBack = buf;
	ALOGD("IssueData: length %d", data_len);
	//文本16进制 转 实际的16进制编码
	for (int i = 0; i < data_len; i = i + 2) {
		*pBack = (jboolean) charTohex(char_array[i]) * 16
				+ charTohex(char_array[i+1]);
		if(DEBUG){
			ALOGD("%02X", *pBack);
		}
		pBack++;
	}

	int ret;
	ret = initSerialPort(SimCardWriterDev, 115200, O_NONBLOCK);
	if (ret < 0) {
		ALOGE("Initialize serial port fail!");
		return release_block_command(FAIL);
	}
	char commandName[] = "WriteCard"; //待组合的命令
	char retbuf[512]; //串口回传数据

	COMMUNICATION_RESP_PACK rcv_pack;

	COMMUNICATION_DATA_PACK data_pack = constructControlDataPack(CMD_WriteCard,
			data_len/2, buf);

	if(DEBUG){
		ALOGD("send Command %s: bCommandId=%d, bLength=%d", commandName, data_pack.bCommandId, data_pack.bLength);
		//打印传输的写卡报文
		ALOGD("dataBuffer:");
		for (int j = 0; j < data_pack.bLength; j++) {
			ALOGD("%02X", data_pack.dataBuffer[j]);
		}
	}
	tcflush(fd, TCIOFLUSH);
	write(fd, &data_pack, data_pack.bLength + 2);

	uint8_t* prcv_pack = (uint8_t*)&rcv_pack;

	int nread = NoneBlockRead(fd, prcv_pack, sizeof(rcv_pack));

	ALOGD("WriteCard read com content: bResult=%d, bLength=%d", rcv_pack.bResult, rcv_pack.bLength);
	ALOGD("WriteCard dataBuffer Content:");
	for(int i=0; i < nread-2; i++){
		ALOGD("%02X",rcv_pack.dataBuffer[i]);
	}
	ALOGD("WriteCard dataBuffer End");

	int returnCode = rcv_pack.bLength==0?DATA_LEN_ZERO:rcv_pack.bResult;

	int dataLen = rcv_pack.bLength;

	if(nread < rcv_pack.bLength+2){
		ALOGD("WriteCard data length error,read_total=%d, data_len=%d", nread, rcv_pack.bLength+2);
		returnCode = DATA_LEN_ERROR;
	}

	ALOGD("WriteCard total read count:%d", nread);

	uint8_t* hex = (uint8_t*) rcv_pack.dataBuffer;

	ALOGD("WriteCard returnCode:%d, dataLen:%d", returnCode, dataLen);


	jchar ret_buf[100];

	hexToAsc(ret_buf, hex, dataLen);

	//一般处理
	if(returnCode != 0){
		jchar success_buf[100] = {'3','0'+(returnCode%10),'B','4','3','7','2','B','5','7'};
		memcpy(ret_buf, success_buf, 20);
		dataLen = 5;
		//returnCode = 0;
	}

	char char_CardInfo[100];
	for(int m=0; m<dataLen*2; m++){
		char_CardInfo[m] = (char)ret_buf[m];
	}

	ALOGD("WriteCard Result:%s", char_CardInfo);



	env->SetCharArrayRegion(Result, 0, dataLen*2,
			reinterpret_cast<jchar*>(ret_buf));

	return release_block_command(returnCode);

}

void com_read_write_test(){
	uint8_t data_buf[512];
	initSerialPort(SimCardWriterDev, 115200, O_NONBLOCK);
	int nread = 0;
	int count = 0;
	while(count<100){
		nread = NoneBlockRead(fd, data_buf, sizeof(data_buf));
		ALOGD("com_test read_count=%d, nread=%d", count, nread);
		write(fd, &data_buf, nread);
		count++;
	}
}

bool set_block_command(){
	if(command_block){
		return false;
	}else{
		command_block = true;
		return true;
	}
}

int release_block_command(int return_code){
	command_block = false;
	return return_code;
}


