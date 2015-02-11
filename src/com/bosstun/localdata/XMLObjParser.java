package com.bosstun.localdata;

import java.io.InputStream;

public interface XMLObjParser<E> {
    /** 
     * 解析输入流 得到E对象 
     * @param is 
     * @return 
     * @throws Exception
     */
    public E parse(InputStream is) throws Exception;  
      
    /** 
     * 序列化E对象集合 得到XML形式的字符串 
     * @param books 
     * @return 
     * @throws Exception 
     */  
    public String serialize(E obj) throws Exception; 
}
