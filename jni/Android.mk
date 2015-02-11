LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := CMCC_SIMCard_BOSSTUN_UA100
LOCAL_SRC_FILES := SIMCard.cpp \
    com_cmcc_nativepackage_SIMCard.cpp
    
LOCAL_LDLIBS    := -lm -llog 

    
include $(BUILD_SHARED_LIBRARY)

