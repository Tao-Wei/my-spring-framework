package com.tw.springframework.core;

import java.io.IOException;
import java.io.InputStream;

/**
 * 对配置文件的抽象定义，交由各个实现类实现
 */
public interface Resource {
    InputStream getInputStream() throws IOException;
}
