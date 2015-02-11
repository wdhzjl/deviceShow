//外部函数声明
#ifndef __SIMCARD_JNI_H__
#define __SIMCARD_JNI_H__


#include <sys/types.h>
#include <sys/stat.h>
#include <sys/select.h>

#include <termios.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <pthread.h>

#include <jni.h>
#include <stddef.h>
#include <assert.h>
#include <string.h>

#define SUCCESS_CODE 0

#define SUCCESS                 0
#define FAIL                    1
#define FIND_PRINTER_FAIL       2
#define READ_ERROR				3	//串口读取错误
#define DATA_LEN_ERROR			4	//数据长度错误
#define COMMAND_BLOCK			5	//互斥状态，不能执行
#define DATA_LEN_ZERO			6	//无数据
#include <android/log.h>

#define LOG_TAG "SimCard_JNI"
#define DEBUG 1
#if DEBUG
#define ALOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#else
#define ALOGD(...)	no_log_print()
#endif
#define ALOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

//命令码映射表
#define CMD_GetCardSN         		0 		//	int GetCardSN ([out] char * CardSN)
#define CMD_GetCardInfo        		1  		//	int GetCardInfo ([out] char * CardInfo)
#define CMD_WriteCard        		2		//	int WriteCard ( [in] char * IssueData，[out] char* Result)

//等待串口响应超时时间
#define COM_WAITE_TIMEOUT			10

#define JNIREG_CLASS "com/newland/nativepackage/SimCard"//指定要注册的类
//#define JNIREG_CLASS "com/boshidu/simcardwritertest/SimCard"
//#define JNIREG_CLASS "com/cmcc/nativepackage/SimCard"

#define MIN(A,B) ((A)<(B))?(A):(B)
#define MAX(A,B) ((A)>(B))?(A):(B)

speed_t getBaudrate(int baudrate);

int initSerialPort(char *path, int baudrate, int flags);

void* StatusThread(void* arg);

void print_newline();

void hexToAsc(jchar* asc, uint8_t* hex, int len);

int charTohex(char hex);

//通信数据包，用于缓存与下位机的通信数据
#define MAX_COM_DATA_PACK_SIZE 	512
//发送数据包（调用命令）
typedef struct
{
	uint8_t  bCommandId;      //命令码
	uint8_t  bLength;         //dataBuffer中有效数据长度
	uint8_t  dataBuffer[MAX_COM_DATA_PACK_SIZE];
}COMMUNICATION_DATA_PACK;

//接收数据包（调用返回结果）
typedef struct
{
	uint8_t  bResult;      //命令码
	uint8_t  bLength;         //dataBuffer中有效数据长度
	uint8_t  dataBuffer[MAX_COM_DATA_PACK_SIZE];
}COMMUNICATION_RESP_PACK;

//构造调用命令数据包
COMMUNICATION_DATA_PACK constructControlDataPack(uint8_t bCommandId, uint8_t dataLength, uint8_t data[]);

ssize_t	tread(int fd, uint8_t *buf, size_t nbytes, unsigned int timout);

ssize_t	treadn(int fd, uint8_t *buf, size_t nbytes, unsigned int timout);

ssize_t reliableRead(int fd, uint8_t *buf, size_t nbytes, unsigned int timout);

ssize_t NoneBlockRead(int fd, uint8_t *buf, size_t nbytes);

JNIEXPORT jint JNICALL Java_com_bsdtec_nativepackage_SimCardJni_WriteCard(
		JNIEnv * env, jobject obj, jcharArray IssueData, jcharArray Result);

void com_read_write_test();

void no_log_print();

bool set_block_command();

int release_block_command(int return_code);

#endif

